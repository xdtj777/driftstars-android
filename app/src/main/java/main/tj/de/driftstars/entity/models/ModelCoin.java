package main.tj.de.driftstars.entity.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import main.tj.de.driftstars.entity.GEntity;
import main.tj.de.driftstars.entity.GModel;

public class ModelCoin extends GModel {

    Paint color;

    public ModelCoin() {
        color = new Paint();
        color.setColor(Color.YELLOW);
    }

    @Override
    public void render(GEntity entity, Canvas c, int x, int y, int width, int height) {
        c.drawRect(x, y, x + width, y + height, color);
    }
}
