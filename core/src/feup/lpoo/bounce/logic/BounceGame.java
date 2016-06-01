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

enum GameState { RUNNING, PAUSED};

public class BounceGame extends Game {
    public static final float PIXELS_PER_METER = 64;
    private final static Vector2 GRAVITY = new Vector2(0, -9.0f*PIXELS_PER_METER);
    private final static int WALL_LAYER = 2;
    private final static float SECONDS_BETWEEN_TICKS = 1/300f;
    private final static float HORIZONTAL_ACCELERATION_TOLERANCE = 1f;
    private final static int HORIZONTAL_MOVEMENT_MODIFIER = 1000000;
    public static final int ATTRITION_MODIFIER = 10000;

    private TiledMap map;
    private World world;
    private int level;

    private int mapHeight;
    private int mapWidth;

    private Body ball;
    private Timer gameTimer;
    private GameState gameState;

    public BounceGame(int level) {
        this.gameState = GameState.PAUSED;
        this.level = level;
        create();
        this.gameState = GameState.RUNNING;
    }

    @Override
    public void create() {
        map = new TmxMapLoader().load("level" + level + ".tmx");
        mapWidth = map.getProperties().get("width", Integer.class).intValue()*map.getProperties().get("tilewidth", Integer.class).intValue();
        mapHeight = map.getProperties().get("height", Integer.class).intValue()*map.getProperties().get("tileheight", Integer.class).intValue();

        world = new World(GRAVITY, true);
        loadWorld();

        gameTimer = new Timer();
        gameTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                //FIXME: Actually use the elapsed time
                update(SECONDS_BETWEEN_TICKS);
            }
        }, 0, SECONDS_BETWEEN_TICKS);
        gameTimer.start();
    }

    private void loadWorld() {
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(PIXELS_PER_METER*1.5f, mapHeight/2-PIXELS_PER_METER/2);

        CircleShape ballShape = new CircleShape();
        ballShape.setRadius(PIXELS_PER_METER/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = ballShape;
        fixtureDef.restitution = 0.5f;
        fixtureDef.density = 1;

        ball = world.createBody(bodyDef);
        ball.createFixture(fixtureDef);

        for(MapObject object : map.getLayers().get(WALL_LAYER).getObjects()) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(rectangle.getX()+rectangle.getWidth()/2, rectangle.getY()+rectangle.getHeight()/2);

            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(rectangle.getWidth()/2, rectangle.getHeight()/2);

            Body body = world.createBody(bodyDef);
            body.createFixture(polygonShape, 1);
        }
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
        ball.applyForceToCenter(-GRAVITY.x, 700000*-GRAVITY.y, true);
    }
}
