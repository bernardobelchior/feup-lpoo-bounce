import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import org.junit.Test;
import org.junit.runner.RunWith;

import feup.lpoo.bounce.Bounce;
import feup.lpoo.bounce.logic.BounceGame;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Bernardo on 06-06-2016.
 */
@RunWith(TestsLauncher.class)
public class GameLogicTest {
    @Test
    public void testTest() {
        assertEquals(1, 1);
    }

    @Test
    public void loadMapTest () {
        Files files = Gdx.files;

        files.isLocalStorageAvailable();
        files.getLocalStoragePath();

        System.out.println(Gdx.files.internal("level1.tmx").file().getAbsolutePath());

        TiledMap map = new TmxMapLoader().load("level1.tmx");

        assertEquals(map.getLayers().getCount(), 8);
    }

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
}
