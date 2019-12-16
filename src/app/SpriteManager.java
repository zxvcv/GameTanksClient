package app;

import javafx.scene.image.Image;

public class SpriteManager {
    public static final Image tankSpriteR = new Image("file:src/sample/images/tank_R.png");
    public static final Image tankSpriteG = new Image("file:src/sample/images/tank_G.png");
    public static final Image tankSpriteB = new Image("file:src/sample/images/tank_B.png");
    public static final Image tankSpriteY = new Image("file:src/sample/images/tank_Y.png");

    public static final Image bulletSprite = new Image("file:src/sample/images/bullet.png");

    public static final Image blockSpriteGrass = new Image("file:src/sample/images/grass.jpg");
    public static final Image blockSpriteStone = new Image("file:src/sample/images/stone.jpg");

    public static final int BULLET_OFFSET = 25;
    public static final int TANK_OFFSET = 25;
    public static final int BLOCK_OFFSET = 25;

    public static Image getTankSpriteColor(String color){
        switch (color){
            case "R":
                return tankSpriteR;
            case "G":
                return tankSpriteG;
            case "B":
                return tankSpriteB;
            case "Y":
                return tankSpriteY;
            default:
                return null;
        }
    }
}
