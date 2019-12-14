package app.data.draw;

import app.SpriteManager;
import app.abstractObjects.Drawable;
import app.abstractObjects.Sprite;
import app.data.send.GrassBlock;
import app.data.send.Position;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GrassBlockSprite extends GrassBlock implements Sprite, Drawable {
    private ImageView imageViev;
    private boolean isDisplay;

    public GrassBlockSprite(Position position, int index) {
        super(position, index);
        Image image = SpriteManager.blockSpriteGrass;
        imageViev = new ImageView();
        imageViev.setImage(image);
        isDisplay = false;
    }

    public GrassBlockSprite(GrassBlock block){
        super(block.getPosition(), block.getIndex());
        Image image = SpriteManager.blockSpriteGrass;
        imageViev = new ImageView();
        imageViev.setImage(image);
        isDisplay = false;
    }

    @Override
    public ImageView getImageViev() {
        return imageViev;
    }

    @Override
    public void display(Group group) {
        group.getChildren().add(imageViev);
        isDisplay = true;
    }

    @Override
    public void undisplay(Group group) {
        group.getChildren().remove(imageViev);
        isDisplay = true;
    }

    @Override
    public boolean isDisplay() {
        return isDisplay;
    }

    @Override
    public void render() {
        imageViev.setX(position.getX() - SpriteManager.BLOCK_OFFSET);
        imageViev.setY(position.getY() - SpriteManager.BLOCK_OFFSET);
    }
}
