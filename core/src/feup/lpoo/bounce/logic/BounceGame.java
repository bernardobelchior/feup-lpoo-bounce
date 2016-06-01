package feup.lpoo.bounce.logic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;

import feup.lpoo.bounce.GUI.GameScreen;

/**
 * Created by Bernardo on 30-05-2016.
 */

enum GameState { PAUSED, RUNNING}

public class BounceGame extends Game {
    public static final float PIXELS_PER_METER = 64;
    public final static Vector2 GRAVITY = new Vector2(0, -9.0f*PIXELS_PER_METER);
    private final static float SECONDS_BETWEEN_TICKS = 1/300f;
    private final static float HORIZONTAL_ACCELERATION_TOLERANCE = 1f;

    //Movement modifiers
    private final static int HORIZONTAL_MOVEMENT_MODIFIER = 1000000;
    private final static int ATTRITION_MODIFIER = 10000;
    private final static int JUMP_HEIGHT_MODIFIER = 700000;

    private TiledMap map;
    private World world;
    private int level;

    private Body ball;
    private Timer gameTimer;
    private GameState gameState;

    public BounceGame(int level) {
        this.gameState = GameState.PAUSED;
        this.level = level;

        map = new TmxMapLoader().load("level" + level + ".tmx");

        world = new World(GRAVITY, true);
        ball = new LevelLoader().load(map, world);

        gameTimer = new Timer();
        gameTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                //FIXME: Actually use the elapsed time
                update(SECONDS_BETWEEN_TICKS);
            }
        }, 0, SECONDS_BETWEEN_TICKS);
        gameTimer.start();

        this.gameState = GameState.RUNNING;
    }

    public void update(float deltaTime) {
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

    public void ballJump() {
        ball.applyForceToCenter(-GRAVITY.x, JUMP_HEIGHT_MODIFIER *-GRAVITY.y, true);
    }

    @Override
    public void create() {

    }
}
