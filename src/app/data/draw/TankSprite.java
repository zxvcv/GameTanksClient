package app.data.draw;

import app.Game;
import app.abstractObjects.Drawable;
import app.abstractObjects.Sprite;
import app.SpriteManager;
import app.data.send.Player;
import app.data.send.Position;
import app.data.send.Rotation;
import app.data.send.Tank;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;

public class TankSprite extends Tank implements Sprite, Drawable {
    private ImageView imageViev;
    private boolean isDisplay;

    public TankSprite(Tank tank) {
        super(tank);
        Image image = SpriteManager.getTankSpriteColor(tank.getColor());
        imageViev = new ImageView();
        imageViev.setImage(image);
        isDisplay = false;
    }

    public TankSprite(Position position, Rotation rotation, Player player, String color, int index) {
        super(position, rotation, player, color, index);
        Image image = SpriteManager.getTankSpriteColor(color);
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
    public synchronized void destroy() {
        Game.getGameManager().getTanks().remove(this);
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
        imageViev.setX(position.getX() - SpriteManager.TANK_OFFSET);
        imageViev.setY(position.getY() - SpriteManager.TANK_OFFSET);
        imageViev.setRotate(rotation.getRotation());



        //old
        //gc.drawImage(image, getPosition().getX(), getPosition().getY());
    }
}
