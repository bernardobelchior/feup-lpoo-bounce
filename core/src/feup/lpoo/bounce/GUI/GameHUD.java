package feup.lpoo.bounce.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import feup.lpoo.bounce.logic.BounceGame;

/**
 * Created by Bernardo on 02-06-2016.
 */
public class GameHUD {
    private static final float FONT_SCALE = 5;

    private Stage stage;
    private FitViewport viewport;

    private SpriteBatch spriteBatch;

    private float aspectRatio;

    private Label scoreLabel;

    private BounceGame game;

    public GameHUD(BounceGame game, SpriteBatch spriteBatch) {
        this.game = game;
        this.spriteBatch = spriteBatch;

        aspectRatio = (float)Gdx.graphics.getWidth()/Gdx.graphics.getHeight();

        viewport = new FitViewport(game.mapHeight*aspectRatio, Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);

        Table table = new Table();
        table.top();
        table.right();
        table.setFillParent(true);

        scoreLabel = new Label(String.format("%06d", game.getScore()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(scoreLabel).padRight(Gdx.graphics.getWidth()/25f);
        scoreLabel.setFontScale(FONT_SCALE);

        stage.addActor(table);
    }

    public void render() {
        scoreLabel.setText(String.format("%06d", game.getScore()));
        spriteBatch.setProjectionMatrix(stage.getCamera().combined);
        stage.draw();
    }
}
