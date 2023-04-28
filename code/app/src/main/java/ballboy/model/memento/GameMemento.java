package ballboy.model.memento;

import ballboy.model.Entity;
import ballboy.model.Level;
import ballboy.model.entities.DynamicEntity;
import ballboy.model.entities.utilities.Vector2D;

import java.util.ArrayList;
import java.util.List;

/**
 * This a memento class used to store the momentary states of all levels.
 */
public class GameMemento {
    private List<List<Entity>> entityLists = new ArrayList<>();
    private List<List<Vector2D>> velocityLists = new ArrayList<>();
    private List<List<Vector2D>> positionLists = new ArrayList<>();
    private List<Boolean> levelsFinished = new ArrayList<>();
    private List<Integer> levelScores = new ArrayList<>();
    private List<Integer> redScores = new ArrayList<>();
    private List<Integer> greenScores = new ArrayList<>();
    private List<Integer> blueScores = new ArrayList<>();
    private int currentLevelIndex;

    public GameMemento(List<Level> levels, int currentLevelIndex){
        this.currentLevelIndex = currentLevelIndex;
        for(Level level: levels){
            List<Entity> entityList = new ArrayList<>();
            entityList.addAll(level.getEntities());
            entityLists.add(entityList);

            List<Vector2D> velocityList = new ArrayList<>();
            List<Vector2D> positionList = new ArrayList<>();
            for(DynamicEntity d: level.getDynamicEntities()){
                velocityList.add(d.getVelocity());
                positionList.add(d.getPosition());
            }
            velocityLists.add(velocityList);
            positionLists.add(positionList);

            levelsFinished.add(level.isLevelFinished());
            levelScores.add(level.getLevelScore());
            redScores.add(level.getRedScore());
            greenScores.add(level.getGreenScore());
            blueScores.add(level.getBlueScore());
        }
    }

    public List<List<Entity>> getEntityLists() {
        return entityLists;
    }

    public List<List<Vector2D>> getVelocityLists() {
        return velocityLists;
    }

    public List<List<Vector2D>> getPositionLists() {
        return positionLists;
    }

    public List<Boolean> getLevelsFinished() {
        return levelsFinished;
    }

    public List<Integer> getLevelScores() {
        return levelScores;
    }

    public List<Integer> getRedScores() {
        return redScores;
    }

    public List<Integer> getGreenScores() {
        return greenScores;
    }

    public List<Integer> getBlueScores() {
        return blueScores;
    }

    public int getCurrentLevelIndex(){
        return currentLevelIndex;
    }
}
