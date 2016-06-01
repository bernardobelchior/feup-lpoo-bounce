package feup.lpoo.bounce;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.TimeUtils;

import feup.lpoo.bounce.GUI.GameScreen;
import feup.lpoo.bounce.logic.BounceGame;

public class Bounce extends ApplicationAdapter {
	private Screen currentScreen;

	private long lastUpdateTime = TimeUtils.millis();

	@Override
	public void create () {
		Gdx.app.log("App:", "startas");

		BounceGame game = new BounceGame(1);
		Thread gameThread = new Thread(game);
		Gdx.app.log("GameThread", "start");
		gameThread.start();
		Gdx.app.log("GameThread", "end");
		currentScreen = new GameScreen(game);
		Thread gameGUIThread = new Thread((Runnable) currentScreen);
		Gdx.app.log("GameGUIThread", "start");
		gameGUIThread.start();
		Gdx.app.log("GameGUIThread", "end");

		Gdx.app.log("App:", "start");

		try {
			gameThread.join();
			gameGUIThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Gdx.app.log("App:", "ended");
	}

	@Override
	public void render () {
		Gdx.app.log("Render", "none");
		long currentTime = TimeUtils.millis();
		currentScreen.render(lastUpdateTime - currentTime);
		lastUpdateTime = currentTime;
	}
}
