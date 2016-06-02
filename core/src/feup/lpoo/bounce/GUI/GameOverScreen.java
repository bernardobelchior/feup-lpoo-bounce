package feup.lpoo.bounce.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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
        //stage = new Stage(viewport, ((GameScreen)game.getScreen()).getSpriteBatch());
        stage = new Stage(viewport, spriteBatch);

        //Define a labelStyle for all labels and create them
        Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        messageLabel = new Label(message, labelStyle);
        scoreTextLabel = new Label("Score:", labelStyle);
        scoreLabel = new Label(String.format("%06d", game.getScore()), labelStyle);

        //Define a buttonStyle for all buttons and create them
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        levelSelectionMenuButton = new Button(buttonStyle);
        retryButton = new Button(buttonStyle);
        nextLevelButton = new Button(buttonStyle);

        //Defining button's listeners
        levelSelectionMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(levelSelectionMenuButton.isPressed()) {
                    //Go to level selection menu
                    // Maybe?
                    // game.dispose();
                }
            }
        });

        retryButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(retryButton.isPressed()) {
                    game.restart();
                }
            }
        });

        nextLevelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(nextLevelButton.isPressed()) {
                    game.nextLevel();
                }
            }
        });

        //Create the actual menu layout
        Table table = new Table();

        //First row
        table.add().expandX();

        //Second row
        table.add().uniform();

        table.add(messageLabel).colspan(3);

        table.add().uniform();

        //Third row
        table.row().uniform();

        table.add().uniform();

        table.add(scoreTextLabel).uniform();
        table.add(scoreLabel).colspan(2);
        table.add().uniform();

        //Fourth row
        table.row().uniform();

        table.add().uniform();
        table.add(levelSelectionMenuButton).uniform();
        table.add(retryButton).uniform();
        table.add(nextLevelButton).uniform();

        //Fifth row
        table.row().expandX();

        table.setDebug(true);

        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //game.getScreen().render(delta);
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
