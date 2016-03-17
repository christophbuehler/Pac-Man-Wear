package com.example.christoph.pac_man_wear.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

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
        super(Direction.RIGHT, new V(1, 1), .1f, map);

        this.ai = ai;
        this.color = color;
        p = new Paint();
        p.setColor(color);

        ai.init(map, this);
    }

    public void draw(Canvas canvas, Paint paint) {
        Paint eyePaint = new Paint();
        Paint pupilPaint = new Paint();
        Path coatPath = new Path();
        V eyeOffset = null;
        V translatedPos = getMap().translatePosition(getPos());

        // draw the ghost
        canvas.drawCircle(translatedPos.getX(), translatedPos.getY(), 9, p);

        // coat
        coatPath.reset();
        coatPath.moveTo(translatedPos.getX() - 9, translatedPos.getY());

        coatPath.lineTo(translatedPos.getX() - 9, translatedPos.getY() + 12);
        coatPath.lineTo(translatedPos.getX() - 4, translatedPos.getY() + 9);
        coatPath.lineTo(translatedPos.getX() - 0, translatedPos.getY() + 12);
        coatPath.lineTo(translatedPos.getX() + 4, translatedPos.getY() + 9);
        coatPath.lineTo(translatedPos.getX() + 9, translatedPos.getY() + 12);

        coatPath.lineTo(translatedPos.getX() + 9, translatedPos.getY());
        coatPath.lineTo(translatedPos.getX() - 9, translatedPos.getY());

        canvas.drawPath(coatPath, p);

        // eyes
        eyePaint.setColor(Color.WHITE);
        pupilPaint.setColor(Color.BLACK);


        switch (getCurrentDir()) {
            case UP:
                eyeOffset = new V(0, -1);
                break;
            case RIGHT:
                eyeOffset = new V(1, 0);
                break;
            case DOWN:
                eyeOffset = new V(0, 1);
                break;
            case LEFT:
                eyeOffset = new V(-1, 0);
                break;
        }

        canvas.drawRoundRect(new RectF(translatedPos.getX() - 5, translatedPos.getY() - 6, translatedPos.getX() - 1, translatedPos.getY()), 6, 6, eyePaint);
        canvas.drawRoundRect(new RectF(translatedPos.getX() + 1, translatedPos.getY() - 6, translatedPos.getX() + 5, translatedPos.getY()), 6, 6, eyePaint);
        canvas.drawCircle(translatedPos.getX() + 3, translatedPos.getY() - 3, 3, eyePaint);
        canvas.drawCircle(translatedPos.getX() - 3 + eyeOffset.getX(), translatedPos.getY() - 3 + eyeOffset.getY(), 1, pupilPaint);
        canvas.drawCircle(translatedPos.getX() + 3 + eyeOffset.getX(), translatedPos.getY() - 3 + eyeOffset.getY(), 1, pupilPaint);
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
