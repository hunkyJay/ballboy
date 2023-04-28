package ballboy.model;

import ballboy.model.memento.GameMemento;
import ballboy.model.observer.*;


import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the GameEngine interface.
 * This provides a common interface for the entire game.
 */
public class GameEngineImpl implements GameEngine {
    private Level level;//Current level
    private List<Level> levels;//All levels read from the config file
    private int currentLevelIndex;//The index of the current level
    private boolean isGameFinished;//Whether the game is finished
    private int totalScore;//The score gained from all levels
    private List<Observer> observers;

    //My constructor that applies for multiple levels
    public GameEngineImpl(List<Level> levels, int currentLevelIndex){
        this.levels = levels;
        this.level =levels.get(currentLevelIndex);
        this.currentLevelIndex = currentLevelIndex;
        isGameFinished = false;
        totalScore = 0;
        observers = new ArrayList<>();
    }

    //Initial constructor for single level mode
//    public GameEngineImpl(Level level) {
//        this.level = level;
//    }

    public Level getCurrentLevel() {
        return level;
    }

    public void startLevel() {
        // TODO: Handle when multiple levels has been implemented
        if(!isGameFinished){
            if(level.isLevelFinished()){
                currentLevelIndex ++;
            }
            if(currentLevelIndex >= levels.size()){
                isGameFinished = true;//Passed all levels, game is finished
            } else{
                level = levels.get(currentLevelIndex);//Go to the next level
                level.notifyObservers();//Update level scores
            }

            int scores = 0;
            for(Level l: levels){
                scores += l.getLevelScore();
            }
    totalScore = scores;
}

    }

public boolean boostHeight() {
        return level.boostHeight();
        }

public boolean dropHeight() {
        return level.dropHeight();
        }

public boolean moveLeft() {
        return level.moveLeft();
        }

public boolean moveRight() {
        return level.moveRight();
        }

public void tick() {
        level.update();
        startLevel();//Check and start level transitions
        }

    ///////
    @Override
    public boolean isGameFinished() {
        return isGameFinished;
    }

    @Override
    public int getTotalScore() {
        return totalScore;
    }

    @Override
    public GameMemento save() {
        return new GameMemento(levels, currentLevelIndex);
    }

    @Override
    public void load(GameMemento memento) {
        if(memento != null) {
            int i = 0;
            for (Level l : levels) {
                l.restoreLevel(memento.getEntityLists().get(i),
                        memento.getVelocityLists().get(i),
                        memento.getPositionLists().get(i),
                        memento.getLevelsFinished().get(i),
                        memento.getLevelScores().get(i),
                        memento.getRedScores().get(i),
                        memento.getGreenScores().get(i),
                        memento.getBlueScores().get(i));
                i++;
            }
            currentLevelIndex = memento.getCurrentLevelIndex();
            level = levels.get(currentLevelIndex);
        }
    }

    @Override
    public void attach(Observer o) {
        observers.add(o);
    }

    @Override
    public void detach(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for(Observer o: observers){
            o.update();
        }
    }
}