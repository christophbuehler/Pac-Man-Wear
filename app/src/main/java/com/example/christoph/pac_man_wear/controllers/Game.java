package com.example.christoph.pac_man_wear.controllers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import com.example.christoph.pac_man_wear.R;
import com.example.christoph.pac_man_wear.PacManActivity;
import com.example.christoph.pac_man_wear.models.Direction;
import com.example.christoph.pac_man_wear.models.Ghost;
import com.example.christoph.pac_man_wear.models.Map;
import com.example.christoph.pac_man_wear.models.Player;
import com.example.christoph.pac_man_wear.utils.V;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Christoph on 15.03.2016.
 */
public class Game {
    private ArrayList<Ghost> ghosts = new ArrayList<Ghost>();
    private Player player;
    private Map map;
    private boolean isPlaying = false;

    private PacManActivity context;
    private Point displaySize;

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game(PacManActivity context, Point displaySize) {
        this.context = context;
        this.displaySize = displaySize;
    }

    /**
     * Play the game.
     */
    public void play() {

        // create a new map
        map = new Map(Bitmap.createScaledBitmap(
              BitmapFactory.decodeResource(context.getResources(), R.drawable.map), 399, 441, false), displaySize);

        // create a new, right facing player
        player = new Player(Direction.RIGHT, new V(1, 1), map);

        // create ghosts
        ghosts.addAll(Arrays.asList(
            new Ghost(new TestAi(), Color.RED, map),
            new Ghost(new TestAi(), Color.BLUE, map),
            new Ghost(new TestAi(), Color.GREEN, map),
            new Ghost(new TestAi(), Color.YELLOW, map)
        ));

        isPlaying = true;
    }

    /**
     * Draw objects.
     * @param canvas The game canvas.
     * @return Whether the Game could be drawn.
     */
    public boolean draw(Canvas canvas, Paint paint) {
        if (!isPlaying) return false;

        map.draw(canvas, paint);

        // draw the player
        player.draw(canvas, paint);

        // draw all the ghosts
        for (Ghost ghost : ghosts) {
            ghost.draw(canvas, paint);
        }

        return true;
    }

    /**
     * Update the game.
     * @param delta The time compensation.
     * @return Whether the game could be updated.
     */
    public boolean update(long delta) {
        if (!isPlaying) return false;

        // update the player
        player.update(delta);

        // update all the ghosts
        for (Ghost ghost : ghosts) {
            ghost.update(delta);
        }

        return true;
    }

    public void handleTouch(V pos) {

    }
}
