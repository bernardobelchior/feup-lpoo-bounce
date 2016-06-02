package feup.lpoo.bounce;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.TimeUtils;

import feup.lpoo.bounce.GUI.GameScreen;
import feup.lpoo.bounce.GUI.GameOverScreen;
import feup.lpoo.bounce.logic.BounceGame;

public class Bounce extends ApplicationAdapter {
	public static final String LOST_MESSAGE = "Try again...";
	public static final String WON_MESSAGE = "You won!";
	private Screen currentScreen;

	private long lastUpdateTime = TimeUtils.millis();

	private BounceGame game;

	@Override
	public void create () {
		game = new BounceGame(1);
		game.start();
		currentScreen = new GameScreen(game);
		game.setScreen(currentScreen);

		game.over();
	}

	@Override
	public void render () {
		update();
		long currentTime = TimeUtils.millis();
		currentScreen.render(lastUpdateTime - currentTime);
		lastUpdateTime = currentTime;
	}

	private void update() {
		switch (game.getGameState()) {
			case WIN:
				game.stop();
				currentScreen = new GameOverScreen(game, WON_MESSAGE);
				break;
			case LOST:
				game.stop();
				currentScreen = new GameOverScreen(game, LOST_MESSAGE);
				break;
		}
	}

	public void setCurrentScreen(Screen currentScreen) {
		this.currentScreen = currentScreen;
	}
}
