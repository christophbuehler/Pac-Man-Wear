package com.example.christoph.pac_man_wear.models;

import android.graphics.Canvas;
import android.graphics.Color;

import com.example.christoph.pac_man_wear.controllers.Ai;

/**
 * Created by Christoph on 15.03.2016.
 */
public class Ghost implements Entity, Drawable {
    private Ai ai;
    private int color;

    public Ghost(Ai ai, int color) {
        this.ai = ai;
        this.color = color;
    }

    public void draw(Canvas canvas) {
        System.out.println("Drawing Ghost");
    }

    public void update(long delta) {
        System.out.println("Updating Ghost.");
    }
}
