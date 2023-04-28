package ballboy.model.observer;

import ballboy.model.Level;
import ballboy.view.GameWindow;
import ballboy.view.InformationDisplayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
/**
 * This is a concrete observer to observe the green scores gained in a level, and display in the game window.
 */
public class GreenScoreObserver implements Observer, InformationDisplayer {
    private int greenScore;
    private Level level;
    private GameWindow window;

    public GreenScoreObserver(Level level, GameWindow window){
        this.greenScore = 0;
        this.level = level;
        this.window = window;
    }

    @Override
    public void update() {
        greenScore = level.getGreenScore();
        display(this.window);
    }

    @Override
    public void display(GameWindow gameWindow) {
//        GraphicsContext gc = window.getGc();
//        gc.clearRect(10, 65, window.getWidth(), 15);
//        gc.setFill(Paint.valueOf("GREEN"));
//        gc.fillText("Current G: " + greenScore, 10, 65);
        Text text = (Text) gameWindow.getPane().getChildren().get(5);
        text.setFill(Color.GREEN);
        text.setText("Current G: " + greenScore);
    }

}
