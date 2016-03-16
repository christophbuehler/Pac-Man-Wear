package com.example.christoph.pac_man_wear.models;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.example.christoph.pac_man_wear.controllers.Camera;
import com.example.christoph.pac_man_wear.utils.V;

/**
 * Created by Christoph on 15.03.2016.
 */
public class Map {
    private Bitmap image;
    private Camera camera;
    private Boolean isInitialized = false;
    private Point displaySize; // viewport
    private Point bitmapSize; // size of the map bitmap
    private int tileSize = 21;
    private float cornerThreshold = 4;
    private int[][] collisionMap = {
        { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
        { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
        { 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1 },
        { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
        { 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1 },
        { 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1 },
        { 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1 },
        { 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0 },
        { 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1 },
        { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
        { 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1 },
        { 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0 },
        { 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1 },
        { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
        { 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1 },
        { 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1 },
        { 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1 },
        { 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1 },
        { 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1 },
        { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
        { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
    };

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public Point getDisplaySize() {
        return displaySize;
    }

    public void setDisplaySize(Point displaySize) {
        this.displaySize = displaySize;
    }

    public Map(Bitmap image, Point displaySize) {
        this.image = image;
        this.displaySize = displaySize;
        this.bitmapSize = new Point(image.getWidth(), image.getHeight());
    }

    public void init(Camera camera) {
        this.camera = camera;
        isInitialized = true;
    }

    public boolean draw(Canvas canvas, Paint paint) {
        V cameraPos = camera.getPos();
        if (!this.isInitialized) return false;

        canvas.drawBitmap(image,
                -cameraPos.getX() * tileSize + displaySize.x / 2 - tileSize / 2,
                -cameraPos.getY() * tileSize + displaySize.y / 2 - tileSize / 2, paint);

        return true;
    }

    /**
     * Get the position, relative to the camera viewport for drawing
     * purposes.
     * @return
     */
    public V translatePosition(V pos) {
        return pos.subtract(camera.getPos()).dot(new V(tileSize, tileSize))
                .add(new V(displaySize.x / 2, displaySize.y / 2));
    }

    /**
     * Validate and execute entity movement. Movement in the desired
     * direction may be delayed until the required conditions will be
     * given.
     * @param entity
     * @return
     */
    public boolean validateMovement(Entity entity) {
        V newPos;

        // try to set the current direction to the desired direction
        switchEntityDirection(entity.getDesiredDir(), entity);

        newPos = entity.getPos().moveLinear(entity.getCurrentDir(), entity.getSpeed());

        // the entity can move in the current direction
        if (!isColliding(entity.getCurrentDir(), newPos)) {
            entity.setPos(newPos);
            return true;
        }

        // the entity ran into a wall
        entity.setPos(entity.getPos().round());
        return false;
    }

    /**
     * Try to switch the direction of an entity.
     * @param dir the desired direction
     * @param entity the entity
     * @return whether the entity could be moved
     */
    private boolean switchEntityDirection(Direction dir, Entity entity) {

        if (dir == entity.getCurrentDir()) return false;

        // entity cannot move in this direction
        if (isColliding(dir, entity.getPos())) return false;

        entity.setCurrentDir(dir);

        return true;
    }

    /**
     * Checks whether an entity is colliding with the wall or not.
     * It is assumed, that an entity has the same width as a tile.
     * @param pos
     * @return
     */
    private boolean isColliding(Direction dir, V pos) {
       switch (dir) {
           case UP:
               return collisionMap[(int) pos.getY()][(int) pos.getX()] == 1;
           case RIGHT:
               return collisionMap[(int) pos.getY()][(int) pos.getX() + 1] == 1;
           case DOWN:
               return collisionMap[(int) pos.getY() + 1][(int) pos.getX()] == 1;
           case LEFT:
               return collisionMap[(int) pos.getY()][(int) pos.getX()] == 1;
       }
       return false;
    }
}