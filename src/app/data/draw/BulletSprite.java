package app.data.draw;

import app.abstractObjects.Sprite;
import app.SpriteManager;
import app.data.send.Bullet;
import app.data.send.Position;
import app.data.send.Rotation;
import app.data.send.Tank;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BulletSprite extends Bullet implements Sprite {
    private Image image;

    public BulletSprite(Bullet bullet){
        super(bullet);
        image = SpriteManager.bulletSprite;
    }

    public BulletSprite(Position position, Rotation rotation, Tank owner, int index) {
        super(position, rotation, owner, index);
        image = SpriteManager.bulletSprite;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, getPosition().getX(), getPosition().getY());
    }
}
