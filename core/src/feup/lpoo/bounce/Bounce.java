package feup.lpoo.bounce;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.TimeUtils;

import feup.lpoo.bounce.GUI.GameScreen;
import feup.lpoo.bounce.GUI.GameOverScreen;
import feup.lpoo.bounce.logic.BounceGame;

public class Bounce extends ApplicationAdapter{
	public enum ProgramState { MAIN_MENU, LEVEL_SELECTION, GAME, GAME_OVER }

	public static final String LOST_MESSAGE = "Try again...";
	public static final String WON_MESSAGE = "You won!";

	private Screen currentScreen;
	private ProgramState programState;

	private long lastUpdateTime = TimeUtils.millis();

	private BounceGame game;

	@Override
	public void create () {
        programState = ProgramState.MAIN_MENU;
		launchGame(1);
	}

	@Override
	public void render () {
		update();
		long currentTime = TimeUtils.millis();
        if(currentScreen != null)
		    currentScreen.render(lastUpdateTime - currentTime);
		lastUpdateTime = currentTime;
	}

	private void update() {
		switch (programState) {
			case MAIN_MENU:
				break;
			case LEVEL_SELECTION:
				break;
			case GAME:
				switch (game.getGameState()) {
					case WIN:
                        programState = ProgramState.GAME_OVER;
						game.stop();
						currentScreen = new GameOverScreen(this, game, WON_MESSAGE);
						break;
					case LOST:
                        programState = ProgramState.GAME_OVER;
						game.stop();
						currentScreen = new GameOverScreen(this, game, LOST_MESSAGE);
						break;
				}
				break;
			case GAME_OVER:
				break;
		}
	}

    public void launchGame(int level) {
        if(game == null || game.getLevel() != level) {
            game = new BounceGame(level);
            game.start();
        } else
            game.restart();

        setProgramState(ProgramState.GAME);
    }

	public void setProgramState(ProgramState programState) {
        this.programState = programState;

        switch (programState) {
            case MAIN_MENU:
                //currentScreen = new MainMenuScreen();
                break;
            case LEVEL_SELECTION:
                //currentScreen = new LevelSelectionScreen();
                break;
            case GAME:
                currentScreen = new GameScreen(game);
                game.setScreen(currentScreen);
                break;
            case GAME_OVER:
                if(game.getGameState() == BounceGame.GameState.WIN)
                    currentScreen = new GameOverScreen(this, game, WON_MESSAGE);
                else if(game.getGameState() == BounceGame.GameState.LOST)
                    currentScreen = new GameOverScreen(this, game, LOST_MESSAGE);
                break;
        }
    }
}
