package clientApp.data;

import clientApp.*;
import clientApp.abstractObjects.*;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Tank extends Transformable implements Destroyable, Drawable,Sendable {
    static final double TANK_SPEED = 1.0;
    static final double TANK_BASIC_HP = 100;
    private double hp;
    private Player player;
    private Position previousPosition;
    private int threadNum; //test

    public Tank(Tank tank){
        this.hp = tank.hp;
        this.position = tank.position;
        this.rotation = tank.rotation;
        this.player = tank.player;
        this.previousPosition = tank.previousPosition;
    }

    public Tank(Position position, Rotation rotation, Player player){
        this.hp = TANK_BASIC_HP;
        this.position = position;
        this.rotation = rotation;
        this.player = player;
        this.previousPosition = new Position(position);
    }

    public double getHp(){
        return hp;
    }

    public Player getPlayer(){
        return player;
    }

    public synchronized void hit(double dmg){
        hp -= dmg;
        if(hp <= 0)
            this.destroy();
    }

    @Override
    public synchronized void destroy() {
        player.remTank();
        Game.getGameManager().getTanks().remove(this);
    }

    @Override
    public void display() {
        //...
    }

    @Override
    public synchronized void move(double _x, double _y) {
        previousPosition.setPosition(position.getX(), position.getY());
        this.getPosition().move(_x, _y);
    }

    @Override
    public String toString(){
        String str;
        str = "Tank" + this.getIndex();
        return str;
    }
}
