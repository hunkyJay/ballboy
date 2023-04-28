package ballboy.view;

/**
 * The base interface for components displaying information in game window.
 */
public interface InformationDisplayer {
    /**
     * @param gameWindow The game window shown to player.
     * Display some extra contents added in game window.
     */
    void display(GameWindow gameWindow);
}
