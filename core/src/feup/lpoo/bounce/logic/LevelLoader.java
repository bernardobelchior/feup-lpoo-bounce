package feup.lpoo.bounce.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import feup.lpoo.bounce.Bounce;

/**
 * Created by Bernardo on 01-06-2016.
 *
 * Class used to load the TiledMap passed as argument to a World.
 */
public class LevelLoader {
    //Map layers definition
    private final static int WALL_LAYER = 2;
    private final static int BALL_LAYER = 3;
    private final static int SPIKE_LAYER = 4;
    private final static int RINGS_LAYER = 5;
    private final static int GEMS_LAYER = 6;
    private final static int BARBED_WIRE_LAYER = 7;
    
    private Body ball;
    private ArrayList<Body> rings;
    private ArrayList<Body> gems;

    private int mapWidth;
    private int mapHeight;

    /**
     * Loads the map into the world
     * @param map Map
     * @param world World
     */
    public void load(TiledMap map, World world) {
        mapWidth = map.getProperties().get("width", Integer.class).intValue()*map.getProperties().get("tilewidth", Integer.class).intValue();
        mapHeight = map.getProperties().get("height", Integer.class).intValue()*map.getProperties().get("tileheight", Integer.class).intValue();

        rings = new ArrayList<Body>();
        gems = new ArrayList<Body>();

        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(96, mapHeight/2-32);

        try {
            Ellipse circle = ((EllipseMapObject) map.getLayers().get(BALL_LAYER).getObjects().get(0)).getEllipse();
            bodyDef.position.set(circle.x + 32, circle.y + 32);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        CircleShape ballShape = new CircleShape();
        ballShape.setRadius(32);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = ballShape;
        fixtureDef.restitution = 0.3f;
        fixtureDef.density = 1;

        ball = world.createBody(bodyDef);
        ball.createFixture(fixtureDef);
        ball.setUserData(Bounce.EntityType.BALL);
        ballShape.dispose();

        try {
            for(MapObject object : map.getLayers().get(WALL_LAYER).getObjects()) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

                bodyDef = new BodyDef();
                bodyDef.type = BodyDef.BodyType.StaticBody;
                bodyDef.position.set(rectangle.getX()+rectangle.getWidth()/2, rectangle.getY()+rectangle.getHeight()/2);

                PolygonShape polygonShape = new PolygonShape();
                polygonShape.setAsBox(rectangle.getWidth()/2, rectangle.getHeight()/2);

                Body body = world.createBody(bodyDef);
                body.createFixture(polygonShape, 1);
                body.setUserData(Bounce.EntityType.WALL);
                polygonShape.dispose();
            }
        } catch(IndexOutOfBoundsException e) {
            e.printStackTrace();
            Gdx.app.log("LevelLoader", "No walls layer in map.");
            System.exit(1);
        }

        try {
            for(MapObject object : map.getLayers().get(SPIKE_LAYER).getObjects()) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

                bodyDef = new BodyDef();
                bodyDef.type = BodyDef.BodyType.StaticBody;
                bodyDef.position.set(rectangle.getX()+rectangle.getWidth()/2, rectangle.getY()+rectangle.getHeight()/2);

                PolygonShape polygonShape = new PolygonShape();
                polygonShape.setAsBox(rectangle.getWidth()/2, rectangle.getHeight()/2);

                Body body = world.createBody(bodyDef);
                body.createFixture(polygonShape, 1);
                body.setUserData(Bounce.EntityType.SPIKE);
                polygonShape.dispose();
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            Gdx.app.log("LevelLoader", "No spikes layer in map.");
        }


        try {
            for(MapObject object : map.getLayers().get(RINGS_LAYER).getObjects()) {
                Ellipse ellipse = ((EllipseMapObject) object).getEllipse();

                bodyDef = new BodyDef();
                bodyDef.type = BodyDef.BodyType.KinematicBody;
                bodyDef.position.set(ellipse.x + ellipse.width/2, ellipse.y + ellipse.height/2);

                CircleShape ellipseShape = new CircleShape();

                float radius;
                if(ellipse.height < ellipse.width)
                    radius = ellipse.height/2;
                else
                    radius = ellipse.width/2;

                ellipseShape.setRadius(radius);

                fixtureDef = new FixtureDef();
                fixtureDef.shape = ellipseShape;
                fixtureDef.density = 1;
                fixtureDef.isSensor = true;

                Body body = world.createBody(bodyDef);
                body.createFixture(fixtureDef);
                body.setUserData(Bounce.EntityType.RING);
                rings.add(body);
                ellipseShape.dispose();
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            Gdx.app.log("LevelLoader", "No rings layer in map.");
        }

        try {
            for(MapObject object : map.getLayers().get(GEMS_LAYER).getObjects()) {
                Ellipse ellipse = ((EllipseMapObject) object).getEllipse();

                bodyDef = new BodyDef();
                bodyDef.type = BodyDef.BodyType.KinematicBody;
                bodyDef.position.set(ellipse.x + ellipse.width/2, ellipse.y + ellipse.height/2);

                float radius;
                if(ellipse.height < ellipse.width)
                    radius = ellipse.height/2;
                else
                    radius = ellipse.width/2;

                CircleShape ellipseShape = new CircleShape();
                ellipseShape.setRadius(radius);

                fixtureDef = new FixtureDef();
                fixtureDef.shape = ellipseShape;
                fixtureDef.density = 1;
                fixtureDef.isSensor = true;

                Body body = world.createBody(bodyDef);
                body.createFixture(fixtureDef);
                body.setUserData(Bounce.EntityType.GEM);
                gems.add(body);
                ellipseShape.dispose();
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            Gdx.app.log("LevelLoader", "No gems layer in map.");
        }

        try {
            for(MapObject object : map.getLayers().get(BARBED_WIRE_LAYER).getObjects()) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

                bodyDef = new BodyDef();
                bodyDef.type = BodyDef.BodyType.StaticBody;
                bodyDef.position.set(rectangle.getX()+rectangle.getWidth()/2, rectangle.getY()+rectangle.getHeight()/2);

                PolygonShape polygonShape = new PolygonShape();
                polygonShape.setAsBox(rectangle.getWidth()/2, rectangle.getHeight()/2);

                Body body = world.createBody(bodyDef);
                body.createFixture(polygonShape, 1);
                body.setUserData(Bounce.EntityType.BARBED_WIRE);
                polygonShape.dispose();
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            Gdx.app.log("LevelLoader", "No barbed wire layer in map.");
        }
    }

    /**
     * Gets the ball
     * @return Ball
     */
    public Body getBall() {
        return ball;
    }

    /**
     * Gets the rings
     * @return Rings
     */
    public ArrayList<Body> getRings() {
        return rings;
    }

    /**
     * Gets the gems
     * @return Gems
     */
    public ArrayList<Body> getGems() {
        return gems;
    }

    /**
     * Gets the map height
     * @return mapHeight
     */
    public int getMapHeight() {
        return mapHeight;
    }

    /**
     * Gets the map width
     * @return mapWidth
     */
    public int getMapWidth() {
        return mapWidth;
    }
}
