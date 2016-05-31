package feup.lpoo.bounce.logic;

import com.badlogic.gdx.Game;
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
public class BounceGame extends Game {
    private final static Vector2 GRAVITY = new Vector2(0, -9.8f);
    private final static int WALL_LAYER = 2;
    private final static float SECONDS_BETWEEN_TICKS = 0.00333f;
    public static final float PIXELS_PER_METER = 64;

    private TiledMap map;
    private World world;
    private int level;

    private int mapHeight;
    private int mapWidth;

    private Body ball;
    private Timer gameTimer;

    public BounceGame(int level) {
        this.level = level;
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

        setScreen(new GameScreen(this));
    }

    private void loadWorld() {
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        //bodyDef.position.set(100, mapHeight/2/PIXELS_PER_METER);
        bodyDef.position.set(128, 128);

        CircleShape ballShape = new CircleShape();
        ballShape.setRadius(PIXELS_PER_METER/2);

        ball = world.createBody(bodyDef);
        ball.createFixture(ballShape, 1);

        for(MapObject object : map.getLayers().get(WALL_LAYER).getObjects()) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.getX()+rectangle.getWidth()/2)/PIXELS_PER_METER,
                    (rectangle.getY()+rectangle.getHeight()/2)/PIXELS_PER_METER);

            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(rectangle.getWidth()/2/PIXELS_PER_METER, rectangle.getHeight()/2/PIXELS_PER_METER);

            Body body = world.createBody(bodyDef);
            body.createFixture(polygonShape, 1);
        }
    }

    public void update(float deltaTime) {
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
        ball.applyLinearImpulse(new Vector2(0 , 10000), ball.getPosition(), true);
    }
}
