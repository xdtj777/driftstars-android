package main.tj.de.driftstars;

import android.content.Context;
import android.graphics.Color;

import main.tj.de.driftstars.entity.EntityCar;
import main.tj.de.driftstars.entity.EntityCoin;
import main.tj.de.driftstars.entity.GEntity;
import main.tj.de.driftstars.player.Player;
import main.tj.de.driftstars.player.PlayerController;
import main.tj.de.driftstars.world.World;

/**
 * Main game engine
 * Starts the game and manages all the important classes
 * Every Tick and gameplay related is handled here
 *
 * @author Tim
 */

public class Game {

    private static Game instance;
    private static boolean running;

    public static int gameWidth, gameHeight;

    public World world;
    public Player player;
    public PlayerController playerController;

    public Renderer renderer;

    public boolean displayTouched;
    public float touchX, touchY;

    public float score;
    public int coins, crashes;

    public Game(Context context) {
        instance = this;

        createWorld();
        createPlayer();
        playerController = new PlayerController();

        renderer = new Renderer(context);
        renderer.setBackgroundColor(Color.BLACK);

        startGameHook();
    }

    /**
     * Initializes the world and sets the worlds with to the screen width
     */
    private void createWorld() {
        world = new World(gameWidth);
    }

    /**
     * Initializes the player and set spawn positions, velocity.
     * The player is then added to the worlds loaded entity list
     */
    private void createPlayer() {
        player = new Player(world, 0, 0, "Player");
        float spawnX = (gameWidth / 2F) - (player.getWidth() / 2F);
        float spawnY = gameHeight - player.getHeight() - (int)(gameHeight/8.0);
        player.setX(spawnX);
        player.setY(spawnY);
        player.setVelocity(16F);

        world.addEntity(player);
    }

    int ticks;
    private long lastticked;
    //tbh idk if this is even used lol
    static final float frameRate = 60F;

    private void startGameHook() {
        //Set the state for the android app
        running = true;

        /*
        Calls the onRender() method according to the frame rates delay
        This is what handles render stuff
        */
        Thread renderThread = new Thread() {
            @Override
            public void run() {
                while(running) {
                    onRender();

                    try {
                        Thread.sleep((int) (1000 / 60.0));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        /*
        Increases tick count and calls onTick() every 20 milliseconds
        This is what handles the games update methods and events
        */
        Thread gameThread = new Thread() {
            @Override
            public void run() {
                while (running) {
                    ticks++;
                    lastticked = System.currentTimeMillis();
                    onTick();

                    try {
                        Thread.sleep((int) (1000 / 60.0));
                    } catch (InterruptedException e) {
                        e.printStackTrace(); }
                    }
            }
        };

        gameThread.start();
        renderThread.start();
    }

    //Everything to render in here
    private void onRender() {
        renderer.invalidate();
    }

    //The number the player starts at
    int start = 40;
    long timertick = 0;
    
    //Everything to update in here
    private void onTick() {

        //Increase score
        if(score < start) {
            score += 0.7F - (score / start / 3.5F);
        } else {
            score += 0.01F + (Math.random() / 20F);
        }

        //Spawn Entities
        if(System.currentTimeMillis() - timertick > 4000) {
            timertick = System.currentTimeMillis();

            world.spawnStaticEntity(new EntityCoin(world, 0, 0, 100, 100, "coin"), 8);

            world.spawnCars(6);
        }

        //Update methods for all entities in world (loop tru worlds loaded entity list)
        for(int a = 0; a < world.loadedEntityList.size(); a++) {
            GEntity e = world.loadedEntityList.get(a);

            if(!(e instanceof Player)) {
                //Increases entities Y position according to its velocity
                if(e.getY() <= gameHeight) {
                    float vel = 1F;
                    /*
                    Velocity increases as score gets bigger
                    To make the entities seem faster
                    */
                    vel *= 1F + (score / 500F) * 4F;
                    
                    e.setY(e.getY() + vel * e.getVelocity());

                } else {
                    //Removes entity from the entity list if its y position is greater than the screen height
                    world.removeEntity(e);

                    break;
                }

                /*
                If the entity collides with the player it will be removed from the entity list
                The games values for "collected items" are being increased
                */
                if(e.collide(player)) {
                    world.removeEntity(e);

                    if(e instanceof EntityCoin) {
                        coins++;
                    }
                    if(e instanceof EntityCar) {
                        crashes++;
                    }

                    break;
                }
            }
        }

        //Player is being moved to left or right, wether the display is being touched on the left or right side of the screen
        float vel = 1F;
        if(displayTouched) {
            //Velocity is increased as the score gets bigger
            vel *= 1F + (score  / 800F) * 2F;

            if (touchX > gameWidth / 2F) {
                if((player.getX() + player.getWidth()) < gameWidth)
                    playerController.move(vel * player.getVelocity());
            } else {
                if(player.getX() > 0)
                    playerController.move(-vel * player.getVelocity());
            }
        }
    }

    //Returns the Score as an Integer
    public int getScore() {
        return Math.round(score);
    }

    //Returns the number of frames skipped
    public float getLag() {
        return frameRate - (float) (System.currentTimeMillis() - lastticked);
    }

    //Used to access this classes non static methods from other classes
    public static Game getEngine() {
        return instance;
    }

}
