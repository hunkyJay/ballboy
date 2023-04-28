package ballboy.model.observer;

import ballboy.model.Level;
import ballboy.view.GameWindow;
import ballboy.view.InformationDisplayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * This is a concrete observer to observe the blue scores gained in a level, and display in the game window.
 */
public class BlueScoreObserver implements Observer, InformationDisplayer {
    private int blueScore;
    private Level level;
    private GameWindow window;


    public BlueScoreObserver(Level level, GameWindow window){
        this.blueScore = 0;
        this.level = level;
        this.window = window;

    }

    @Override
    public void update() {
        blueScore = level.getBlueScore();
        display(this.window);
    }

    @Override
    public void display(GameWindow gameWindow) {
//        GraphicsContext gc = window.getGc();
//        gc.clearRect(10, 80, window.getWidth(), 15);
//        gc.setFill(Paint.valueOf("BLUE"));
//        gc.fillText("Current B: " + blueScore, 10, 80);
        Text text = (Text) gameWindow.getPane().getChildren().get(6);
        text.setFill(Color.BLUE);
        text.setText("Current B: " + blueScore);
    }


}
