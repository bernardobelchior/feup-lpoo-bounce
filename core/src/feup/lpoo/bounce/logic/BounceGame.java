/**
 * Created by Bernardo on 30-05-2016.
 */

package feup.lpoo.bounce.logic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;

public class BounceGame extends Game {
    private static final int GEM_SCORE = 5;
    private static final int RING_SCORE = 1;

    public enum GameState { PAUSED, RUNNING, LOST, WIN }
    public enum EntityType { WALL, BALL, SPIKE, GEM, RING }
    //World gravity
    public final static Vector2 GRAVITY = new Vector2(0, -576);

    //Game update rate in seconds
    private final static float GAME_UPDATE_RATE = 1/300f;

    //Used to check if the phone is relatively standing still
    private final static float HORIZONTAL_ACCELERATION_TOLERANCE = 1f;

    //Movement modifiers
    private final static int HORIZONTAL_MOVEMENT_MODIFIER = 1000000;
    private final static int ATTRITION_MODIFIER = 10000;
    private final static int JUMP_HEIGHT_MODIFIER = 700000;

    //Map dimensions
    public final int mapWidth;
    public final int mapHeight;

    private TiledMap map;
    private World world;

    private Body ball;
    private Timer gameTimer;
    private GameState gameState;
    private int score;

    private ArrayList<Body> rings;
    private ArrayList<Body> gems;

    private int level;

    public BounceGame(int level) {
        this.gameState = GameState.PAUSED;
        this.level = level;

        map = new TmxMapLoader().load("level" + level + ".tmx");

        mapWidth = map.getProperties().get("width", Integer.class).intValue()*map.getProperties().get("tilewidth", Integer.class).intValue();
        mapHeight = map.getProperties().get("height", Integer.class).intValue()*map.getProperties().get("tileheight", Integer.class).intValue();

        gameTimer = new Timer();
        gameTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                //FIXME: Actually use the elapsed time
                update(GAME_UPDATE_RATE);
            }
        }, 0, GAME_UPDATE_RATE);

        setUpWorld();
    }

    private void setUpWorld() {
        world = new World(GRAVITY, true);
        LevelLoader levelLoader = new LevelLoader();
        levelLoader.load(map, world);
        ball = levelLoader.getBall();
        rings = levelLoader.getRings();
        gems = levelLoader.getGems();

        score = 0;

        world.setContactListener(new BounceContactListener(this));
    }

    public boolean start() {
        if(gameState == GameState.RUNNING)
            return false;

        gameTimer.start();
        gameState = GameState.RUNNING;

        return true;
    }

    public void update(float deltaTime) {
        if(!isRunning())
            return;

        float horizontalAcceleration = Gdx.input.getAccelerometerY();

        //Moves the ball depending on the accelerometer
        if(Math.abs(horizontalAcceleration) > HORIZONTAL_ACCELERATION_TOLERANCE)
            ball.applyForceToCenter(horizontalAcceleration*HORIZONTAL_MOVEMENT_MODIFIER, 0, true);

        //Attrition application
        ball.applyForceToCenter(-ball.getLinearVelocity().x* ATTRITION_MODIFIER, 0, true);

        world.step(deltaTime, 6, 2);
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    public Body getBall() {
        return ball;
    }

    public boolean ballJump() {
        if(!isRunning())
            return false;

        ball.applyForceToCenter(-GRAVITY.x, JUMP_HEIGHT_MODIFIER *-GRAVITY.y, true);

        return true;
    }

    @Override
    public void create() {

    }

    public void over() {
        gameState = GameState.LOST;
        gameTimer.stop();
        Gdx.app.log("Game", "Lost");
    }

    public void win() {
        gameState = GameState.WIN;
        gameTimer.stop();
        Gdx.app.log("Game", "Won");
    }

    public boolean isRunning() {
        return gameState == GameState.RUNNING;
    }

    public int getScore() {
        return score;
    }

    public void ringDestroyed(Body ring) {
        world.destroyBody(ring);
        rings.remove(ring);
        score += RING_SCORE;

        if(rings.size() == 0)
            win();
    }

    public void gemDestroyed(Body gem) {
        world.destroyBody(gem);
        gems.remove(gem);
        score += GEM_SCORE;
    }

    public ArrayList<Body> getRings() {
        return rings;
    }

    public ArrayList<Body> getGems() {
        return gems;
    }

    public GameState getGameState() {
        return gameState;
    }

    @Override
    public void dispose() {
        super.dispose();
        map.dispose();
        world.dispose();
    }

    public void restart() {
        setUpWorld();
        start();
    }

    public boolean nextLevel() {
        if(level > 2)
            return false;

        level++;
        map = new TmxMapLoader().load("level" + level + ".tmx");

        restart();

        return true;
    }

    public void stop() {
        gameState = GameState.PAUSED;
    }

    public int getLevel() {
        return level;
    }
}
