package com.example.christoph.pac_man_wear.controllers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import com.example.christoph.pac_man_wear.R;
import com.example.christoph.pac_man_wear.PacManActivity;
import com.example.christoph.pac_man_wear.controllers.ais.BlinkyAi;
import com.example.christoph.pac_man_wear.controllers.ais.ClydeAi;
import com.example.christoph.pac_man_wear.controllers.ais.InkyAi;
import com.example.christoph.pac_man_wear.controllers.ais.PinkyAi;
import com.example.christoph.pac_man_wear.models.animations.Animation;
import com.example.christoph.pac_man_wear.models.Direction;
import com.example.christoph.pac_man_wear.models.Ghost;
import com.example.christoph.pac_man_wear.models.Map;
import com.example.christoph.pac_man_wear.models.Player;
import com.example.christoph.pac_man_wear.models.animations.TextAnimation;
import com.example.christoph.pac_man_wear.utils.V;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;

/**
 * Created by Christoph on 15.03.2016.
 */
public class Game {
    private ArrayList<Ghost> ghosts = new ArrayList<Ghost>();
    private Player player;
    private Map map;
    private boolean isPlaying = false;
    private boolean isReady = false;
    private int level = 0;
    private String ghostMode;
    private long levelStartedTime;
    private long gameTime;

    /**
     * Defines, when scatter modes start and how long they
     * last. These values change slightly with the level.
     * After this cycle, the ghosts switch to a permanent
     * chase mode, which lasts until the level is finished.
     */
    private int[][] scatterModeTimes = {
            { 0, 7 },
            { 20, 7 },
            { 20, 5 },
            { 20, 5 }
    };

    private ArrayList<Animation> animations;

    private PacManActivity context;
    private Point displaySize;

    public String getGhostMode() {
        return ghostMode;
    }

    public void setGhostMode(String ghostMode) {
        this.ghostMode = ghostMode;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

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

        animations = new ArrayList<Animation>();
    }

    /**
     * Play the game.
     */
    public void play() {
        loadLevel();
    }

    private void loadLevel() {
        final Game game = this;

        // create a new map
        map = new Map(Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(context.getResources(), R.drawable.map), 399, 441, false), displaySize, game);

        // create ghosts
        Ghost blinky = new Ghost(Color.parseColor("#ff0000"), new V(8, 9), map);
        Ghost pinky = new Ghost(Color.parseColor("#ffb8ff"), new V(9, 9), map);
        Ghost inky = new Ghost(Color.parseColor("#00FFFF"), new V(10, 9), map);
        Ghost clyde = new Ghost(Color.parseColor("#ffb851"), new V(8, 9), map);

        level += 1;

        ghostMode = "scatter";

        // create a new, right-facing player
        player = new Player(Direction.RIGHT, new V(9, 15), map, game);

        // create ghost ais
        blinky.setAi(new BlinkyAi(blinky, map));
        pinky.setAi(new PinkyAi(blinky, map));
        inky.setAi(new InkyAi(blinky, map));
        clyde.setAi(new ClydeAi(blinky, map));

        // add ghosts
        ghosts.addAll(Arrays.asList(blinky, pinky, inky, clyde));

        // show get ready text
        animations.add(new TextAnimation("GET READY!", Color.WHITE, new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {

                animations.add(new TextAnimation("LEVEL " + level, Color.YELLOW, new Callable<Boolean>() {

                    @Override
                    public Boolean call() throws Exception {
                        game.levelStartedTime = gameTime;
                        game.isPlaying = true;
                        return true;
                    }
                }));

                return true;
            }
        }));

        isReady = true;
    }

    /**
     * Draw objects.
     * @param canvas The game canvas.
     * @return Whether the Game could be drawn.
     */
    public boolean draw(Canvas canvas, Paint paint) {
        if (!isReady) return false;

        Paint p = new Paint();
        p.setColor(Color.RED);
        canvas.drawText(ghostMode, 40, 40, p);

        map.draw(canvas, paint);

        // draw the player
        player.draw(canvas, paint);

        // draw all the ghosts
        for (Ghost ghost : ghosts) {
            ghost.draw(canvas, paint);
        }

        // draw animations
        for (Animation animation : animations) {
            animation.draw(canvas, map);
        }

        return true;
    }

     public void makeVincible() {
         for (Ghost ghost : ghosts) {
             if (!ghost.isFrightened() && !ghost.isEaten())
                ghost.setFrightened(true);
         }
     }

    /**
     * Update the game.
     * @param gameTime The time compensation.
     * @return Whether the game could be updated.
     */
    public boolean update(long gameTime) {
        this.gameTime = gameTime;

        // update animations
        for (Animation animation : animations) {
            if (!animation.update()) {

                // animation has finished
                animations.remove(animation);
            }
        }

        if (!isPlaying) return false;

        computeGhostMode(gameTime);

        // update the player
        player.update(gameTime);

        // update all the ghosts
        for (Ghost ghost : ghosts) {
            ghost.update(gameTime, player);
        }

        return true;
    }

    /**
     * The player lost the game.
     */
    public void gameOver() {

        // show game over text
        playAnimation(new TextAnimation("GAME OVER!", Color.RED, new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {

                // navigate to the home screen
                context.showHomeScreen();

                return true;
            }
        }));
    }

    /**
     * The level was completed by collecting all the dots.
     */
    public void completedLevel() {
        isPlaying = false;

        playAnimation(new TextAnimation("WELL DONE!", Color.YELLOW, new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {

                // load the next level
                loadLevel();

                return true;
            }
        }));
    }

    /**
     * Set the current ghost mode by the game time.
     * @param gameTime
     */
    private void computeGhostMode(long gameTime) {
        int gameTimeSeconds = (int) (gameTime / 1000);
        for (int i = 0; i < scatterModeTimes.length; i++) {

            // subtract the starting time
            gameTimeSeconds -= scatterModeTimes[i][0];

            // scatter mode has not yet started
            if (gameTimeSeconds < 0) {
                ghostMode = "chase";
                return;
            }

            // subtract the scatter mode duration
            gameTimeSeconds -= scatterModeTimes[i][1];

            // the ghosts are currently in scatter mode
            if (gameTimeSeconds < 0) {
                ghostMode = "scatter";
                return;
            }
        }

        // enter permanent chase mode
        ghostMode = "chase";
    }

    public void playAnimation(Animation animation) {
        animations.add(animation);
    }
}
