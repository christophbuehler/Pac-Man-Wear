package com.example.christoph.pac_man_wear.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import com.example.christoph.pac_man_wear.controllers.ais.Ai;
import com.example.christoph.pac_man_wear.utils.V;

/**
 * Created by Christoph on 15.03.2016.
 */
public class Ghost extends Entity implements Drawable {
    private Ai ai;
    private int color;
    private boolean isFrightened = false;
    private boolean isEaten = false;
    private float rad = 9;
    private int frightenTimer = 0;
    private int frightenDuration = 400;

    public boolean isFrightened() {
        return isFrightened;
    }

    public void setFrightened(boolean isVincible) {
        if (isVincible) frightenTimer = frightenDuration;
        this.isFrightened = isVincible;
    }

    public void setAi(Ai ai) {
        this.ai = ai;
    }

    public boolean isEaten() {
        return isEaten;
    }

    public void setEaten(boolean isEaten) {
        this.isEaten = isEaten;
    }

    public Ghost(int color, V pos, Map map) {
        super(Direction.RIGHT, pos, .1f, map);

        this.color = color;
    }

    public void draw(Canvas canvas, Paint paint) {
        if (isEaten()) return;

        Paint eyePaint = new Paint();
        Paint pupilPaint = new Paint();
        Paint mainColor = new Paint();
        Paint mouthColor = new Paint();
        Path coatPath = new Path();
        V eyeOffset = null;
        V translatedPos = getMap().translatePosition(getPos());

        mainColor.setColor(isFrightened ? Color.BLUE : this.color);

        // draw the ghost
        canvas.drawCircle(translatedPos.getX(), translatedPos.getY(), rad, mainColor);

        // coat
        coatPath.reset();
        coatPath.moveTo(translatedPos.getX() - rad, translatedPos.getY());

        coatPath.lineTo(translatedPos.getX() - 9, translatedPos.getY() + 12);
        coatPath.lineTo(translatedPos.getX() - 4, translatedPos.getY() + 9);
        coatPath.lineTo(translatedPos.getX() - 0, translatedPos.getY() + 12);
        coatPath.lineTo(translatedPos.getX() + 4, translatedPos.getY() + 9);
        coatPath.lineTo(translatedPos.getX() + 9, translatedPos.getY() + 12);

        coatPath.lineTo(translatedPos.getX() + 9, translatedPos.getY());
        coatPath.lineTo(translatedPos.getX() - 9, translatedPos.getY());

        canvas.drawPath(coatPath, mainColor);

        if (isFrightened) {

            // scared mouth
            mouthColor.setStrokeWidth(1);
            mouthColor.setColor(Color.parseColor("#4488ff"));

            canvas.drawLine(translatedPos.getX() - 4, translatedPos.getY() + 6, translatedPos.getX() - 2, translatedPos.getY() + 4, mouthColor);
            canvas.drawLine(translatedPos.getX() - 2, translatedPos.getY() + 4, translatedPos.getX(), translatedPos.getY() + 6, mouthColor);
            canvas.drawLine(translatedPos.getX(), translatedPos.getY() + 6, translatedPos.getX() + 2, translatedPos.getY() + 4, mouthColor);
            canvas.drawLine(translatedPos.getX() + 2, translatedPos.getY() + 4, translatedPos.getX() + 4, translatedPos.getY() + 6, mouthColor);
        }

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

    public void update(long delta, Player player) {

        // the ghost is vincible
        if (frightenTimer > 0) {
            frightenTimer--;
        } else {
            isFrightened = false;
        }

        // let the ai compute the target direction
        ai.update();

        // move the ghost
        super.update();

        // player collided with this ghost
        if (player.getPos().dist(getPos()) * getMap().getTileSize() <= player.getRad() + rad) {

            if (isFrightened) {
                isEaten = true;
            } else {
                player.die();
            }
        }
    }
}
