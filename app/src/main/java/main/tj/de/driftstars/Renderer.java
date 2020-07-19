package main.tj.de.driftstars;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import main.tj.de.driftstars.entity.GEntity;

public class Renderer extends View {

    Paint textPaint;

    public Renderer(Context context) {
        super(context);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Render Loaded Entity models
        for(int i = 0; i < Game.getEngine().world.loadedEntityList.size(); i++) {
            GEntity entity = Game.getEngine().world.loadedEntityList.get(i);
            if(entity != null) {
                entity.getModel().render(entity, canvas, entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight());
            }
        }

        textPaint.setTextSize(80F);
        canvas.drawText(Game.getEngine().getLag() + " FPS", 10, 60, textPaint);
        //canvas.drawText(Game.getEngine().score + "", 10, 110, textPaint);
        canvas.drawText(Game.getEngine().getScore() + "kmh", 10, 160, textPaint);
        canvas.drawText(Game.getEngine().coins + " $", 10, 210, textPaint);
        canvas.drawText(Game.getEngine().crashes + " X", 10, 260, textPaint);
    }

}
