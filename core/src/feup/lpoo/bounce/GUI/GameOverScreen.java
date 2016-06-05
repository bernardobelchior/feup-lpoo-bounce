package feup.lpoo.bounce.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import feup.lpoo.bounce.Bounce;
import feup.lpoo.bounce.Utils;
import feup.lpoo.bounce.logic.BounceGame;
import feup.lpoo.bounce.Bounce.*;

/**
 * Created by Bernardo on 02-06-2016.
 */
public class GameOverScreen implements Screen {
    private Bounce bounce;
    private BounceGame game;
    private GameState gameState;

    private Label messageLabel;
    private Label scoreTextLabel;
    private Label scoreLabel;
    private ImageButton levelSelectionMenuButton;
    private ImageButton retryButton;
    private ImageButton nextLevelButton;

    private Stage stage;
    private FitViewport viewport;
    private SpriteBatch spriteBatch;

    public GameOverScreen(final Bounce bounce, final BounceGame game, GameState gameState) {
        this.bounce = bounce;
        this.game = game;
        this.gameState = gameState;

        //Set viewport and sprite batch for stage
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        spriteBatch = new SpriteBatch();
        stage = new Stage(viewport, spriteBatch);
        Gdx.input.setInputProcessor(stage);

        stage.addActor(createMenu());
    }

    private Table createMenu() {
        //Define a labelStyle for all labels and create them
        Label.LabelStyle labelStyle = new Label.LabelStyle(Graphics.getFont(), Color.WHITE);

        switch (gameState) {
            case WIN:
                messageLabel = new Label(Bounce.WIN_MESSAGE, labelStyle);
                break;
            case LOSS:
                messageLabel = new Label(Bounce.LOSS_MESSAGE, labelStyle);
                break;
        }

        messageLabel.setFontScale(Graphics.BITMAP_FONT_SCALING);
        messageLabel.setAlignment(Align.center);

        scoreTextLabel = new Label("Score:", labelStyle);
        scoreTextLabel.setFontScale(Graphics.BITMAP_FONT_SCALING);
        scoreTextLabel.setAlignment(Align.center);

        scoreLabel = new Label(String.format("%06d", game.getScore()), labelStyle);
        scoreLabel.setFontScale(Graphics.BITMAP_FONT_SCALING);
        scoreLabel.setAlignment(Align.center);

        //Define a buttonStyle for all buttons and create them
        levelSelectionMenuButton = Utils.createButtonWithImage(Graphics.getBackButtonTextureRegionDrawable());
        retryButton = Utils.createButtonWithImage(Graphics.getRetryButtonTextureRegionDrawable());
        nextLevelButton = Utils.createButtonWithImage(Graphics.getNextButtonTextureRegionDrawable());

        if(gameState == Bounce.GameState.LOSS || game.getLevel() == Bounce.NUMBER_OF_LEVELS)
            nextLevelButton.setDisabled(true);

        //Defining button's listeners
        levelSelectionMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(levelSelectionMenuButton.isPressed()) {
                    bounce.setProgramState(ProgramState.LEVEL_SELECTION);
                }
            }
        });

        retryButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(retryButton.isPressed()) {
                    game.restart();
                    bounce.setProgramState(Bounce.ProgramState.GAME);
                }
            }
        });

        nextLevelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(nextLevelButton.isPressed()) {
                    game.nextLevel();
                    bounce.setProgramState(Bounce.ProgramState.GAME);
                }
            }
        });

        //TODO: Add background
        //Create the actual menu layout
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        //table.setBackground(GameOverBackground);

        table.pad(Gdx.graphics.getHeight()/6f, Gdx.graphics.getWidth()/8f,
                Gdx.graphics.getHeight()/6f, Gdx.graphics.getWidth()/8f);

        //First row
        table.add(messageLabel).colspan(3).uniform();

        //Second row
        table.row().expand();

        table.add(scoreTextLabel);
        table.add(scoreLabel).colspan(2);

        //Third row
        table.row().expand();

        table.add(levelSelectionMenuButton).uniform();
        table.add(retryButton).uniform();
        table.add(nextLevelButton).uniform();

        //table.setDebug(true);
        return table;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.getScreen().render(delta);

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
