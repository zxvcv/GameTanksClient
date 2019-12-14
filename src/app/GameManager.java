package app;

import app.data.draw.BulletSprite;
import app.data.draw.MapSprite;
import app.data.draw.TankSprite;
import app.data.send.*;

import java.util.concurrent.ConcurrentLinkedQueue;

public class GameManager{
    private ConcurrentLinkedQueue<GameMessage> messageQueueReceived;
    private ConcurrentLinkedQueue<GameMessage> messageQueueToSend;

    private ConcurrentLinkedQueue<Tank> tanks;
    private Map map;
    private ConcurrentLinkedQueue<Bullet> bullets;
    private ConcurrentLinkedQueue<Player> players;

    public GameManager(){
        tanks = new ConcurrentLinkedQueue<>();
        bullets = new ConcurrentLinkedQueue<>();
        players = new ConcurrentLinkedQueue<>();
        messageQueueReceived = new ConcurrentLinkedQueue<>();
        messageQueueToSend = new ConcurrentLinkedQueue<>();
    }

    public ConcurrentLinkedQueue<Tank> getTanks(){
        return tanks;
    }

    public Map getMap(){
        return map;
    }

    public void setMap(Map map){
        this.map = map;
    }

    public ConcurrentLinkedQueue<Bullet> getBullets(){
        return bullets;
    }

    public ConcurrentLinkedQueue<Player> getPlayers(){
        return players;
    }

    public ConcurrentLinkedQueue<GameMessage> getMessageQueueToSend() {
        return messageQueueToSend;
    }

    public ConcurrentLinkedQueue<GameMessage> getMessageQueueReceived() {
        return messageQueueReceived;
    }

    public void render(){
        for(Tank t : tanks)
            ((TankSprite) t).render();
        for(Bullet b : bullets)
            ((BulletSprite) b).render();
    }

    public Player getPlayerWithIndex(int index){
        for(Player p : players) {
            if (p.getIndex() == index)
                return p;
        }
        return null;
    }

    public Tank getTankWithIndex(int index){
        for(Tank t : tanks) {
            if (t.getIndex() == index)
                return t;
        }
        return null;
    }

    public Bullet getBulletWithIndex(int index){
        for(Bullet b : bullets) {
            if (b.getIndex() == index)
                return b;
        }
        return null;
    }
}
