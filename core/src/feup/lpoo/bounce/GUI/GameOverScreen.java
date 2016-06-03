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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import feup.lpoo.bounce.Bounce;
import feup.lpoo.bounce.logic.BounceGame;

/**
 * Created by Bernardo on 02-06-2016.
 */
public class GameOverScreen implements Screen {
    private Bounce bounce;
    private BounceGame game;

    private Label messageLabel;
    private Label scoreTextLabel;
    private Label scoreLabel;
    private ImageButton levelSelectionMenuButton;
    private ImageButton retryButton;
    private ImageButton nextLevelButton;

    private Stage stage;
    private FitViewport viewport;
    private SpriteBatch spriteBatch;

    private Texture backTexture;
    private Texture retryTexture;
    private Texture nextTexture;

    public GameOverScreen(final Bounce bounce, final BounceGame game, String message) {
        this.bounce = bounce;
        this.game = game;

        backTexture = new Texture("next.png");
        retryTexture = new Texture("retry.png");
        nextTexture = new Texture("next.png");

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
        ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.pressedOffsetX = 1;
        imageButtonStyle.pressedOffsetY = -1;
        
        imageButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(backTexture));
        imageButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(backTexture));
        levelSelectionMenuButton = new ImageButton(imageButtonStyle);
        levelSelectionMenuButton.setFillParent(true);

        imageButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(retryTexture));
        imageButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(retryTexture));
        retryButton = new ImageButton(imageButtonStyle);
        retryButton.setFillParent(true);

        imageButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(nextTexture));
        imageButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(nextTexture));
        nextLevelButton = new ImageButton(imageButtonStyle);
        nextLevelButton.setFillParent(true);

        //Defining button's listeners
        levelSelectionMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                //TODO: Go back
                //bounce.setProgramState(Bounce.ProgramState.LEVEL_SELECTION);
            }
        });

        retryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.restart();
                bounce.setProgramState(Bounce.ProgramState.GAME);
            }
        });

        nextLevelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                    game.nextLevel();
                    bounce.setProgramState(Bounce.ProgramState.GAME);
            }
        });

        Table table = createMenuTable();
        table.setTouchable(Touchable.childrenOnly);

        /*table.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Clicked", "table on x: " + x + " y: " + y);
            }
        });*/

        stage.addActor(table);
    }

    private Table createMenuTable() {
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
        return table;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.getScreen().render(delta);
        stage.act(delta);
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
