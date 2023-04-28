package ballboy.view;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
/**
 * The concret class for informationDisplayer, displaying the winner page.
 */
public class GameVictoryDisplay implements InformationDisplayer {

    @Override
    public void display(GameWindow gameWindow) {
        ImageView winner = new ImageView("winner.png");
        winner.setFitHeight(gameWindow.getHeight());
        winner.setFitWidth(gameWindow.getWidth());
        //pane.getChildren().add(winner);
        gameWindow.getPane().getChildren().add(winner);
    }

}
