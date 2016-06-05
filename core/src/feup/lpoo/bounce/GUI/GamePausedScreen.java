package feup.lpoo.bounce.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

/**
 * Created by Bernardo on 03-06-2016.
 */
public class GamePausedScreen implements Screen {
    private BounceGame game;
    private Bounce bounce;
    private GameScreen gameScreen;

    private Stage stage;
    private FitViewport viewport;
    private SpriteBatch spriteBatch;

    private Label messageLabel;
    private Label scoreTextLabel;
    private Label scoreLabel;
    private ImageButton levelSelectionMenuButton;
    private ImageButton retryButton;
    private ImageButton resumeButton;

    public GamePausedScreen(final Bounce bounce, final BounceGame game) {
        this.game = game;
        this.bounce = bounce;
        this.gameScreen = (GameScreen) game.getScreen();

        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);
        Gdx.input.setInputProcessor(stage);

        stage.addActor(createMenu());
    }

    private Table createMenu() {
        Label.LabelStyle labelStyle = new Label.LabelStyle(Graphics.getFont(), Color.WHITE);

        messageLabel = new Label(Bounce.PAUSED_MESSAGE, labelStyle);
        messageLabel.setFontScale(Graphics.BITMAP_FONT_SCALING);
        messageLabel.setAlignment(Align.center);

        scoreTextLabel = new Label("Score:", labelStyle);
        scoreTextLabel.setFontScale(Graphics.BITMAP_FONT_SCALING);
        scoreTextLabel.setAlignment(Align.center);

        scoreLabel = new Label(String.format("%06d", game.getScore()), labelStyle);
        scoreLabel.setFontScale(Graphics.BITMAP_FONT_SCALING);
        scoreLabel.setAlignment(Align.center);

        levelSelectionMenuButton = Utils.createButtonWithImage(new TextureRegionDrawable(Graphics.getBackButtonTextureRegionDrawable()));
        retryButton = Utils.createButtonWithImage(new TextureRegionDrawable(Graphics.getRetryButtonTextureRegionDrawable()));
        resumeButton = Utils.createButtonWithImage(new TextureRegionDrawable(Graphics.getNextButtonTextureRegionDrawable()));

        levelSelectionMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(levelSelectionMenuButton.isPressed()) {
                    bounce.setProgramState(Bounce.ProgramState.LEVEL_SELECTION);
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

        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(resumeButton.isPressed()) {
                    game.start();
                    bounce.setProgramState(Bounce.ProgramState.GAME);
                }
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        table.center();
        //table.setBackground(GamePausedBackground);

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
        table.add(resumeButton).uniform();
        return table;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        gameScreen.render(delta);

        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        stage.draw();
        spriteBatch.begin();

        spriteBatch.end();
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
