package feup.lpoo.bounce.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Class the handles the Monster's movement.
 *
 * Created by Bernardo Belchior & Margarida Viterbo on 06-06-2016.
 */
public class Monster {
    public static final float MOVEMENT_SPEED = 100f/BounceGame.PIXELS_PER_METER;

    private Vector2 initialPosition;
    private float movementHeight;
    private float movementWidth;
    private Body monster;

    /**
     * Monster default constructor
     * @param monster Box2d body that represents the monster
     * @param movementWidth Movement delta from the initial position in the x axis
     * @param movementHeight Movement delta from the initial position in the y axis
     */
    public Monster(Body monster, float movementWidth, float movementHeight) {
        this.monster = monster;
        this.movementWidth = movementWidth;
        this.movementHeight = movementHeight;
        initialPosition = new Vector2(monster.getPosition().x, monster.getPosition().y);

        monster.setLinearVelocity(new Vector2(movementWidth != 0 ? MOVEMENT_SPEED : 0,
                movementHeight != 0 ? MOVEMENT_SPEED : 0));
    }

    /**
     * Gets the position
     * @return Body's position
     */
    public Vector2 getPosition() {
        return monster.getPosition();
    }

    /**
     * Gets the movement height
     * @return movementHeight
     */
    public float getMovementHeight() {
        return movementHeight;
    }

    /**
     * Gets the movement width
     * @return movementWidth
     */
    public float getMovementWidth() {
        return movementWidth;
    }

    /**
     * Sets the monster's velocity so it stays in the specified bounds.
     */
    public void move() {
        if((monster.getPosition().x > initialPosition.x + movementWidth && monster.getLinearVelocity().x > 0 )||
                (monster.getPosition().x < initialPosition.x && monster.getLinearVelocity().x < 0))
            monster.setLinearVelocity(-monster.getLinearVelocity().x , monster.getLinearVelocity().y);

        if((monster.getPosition().y > initialPosition.y + movementHeight && monster.getLinearVelocity().y > 0) ||
                (monster.getPosition().y < initialPosition.y && monster.getLinearVelocity().y < 0))
            monster.setLinearVelocity(monster.getLinearVelocity().x, -monster.getLinearVelocity().y);
    }

    /**
     * Gets the body associated with the monster
     * @return monster
     */
    public Body getBody() {
        return monster;
    }
}
