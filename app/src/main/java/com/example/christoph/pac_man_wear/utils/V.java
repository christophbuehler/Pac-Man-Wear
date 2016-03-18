package com.example.christoph.pac_man_wear.utils;

import android.graphics.Point;

import com.example.christoph.pac_man_wear.models.Direction;

/**
 * Created by Christoph on 15.03.2016.
 * Provides basic 2D vector functions.
 */
public class V {
    private float x;
    private float y;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public V(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public V add(V v) {
        return new V(x + v.x, y + v.y);
    }

    public V subtract(V v) {
        return new V(x - v.x, y - v.y);
    }

    public V div(V v) {
        return new V(x / v.x, y / v.y);
    }

    public V dot(V v) {
        return new V(x * v.x, y * v.y);
    }

    public V moveLinear(Direction dir, float speed) {
        switch (dir) {
            case UP:
                return new V(x, y - speed);
            case RIGHT:
                return new V(x + speed, y);
            case DOWN:
                return new V(x, y + speed);
            case LEFT:
                return new V(x - speed, y);
        }
        return this;
    }

    public double dist(V v) {
        V delta = new V(x - v.getX(), y - v.getY());
        return Math.sqrt(delta.getX() * delta.getX() + delta.getY() * delta.getY());
    }

    public boolean isIdentical(V v) {
        return v.getX() == x && v.getY() == y;
    }

    public V clone() {
        return new V(x, y);
    }

    public V round() {
        return new V(Math.round(x), Math.round(y));
    }

    public Point toPoint() {
        return new Point((int) x, (int) y);
    }
}
