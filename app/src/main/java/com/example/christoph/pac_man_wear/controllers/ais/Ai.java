package com.example.christoph.pac_man_wear.controllers.ais;

import com.example.christoph.pac_man_wear.models.Direction;
import com.example.christoph.pac_man_wear.models.Ghost;
import com.example.christoph.pac_man_wear.models.Map;
import com.example.christoph.pac_man_wear.utils.V;

/**
 * Created by Christoph on 17.03.2016.
 */
public abstract class Ai {
    private Ghost ghost;
    private Map map;
    private V lastTile = null;
    private String lastGhostMode;

    public Ghost getGhost() {
        return ghost;
    }

    public void setGhost(Ghost ghost) {
        this.ghost = ghost;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Ai(Ghost ghost, Map map) {
        this.ghost = ghost;
        this.map = map;
    }

    public boolean update() {
        V currentTile = ghost.getPos().round();
        String currentGhostMode = map.getGame().getGhostMode();
        if (lastTile != null && lastTile.isIdentical(currentTile)) return false;

        lastTile = currentTile;

        /**
         * The ghost can never reverse direction, except when
         * the ghost mode (scatter / chase) changes. In this
         * case, a reversal of the direction is necessary.
         */

        // the ghost mode has changed
        if (!currentGhostMode.equals(lastGhostMode)) {
            switch(ghost.getCurrentDir()) {
                case UP:
                    ghost.setDesiredDir(Direction.DOWN);
                    break;
                case RIGHT:
                    ghost.setDesiredDir(Direction.LEFT);
                    break;
                case DOWN:
                    ghost.setDesiredDir(Direction.UP);
                    break;
                case LEFT:
                    ghost.setDesiredDir(Direction.RIGHT);
                    break;
            }

            return true;
        }

        // move randomly
        switch((int) (Math.random() * 4)) {
            case 0:
                ghost.setDesiredDir(Direction.DOWN);
                break;
            case 1:
                ghost.setDesiredDir(Direction.LEFT);
                break;
            case 2:
                ghost.setDesiredDir(Direction.UP);
                break;
            case 3:
                ghost.setDesiredDir(Direction.RIGHT);
                break;
        }

        return true;
    }
}
