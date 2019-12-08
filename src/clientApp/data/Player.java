package clientApp.data;

import clientApp.Game;
import clientApp.abstractObjects.GameObject;
import clientApp.abstractObjects.Indexable;
import clientApp.abstractObjects.Sendable;

public class Player extends Indexable implements Sendable {
    private Tank tank;
    private int points;
    private PlayerState playerState;
    private int threadNum; //test

    public enum PlayerState{
        ACTIVE,
        UNACTIVE,
        EXIT
    }

    public Player(){
        this.tank = null;
        this.points = 0;
        this.playerState = PlayerState.ACTIVE;
    }

    public synchronized void addPoints(int pt){
        points += pt;
    }

    public synchronized void subPoints(int pt){
        points -= pt;
    }

    public int getPoints(){
        return points;
    }

    public PlayerState getState() {
        return playerState;
    }

    public void setState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public synchronized void setTank(Tank tank){
        this.tank = tank;
    }

    public synchronized void remTank(){
        tank = null;
    }

    public Tank getTank(){
        return tank;
    }

    @Override
    public String toString(){
        String str;
        str = "Player" + this.getIndex();
        return str;
    }
}
