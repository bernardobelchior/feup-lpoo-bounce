import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import org.junit.Test;
import org.junit.runner.RunWith;

import feup.lpoo.bounce.Bounce;
import feup.lpoo.bounce.logic.BounceGame;
import feup.lpoo.bounce.logic.Monster;

import static junit.framework.TestCase.assertEquals;


/**
 * Created by Bernardo on 06-06-2016.
 */
@RunWith(TestLauncher.class)
public class GameLogicTest {


    @Test
    public void gameStateTest() {
        BounceGame game = new BounceGame(1);

        assertEquals(game.getGameState(), Bounce.GameState.PAUSED);

        game.start();

        assertEquals(game.getGameState(), Bounce.GameState.RUNNING);

        game.pauseGame();

        assertEquals(game.getGameState(), Bounce.GameState.PAUSED);

        game.start();

        assertEquals(game.getGameState(), Bounce.GameState.RUNNING);

        game.over();

        assertEquals(game.getGameState(), Bounce.GameState.LOSS);

        game.restart();
        game.win();

        assertEquals(game.getGameState(), Bounce.GameState.WIN);
    }

    @Test
    public void ballDoubleJumpTest() {
        BounceGame game = new BounceGame(1);

        game.ballJump();
        Vector2 positionAfterFirstJump = game.getBall().getPosition();

        game.ballJump();
        Vector2 positionAfterSecondJump = game.getBall().getPosition();

        assertEquals(positionAfterFirstJump, positionAfterSecondJump);
    }

    @Test
    public void scoreTest() {
        BounceGame game = new BounceGame(1);

        int expectedScore = 0;
        int gemsSize, ringsSize;

        assertEquals(expectedScore, game.getScore());

        gemsSize = game.getGems().size();
        game.gemDestroyed(game.getGems().get(0));
        expectedScore += game.GEM_SCORE;

        //Needed in order to delete the gem
        game.update(1/300f);

        assertEquals(expectedScore, game.getScore());
        assertEquals(gemsSize - 1, game.getGems().size());

        ringsSize = game.getRingsLeft();
        game.ringDestroyed(game.getRings().get(0));
        expectedScore += game.RING_SCORE;

        //Needed in order to delete the ring
        game.update(1/300f);

        assertEquals(expectedScore, game.getScore());
        assertEquals(ringsSize - 1, game.getRingsLeft());
    }
}
