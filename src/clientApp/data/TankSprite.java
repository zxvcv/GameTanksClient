package clientApp.data;

import clientApp.abstractObjects.Sprite;
import clientApp.SpriteManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class TankSprite extends Tank implements Sprite {
    private Image image;

    public TankSprite(Tank tank) {
        super(tank);
        image = SpriteManager.tankSprite;
    }

    public TankSprite(Position position, Rotation rotation, Player player) {
        super(position, rotation, player);
        image = SpriteManager.tankSprite;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, getPosition().getX(), getPosition().getY());
    }
}
