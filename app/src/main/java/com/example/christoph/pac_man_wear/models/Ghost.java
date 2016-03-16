package com.example.christoph.pac_man_wear.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.christoph.pac_man_wear.controllers.Ai;
import com.example.christoph.pac_man_wear.utils.V;

/**
 * Created by Christoph on 15.03.2016.
 */
public class Ghost extends Entity implements Drawable {
    private Ai ai;
    private int color;

    public Ghost(Ai ai, int color, Map map) {
        super(Direction.RIGHT, new V(20, 20), 2, map);

        this.ai = ai;
        this.color = color;

        ai.init(map, this);
    }

    public void draw(Canvas canvas, Paint paint) {
        V translatedPos = getMap().translatePosition(getPos());

        // draw the ghost
        canvas.drawCircle(translatedPos.getX(), translatedPos.getY(), 20, paint);
    }

    public void update(long delta) {
        // super.update();
    }
}
