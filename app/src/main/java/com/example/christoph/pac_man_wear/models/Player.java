package com.example.christoph.pac_man_wear.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.example.christoph.pac_man_wear.controllers.Camera;
import com.example.christoph.pac_man_wear.controllers.Game;
import com.example.christoph.pac_man_wear.models.animations.PlopAnimation;
import com.example.christoph.pac_man_wear.utils.V;

import java.util.concurrent.Callable;

/**
 * Created by Christoph on 15.03.2016.
 */
public class Player extends Entity implements Drawable, Camera {
    private Game game;
    private int points = 0;
    private float rad = 9;
    private boolean mouthOpen = true;
    private boolean isDead = false;

    public float getRad() {
        return rad;
    }

    public void setRad(float rad) {
        this.rad = rad;
    }

    /**
     * Player constructor.
     * @param currentDir
     * @param pos
     * @param map
     */
    public Player(Direction currentDir, V pos, Map map, Game game) {
        super(currentDir, pos, .2f, map);

        this.game = game;

        // initialize map
        map.init(this);
    }

    public void draw(Canvas canvas, Paint paint) {
        Paint mouthPaint = new Paint();
        Paint p = new Paint();
        Point center = new Point(getMap().getDisplaySize().x / 2, getMap().getDisplaySize().y / 2);
        Path mouthPath = new Path();

        // player is dead
        if (isDead) return;

        p.setColor(Color.parseColor("#FFFF33"));

        // the player is always at the center of the display
        canvas.drawCircle(center.x, center.y, rad, p);

        // mouth
        mouthPaint.setColor(Color.BLACK);
        mouthPath.reset();
        mouthPath.moveTo(center.x, center.y);

        switch (getCurrentDir()) {
            case UP:
                mouthPath.lineTo(center.x - 7, center.y - 9);
                mouthPath.lineTo(center.x + 7, center.y - 9);
                break;
            case RIGHT:
                mouthPath.lineTo(center.x + 9, center.y - 7);
                mouthPath.lineTo(center.x + 9, center.y + 7);
                break;
            case DOWN:
                mouthPath.lineTo(center.x - 7, center.y + 9);
                mouthPath.lineTo(center.x + 7, center.y + 9);
                break;
            case LEFT:
                mouthPath.lineTo(center.x - 9, center.y - 7);
                mouthPath.lineTo(center.x - 9, center.y + 7);
                break;
        }

        mouthPath.lineTo(center.x, center.y);

        if (mouthOpen)
            canvas.drawPath(mouthPath, mouthPaint);
    }

    public void addPoint() {
        points++;
    }

    public void die() {
        if (isDead) return;

        isDead = true;

        // play the plop animation
        game.playAnimation(new PlopAnimation(getPos().clone(), new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {

                game.gameOver();

                return true;
            }
        }));
    }

    public void update(long gameTime) {

        // player is dead
        if (isDead) return;

        super.update();

        mouthOpen = (((int) (gameTime / 100)) & 1) == 0;

        // try to eat
        getMap().eat(this);
    }
}
