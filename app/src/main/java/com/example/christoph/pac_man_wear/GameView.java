package com.example.christoph.pac_man_wear;

import android.graphics.Canvas;
import android.graphics.Point;
import android.view.View;
import android.view.Display;
import com.example.christoph.pac_man_wear.controllers.Game;

/**
 * Created by Christoph on 15.03.2016.
 */
public class GameView extends View {
    private Point displaySize;
    private PacManActivity context;
    private Game game;

    public GameView(PacManActivity context) {
        super(context);

        Display display = context.getWindowManager().getDefaultDisplay();

        // get the display size
        displaySize = new Point();
        display.getSize(displaySize);

        this.context = context;

        this.game = new Game();
    }

    public void update(long delta) {
        this.game.update(delta);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.game.draw(canvas);
    }
}
