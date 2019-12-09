package app;

import app.data.draw.BulletSprite;
import app.data.draw.TankSprite;
import app.data.send.*;
import javafx.scene.canvas.GraphicsContext;

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
        map = new Map();
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

    public void display(GraphicsContext gc){
        for(Tank t : tanks)
            ((TankSprite)t).render(gc);
        for(Bullet b : bullets)
            ((BulletSprite)b).render(gc);
    }
}
