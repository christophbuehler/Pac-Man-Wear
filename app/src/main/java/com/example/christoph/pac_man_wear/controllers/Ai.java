package com.example.christoph.pac_man_wear.controllers;

import com.example.christoph.pac_man_wear.models.Entity;
import com.example.christoph.pac_man_wear.models.Map;

/**
 * Created by Christoph on 15.03.2016.
 * The ai determines the desired direction of an entity.
 */
public interface Ai {
    public boolean update();
    public void init(Map map, Entity entity);
}