package ballboy.model.observer;

import ballboy.model.Level;
import ballboy.view.GameWindow;
import ballboy.view.InformationDisplayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
/**
 * This is a concrete observer to observe the red scores gained in a level, and display in the game window.
 */
public class RedScoreObserver implements Observer, InformationDisplayer {
    private int redScore;
    private Level level;
    private GameWindow window;

    public RedScoreObserver(Level level, GameWindow window){
        this.redScore = 0;
        this.level = level;
        this.window = window;
    }

    @Override
    public void update() {
        redScore = level.getRedScore();
        display(this.window);
    }

    @Override
    public void display(GameWindow gameWindow) {
//        GraphicsContext gc = window.getGc();
//        gc.clearRect(10, 50, window.getWidth(), 15);
//        gc.setFill(Paint.valueOf("RED"));
//        gc.fillText("Current R: " + redScore, 10, 50);
        Text text = (Text) gameWindow.getPane().getChildren().get(4);
        text.setFill(Color.RED);
        text.setText("Current R: " + redScore);

    }

}
