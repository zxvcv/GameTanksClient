package sample;

import app.Game;
import app.GameManager;
import app.data.send.GameMessage;
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
    private int playerIndex;
    private Long lastNanoTime;

    @Override
    public void start(Stage theStage)
    {
        Game game = new Game();
        gameManager = Game.getGameManager();
        try {
            game.connectToTheServer();
            game.setupCycle();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        theStage.setTitle( "Client App" );

        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );

        Canvas canvas = new Canvas( 512, 512 );
        root.getChildren().add( canvas );

        theScene.setOnKeyPressed(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();
                        switch (code) {
                            case "W":
                                gameManager.getMessageQueueToSend().add(new GameMessage("PRESSED W", playerIndex));
                                break;
                            case "A":
                                gameManager.getMessageQueueToSend().add(new GameMessage("PRESSED A", playerIndex));
                                break;
                            case "S":
                                gameManager.getMessageQueueToSend().add(new GameMessage("PRESSED S", playerIndex));
                                break;
                            case "D":
                                gameManager.getMessageQueueToSend().add(new GameMessage("PRESSED D", playerIndex));
                                break;
                            case "SPACE":
                                gameManager.getMessageQueueToSend().add(new GameMessage("PRESSED SPACE", playerIndex));
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
                        String code = e.getCode().toString();
                        switch (code) {
                            case "W":
                                gameManager.getMessageQueueToSend().add(new GameMessage("RELEASED W", playerIndex));
                                break;
                            case "A":
                                gameManager.getMessageQueueToSend().add(new GameMessage("RELEASED A", playerIndex));
                                break;
                            case "S":
                                gameManager.getMessageQueueToSend().add(new GameMessage("RELEASED S", playerIndex));
                                break;
                            case "D":
                                gameManager.getMessageQueueToSend().add(new GameMessage("RELEASED D", playerIndex));
                                break;
                            case "SPACE":
                                gameManager.getMessageQueueToSend().add(new GameMessage("RELEASED SPACE", playerIndex));
                                break;
                            default:
                                break;
                        }
                    }
                });

        GraphicsContext gc = canvas.getGraphicsContext2D();

        lastNanoTime = System.nanoTime();

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
                lastNanoTime = currentNanoTime;

                // render
                gc.clearRect(0, 0, 512,512);
                gameManager.display(gc);
            }
        }.start();

        theStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
