package main.tj.de.driftstars.entity;

import main.tj.de.driftstars.entity.models.ModelCar;
import main.tj.de.driftstars.world.World;

public class EntityCar extends GEntity {

    public EntityCar(World world, float x, float y) {
        super(world, x, y, 250, 400, "car");
    }

    @Override
    public GModel setModel() {
        return new ModelCar();
    }

}
