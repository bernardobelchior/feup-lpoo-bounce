package feup.lpoo.bounce.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Bernardo on 06-06-2016.
 *
 * Class the handles the Monster's movement.
 */
public class Monster {
    private static final float MOVEMENT_SPEED = 100f;

    private Vector2 speed;
    private Vector2 initialPosition;
    private int movementHeight;
    private int movementWidth;
    private Body monster;

    /**
     * Monster default constructor
     * @param monster Box2d body that represents the monster
     * @param movementWidth Movement delta from the initial position in the x axis
     * @param movementHeight Movement delta from the initial position in the y axis
     */
    public Monster(Body monster, int movementWidth, int movementHeight) {
        this.monster = monster;
        this.movementWidth = movementWidth;
        this.movementHeight = movementHeight;
        initialPosition = new Vector2(monster.getPosition().x, monster.getPosition().y);

        speed = new Vector2(movementWidth != 0 ? MOVEMENT_SPEED : 0,
                movementHeight != 0 ? MOVEMENT_SPEED : 0);
        monster.setLinearVelocity(speed);
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
    public int getMovementHeight() {
        return movementHeight;
    }

    /**
     * Gets the movement width
     * @return movementWidth
     */
    public int getMovementWidth() {
        return movementWidth;
    }

    public void move() {

        if((monster.getPosition().x > initialPosition.x + movementWidth && monster.getLinearVelocity().x > 0 )||
                (monster.getPosition().x < initialPosition.x && monster.getLinearVelocity().x < 0))
            monster.setLinearVelocity(-monster.getLinearVelocity().x , monster.getLinearVelocity().y);

        if((monster.getPosition().y > initialPosition.y + movementHeight && monster.getLinearVelocity().y > 0) ||
                (monster.getPosition().y < initialPosition.y && monster.getLinearVelocity().y < 0))
            monster.setLinearVelocity(monster.getLinearVelocity().x, -monster.getLinearVelocity().y);
    }
}
