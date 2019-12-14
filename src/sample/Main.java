package sample;

import app.Game;
import app.GameManager;
import app.abstractObjects.Drawable;
import app.data.send.GameMessage;
import app.data.send.Player;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private GameManager gameManager;
    private Long lastNanoTime;

    @Override
    public void start(Stage theStage)
    {
        theStage.setTitle( "Client App" );
        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );
        Canvas canvas = new Canvas( 512, 512 );
        root.getChildren().add( canvas );

        Game game = new Game(root);
        gameManager = Game.getGameManager();
        try {
            game.connectToTheServer();
            game.setupCycle();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        theScene.setOnKeyPressed(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        Player player = Game.getPlayer();
                        String code = e.getCode().toString();

                        String message = "PRESSED ";
                        switch (code) {
                            case "W": case "A": case "S": case "D":
                            case "SPACE":
                                if(!player.getKeysLog().getKey(code)){
                                    player.getKeysLog().setKeyState(code, true);
                                    message +=code;
                                    gameManager.getMessageQueueToSend().add(new GameMessage(message, player.getIndex()));
                                }
                                break;
                            default:
                                break;
                        }
                    }
                });

        theScene.setOnKeyReleased(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        Player player = Game.getPlayer();
                        String code = e.getCode().toString();

                        String message = "RELEASED ";
                        switch (code) {
                            case "W": case "A": case "S": case "D":
                            case "SPACE":
                                if(player.getKeysLog().getKey(code)){
                                    player.getKeysLog().setKeyState(code, false);
                                    message +=code;
                                    gameManager.getMessageQueueToSend().add(new GameMessage(message, player.getIndex()));
                                }
                                break;
                            default:
                                break;
                        }
                    }
                });

        //GraphicsContext gc = canvas.getGraphicsContext2D();


        lastNanoTime = System.nanoTime();

        new AnimationTimer()
        {
            long a;
            public void handle(long currentNanoTime)
            {
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
                lastNanoTime = currentNanoTime;

                // render
                //gc.clearRect(0, 0, 512,512);
                //gameManager.display(root);

                while(!Game.getObjectsToDisplay().isEmpty())
                    Game.getObjectsToDisplay().poll().display(root);

                while(!Game.getObjectsToUndisplay().isEmpty()){
                    Drawable d = Game.getObjectsToUndisplay().poll();
                    if(d != null)
                        d.undisplay(root);
                }
                gameManager.render();
            }
        }.start();

        theStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
