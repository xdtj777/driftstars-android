package main.tj.de.driftstars.entity.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import main.tj.de.driftstars.entity.GEntity;
import main.tj.de.driftstars.entity.GModel;

public class ModelPlayer extends GModel {

    Paint color;

    public ModelPlayer() {
        color = new Paint();
        color.setColor(Color.RED);
    }

    @Override
    public void render(GEntity entity, Canvas c, int x, int y, int width, int height) {
        c.drawRect(x, y, x + width, y + height, color);
    }
}
