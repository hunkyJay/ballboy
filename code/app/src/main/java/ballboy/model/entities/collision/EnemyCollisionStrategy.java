package ballboy.model.entities.collision;

import ballboy.model.Entity;
import ballboy.model.Level;

/**
 * Collision logic for enemies.
 */
public class EnemyCollisionStrategy implements CollisionStrategy {
    private final Level level;

    public EnemyCollisionStrategy(Level level) {
        this.level = level;
    }

    @Override
    public void collideWith(
            Entity enemy,
            Entity hitEntity) {
        if (level.isHero(hitEntity)) {
            level.resetHero();
        }
        if(level.isSquareCat(hitEntity)){
            level.removeEntity(enemy);

            if(enemy.getScore() == 1) {
                level.setRedScore(level.getRedScore() + enemy.getScore());
            }else if(enemy.getScore() == 2) {
                level.setGreenScore(level.getGreenScore() + enemy.getScore());
            }else if(enemy.getScore() == 3) {
                level.setBlueScore(level.getBlueScore() + enemy.getScore());
            }
            level.setLevelScore(level.getLevelScore() + enemy.getScore());
        }

        level.notifyObservers();
    }
}
