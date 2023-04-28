package ballboy.model;

import ballboy.model.memento.GameMemento;
import ballboy.model.observer.Subject;

/**
 * The base interface for interacting with the Ballboy model
 */
public interface GameEngine extends Subject {
    /**
     * Return the currently loaded level
     *
     * @return The current level
     */
    Level getCurrentLevel();

    /**
     * Start the level
     */
    void startLevel();

    /**
     * Increases the bounce height of the current hero.
     *
     * @return boolean True if the bounce height of the hero was successfully boosted.
     */
    boolean boostHeight();

    /**
     * Reduces the bounce height of the current hero.
     *
     * @return boolean True if the bounce height of the hero was successfully dropped.
     */
    boolean dropHeight();

    /**
     * Applies a left movement to the current hero.
     *
     * @return True if the hero was successfully moved left.
     */
    boolean moveLeft();

    /**
     * Applies a right movement to the current hero.
     *
     * @return True if the hero was successfully moved right.
     */
    boolean moveRight();

    /**
     * Instruct the model to progress forward in time by one increment.
     */
    void tick();

    //////
    /**
     * Check whether the game is finished
     * @return True if the last level is passed and the whole game is finished.
     */
    boolean isGameFinished();
    /**
     * Get the number of score gained in all levels
     * @return int The number of total score
     */
    int getTotalScore();

    /**
     * Create a new gameMemento to save the current level state.
     * @return GameMemento The memento that stores current level state.
     */
    GameMemento save();

    /**
     * @param memento The gameMemento to load
     * Restore the states from a saved memento.
     */
    void load(GameMemento memento);
}
