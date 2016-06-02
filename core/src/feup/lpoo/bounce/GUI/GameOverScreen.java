package feup.lpoo.bounce.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import feup.lpoo.bounce.logic.BounceGame;

/**
 * Created by Bernardo on 02-06-2016.
 */
public class GameOverScreen implements Screen {
    private BounceGame game;

    private Label messageLabel;
    private Label scoreTextLabel;
    private Label scoreLabel;
    private Button levelSelectionMenuButton;
    private Button retryButton;
    private Button nextLevelButton;
    private Stage stage;
    private FitViewport viewport;
    private SpriteBatch spriteBatch;

    public GameOverScreen(final BounceGame game, String message) {
        this.game = game;

        float aspectRatio = (float) Gdx.graphics.getWidth()/Gdx.graphics.getHeight();

        //Set viewport and sprite batch for stage
        viewport = new FitViewport(game.mapHeight*aspectRatio, game.mapHeight, new OrthographicCamera());
        spriteBatch = new SpriteBatch();
        stage = new Stage(viewport, spriteBatch);
        Gdx.input.setInputProcessor(stage);

        //Define a labelStyle for all labels and create them
        Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        messageLabel = new Label(message, labelStyle);
        messageLabel.setFontScale(5f);
        messageLabel.setAlignment(Align.center);

        scoreTextLabel = new Label("Score:", labelStyle);
        scoreTextLabel.setFontScale(5f);
        scoreTextLabel.setAlignment(Align.center);

        scoreLabel = new Label(String.format("%06d", game.getScore()), labelStyle);
        scoreLabel.setFontScale(5f);
        scoreLabel.setAlignment(Align.center);

        //Define a buttonStyle for all buttons and create them
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        levelSelectionMenuButton = new Button(buttonStyle);
        retryButton = new Button(buttonStyle);
        nextLevelButton = new Button(buttonStyle);

        //Defining button's listeners
        levelSelectionMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                Gdx.app.log("Level selection.", "clicked");
                game.restart();
            }
        });

        retryButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("retry", "clicked");
                game.restart();
                return true;
            }
        });

        /*retryButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(retryButton.isPressed()) {
                    game.restart();
                }
            }
        });*/

        nextLevelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log("hehe", "clickei");
                if(nextLevelButton.isPressed()) {
                    game.nextLevel();
                }
            }
        });

        //Create the actual menu layout
        Table table = new Table();
        table.setFillParent(true);
        table.center();


        //First row
        table.add().expand().uniform();

        //Second row
        table.row().fillX().uniform();
        table.add().expand();

        table.add(messageLabel).colspan(3).uniform();

        table.add().expand();

        //Third row
        table.row().expand();

        table.add();

        table.add(scoreTextLabel);
        table.add(scoreLabel).colspan(2);
        table.add();

        //Fourth row
        table.row().expand();

        table.add();
        table.add(levelSelectionMenuButton).uniform();
        table.add(retryButton).uniform();
        table.add(nextLevelButton).uniform();
        table.add();

        //Fifth row
        table.row().expand().uniform();
        table.add().expand();

        table.setDebug(true);

        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.getScreen().render(delta);
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
