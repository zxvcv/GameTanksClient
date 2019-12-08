package clientApp.data;

import clientApp.abstractObjects.Movable;
import clientApp.abstractObjects.Sendable;

public class Position implements Movable, Sendable {
    private double x;
    private double y;

    public Position(double _x, double _y){
        x = _x;
        y = _y;
    }

    public Position(final Position _position){
        x = _position.x;
        y = _position.y;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public synchronized void setPosition(double x, double y){
        this.x=x;
        this.y=y;
    }

    @Override
    public synchronized void move(double _x, double _y){
        x += _x;
        y += _y;
    }
}
