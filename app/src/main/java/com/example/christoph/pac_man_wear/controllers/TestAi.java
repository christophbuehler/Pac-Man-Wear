package com.example.christoph.pac_man_wear.controllers;

import com.example.christoph.pac_man_wear.models.Entity;
import com.example.christoph.pac_man_wear.models.Map;

/**
 * Created by Christoph on 15.03.2016.
 */
public class TestAi implements Ai {
    private Map map;
    private Entity entity;

    public TestAi() {

    }

    public void init(Map map, Entity entity) {
        this.map = map;
        this.entity = entity;
    }

    public boolean update() {

        return true;
    }
}
