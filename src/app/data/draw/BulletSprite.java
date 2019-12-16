package app.data.draw;

import app.Game;
import app.abstractObjects.Drawable;
import app.abstractObjects.Sprite;
import app.SpriteManager;
import app.data.send.Bullet;
import app.data.send.Position;
import app.data.send.Rotation;
import app.data.send.Tank;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BulletSprite extends Bullet implements Sprite, Drawable {
    private ImageView imageViev;
    private boolean isDisplay;

    public BulletSprite(Bullet bullet){
        super(bullet);
        Image image = SpriteManager.bulletSprite;
        imageViev = new ImageView();
        imageViev.setImage(image);
        isDisplay = false;
    }

    public BulletSprite(Position position, Rotation rotation, Tank owner, int index) {
        super(position, rotation, owner, index);
        Image image = SpriteManager.bulletSprite;
        imageViev = new ImageView();
        imageViev.setImage(image);
        isDisplay = false;
    }

    @Override
    public void display(Group group) {
        group.getChildren().add(imageViev);
        isDisplay = true;
    }

    @Override
    public void destroy() {
        Game.getGameManager().getBullets().remove(this);
    }

    @Override
    public void undisplay(Group group) {
        group.getChildren().remove(imageViev);
        isDisplay = false;
        this.destroy();
    }

    @Override
    public boolean isDisplay() {
        return isDisplay;
    }

    @Override
    public ImageView getImageViev() {
        return imageViev;
    }

    @Override
    public void render() {
        imageViev.setX(position.getX() - SpriteManager.BULLET_OFFSET);
        imageViev.setY(position.getY() - SpriteManager.BULLET_OFFSET);
        imageViev.setRotate(rotation.getRotation());

        //old
        //gc.drawImage(image, getPosition().getX(), getPosition().getY());
    }
}
