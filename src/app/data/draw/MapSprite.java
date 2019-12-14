package app.data.draw;

import app.SpriteManager;
import app.abstractObjects.Block;
import app.abstractObjects.Drawable;
import app.abstractObjects.Sprite;
import app.data.send.GrassBlock;
import app.data.send.Map;
import app.data.send.Position;
import app.data.send.StoneBlock;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MapSprite extends Map implements Sprite, Drawable {
    Group blocksGrp;
    private boolean isDisplay;

    public MapSprite(Block[][] blocks, int index) {
        super(blocks, index);
        blocksGrp = new Group();

        int sizeX = mapBlocks.length;
        int sizeY = mapBlocks[0].length;

        for(int x = 0; x < sizeX; ++x)
            for(int y = 0; y < sizeY; ++y)
                if(mapBlocks[x][y] instanceof Sprite){
                    ((Sprite) mapBlocks[x][y]).render();
                    blocksGrp.getChildren().add( ((Sprite) mapBlocks[x][y]).getImageViev() );
                }

    }

    @Override
    public void display(Group group) {
        group.getChildren().add(blocksGrp);
        blocksGrp.toBack();
        isDisplay = true;
    }

    @Override
    public void undisplay(Group group) {
        group.getChildren().remove(blocksGrp);
        isDisplay = true;
    }

    @Override
    public boolean isDisplay() {
        return isDisplay;
    }

    @Override
    public ImageView getImageViev() {
        return null;
    }

    @Override
    public void render() {
    }
}
