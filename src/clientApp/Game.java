package clientApp;

import clientApp.data.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.*;

public class Game {
    static final int SERVER_SOCKET_NUM = 8100;

    private static GameManager gameManager;
    private ExecutorService executorService;
    private Socket socket;
    private Player player;


    public Game(){
        gameManager = new GameManager();
    }

    public static GameManager getGameManager(){
        return gameManager;
    }

    class ClientDataTransmitterOut implements Runnable {
        private Socket socket;
        private Player player;
        private ObjectOutputStream outputStream;

        public ClientDataTransmitterOut(Socket socket, Player player) throws IOException {
            this.socket = socket;
            this.player = player;
            outputStream = new ObjectOutputStream(socket.getOutputStream());
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
            inputStream = new ObjectInputStream(socket.getInputStream());
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

    public void clientModeConnectToTheServer() throws IOException, ClassNotFoundException {
        executorService = Executors.newFixedThreadPool(2);

        //laczenie z serverem
        socket  = new Socket();
        Scanner consoleIn = new Scanner(System.in);
        String hostName;
        do{
            System.out.print("set host name, or 0 to exit: ");
            hostName = consoleIn.nextLine();
            if(hostName.matches("0")) {
                System.out.println("[I] server shutdown");
                return;
            }

            try {
                socket.connect(new InetSocketAddress(hostName, SERVER_SOCKET_NUM), 5000);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("[I] unable to connect to the server");
                continue;
            }
            break;
        } while(true);

        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        //ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

        //odebranie nadanego indeksu Gracza
        player = (Player) inputStream.readObject();
    }

    public void clientModeFirstCycle() throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        Object data;
        GameMessage message = new GameMessage("");

        //oczekiwanie na otrzymanie danych poczatkowych gry i odbiór tych danych
        do{

            data = inputStream.readObject();

            if(data instanceof Player)
                gameManager.getPlayers().add((Player)data);
            else if(data instanceof Tank)
                gameManager.getTanks().add(new TankSprite((Tank)data));
            else if(data instanceof Bullet)
                gameManager.getBullets().add(new BulletSprite((Bullet)data));
            else if(data instanceof Map)
                gameManager.setMap((Map)data);
            else if(data instanceof GameMessage)
                message = (GameMessage) data;
        }while(!message.getMessage().matches("DATA_END"));

        //potwierdzenie otrzymania danych poczatkowych i gotowosci do gry
        message.setMessage("READY");
        outputStream.writeObject(message);

        //oczekiwanie na sygnał startu gry od servera
        message = new GameMessage("");
        do{
            data = inputStream.readObject();
            if(data instanceof GameMessage)
                message = (GameMessage) data;
        }while(!message.getMessage().matches("START"));

        executorService.execute(new ClientDataTransmitterOut(socket, player));
        executorService.execute(new ClientDataTransmitterIn(socket, player));
    }

    public void clientModeCycle() throws IOException, ClassNotFoundException {
        Object data;
        GameMessage message;

        // aktualizacja danych
        ConcurrentLinkedQueue<GameMessage> messages = gameManager.getMessageQueueReceived();
        while(!messages.isEmpty()){
            message = messages.poll();
            //...dzialania w zaleznosci od odebranej wiadomosci
            //....
        }
    }
}
