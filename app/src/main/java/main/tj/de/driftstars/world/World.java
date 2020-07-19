package main.tj.de.driftstars.world;

import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

import main.tj.de.driftstars.entity.EntityCar;
import main.tj.de.driftstars.entity.GEntity;
import main.tj.de.driftstars.player.Player;

/**
 * The world in which entities are being handled and listed.
 * There will be variations in map, texture and different road tracks here.
 *
 * @author Tim
 */

public class World {

    private int width;
    public List<GEntity> loadedEntityList = new ArrayList<>();

    public World(int gameWidth) {
        width = gameWidth;
    }

    /**
     * Adds the entity to the loaded entity list
     * @param entity Entity to add
     */
    public void addEntity(GEntity entity) {
        loadedEntityList.add(entity);
    }

    /**
     * Removes the entity from the loaded entity list
     * @param entity Entity to remove
     */
    public void removeEntity(GEntity entity) {
        this.loadedEntityList.remove(entity);
    }

    /**
     * Spawns a new entity with random X-position.
     * The new entity is added to the loaded entity list
     * @param entity Entity to spawn
     * @param iterations Number of entities being spawned
     */
    public void spawnStaticEntity(GEntity entity, int iterations) {
        GEntity e = entity;
        for(int i = 0; i < iterations; i++) {
            /*
            TODO: World: Add *WORKING* collision detection with other entities when spawning
            TODO: World: Add margin to the spawn position (2 entities cant spawn DIRECTLY next to each other)
            */
            /*
            Generates a random value for the x position.
            If x intersects with another entity in the loaded entity list
            the entity e wont be spawned.
             */
            //WHY THE FUCK IS THIS NOT WORKING???
            float x = getRandomX(e, 0);
            if(x == 0F) return;

            //Generates a random value for the y position
            float space = 10000F;
            float y = -e.getHeight() - (float)(Math.random() * space);

            //Set the values
            e.setX(x);
            e.setY(y);
            e.setVelocity(8F);

            //Add entity e to the loaded entity list
            addEntity(e);
        }
    }

    /**
     * Returns a calculated X-position in the worlds with bounds.
     * If the entity intersects with another entity of the loaded entity list this will return 0
     * @param entity Entity to calculate X-position for
     * @param margin Number of extra pixels outside the entities bounding box for collision detection
     * @return Calculated X-position for the entity in the worlds with bounds
     */
    private float getRandomX(GEntity entity, final int margin) {
        //Random number in between 0 and the worlds with subtracted by the entities width
        float x = (float) (Math.random() * (this.width - entity.getWidth()));

        //Iterate through the entity list
        for(int i = 0; i < loadedEntityList.size(); i++) {
            GEntity e = loadedEntityList.get(i);

            //If entity is not a Player -> Create a margin rect of the entities bounding box and return 0 if they intersect
            if(!(e instanceof Player)) {
                //TODO: Make this kek work IDK if the error is here or not but might be the spawn method
                Rect marginRect = new Rect(entity.getBoundingBox().left - margin, entity.getBoundingBox().top - margin, entity.getBoundingBox().right + margin, entity.getBoundingBox().bottom + margin);
                if(marginRect.intersect(e.getBoundingBox())) {
                    return 0F;
                }
            }
        }

        return x;
    }

    /*
    Same as spawnStaticEntity just for EntityCar

    This Method does NOT work because of 2 reasons:
    1. TODO: World: Stop spawning 3 cars next to each other (player obv cannot dodge these)
    2. TODO: World: Somehow something with the random x function is wrong so cars will spawn on top of each other
     */
    public void spawnCars(int amount) {
        //Feel free to replace this method... Clearly not working!

        int yf = 0;
        float flA = 0;
        float flB = 0;

        for(int i = 0; i < amount; i++) {
            EntityCar car = new EntityCar(this, 0, 0);

            //int wtfmult = ((int)(Math.random() * 3.0) - 1);
            //float x = ((width / 2F) - (car.getWidth() / 2F)) + (((int)(Math.random() * 3.0) - 1) * (car.getWidth() + 100));
            float x = getRandomXCar(car, 50);
            if(x == 0F) return;

            //if(i % 3 > 1) {
            //    yf += 2;
            //}

            float space = 10000F;
            float y = (-car.getHeight() * (1 + yf)) - (float) (Math.random() * space);

            if(i % 3 == 0) {
                flA = y;
            }
            if(i % 3 == 1) {
                flB = y;
            }
            if(i % 3 == 2) {
                //float headspace = car.getHeight() * 1.5F;
                if(!(y < flA - car.getHeight()) && !(y < flB - car.getHeight())) {
                    return;
                }
            }

            car.setX(x);
            car.setY(y);

            float vel = 8F;
            //vel = (float) (Math.random() * 4.0) + 8F;
            car.setVelocity(vel); //Replace 8F with something higher

            addEntity(car);
        }
    }

    /*
    Same as getRandomX but with only 3 possible x position to replicate 3 different road tracks
    This should work but I don't know for sure if any of these randomX methods ever return 0 xd (I don't think so... margin is also broken)
    If they do this works just fine
     */
    private float getRandomXCar(GEntity entity, final int margin) {
        float x = ((width / 2F) - (entity.getWidth() / 2F)) + (((int)(Math.random() * 3.0) - 1) * (entity.getWidth() + 100));
        for(int i = 0; i < loadedEntityList.size(); i++) {
            GEntity e = loadedEntityList.get(i);
            if(!(e instanceof Player)) {
                Rect marginRect = new Rect(entity.getBoundingBox().left - margin, entity.getBoundingBox().top - margin, entity.getBoundingBox().right + margin, entity.getBoundingBox().bottom + margin);
                if(marginRect.intersect(e.getBoundingBox())) {
                    return 0F;
                }
            }
        }
        return x;
    }
}
