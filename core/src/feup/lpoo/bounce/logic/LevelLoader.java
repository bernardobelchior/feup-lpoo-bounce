package feup.lpoo.bounce.logic;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

/**
 * Created by Bernardo on 01-06-2016.
 */
public class LevelLoader {
    //Map layers definition
    private final static int WALL_LAYER = 2;
    private final static int SPIKE_LAYER = 4;
    private final static int RINGS_LAYER = 5;
    private final static int GEMS_LAYER = 6;
    
    private Body ball;
    private ArrayList<Body> rings;
    private ArrayList<Body> gems;

    public void load(TiledMap map, World world) {
        int mapWidth = map.getProperties().get("width", Integer.class).intValue()*map.getProperties().get("tilewidth", Integer.class).intValue();
        int mapHeight = map.getProperties().get("height", Integer.class).intValue()*map.getProperties().get("tileheight", Integer.class).intValue();

        rings = new ArrayList<Body>();
        gems = new ArrayList<Body>();

        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(96, mapHeight/2-32);

        CircleShape ballShape = new CircleShape();
        ballShape.setRadius(32);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = ballShape;
        fixtureDef.restitution = 0.5f;
        fixtureDef.density = 1;

        ball = world.createBody(bodyDef);
        ball.createFixture(fixtureDef);
        ball.setUserData(BounceGame.EntityType.BALL);

        for(MapObject object : map.getLayers().get(WALL_LAYER).getObjects()) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(rectangle.getX()+rectangle.getWidth()/2, rectangle.getY()+rectangle.getHeight()/2);

            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(rectangle.getWidth()/2, rectangle.getHeight()/2);

            Body body = world.createBody(bodyDef);
            body.createFixture(polygonShape, 1);
            body.setUserData(BounceGame.EntityType.WALL);
        }

        for(MapObject object : map.getLayers().get(SPIKE_LAYER).getObjects()) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(rectangle.getX()+rectangle.getWidth()/2, rectangle.getY()+rectangle.getHeight()/2);

            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(rectangle.getWidth()/2, rectangle.getHeight()/2);

            Body body = world.createBody(bodyDef);
            body.createFixture(polygonShape, 1);
            body.setUserData(BounceGame.EntityType.SPIKE);
        }

        //FIXME: Use the appropriate shape
        for(MapObject object : map.getLayers().get(RINGS_LAYER).getObjects()) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.KinematicBody;
            bodyDef.position.set(rectangle.getX()+rectangle.getWidth()/2, rectangle.getY()+rectangle.getHeight()/2);

            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(rectangle.getWidth()/2, rectangle.getHeight()/2);

            fixtureDef = new FixtureDef();
            fixtureDef.shape = polygonShape;
            fixtureDef.density = 1;
            fixtureDef.isSensor = true;

            Body body = world.createBody(bodyDef);
            body.createFixture(fixtureDef);
            body.setUserData(BounceGame.EntityType.RING);
            rings.add(body);
        }

        //FIXME: Use the appropriate shape
        for(MapObject object : map.getLayers().get(GEMS_LAYER).getObjects()) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.KinematicBody;
            bodyDef.position.set(rectangle.getX()+rectangle.getWidth()/2, rectangle.getY()+rectangle.getHeight()/2);

            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(rectangle.getWidth()/2, rectangle.getHeight()/2);

            fixtureDef = new FixtureDef();
            fixtureDef.shape = polygonShape;
            fixtureDef.density = 1;
            fixtureDef.isSensor = true;

            Body body = world.createBody(bodyDef);
            body.createFixture(fixtureDef);
            body.setUserData(BounceGame.EntityType.GEM);
            gems.add(body);
        }
    }

    public Body getBall() {
        return ball;
    }

    public ArrayList<Body> getRings() {
        return rings;
    }

    public ArrayList<Body> getGems() {
        return gems;
    }
}
