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

/**
 * Created by Bernardo on 01-06-2016.
 */
public class LevelLoader {
    //Map layers definition
    private final static int WALL_LAYER = 3;
    private final static int SPIKE_LAYER = 4;

    public Body load(TiledMap map, World world) {
        int mapWidth = map.getProperties().get("width", Integer.class).intValue()*map.getProperties().get("tilewidth", Integer.class).intValue();
        int mapHeight = map.getProperties().get("height", Integer.class).intValue()*map.getProperties().get("tileheight", Integer.class).intValue();

        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(96, mapHeight/2-32);

        CircleShape ballShape = new CircleShape();
        ballShape.setRadius(32);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = ballShape;
        fixtureDef.restitution = 0.5f;
        fixtureDef.density = 1;

        Body ball = world.createBody(bodyDef);
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

        return ball;
    }
}
