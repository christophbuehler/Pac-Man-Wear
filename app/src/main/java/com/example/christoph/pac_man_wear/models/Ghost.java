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
    private Paint p;

    public Ghost(Ai ai, int color, Map map) {
        super(Direction.RIGHT, new V(1, 1), .2f, map);

        this.ai = ai;
        this.color = color;
        p = new Paint();
        p.setColor(color);

        ai.init(map, this);
    }

    public void draw(Canvas canvas, Paint paint) {
        V translatedPos = getMap().translatePosition(getPos());

        // draw the ghost
        canvas.drawCircle(translatedPos.getX(), translatedPos.getY(), 6, p);
    }

    public void update(long delta) {
        if (super.update()) return;

        // the ghost is stuck
        switch ((int) (Math.random() * 4)) {
            case 0:
                setDesiredDir(Direction.UP);
                break;
            case 1:
                setDesiredDir(Direction.RIGHT);
                break;
            case 2:
                setDesiredDir(Direction.DOWN);
                break;
            case 3:
                setDesiredDir(Direction.LEFT);
                break;
        }
    }
}
