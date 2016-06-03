package feup.lpoo.bounce.logic;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import feup.lpoo.bounce.Bounce;
import feup.lpoo.bounce.Bounce.EntityType;

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
        EntityType entityA = (EntityType) contact.getFixtureA().getBody().getUserData();
        EntityType entityB = (EntityType) contact.getFixtureB().getBody().getUserData();

        if(entityA == Bounce.EntityType.BALL) {
            switch (entityB) {
                case SPIKE:
                    game.over();
                    break;
                case RING:
                    game.ringDestroyed(contact.getFixtureB().getBody());
                    break;
                case GEM:
                    game.gemDestroyed(contact.getFixtureB().getBody());
                    break;
            }
        } else if (entityB == Bounce.EntityType.BALL) {
            switch (entityA) {
                case SPIKE:
                    game.over();
                    break;
                case RING:
                    game.ringDestroyed(contact.getFixtureA().getBody());
                    break;
                case GEM:
                    game.gemDestroyed(contact.getFixtureA().getBody());
                    break;
            }
        }
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
