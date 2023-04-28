package ballboy.model.observer;

import ballboy.model.GameEngine;
import ballboy.view.GameWindow;
import ballboy.view.InformationDisplayer;
import javafx.scene.text.Text;
/**
 * This is a concrete observer to observe the total scores gained in all levels, and display in the game window.
 */
public class TotalScoreObserver implements Observer, InformationDisplayer {
    private int totalScore;
    private GameEngine engine;
    private GameWindow window;

    public TotalScoreObserver(GameEngine engine, GameWindow window){
        this.engine = engine;
        this.window = window;
        this.totalScore = 0;
    }

    @Override
    public void update() {
        this.totalScore = engine.getTotalScore();
        display(this.window);
    }

    @Override
    public void display(GameWindow gameWindow) {
//        GraphicsContext gc = window.getGc();
//        gc.clearRect(0, 5, window.getWidth(), 10);
//        gc.setFill(Paint.valueOf("BLACK"));
//        gc.fillText("Total Score: " + totalScore, 10, 10);
        Text text = (Text) gameWindow.getPane().getChildren().get(2);
        text.setText("Total Score: " + totalScore);
    }
}
