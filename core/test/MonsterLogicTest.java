import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import org.junit.Test;
import org.junit.runner.RunWith;

import feup.lpoo.bounce.logic.BounceGame;
import feup.lpoo.bounce.logic.Monster;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Bernardo on 07-06-2016.
 */
@RunWith(TestLauncher.class)
public class MonsterLogicTest {

    @Test
    public void monsterSpeedTest() {
        World world = new World(new Vector2(0, 0), true);

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.x = 0;
        bodyDef.position.y = 0;
        bodyDef.type = BodyDef.BodyType.KinematicBody;

        Monster monster = new Monster(world.createBody(bodyDef), 0 , 0);

        assertEquals(new Vector2(0, 0), monster.getBody().getLinearVelocity());

        monster = new Monster(world.createBody(bodyDef), 64, 128);

        assertEquals(new Vector2(Monster.MOVEMENT_SPEED, Monster.MOVEMENT_SPEED),
                monster.getBody().getLinearVelocity());
    }

    @Test
    public void monsterMoveTest() {
        World world = new World(new Vector2(0, 0), true);

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.x = 0;
        bodyDef.position.y = 0;
        bodyDef.type = BodyDef.BodyType.KinematicBody;

        Monster monster = new Monster(world.createBody(bodyDef), 64, 0);
        Vector2 initialVelocity = monster.getBody().getLinearVelocity().cpy();

        for(int i = 0; i < 150; i++ ) {
            world.step(1f, 6, 2);
            monster.move();
        }

        Vector2 finalVelocity = monster.getBody().getLinearVelocity();

        assertEquals(initialVelocity.y, finalVelocity.y);
        assertTrue(finalVelocity.x > 0);
    }
}
