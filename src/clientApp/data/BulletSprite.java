package clientApp.data;

import clientApp.abstractObjects.Sprite;
import clientApp.SpriteManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BulletSprite extends Bullet implements Sprite {
    private Image image;

    public BulletSprite(Bullet bullet){
        super(bullet.getPosition(), bullet.getRotation(), bullet.getOwner());
        image = SpriteManager.bulletSprite;
    }

    public BulletSprite(Position position, Rotation rotation, Tank owner) {
        super(position, rotation, owner);
        image = SpriteManager.bulletSprite;
    }

    @Override
    public void render(GraphicsContext gc) {

    }
}
