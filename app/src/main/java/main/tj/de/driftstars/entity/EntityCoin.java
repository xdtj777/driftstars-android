package main.tj.de.driftstars.entity;

import main.tj.de.driftstars.entity.models.ModelCoin;
import main.tj.de.driftstars.world.World;

public class EntityCoin extends GEntity {

    public EntityCoin(World world, float x, float y, float w, float h, String name) {
        super(world, x, y, w, h, name);
    }

    @Override
    public GModel setModel() {
        return new ModelCoin();
    }

}
