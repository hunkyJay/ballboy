package ballboy.model.observer;

import ballboy.model.Level;
import ballboy.view.GameWindow;
import ballboy.view.InformationDisplayer;
import javafx.scene.text.Text;
/**
 * This is a concrete observer to observe the total scores gained in a level, and display in the game window.
 */
public class LevelScoreObserver implements Observer, InformationDisplayer {
    private int levelScore;
    private Level level;
    private GameWindow window;

    public LevelScoreObserver(Level level, GameWindow window){
        this.levelScore = 0;
        this.level = level;
        this.window = window;
    }

    @Override
    public void update() {
        this.levelScore = level.getLevelScore();
        display(this.window);
    }

    @Override
    public void display(GameWindow gameWindow) {
//        GraphicsContext gc = window.getGc();
//        gc.clearRect(0, 20, window.getWidth(), 15);
//        gc.setFill(Paint.valueOf("BLACK"));
//        gc.fillText("Current Level Score: " + levelScore, 10, 30);
        Text text = (Text) gameWindow.getPane().getChildren().get(3);
        text.setText("Current Level Score: " + levelScore);
    }

}
