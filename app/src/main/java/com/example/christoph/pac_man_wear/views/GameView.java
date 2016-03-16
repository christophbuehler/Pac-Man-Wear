package com.example.christoph.pac_man_wear.views;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;
import android.view.Display;

import com.example.christoph.pac_man_wear.PacManActivity;
import com.example.christoph.pac_man_wear.controllers.Game;

/**
 * Created by Christoph on 15.03.2016.
 */
public class GameView extends View {
    private Point displaySize;
    private PacManActivity context;
    private Game game;
    protected Paint paint;

    public GameView(PacManActivity context) {
        super(context);

        Display display = context.getWindowManager().getDefaultDisplay();

        // get the display size
        displaySize = new Point();
        display.getSize(displaySize);

        paint = new Paint();

        this.context = context;
        game = new Game(context, displaySize);

        game.play();
    }

    public void update(long delta) {
        this.game.update(delta);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.game.draw(canvas, paint);
    }
}
