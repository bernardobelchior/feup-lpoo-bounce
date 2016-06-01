package feup.lpoo.bounce.logic;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import feup.lpoo.bounce.Bounce;
import sun.security.provider.ConfigFile;

/**
 * Created by Bernardo on 01-06-2016.
 */
public class BounceContactListener implements ContactListener {
    private BounceGame game;

    public BounceContactListener(BounceGame game) {
        this.game = game;
    }

    @Override
    public void beginContact(Contact contact) {
        BounceGame.EntityType entityA = (BounceGame.EntityType) contact.getFixtureA().getBody().getUserData();
        BounceGame.EntityType entityB = (BounceGame.EntityType) contact.getFixtureB().getBody().getUserData();

        if(entityA == BounceGame.EntityType.BALL && entityB == BounceGame.EntityType.SPIKE ||
                entityA == BounceGame.EntityType.SPIKE && entityB == BounceGame.EntityType.BALL)
            game.over();
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
