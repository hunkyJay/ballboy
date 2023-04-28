package ballboy.model.levels;

import ballboy.ConfigurationParseException;
import ballboy.model.Entity;
import ballboy.model.Level;
import ballboy.model.entities.ControllableDynamicEntity;
import ballboy.model.entities.DynamicEntity;
import ballboy.model.entities.StaticEntity;
import ballboy.model.entities.utilities.Vector2D;
import ballboy.model.factories.EntityFactory;
import ballboy.model.observer.Observer;
import javafx.scene.paint.Color;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * Level logic, with abstract factor methods.
 */
public class LevelImpl implements Level {

    private final List<Entity> entities = new ArrayList<>();
    private final PhysicsEngine engine;
    private final EntityFactory entityFactory;
    private ControllableDynamicEntity<DynamicEntity> hero;
    private Entity finish;
    private Entity squareCat;//
    private double levelHeight;
    private double levelWidth;
    private double levelGravity;
    private double floorHeight;
    private Color floorColor;
    private boolean levelFinished;//
    private int levelScore;//
    private int redScore;//
    private int greenScore;//
    private int blueScore;//
    private List<Observer> observers;//


    private final double frameDurationMilli;

    /**
     * A callback queue for post-update jobs. This is specifically useful for scheduling jobs mid-update
     * that require the level to be in a valid state.
     */
    private final Queue<Runnable> afterUpdateJobQueue = new ArrayDeque<>();

    //Read json config constructor
    public LevelImpl(
            JSONObject levelConfiguration,
            PhysicsEngine engine,
            EntityFactory entityFactory,
            double frameDurationMilli) {
        this.engine = engine;
        this.entityFactory = entityFactory;
        this.frameDurationMilli = frameDurationMilli;
        initLevel(levelConfiguration);
    }

    /**
     * Instantiates a level from the level configuration.
     *
     * @param levelConfiguration The configuration for the level.
     */
    private void initLevel(JSONObject levelConfiguration) {
        this.levelWidth = ((Number) levelConfiguration.get("levelWidth")).doubleValue();
        this.levelHeight = ((Number) levelConfiguration.get("levelHeight")).doubleValue();
        this.levelGravity = ((Number) levelConfiguration.get("levelGravity")).doubleValue();

        JSONObject floorJson = (JSONObject) levelConfiguration.get("floor");
        this.floorHeight = ((Number) floorJson.get("height")).doubleValue();
        String floorColorWeb = (String) floorJson.get("color");
        this.floorColor = Color.web(floorColorWeb);

        JSONArray generalEntities = (JSONArray) levelConfiguration.get("genericEntities");
        for (Object o : generalEntities) {
            this.entities.add(entityFactory.createEntity(this, (JSONObject) o));
        }

        JSONObject heroConfig = (JSONObject) levelConfiguration.get("hero");
        double maxVelX = ((Number) levelConfiguration.get("maxHeroVelocityX")).doubleValue();

        Object hero = entityFactory.createEntity(this, heroConfig);
        if (!(hero instanceof DynamicEntity)) {
            throw new ConfigurationParseException("hero must be a dynamic entity");
        }
        DynamicEntity dynamicHero = (DynamicEntity) hero;
        Vector2D heroStartingPosition = dynamicHero.getPosition();
        this.hero = new ControllableDynamicEntity<>(dynamicHero, heroStartingPosition, maxVelX, floorHeight,
                levelGravity);
        this.entities.add(this.hero);

        JSONObject finishConfig = (JSONObject) levelConfiguration.get("finish");
        this.finish = entityFactory.createEntity(this, finishConfig);
        this.entities.add(finish);

        //Add squarecat to the level
        JSONObject catConfig = (JSONObject) levelConfiguration.get("squarecat");
        this.squareCat = entityFactory.createEntity(this, catConfig);
        this.entities.add(this.squareCat);

        this.levelFinished = false;//initially the level is not finished
        //initialize the scores
        this.levelScore = 0;
        this.redScore = 0;
        this.greenScore = 0;
        this.blueScore = 0;
        this.observers = new ArrayList<>();//initialize the observer list
    }

    @Override
    public List<Entity> getEntities() {
        return Collections.unmodifiableList(entities);
    }

    //private List<DynamicEntity> getDynamicEntities() {
    @Override
    public List<DynamicEntity> getDynamicEntities() {
        return entities.stream().filter(e -> e instanceof DynamicEntity).map(e -> (DynamicEntity) e).collect(
                Collectors.toList());
    }

    private List<StaticEntity> getStaticEntities() {
        return entities.stream().filter(e -> e instanceof StaticEntity).map(e -> (StaticEntity) e).collect(
                Collectors.toList());
    }

    @Override
    public double getLevelHeight() {
        return this.levelHeight;
    }

    @Override
    public double getLevelWidth() {
        return this.levelWidth;
    }

    @Override
    public double getHeroHeight() {
        return hero.getHeight();
    }

    @Override
    public double getHeroWidth() {
        return hero.getWidth();
    }

    @Override
    public double getFloorHeight() {
        return floorHeight;
    }

    @Override
    public Color getFloorColor() {
        return floorColor;
    }

    @Override
    public double getGravity() {
        return levelGravity;
    }

    @Override
    public void update() {
        List<DynamicEntity> dynamicEntities = getDynamicEntities();

        dynamicEntities.stream().forEach(e -> {
            e.update(frameDurationMilli, levelGravity);
        });

        for (int i = 0; i < dynamicEntities.size(); ++i) {
            DynamicEntity dynamicEntityA = dynamicEntities.get(i);

            for (int j = i + 1; j < dynamicEntities.size(); ++j) {
                DynamicEntity dynamicEntityB = dynamicEntities.get(j);

                if (dynamicEntityA.collidesWith(dynamicEntityB)) {
                    dynamicEntityA.collideWith(dynamicEntityB);
                    dynamicEntityB.collideWith(dynamicEntityA);
                    if (!isHero(dynamicEntityA) && !isHero(dynamicEntityB)) {
                        engine.resolveCollision(dynamicEntityA, dynamicEntityB);
                    }
                }
            }

            //Dynamic entities except squarecat should collide with static entities
            if(dynamicEntityA != squareCat) {
                for (StaticEntity staticEntity : getStaticEntities()) {
                    if (dynamicEntityA.collidesWith(staticEntity)) {
                        dynamicEntityA.collideWith(staticEntity);
                        engine.resolveCollision(dynamicEntityA, staticEntity, this);
                    }
                }
            }
        }

        //entities cannot pass through the floor except squarecat
        dynamicEntities.stream().forEach(e -> {if(!isSquareCat(e)) {
            engine.enforceWorldLimits(e, this);
        }});
        //dynamicEntities.stream().forEach(e -> engine.enforceWorldLimits(e, this));

        afterUpdateJobQueue.forEach(j -> j.run());
        afterUpdateJobQueue.clear();

    }

    @Override
    public double getHeroX() {
        return hero.getPosition().getX();
    }

    @Override
    public double getHeroY() {
        return hero.getPosition().getY();
    }

    @Override
    public boolean boostHeight() {
        return hero.boostHeight();
    }

    @Override
    public boolean dropHeight() {
        return hero.dropHeight();
    }

    @Override
    public boolean moveLeft() {
        return hero.moveLeft();
    }

    @Override
    public boolean moveRight() {
        return hero.moveRight();
    }

    @Override
    public boolean isHero(Entity entity) {
        return entity == hero;
    }

    @Override
    public boolean isFinish(Entity entity) {
        return this.finish == entity;
    }

    @Override
    public void resetHero() {
        afterUpdateJobQueue.add(() -> this.hero.reset());
    }

    @Override
    public void finish() {
        //Platform.exit();
        levelFinished = true;//finish the level
    }
    ////////////
    @Override
    public boolean isLevelFinished(){
        return this.levelFinished;
    }

    @Override
    public boolean isSquareCat(Entity entity) {
        return this.squareCat == entity;
    }

    @Override
    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    @Override
    public int getLevelScore() {
        return levelScore;
    }

    @Override
    public int getRedScore() {
        return redScore;
    }

    @Override
    public int getGreenScore() {
        return greenScore;
    }

    @Override
    public int getBlueScore() {
        return blueScore;
    }

    @Override
    public void setLevelScore(int score) {
        this.levelScore = score;
    }

    @Override
    public void setRedScore(int score) {
        this.redScore = score;
    }

    @Override
    public void setGreenScore(int score) {
        this.greenScore = score;
    }

    @Override
    public void setBlueScore(int score) {
        this.blueScore = score;
    }

    @Override
    public void restoreLevel(List<Entity> entities,
                             List<Vector2D> velocities,
                             List<Vector2D> positions,
                             boolean isFinished,
                             int levelScore,
                             int redScore,
                             int greenScore,
                             int blueScore) {
        this.entities.clear();
        this.entities.addAll(entities);

        int i = 0;
        for(DynamicEntity dynamicEntity: getDynamicEntities()){
            dynamicEntity.setVelocity(velocities.get(i));
            dynamicEntity.setPosition(positions.get(i));
            i++;
        }
        this.levelFinished = isFinished;
        this.levelScore = levelScore;
        this.redScore = redScore;
        this.greenScore = greenScore;
        this.blueScore = blueScore;
    }

    @Override
    public void attach(Observer o) {
        this.observers.add(o);
    }

    @Override
    public void detach(Observer o) {
        this.observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for(Observer o: observers){
            o.update();
        }
    }
}
