package feup.lpoo.bounce;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;

import feup.lpoo.bounce.GUI.GameOverScreen;
import feup.lpoo.bounce.GUI.GamePausedScreen;
import feup.lpoo.bounce.GUI.GameScreen;
import feup.lpoo.bounce.GUI.LevelSelectionScreen;
import feup.lpoo.bounce.GUI.MainMenuScreen;
import feup.lpoo.bounce.GUI.OptionsScreen;
import feup.lpoo.bounce.logic.BounceGame;

/**
 * Main class. Handles the whole application state.
 */
public class Bounce extends ApplicationAdapter{
    //Global enumerations
	public enum ProgramState { MAIN_MENU, LEVEL_SELECTION, GAME, GAME_OVER, OPTIONS, HOW_TO_PLAY, GAME_PAUSED }
    public enum GameState { PAUSED, RUNNING, LOSS, WIN }
    public enum EntityType { WALL, BALL, SPIKE, GEM, BARBED_WIRE, MONSTER, RING }

    //Global variables
    public static final int NUMBER_OF_LEVELS = 6;
    public static final String LOSS_MESSAGE = "Try again...";
    public static final String WIN_MESSAGE = "You won!";
    public static final String PAUSED_MESSAGE = "Paused";

    private Screen currentScreen;
	private ProgramState programState;
    private BounceGame game;
	private long lastUpdateTime = TimeUtils.millis();

    @Override
	public void create () {
        GameSound.setMusicMuted(false);
        setProgramState(ProgramState.MAIN_MENU);
	}

    @Override
	public void render () {
		update();
		long currentTime = TimeUtils.millis();
        currentScreen.render(lastUpdateTime - currentTime);
		lastUpdateTime = currentTime;
	}

    /**
     * Updates the application state.
     */
	private void update() {
		switch (programState) {
			case MAIN_MENU:
				break;
			case LEVEL_SELECTION:
				break;
			case GAME:
                GameState gameState = game.getGameState();
                if(gameState == GameState.WIN || gameState == GameState.LOSS) {
                    programState = ProgramState.GAME_OVER;
                    game.pauseGame();
                    currentScreen = new GameOverScreen(this, game, gameState);
                }
				break;
			case GAME_OVER:
				break;
		}
	}

    /**
     * Launches a BounceGame with the respective level.
     * @param level Level
     */
    public void launchGame(int level) {
        if(game == null || game.getLevel() != level) {
            game = new BounceGame(level);
            GameSound.soundMuted = false;
            GameSound.setMusicMuted(false);
            game.start();
        } else
            game.restart();

        setProgramState(ProgramState.GAME);
    }

    /**
     * Sets the application state
     * @param programState New program state
     */
	public void setProgramState(ProgramState programState) {
        this.programState = programState;

        switch (programState) {
            case MAIN_MENU:
                currentScreen = new MainMenuScreen(this);
                break;
            case LEVEL_SELECTION:
                currentScreen = new LevelSelectionScreen(this);
                break;
            case GAME:
                currentScreen = new GameScreen(this, game);
                game.setScreen(currentScreen);
                break;
            case GAME_OVER:
                //End of game already handles GAME_OVER
                break;
            case GAME_PAUSED:
                currentScreen = new GamePausedScreen(this, game);
                break;
            case OPTIONS:
                currentScreen = new OptionsScreen(this);
                break;
        }
    }
}
