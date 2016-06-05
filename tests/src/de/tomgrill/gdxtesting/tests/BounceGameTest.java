package de.tomgrill.gdxtesting.tests;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import de.tomgrill.gdxtesting.GdxTestRunner;
import feup.lpoo.bounce.Bounce.GameState;
import feup.lpoo.bounce.logic.BounceGame;

import static junit.framework.TestCase.assertEquals;


/**
 * Created by Bernardo on 04-06-2016.
 */
@RunWith(GdxTestRunner.class)
public class BounceGameTest {

    @Test
    public void gameStateTest() {
        BounceGame game = new BounceGame(1);

        assertEquals(game.getGameState(), GameState.PAUSED);

        game.start();

        assertEquals(game.getGameState(), GameState.RUNNING);

        game.pauseGame();

        assertEquals(game.getGameState(), GameState.PAUSED);

        game.start();

        assertEquals(game.getGameState(), GameState.RUNNING);

        game.over();

        assertEquals(game.getGameState(), GameState.LOSS);

        game.restart();
        game.win();

        assertEquals(game.getGameState(), GameState.WIN);
    }
}
