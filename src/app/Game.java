package app;

import app.abstractObjects.Block;
import app.abstractObjects.Drawable;
import app.abstractObjects.Indexable;
import app.data.draw.*;
import app.data.send.*;
import javafx.scene.Group;
import sample.Main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.*;

public class Game {
    private static GameManager gameManager;
    private ExecutorService executorService;
    private Socket socket;
    private Group root;
    private static ObjectInputStream inputStream;
    private static ObjectOutputStream outputStream;
    private static Player player;
    private static Indexer indexer = new Indexer();
    private static LinkedList<Drawable> objectsToDisplay = new LinkedList<>();
    private static LinkedList<Drawable> objectsToUndisplay = new LinkedList<>();

    public Game(Group root){
        gameManager = new GameManager();
        this.root = root;
    }

    public static GameManager getGameManager(){
        return gameManager;
    }

    public static Player getPlayer() {
        return player;
    }

    public static Indexer getIndexer() {
        return indexer;
    }

    public static LinkedList<Drawable> getObjectsToDisplay() {
        return objectsToDisplay;
    }

    public static LinkedList<Drawable> getObjectsToUndisplay() {
        return objectsToUndisplay;
    }

    class ClientDataTransmitterOut implements Runnable {
        private Socket socket;
        private Player player;
        private ObjectOutputStream outputStream;

        public ClientDataTransmitterOut(Socket socket, Player player) {
            this.socket = socket;
            this.player = player;
            outputStream = Game.outputStream;
        }

        @Override
        public void run() {
            GameMessage message;

            while(true){
                try {
                    if(!gameManager.getMessageQueueToSend().isEmpty()){
                        message = gameManager.getMessageQueueToSend().poll();
                        outputStream.writeObject(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace(); return;
                }
            }
        }
    }

    class ClientDataTransmitterIn implements Runnable {
        private Socket socket;
        private Player player;
        private ObjectInputStream inputStream;

        public ClientDataTransmitterIn(Socket socket, Player player) throws IOException {
            this.socket = socket;
            this.player = player;
            inputStream = Game.inputStream;
        }

        @Override
        public void run() {
            Object data;
            GameMessage message;

            while(true){
                try {
                    data = inputStream.readObject();
                    message = (GameMessage) data;
                    gameManager.getMessageQueueReceived().add(message);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace(); return;
                }
            }
        }
    }

    class ClientCycleGame implements Runnable {
        private Socket socket;
        private Player player;

        public ClientCycleGame(Socket socket, Player player){
            this.socket = socket;
            this.player = player;
        }

        @Override
        public void run() {
            GameMessage message;
            GameMessageData messageData;

            //uruchomienie wątku obslugi czasu
            //executorService.execute(new TimeManager(timeLock));

            while(true){
                while(!gameManager.getMessageQueueReceived().isEmpty()){
                    message = gameManager.getMessageQueueReceived().poll();
                    //aktualizacja obiektów klienta
                    if(message instanceof GameMessageData){
                        messageData = (GameMessageData)message;
                        if(message.getMessage().startsWith("TANK")){
                            Tank tank = gameManager.getTankWithIndex(messageData.getObjectIndex());

                            tank.getPosition().setPosition(messageData.getPosition().getX(), messageData.getPosition().getY());
                            tank.getRotation().setRotation(messageData.getRotation().getRotation());
                        }
                        if(message.getMessage().startsWith("BULLET")){
                            BulletSprite bullet = (BulletSprite)gameManager.getBulletWithIndex(messageData.getObjectIndex());

                            //jezeli nie ma takiego pocisku to znaczy ze jest nowy i trzeba go stworzyc
                            if(bullet == null){
                                Player player = gameManager.getPlayerWithIndex(messageData.getPlayerIndex());
                                Tank owner = player.getTank();
                                bullet = new BulletSprite(messageData.getPosition(), messageData.getRotation(), owner, messageData.getObjectIndex());
                                gameManager.getBullets().add(bullet);
                                objectsToDisplay.add(bullet);
                            } else {
                                bullet.getPosition().setPosition(messageData.getPosition().getX(), messageData.getPosition().getY());
                                bullet.getRotation().setRotation(messageData.getRotation().getRotation());
                            }
                        }
                    }else if(message != null){
                        if(message.getMessage().startsWith("DESTROY")){
                            if(message.getMessage().endsWith("BULLET")){
                                BulletSprite bs = (BulletSprite)gameManager.getBulletWithIndex(message.getPlayerIndex());
                                objectsToUndisplay.add(bs);
                                //gameManager.getBullets().remove(bs);
                            }
                            if(message.getMessage().endsWith("TANK")){
                                TankSprite ts = (TankSprite)gameManager.getTankWithIndex(message.getPlayerIndex());
                                objectsToUndisplay.add(ts);
                                //gameManager.getTanks().remove(ts);
                            }
                        }
                    }else{
                        System.out.println("null message!");

                    }
                }
            }
        }
    }

    public void connectToTheServer() throws IOException, ClassNotFoundException {
        executorService = Executors.newFixedThreadPool(4);

        //laczenie z serverem
        socket  = new Socket();
        Scanner consoleIn = new Scanner(System.in);
        String[] host;
        do{
            System.out.print("set host name and socket after \":\" i.e.:(192.168.0.1:8100), or 0 to exit: ");
            host = consoleIn.nextLine().split(":");
            //hostName = "127.0.0.1";
            if(host[0].matches("0")) {
                System.out.println("[I] server shutdown");
                return;
            }

            try {
                socket.connect(new InetSocketAddress(host[0], Integer.parseInt(host[1])), 5000);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("[I] unable to connect to the server");
                continue;
            }
            break;
        } while(true);

        inputStream = new ObjectInputStream(socket.getInputStream());
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        //odebranie nadanego indeksu Gracza
        player = (Player) inputStream.readObject();
        System.out.println("THIS PLAYER INDEX: " + player.getIndex());
    }

    public void setupCycle() throws IOException, ClassNotFoundException {
        Object data;
        GameMessage message = new GameMessage("", 0);

        //oczekiwanie na otrzymanie danych poczatkowych gry i odbiór tych danych
        do{

            data = inputStream.readObject();

            if(data instanceof Player)
                gameManager.getPlayers().add((Player)data);
            else if(data instanceof Tank){
                TankSprite ts = new TankSprite((Tank)data);
                ts.display(root);
                gameManager.getTanks().add(ts);
            } else if(data instanceof Bullet){
                BulletSprite bs = new BulletSprite((Bullet)data);
                bs.display(root);
                gameManager.getBullets().add(bs);
            } else if(data instanceof Map) {
                Block[][] blocksIN = ((Map) data).getBlocks();
                int sizeX = blocksIN.length;
                int sizeY = blocksIN[0].length;
                Block[][] blocksOUT = new Block[sizeX][sizeY];

                for(int x = 0; x < sizeX; ++x){
                    for(int y = 0; y < sizeY; ++y){
                        if(blocksIN[x][y] instanceof StoneBlock){
                            blocksOUT[x][y] = new StoneBlockSprite((StoneBlock)blocksIN[x][y]);
                        }else if(blocksIN[x][y] instanceof GrassBlock){
                            blocksOUT[x][y] = new GrassBlockSprite((GrassBlock)blocksIN[x][y]);
                        }
                    }
                }

                MapSprite m = new MapSprite(blocksOUT, ((Map) data).getIndex());
                m.display(root);
                gameManager.setMap(m);
            } else if(data instanceof GameMessage)
                message = (GameMessage) data;
        }while(!message.getMessage().matches("DATA_END"));

        //potwierdzenie otrzymania danych poczatkowych i gotowosci do gry
        message.setMessage("READY");
        outputStream.writeObject(message);

        //oczekiwanie na sygnał startu gry od servera
        message = new GameMessage("", 0);
        do{
            data = inputStream.readObject();
            if(data instanceof GameMessage)
                message = (GameMessage) data;
        }while(!message.getMessage().matches("START"));

        executorService.execute(new ClientDataTransmitterOut(socket, player));
        executorService.execute(new ClientDataTransmitterIn(socket, player));
        executorService.execute(new ClientCycleGame(socket, player));
    }
}
