package feup.lpoo.bounce;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import feup.lpoo.bounce.GUI.GameScreen;
import feup.lpoo.bounce.logic.BounceGame;

public class Bounce extends ApplicationAdapter {
	private Screen currentScreen;

	@Override
	public void create () {
		BounceGame game = new BounceGame(1);
		currentScreen = new GameScreen(game);
		game.setScreen(currentScreen);
	}

	@Override
	public void render () {
		//FIXME: get delta
		currentScreen.render(0.33f);
	}
}
