package clientApp.abstractObjects;

import clientApp.data.Bullet;
import clientApp.data.Map;
import clientApp.data.Tank;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public interface CollisionManager {
    LinkedList<Drawable> checkCollisions(Map map, ConcurrentLinkedQueue<Tank> tanks, ConcurrentLinkedQueue<Bullet> bullets);
}
