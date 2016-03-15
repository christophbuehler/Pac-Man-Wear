package com.example.christoph.pac_man_wear.controllers;

import android.graphics.Canvas;
import android.graphics.Color;

import com.example.christoph.pac_man_wear.models.Direction;
import com.example.christoph.pac_man_wear.models.Ghost;
import com.example.christoph.pac_man_wear.models.Player;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Christoph on 15.03.2016.
 */
public class Game {
    private ArrayList<Ghost> ghosts = new ArrayList<Ghost>();
    private Player player;
    private boolean isPlaying = false;

    public Game() {

    }

    /**
     * Play the game.
     */
    public void play() {

        // create a new, right facing player
        player = new Player(Direction.RIGHT);

        // create ghosts
        ghosts.addAll(Arrays.asList(
            new Ghost(new TestAi(), Color.RED),
            new Ghost(new TestAi(), Color.BLUE),
            new Ghost(new TestAi(), Color.GREEN),
            new Ghost(new TestAi(), Color.YELLOW)
        ));

        isPlaying = true;
    }

    /**
     * Draw objects.
     * @param canvas The game canvas.
     * @return Whether the Game could be drawn.
     */
    public boolean draw(Canvas canvas) {
        if (!isPlaying) return false;

        // draw the player
        player.draw(canvas);

        // draw all the ghosts
        for (Ghost ghost : ghosts) {
            ghost.draw(canvas);
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
}
