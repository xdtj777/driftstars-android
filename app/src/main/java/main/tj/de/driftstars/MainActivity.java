package main.tj.de.driftstars;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

/**
 * The class android will call when launching the app
 * Initializes the game engine and sets values from the system
 *
 * @author Tim
 */

public class MainActivity extends AppCompatActivity {

    private Game engine;

    //Called when the App launches
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set the Games width and height to the screen size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Game.gameWidth = displayMetrics.widthPixels;
        Game.gameHeight = displayMetrics.heightPixels;

        //Initializes Game engine and displays the engines renderer
        engine = new Game(this);
        setContentView(engine.renderer);
    }

    //Sets the Games touch state and coordinates
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int action = e.getActionMasked();

        Game.getEngine().touchX = e.getX();
        Game.getEngine().touchY = e.getY();

        /*
        Events are only fired the moment the screen is touched once.
        That's why there is a state for the display being touched in the game engine so the game can handle stuff while touched.

        Sets the boolean according to the event being down (touch) or up (release)
        */
        switch(action) {
            case MotionEvent.ACTION_DOWN:
                Game.getEngine().displayTouched = true;
                break;

            case MotionEvent.ACTION_UP:
                Game.getEngine().displayTouched = false;
                break;
        }

        return true;
    }
}
