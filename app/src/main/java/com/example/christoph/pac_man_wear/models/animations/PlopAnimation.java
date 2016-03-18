package com.example.christoph.pac_man_wear.models.animations;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.christoph.pac_man_wear.models.Map;
import com.example.christoph.pac_man_wear.models.animations.Animation;
import com.example.christoph.pac_man_wear.utils.V;

import java.util.concurrent.Callable;

/**
 * Created by Christoph on 17.03.2016.
 */
public class PlopAnimation implements Animation {
    private float state = 0;
    private V pos;
    private Paint p;
    private Callable<Boolean> onDone;

    public PlopAnimation(V pos, Callable<Boolean> onDone) {
        this.pos = pos;
        this.onDone = onDone;

        p = new Paint();
        p.setColor(Color.parseColor("#FFFF33"));
        p.setStrokeWidth(2);
    }

    @Override
    public boolean update() {
        state += .05;

        if (state < 1) return true;

        try {
            onDone.call();
        } catch (Exception e) {

        }

        return false;
    }

    @Override
    public void draw(Canvas canvas, Map map) {
        V translatedPos = map.translatePosition(pos);

        canvas.drawLine(translatedPos.getX() - state * 8, translatedPos.getY() - state * 8, translatedPos.getX() - state * 2, translatedPos.getY() - state * 2, p);
        canvas.drawLine(translatedPos.getX() + state * 8, translatedPos.getY() - state * 8, translatedPos.getX() + state * 2, translatedPos.getY() - state * 2, p);
        canvas.drawLine(translatedPos.getX() + state * 8, translatedPos.getY() + state * 8, translatedPos.getX() + state * 2, translatedPos.getY() + state * 2, p);
        canvas.drawLine(translatedPos.getX() - state * 8, translatedPos.getY() + state * 8, translatedPos.getX() - state * 2, translatedPos.getY() + state * 2, p);
    }
}
