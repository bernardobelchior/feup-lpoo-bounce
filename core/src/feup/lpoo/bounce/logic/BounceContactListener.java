package feup.lpoo.bounce.logic;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import feup.lpoo.bounce.Bounce;
import feup.lpoo.bounce.Bounce.EntityType;
import feup.lpoo.bounce.GameSound;

/**
 * Class used to handle the contacts that occur in the BounceGame world.
 *
 * Created by Bernardo Belchior & Margarida Viterbo on 01-06-2016.
 */
public class BounceContactListener implements ContactListener {
    private BounceGame game;

    /**
     * BounceContactListener constuctor
     * @param game Game in which the contact will happen
     */
    public BounceContactListener(BounceGame game) {
        this.game = game;
    }

    @Override
    public void beginContact(Contact contact) {
        EntityType entityA = (EntityType) contact.getFixtureA().getBody().getUserData();
        EntityType entityB = (EntityType) contact.getFixtureB().getBody().getUserData();

        //Checks what collided with what and
        //handles its consequences
        if(entityA == Bounce.EntityType.BALL) {
            switch (entityB) {
                //Checks if the ball hit the spike from above
                case SPIKE:
                    if(contact.getWorldManifold().getNormal().angle() > 0 &&
                            contact.getWorldManifold().getNormal().angle() < 180)
                        game.over();
                    break;
                //When a ball hits a monster or a barbed wire, the game ends.
                case MONSTER:
                case BARBED_WIRE:
                    game.over();
                    break;
                //Plays the pickUpSound and destroys the ring in the world
                case RING:
                    GameSound.playPickUpSound();
                    game.ringDestroyed(contact.getFixtureB().getBody());
                    break;
                //Plays the pickUpSound and destroys the gem in the world
                case GEM:
                    GameSound.playPickUpSound();
                    game.gemDestroyed(contact.getFixtureB().getBody());
                    break;
                //Checks if the ball hit the floor and enables its next jump if true
                case WALL:
                    if(contact.getWorldManifold().getNormal().angle() == 270)
                        game.enableBallJump();
                    break;
                //Checks if the ball hit the spike from below
                case INVERTED_SPIKE:
                    if(contact.getWorldManifold().getNormal().angle() < 360 &&
                            contact.getWorldManifold().getNormal().angle() > 180)
                        game.over();
                    break;
            }
        } else if (entityB == Bounce.EntityType.BALL) {
            switch (entityA) {
                //Checks if the ball hit the spike from above
                case SPIKE:
                    if(contact.getWorldManifold().getNormal().angle() > 0 &&
                            contact.getWorldManifold().getNormal().angle() < 180)
                        game.over();
                    break;
                //When a ball hits a monster or a barbed wire, the game ends.
                case MONSTER:
                case BARBED_WIRE:
                    game.over();
                    break;
                //Plays the pickUpSound and destroys the ring in the world
                case RING:
                    GameSound.playPickUpSound();
                    game.ringDestroyed(contact.getFixtureA().getBody());
                    break;
                //Plays the pickUpSound and destroys the gem in the world
                case GEM:
                    GameSound.playPickUpSound();
                    game.gemDestroyed(contact.getFixtureA().getBody());
                    break;
                //Checks if the ball hit the floor and enables its next jump if true
                case WALL:
                    if(contact.getWorldManifold().getNormal().angle() == 90)
                        game.enableBallJump();
                    break;
                //Checks if the ball hit the spike from below
                case INVERTED_SPIKE:
                    if(contact.getWorldManifold().getNormal().angle() < 360 &&
                            contact.getWorldManifold().getNormal().angle() > 180)
                        game.over();
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
