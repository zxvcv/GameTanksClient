package clientApp.data;

import clientApp.abstractObjects.Block;
import clientApp.abstractObjects.Drawable;
import clientApp.abstractObjects.Indexable;
import clientApp.abstractObjects.Sendable;

import java.util.LinkedList;

public class Map extends Indexable implements Drawable, Sendable {
    public static final int MAP_SIZE = 25; //always square
    private Block[][] mapBlocks;

    public Map() {
        mapBlocks = new Block[MAP_SIZE][MAP_SIZE];
        //always the same map
    }

    public Block getBlock(int x, int y){
        return mapBlocks[x][y];
    }

    @Override
    public void display(){

    }
}
