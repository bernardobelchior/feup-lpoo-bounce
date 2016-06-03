package feup.lpoo.bounce.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

    private TextureRegionDrawable backTexture;
    private TextureRegionDrawable retryTexture;
    private TextureRegionDrawable nextTexture;

    public GameOverScreen(final Bounce bounce, final BounceGame game, GameState gameState) {
        this.bounce = bounce;
        this.game = game;
        this.gameState = gameState;

        backTexture = new TextureRegionDrawable(new TextureRegion(new Texture("back.png")));
        retryTexture = new TextureRegionDrawable(new TextureRegion((new Texture("retry.png"))));
        nextTexture = new TextureRegionDrawable(new TextureRegion(new Texture("next.png")));

        //Set viewport and sprite batch for stage
        viewport = new FitViewport(game.getMapHeight() *(float)Gdx.graphics.getWidth()/Gdx.graphics.getHeight(), game.getMapHeight(), new OrthographicCamera());
        spriteBatch = new SpriteBatch();
        stage = new Stage(viewport, spriteBatch);
        Gdx.input.setInputProcessor(stage);

        //Define a labelStyle for all labels and create them
        Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        switch (gameState) {
            case WIN:
                messageLabel = new Label(Bounce.WIN_MESSAGE, labelStyle);
                break;
            case LOSS:
                messageLabel = new Label(Bounce.LOSS_MESSAGE, labelStyle);
                break;
        }

        messageLabel.setFontScale(5f);
        messageLabel.setAlignment(Align.center);

        scoreTextLabel = new Label("Score:", labelStyle);
        scoreTextLabel.setFontScale(5f);
        scoreTextLabel.setAlignment(Align.center);

        scoreLabel = new Label(String.format("%06d", game.getScore()), labelStyle);
        scoreLabel.setFontScale(5f);
        scoreLabel.setAlignment(Align.center);

        //Define a buttonStyle for all buttons and create them
        levelSelectionMenuButton = createButtonWithImage(backTexture);
        retryButton = createButtonWithImage(retryTexture);
        nextLevelButton = createButtonWithImage(nextTexture);

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

        Table table = createMenuTable();

        stage.addActor(table);
    }

    private ImageButton createButtonWithImage(TextureRegionDrawable textureRegionDrawable) {
        ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.pressedOffsetX = 2;
        imageButtonStyle.pressedOffsetY = -2;
        imageButtonStyle.imageUp = textureRegionDrawable;
        imageButtonStyle.imageDown = textureRegionDrawable;
        return new ImageButton(imageButtonStyle);
    }

    private Table createMenuTable() {
        //TODO: Add background
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

        table.add().uniform();

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

        //table.setDebug(true);
        return table;
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
