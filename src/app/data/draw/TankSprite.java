package app.data.draw;

import app.abstractObjects.Sprite;
import app.SpriteManager;
import app.data.send.Player;
import app.data.send.Position;
import app.data.send.Rotation;
import app.data.send.Tank;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class TankSprite extends Tank implements Sprite {
    private Image image;

    public TankSprite(Tank tank) {
        super(tank);
        image = SpriteManager.tankSprite;
    }

    public TankSprite(Position position, Rotation rotation, Player player, int index) {
        super(position, rotation, player, index);
        image = SpriteManager.tankSprite;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, getPosition().getX(), getPosition().getY());
    }
}
