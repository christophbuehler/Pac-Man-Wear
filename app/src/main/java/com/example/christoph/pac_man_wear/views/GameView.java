package com.example.christoph.pac_man_wear.views;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.view.Display;

import com.example.christoph.pac_man_wear.PacManActivity;
import com.example.christoph.pac_man_wear.controllers.Game;
import com.example.christoph.pac_man_wear.models.Direction;
import com.example.christoph.pac_man_wear.utils.V;

import java.util.Collections;

/**
 * Created by Christoph on 15.03.2016.
 */
public class GameView extends View {
    private Point displaySize;
    private PacManActivity context;
    private Game game;
    protected Paint paint;
    private Point touchEnterPoint;

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
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchEnterPoint = new Point((int) event.getX(), (int) event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                if (!game.isPlaying()) return false;

                game.getPlayer().setDesiredDir((Math.abs(event.getX() - touchEnterPoint.x) > Math.abs(event.getY() - touchEnterPoint.y)
                        ? (event.getX() > touchEnterPoint.x ? Direction.RIGHT : Direction.LEFT)
                        : (event.getY() > touchEnterPoint.y ? Direction.DOWN : Direction.UP)));
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        game.draw(canvas, paint);
    }
}
