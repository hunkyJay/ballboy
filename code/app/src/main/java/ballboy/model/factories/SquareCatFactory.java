package ballboy.model.factories;

import ballboy.ConfigurationParseException;
import ballboy.model.Entity;
import ballboy.model.Level;
import ballboy.model.entities.DynamicEntityImpl;
import ballboy.model.entities.behaviour.SquareCatBehaviourStrategy;
import ballboy.model.entities.collision.PassiveCollisionStrategy;
import ballboy.model.entities.utilities.*;
import javafx.scene.image.Image;
import org.json.simple.JSONObject;

import java.util.Optional;

/*
 * Concrete entity factory for squarecat entities.
 */
public class SquareCatFactory implements EntityFactory {
    @Override
    public Entity createEntity(Level level, JSONObject config) {
        try {
            double startX = ((Number) config.get("startX")).doubleValue();
            double startY = ((Number) config.get("startY")).doubleValue();
            double speed = ((Number) config.get("speed")).doubleValue();
            double orbitEdgeLength = ((Number) config.get("orbitEdgeLength")).doubleValue();
            Optional<Double> height = Optional.ofNullable(((Number) config.get("height"))).map(Number::doubleValue);
            String imageName = (String) config.getOrDefault("image", "squarecat.png");

            Image image = new Image(imageName);

//            double height = ((Number) config.get("height")).doubleValue();
//            double width = ((Number) config.get("width")).doubleValue();

            Vector2D startingPosition = new Vector2D(startX, startY);

            KinematicState kinematicState = new KinematicStateImpl.KinematicStateBuilder()
                    .setPosition(startingPosition)
                    .build();

            AxisAlignedBoundingBox volume = new AxisAlignedBoundingBoxImpl(
                    startingPosition,
                    height.orElse(image.getHeight()),
                    height.map(h -> h * image.getWidth() / image.getHeight()).orElse(image.getWidth())
            );

            return new DynamicEntityImpl(
                    kinematicState,
                    volume,
                    Entity.Layer.EFFECT,
                    new Image(imageName),
                    new PassiveCollisionStrategy(),
                    new SquareCatBehaviourStrategy(level,orbitEdgeLength,speed)
            );

        } catch (Exception e) {
            throw new ConfigurationParseException(
                    String.format("Invalid squareccat entity configuration | %s | %s", config, e));
        }
    }
}
