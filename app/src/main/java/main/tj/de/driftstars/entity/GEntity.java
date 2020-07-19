package main.tj.de.driftstars.entity;

import android.graphics.Rect;

import main.tj.de.driftstars.world.World;

public abstract class GEntity {

    private float x, y, w, h;
    private float velocity;
    private String name;

    private World world;
    private GModel model;

    private Rect boundingBox;

    public GEntity(World world, float x, float y, float w, float h, String name) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.name = name;
        this.model = setModel();

        this.boundingBox = new Rect((int) x, (int) y, (int) (x + w), (int) (y + h));
    }

    public boolean collide(GEntity entity) {
        //Rect r1 = new Rect(0, 0, 0, 0);
        //if(this.x > entity.x && (this.x + this.w) < (entity.x + entity.w)) {
        //     System.out.println(entity.name + ": x collision");
        //    if(this.y > entity.y && (this.y + this.h) < (entity.y + entity.h)) {
        //        return true;
        //    }
        //}
        //return false;

        return this.boundingBox.intersect(entity.boundingBox);
    }

    public abstract GModel setModel();

    public GModel getModel() {
        return model;
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public void setX(float x) {
        this.x = x;
        this.boundingBox.set((int) x, (int) y, (int) (x + w), (int) (y + h));
    }

    public void setY(float y) {
        this.y = y;
        this.boundingBox.set((int) x, (int) y, (int) (x + w), (int) (y + h));
    }

    public int getWidth() {
        return (int) w;
    }

    public int getHeight() {
        return (int) h;
    }

    public float getVelocity() { return velocity; }

    public void setVelocity(float v) {
        velocity = v;
    }

    public Rect getBoundingBox() {
        return boundingBox;
    }

}
