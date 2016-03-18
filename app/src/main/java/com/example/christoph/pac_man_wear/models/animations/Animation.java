package com.example.christoph.pac_man_wear.models.animations;

import android.graphics.Canvas;

import com.example.christoph.pac_man_wear.models.Map;
import com.example.christoph.pac_man_wear.utils.V;

/**
 * Created by Christoph on 17.03.2016.
 */
public interface Animation {
    public boolean update();
    public void draw(Canvas canvas, Map map);
}
