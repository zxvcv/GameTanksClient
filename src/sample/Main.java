package sample;

import app.Game;
import app.GameManager;
import app.data.send.GameMessage;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private GameManager gameManager = Game.getGameManager();
    private int playerIndex;
    private Long lastNanoTime;

    @Override
    public void start(Stage theStage)
    {
        Game game = new Game();
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

        theScene.setOnKeyReleased(e -> {
                    String code = e.getCode().toString();
                    switch (code) {
                        case "s":
                            gameManager.getMessageQueueToSend().add(new GameMessage("SHOOT", playerIndex));
                            break;
                        case "a":
                            gameManager.getMessageQueueToSend().add(new GameMessage("SHOW", playerIndex));
                            break;
                        default:
                            break;
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
                //gameManager.display(gc);
            }
        }.start();

        theStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
