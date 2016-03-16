package com.example.christoph.pac_man_wear.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.example.christoph.pac_man_wear.controllers.Camera;
import com.example.christoph.pac_man_wear.utils.V;

/**
 * Created by Christoph on 15.03.2016.
 */
public class Player extends Entity implements Drawable, Camera {

    /**
     * Player constructor.
     * @param currentDir
     * @param pos
     * @param map
     */
    public Player(Direction currentDir, V pos, Map map) {
        super(currentDir, pos, .6f, map);

        // initialize map
        map.init(this);
    }

    public void draw(Canvas canvas, Paint paint) {

        Paint p = new Paint();
        p.setColor(Color.YELLOW);

        // the player is always at the center of the display
        canvas.drawCircle(getMap().getDisplaySize().x / 2, getMap().getDisplaySize().y / 2, 7, p);
    }

    public void update(long delta) {
        super.update();
    }
}
