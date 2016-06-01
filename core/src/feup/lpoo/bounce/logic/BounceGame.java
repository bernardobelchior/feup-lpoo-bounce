package feup.lpoo.bounce.logic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by Bernardo on 30-05-2016.
 */

enum GameState { PAUSED, RUNNING}
enum EntityType { WALL, BALL, SPIKE }

public class BounceGame extends Game {
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

    public BounceGame(int level) {
        this.gameState = GameState.PAUSED;

        map = new TmxMapLoader().load("level" + level + ".tmx");

        mapWidth = map.getProperties().get("width", Integer.class).intValue()*map.getProperties().get("tilewidth", Integer.class).intValue();
        mapHeight = map.getProperties().get("height", Integer.class).intValue()*map.getProperties().get("tileheight", Integer.class).intValue();

        world = new World(GRAVITY, true);
        ball = new LevelLoader().load(map, world);

        gameTimer = new Timer();
        gameTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                //FIXME: Actually use the elapsed time
                update(GAME_UPDATE_RATE);
            }
        }, 0, GAME_UPDATE_RATE);

        world.setContactListener(new BounceContactListener(this));
    }

    public boolean start() {
        if(this.gameState == GameState.RUNNING)
            return false;

        gameTimer.start();
        this.gameState = GameState.RUNNING;

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

    public boolean over() {
        this.gameState = GameState.PAUSED;
        gameTimer.stop();

        return true;
    }

    public boolean isRunning() {
        return gameState == GameState.RUNNING;
    }
}
