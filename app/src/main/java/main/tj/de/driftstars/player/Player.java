package main.tj.de.driftstars.player;

import main.tj.de.driftstars.entity.GEntity;
import main.tj.de.driftstars.entity.GModel;
import main.tj.de.driftstars.entity.models.ModelPlayer;
import main.tj.de.driftstars.world.World;

public class Player extends GEntity {

    float angle;

    public Player(World world, float x, float y, String name) {
        super(world, x, y, 250, 400, name);
    }

    @Override
    public GModel setModel() {
        return new ModelPlayer();
    }
    public float getAngle() {
        return angle;
    }

}
