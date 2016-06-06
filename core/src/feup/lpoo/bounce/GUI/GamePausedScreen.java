package feup.lpoo.bounce.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sun.corba.se.impl.orbutil.graph.Graph;

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

    private Table table;
    private Stage stage;
    private FitViewport viewport;
    private SpriteBatch spriteBatch;

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

        Label messageLabel = new Label(Bounce.PAUSED_MESSAGE, labelStyle);
        messageLabel.setFontScale(Graphics.BITMAP_FONT_SCALING);
        messageLabel.setAlignment(Align.center);

        Label scoreTextLabel = new Label("Score:", labelStyle);
        scoreTextLabel.setFontScale(Graphics.BITMAP_FONT_SCALING*0.75f);
        scoreTextLabel.setAlignment(Align.center);

        Label scoreLabel = new Label(String.format("%06d", game.getScore()), labelStyle);
        scoreLabel.setFontScale(Graphics.BITMAP_FONT_SCALING*0.75f);
        scoreLabel.setAlignment(Align.center);

        Label highscoreTextLabel = new Label("Highscore:", labelStyle);
        highscoreTextLabel.setFontScale(Graphics.BITMAP_FONT_SCALING*0.75f);
        highscoreTextLabel.setAlignment(Align.center);

        Label highscoreLabel = new Label(String.format("%06d", game.getHighscore()), labelStyle);
        highscoreLabel.setFontScale(Graphics.BITMAP_FONT_SCALING*0.75f);
        highscoreLabel.setAlignment(Align.center);

        final ImageButton levelSelectionMenuButton = Utils.createButtonWithImage(new TextureRegionDrawable(Graphics.getBackButtonTextureRegionDrawable()));
        final ImageButton retryButton = Utils.createButtonWithImage(new TextureRegionDrawable(Graphics.getRetryButtonTextureRegionDrawable()));
        final ImageButton resumeButton = Utils.createButtonWithImage(new TextureRegionDrawable(Graphics.getNextButtonTextureRegionDrawable()));

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

        table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        table.center();

        table.pad(Gdx.graphics.getHeight()/6f, Gdx.graphics.getWidth()/5f,
                Gdx.graphics.getHeight()/6f, Gdx.graphics.getWidth()/5f);

        //First row
        table.add(messageLabel).colspan(3).expand();

        //Second row
        table.row().expand();

        table.add(scoreTextLabel).expandX().colspan(2);
        table.add(scoreLabel).expandX().left();

        //Third row
        table.row().expand();

        table.add(highscoreTextLabel).expandX().colspan(2);
        table.add(highscoreLabel).expandX().left();

        //Fourth row
        table.row().expand();

        table.add(levelSelectionMenuButton).uniform().padBottom(Gdx.graphics.getHeight()/40f);
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

        spriteBatch.begin();
        spriteBatch.draw(Graphics.getPausedGameBackgroundTextureRegion(), table.getX() + table.getPadLeft(), table.getY() + table.getPadTop(),
                table.getWidth() - table.getPadX(), table.getHeight() - table.getPadY());
        spriteBatch.end();

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
