package clientApp.data;

import clientApp.*;
import clientApp.abstractObjects.*;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Bullet extends Transformable implements Destroyable, Drawable, Sendable {
    static final double BULLET_SPEED = 2.0;
    static final double BULLET_DMG = 10;
    private Tank owner;
    private int threadNum; //test

    Bullet(Position position, Rotation rotation, Tank owner){
        this.position = position;
        this.rotation = rotation;
        this.owner = owner;
    }

    public Tank getOwner(){
        return owner;
    }

    @Override
    public void destroy() {
        Game.getGameManager().getBullets().remove(this);
    }

    @Override
    public void display() {
        //...
    }

    @Override
    public String toString(){
        String str;
        str = "Bullet" + this.getIndex();
        return str;
    }
}
