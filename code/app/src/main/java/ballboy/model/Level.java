package ballboy.model;

import ballboy.model.entities.DynamicEntity;
import ballboy.model.entities.utilities.Vector2D;
import ballboy.model.memento.GameMemento;
import ballboy.model.observer.Subject;
import javafx.scene.paint.Color;

import java.util.List;

/**
 * The base interface for a Ballboy level.
 */
public interface Level extends Subject {

    /**
     * Return a List of the currently existing Entities.
     *
     * @return The list of current entities for this level
     */
    List<Entity> getEntities();

    /**
     * The height of the level
     *
     * @return The height (should be in the same format as Entity sizes)
     */
    double getLevelHeight();

    /**
     * The width of the level
     *
     * @return The width (should be in the same format as Entity sizes)
     */
    double getLevelWidth();

    /**
     * @return double The height of the hero.
     */
    double getHeroHeight();

    /**
     * @return double The width of the hero.
     */
    double getHeroWidth();

    /**
     * @return double The vertical position of the floor.
     */
    double getFloorHeight();

    /**
     * @return Color The current configured color of the floor.
     */
    Color getFloorColor();

    /**
     * @return double The current level gravity.
     */
    double getGravity();

    /**
     * Instruct the level to progress forward in time by one increment.
     */
    void update();

    /**
     * The current x position of the hero. This is useful for views so they can follow the hero.
     *
     * @return The hero x position (should be in the same format as Entity sizes)
     */
    double getHeroX();

    /**
     * The current y position of the hero. This is useful for views so they can follow the hero.
     *
     * @return The hero y position (should be in the same format as Entity sizes)
     */
    double getHeroY();

    /**
     * Increase the height the bouncing hero can reach. This could be the vertical acceleration of the hero, unless
     * the current level has special behaviour.
     *
     * @return true if successful
     */
    boolean boostHeight();

    /**
     * Reduce the height the bouncing hero can reach. This could be the vertical acceleration of the hero, unless the
     * current level has special behaviour.
     *
     * @return true if successful
     */
    boolean dropHeight();

    /**
     * Move the hero left or accelerate the hero left, depending on the current level's desired behaviour
     *
     * @return true if successful
     */
    boolean moveLeft();

    /**
     * Move the hero right or accelerate the hero right, depending on the current level's desired behaviour
     *
     * @return true if successful
     */
    boolean moveRight();

    /**
     * @param entity The entity to be checked.
     * @return boolean True if the provided entity is the current hero.
     */
    boolean isHero(Entity entity);

    /**
     * @param entity The entity to be checked.
     * @return boolean True if the provided entity is the finish of this level.
     */
    boolean isFinish(Entity entity);

    /*
     * Currently, this will just reset the hero to its starting position.
     */
    void resetHero();

    /**
     * Finishes the level.
     */
    void finish();

    /////////////
    /**
     * Check whether the level is finished.
     * @return boolean True if the level is finished.
     */
    boolean isLevelFinished();

//    /**
//     * @param isFinished The level finished state is true of false.
//     * Set a finished state for a level.
//     */
//    void setFinished(boolean isFinished);

    /**
     * @param entity The entity to be checked.
     * @return boolean True if the provided entity is the current squarecat.
     */
    boolean isSquareCat(Entity entity);

    /**
     * @param entity The entity to be removed.
     * remove the entity from the level.
     */
    void removeEntity(Entity entity);

    /**
     *  Get a list of dynamic entities in the level.
     * @return  The list of current dynamic entities for this level.
     */
    List<DynamicEntity> getDynamicEntities();

    /**
     * Get total scores in the current level.
     * @return int Number of scores.
     */
    int getLevelScore();

    /**
     * Get red scores in the current level.
     * @return int Number of red scores.
     */
    int getRedScore();

    /**
     * Get green scores in the current level.
     * @return int Number of green scores.
     */
    int getGreenScore();

    /**
     * Get blue scores in the current level.
     * @return int Number of blue scores.
     */
    int getBlueScore();

    /**
     * @param score The score to be set.
     * Set the number of total score for the current level.
     */
    void setLevelScore(int score);

    /**
     * @param score The score to be set.
     * Set the number of red score for the current level.
     */
    void setRedScore(int score);

    /**
     * @param score The score to be set.
     * Set the number of green score for the current level.
     */
    void setGreenScore(int score);

    /**
     * @param score The score to be set.
     * Set the number of blue score for the current level.
     */
    void setBlueScore(int score);

    /**
     * @param entities The entity list record to be loaded.
     * @param velocities The record of entities' velocities to be loaded.
     * @param positions The record of entities' positions to be loaded.
     * @param isFinished The level finishing state to be loaded.
     * @param levelScore The record of level score to be loaded.
     * @param redScore The record of level red score to be loaded.
     * @param greenScore The record of level green score to be loaded.
     * @param blueScore record of level blue score to be loaded.
     * Set floor color for the current level.
     */
    void restoreLevel(List<Entity> entities,
                      List<Vector2D> velocities,
                      List<Vector2D> positions,
                      boolean isFinished,
                      int levelScore,
                      int redScore,
                      int greenScore,
                      int blueScore );
}
