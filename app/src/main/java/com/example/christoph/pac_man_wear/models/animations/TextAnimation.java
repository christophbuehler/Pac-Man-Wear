package com.example.christoph.pac_man_wear.models.animations;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.example.christoph.pac_man_wear.models.Map;
import com.example.christoph.pac_man_wear.utils.V;

import java.util.concurrent.Callable;

/**
 * Created by Christoph on 17.03.2016.
 */
public class TextAnimation implements Animation {
    private String text;
    private int color;
    private Callable<Boolean> onDone;
    private float state = 0;
    private Paint p;

    public TextAnimation(String text, int color, Callable<Boolean> onDone) {
        this.text = text;
        this.color = color;
        this.onDone = onDone;

        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(color);
        p.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        p.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    public boolean update() {
        state += .04;

        if (state < 1) return true;

        try {
            onDone.call();
        } catch (Exception e) {

        }

        return false;
    }

    @Override
    public void draw(Canvas canvas, Map map) {
        V center = new V(map.getDisplaySize().x / 2, map.getDisplaySize().y / 2);

        p.setTextSize(20 + (state + .1f) * 60);
        canvas.drawText(text, center.getX(), center.getY(), p);
    }
}
