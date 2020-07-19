package main.tj.de.driftstars.entity.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import main.tj.de.driftstars.entity.GEntity;
import main.tj.de.driftstars.entity.GModel;

public class ModelCar extends GModel {

    Paint color;

    public ModelCar() {
        color = new Paint();
        color.setColor(Color.BLUE);
    }

    @Override
    public void render(GEntity entity, Canvas c, int x, int y, int width, int height) {
        c.drawRect(x, y, x + width, y + height, color);
    }

}
