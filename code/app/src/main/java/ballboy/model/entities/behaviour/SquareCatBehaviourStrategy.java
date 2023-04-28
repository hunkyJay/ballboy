package ballboy.model.entities.behaviour;

import ballboy.model.Level;
import ballboy.model.entities.DynamicEntity;
import ballboy.model.entities.utilities.Vector2D;

/**
 * An orbit behaviour strategy that makes the entity move in a square shape
 * around the hero. A constant velocity and a specific square orbit length
 * are also applied to the entity when orbiting.
 */
public class SquareCatBehaviourStrategy implements BehaviourStrategy {
    private final Level level;
    private double orbitEdgeLength;
    private double speed;
    private double runningDistance;
    private relevantPos catPos;

    //Four edges of the square orbit
    enum relevantPos{
        LEFTEDGE{public relevantPos nextEdge(){return  TOPEDGE;}},
        TOPEDGE{public relevantPos nextEdge(){return  RIGHTEDGE;}},
        RIGHTEDGE{public relevantPos nextEdge(){return  BUTTOMEDGE;}},
        BUTTOMEDGE{public relevantPos nextEdge(){return  LEFTEDGE;}};

        public abstract relevantPos nextEdge();
    }

    public SquareCatBehaviourStrategy(Level level, double orbitEdgeLength, double speed){
        this.level = level;
        this.orbitEdgeLength = orbitEdgeLength;
        this.speed = speed;
        this.runningDistance = 0;
        this.catPos = relevantPos.LEFTEDGE;
    }

    @Override
    public void behave(DynamicEntity entity, double frameDurationMilli) {
        this.runningDistance += speed * frameDurationMilli/1000;

        while(runningDistance > orbitEdgeLength){
            catPos = catPos.nextEdge();
            runningDistance -= orbitEdgeLength;
        }

        switch (catPos){
            case LEFTEDGE:
                entity.setPosition(new Vector2D(
                        level.getHeroX() - orbitEdgeLength/2,
                        level.getHeroY() + orbitEdgeLength/2 - runningDistance
                        ));
                break;
            case TOPEDGE:
                entity.setPosition(new Vector2D(
                        level.getHeroX() - orbitEdgeLength/2 + runningDistance,
                        level.getHeroY() - orbitEdgeLength/2
                ));
                break;
            case RIGHTEDGE:
                entity.setPosition(new Vector2D(
                        level.getHeroX() + orbitEdgeLength/2,
                        level.getHeroY() - orbitEdgeLength/2 + runningDistance
                ));
                break;
            case BUTTOMEDGE:
                entity.setPosition(new Vector2D(
                        level.getHeroX() + orbitEdgeLength/2 - runningDistance,
                        level.getHeroY() + orbitEdgeLength/2
                ));
                break;
        }

    }
}
