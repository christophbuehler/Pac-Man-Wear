package com.example.christoph.pac_man_wear.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.example.christoph.pac_man_wear.controllers.Camera;
import com.example.christoph.pac_man_wear.controllers.Game;
import com.example.christoph.pac_man_wear.utils.V;

/**
 * Created by Christoph on 15.03.2016.
 */
public class Player extends Entity implements Drawable, Camera {
    private Game game;
    private int points = 0;

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
        Paint pointsPaint = new Paint();
        Paint p = new Paint();
        p.setColor(Color.YELLOW);

        // the player is always at the center of the display
        canvas.drawCircle(getMap().getDisplaySize().x / 2, getMap().getDisplaySize().y / 2, 8, p);

        // display points
        pointsPaint.setColor(Color.YELLOW);
        pointsPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        pointsPaint.setTextSize(20);
        canvas.drawText(Integer.toString(points), 40, 40, pointsPaint);
    }

    public void update(long delta) {
        super.update();

        // try to eat
        if (getMap().eat(getPos())) points++;
    }
}
