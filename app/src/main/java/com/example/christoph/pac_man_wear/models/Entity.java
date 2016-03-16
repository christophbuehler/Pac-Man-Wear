package com.example.christoph.pac_man_wear.models;

import android.graphics.Point;

import com.example.christoph.pac_man_wear.utils.V;

/**
 * Created by Christoph on 15.03.2016.
 */
public abstract class Entity {
    private V pos;
    private Direction desiredDir; // The direction the entity wants to go.
    private Direction currentDir; // The direction the entity is facing.
    private float speed;
    private Map map;

    public V getPos() {
        return pos;
    }

    public void setPos(Point pos) {
        this.pos = new V(pos.x, pos.y);
    }

    public void setPos(V pos) {
        this.pos = pos;
    }

    public Direction getDesiredDir() {
        return desiredDir;
    }

    public void setDesiredDir(Direction desiredDir) {
        this.desiredDir = desiredDir;
    }

    public Direction getCurrentDir() {
        return currentDir;
    }

    public void setCurrentDir(Direction currentDir) {
        this.currentDir = currentDir;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Entity(Direction currentDir, V pos, float speed, Map map) {
        this.currentDir = currentDir;
        this.desiredDir = currentDir;
        this.pos = pos;
        this.speed = speed;
        this.map = map;
    }

    public boolean update() {
        return map.validateMovement(this);
    }
}
