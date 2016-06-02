package feup.lpoo.bounce;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

import feup.lpoo.bounce.GUI.GameScreen;
import feup.lpoo.bounce.GUI.MenuScreen;
import feup.lpoo.bounce.logic.BounceGame;

public class Bounce extends ApplicationAdapter {
	private Screen currentScreen;

	private long lastUpdateTime = TimeUtils.millis();

	@Override
	public void create () {
		BounceGame game = new BounceGame(1);
		game.start();
		currentScreen = new MenuScreen(game);
		game.setScreen(currentScreen);
	}

	@Override
	public void render () {
		long currentTime = TimeUtils.millis();
		currentScreen.render(lastUpdateTime - currentTime);
		lastUpdateTime = currentTime;
	}
}
