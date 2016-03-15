package com.example.christoph.pac_man_wear.models;

import android.graphics.Canvas;
import android.graphics.Point;


/**
 * Created by Christoph on 15.03.2016.
 */
public class Player implements Entity, Drawable {
    private Point pos;
    private Direction dir; // The direction the player is facing.

    /**
     * Player constructor.
     * @param dir The initial direction.
     */
    public Player(Direction dir) {
        this.dir = dir;
    }

    public void draw(Canvas canvas) {
        System.out.println("Drawing Player");
    }

    public void update(long delta) {
        System.out.println("Updating Ghost.");
    }

    /**
     * Change the direction the player is facing.
     * @param dir The new direction.
     * @return Whether the direction had to be changed.
     */
    public Boolean changeDirection(Direction dir) {
        if (this.dir.equals(dir)) return false;
        this.dir = dir;
        return true;
    }
}
