package main.tj.de.driftstars.player;

import main.tj.de.driftstars.Game;

public class PlayerController {

    public void move(float velocity) {
        Game.getEngine().player.setX(Game.getEngine().player.getX() + velocity);
    }

    public void boost() {
       // Game.getEngine().player.setVelocity(1F);
    }

}
