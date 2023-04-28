package ballboy.model.entities;

import ballboy.model.Entity;
import ballboy.model.entities.behaviour.BehaviourStrategy;
import ballboy.model.entities.collision.CollisionStrategy;
import ballboy.model.entities.utilities.AxisAlignedBoundingBox;
import ballboy.model.entities.utilities.KinematicState;
import ballboy.model.entities.utilities.Vector2D;
import javafx.scene.image.Image;

/**
 * A dynamic entity instance depended on by Levels.
 */
public class DynamicEntityImpl extends DynamicEntity {
    private final CollisionStrategy collisionStrategy;
    private final BehaviourStrategy behaviourStrategy;
    private final AxisAlignedBoundingBox volume;
    private final Layer layer;
    private final Image image;
    private final KinematicState kinematicState;
    private final int score;//the score player get from a dynamic entity

    //none score required entity constructor
    public DynamicEntityImpl(
            KinematicState kinematicState,
            AxisAlignedBoundingBox volume,
            Layer layer,
            Image image,
            CollisionStrategy collisionStrategy,
            BehaviourStrategy behaviourStrategy
    ) {
        this.kinematicState = kinematicState;
        this.volume = volume;
        this.layer = layer;
        this.image = image;
        this.collisionStrategy = collisionStrategy;
        this.behaviourStrategy = behaviourStrategy;
        this.score = 0;
    }

    //score required constructor
    public DynamicEntityImpl(
            KinematicState kinematicState,
            AxisAlignedBoundingBox volume,
            Layer layer,
            Image image,
            CollisionStrategy collisionStrategy,
            BehaviourStrategy behaviourStrategy,
            int score
            ) {
        this.kinematicState = kinematicState;
        this.volume = volume;
        this.layer = layer;
        this.image = image;
        this.collisionStrategy = collisionStrategy;
        this.behaviourStrategy = behaviourStrategy;
        this.score = score;
    }



    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public Vector2D getPosition() {
        return kinematicState.getPosition();
    }

    @Override
    public void setPosition(Vector2D pos) {
        this.kinematicState.setPosition(pos);
    }

    @Override
    public Vector2D getPositionBeforeLastUpdate() {
        return this.kinematicState.getPreviousPosition();
    }

    @Override
    public Vector2D getVelocity() {
        return this.kinematicState.getVelocity();
    }

    @Override
    public void setVelocity(Vector2D vel) {
        this.kinematicState.setVelocity(vel);
    }

    @Override
    public double getHorizontalAcceleration() {
        return this.kinematicState.getHorizontalAcceleration();
    }

    @Override
    public void setHorizontalAcceleration(double horizontalAcceleration) {
        this.kinematicState.setHorizontalAcceleration(horizontalAcceleration);
    }

    @Override
    public double getHeight() {
        return volume.getHeight();
    }

    @Override
    public double getWidth() {
        return volume.getWidth();
    }

    @Override
    public Layer getLayer() {
        return this.layer;
    }

    @Override
    public boolean collidesWith(Entity entity) {
        return volume.collidesWith(entity.getVolume());
    }

    @Override
    public AxisAlignedBoundingBox getVolume() {
        return this.volume;
    }

    @Override
    public void collideWith(Entity entity) {
        collisionStrategy.collideWith(this, entity);
    }

    @Override
    public void update(
            double milliSeconds,
            double levelGravity) {
        kinematicState.update(milliSeconds, levelGravity);
        behaviourStrategy.behave(this, milliSeconds);
        this.volume.setTopLeft(this.kinematicState.getPosition());
    }

    @Override
    public int getScore() {
        return score;
    }
}
