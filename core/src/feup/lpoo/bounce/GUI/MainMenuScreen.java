package feup.lpoo.bounce.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import feup.lpoo.bounce.Bounce;

/**
 * Created by Bernardo on 04-06-2016.
 */
public class MainMenuScreen implements Screen {
    public static final String PLAY_LABEL = "Play";
    public static final String EXIT_LABEL = "Exit";
    private Bounce bounce;

    private Stage stage;
    private SpriteBatch spriteBatch;
    private Texture menuBackground;

    public MainMenuScreen(Bounce bounce) {
        this.bounce = bounce;

        menuBackground = new Texture("main_menu.png");
        FitViewport viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        spriteBatch = new SpriteBatch();
        stage = new Stage(viewport, spriteBatch);

        createMenu();

        Gdx.input.setInputProcessor(stage);
    }

    public void createMenu() {
        TextureRegionDrawable buttonTexture = new TextureRegionDrawable(new TextureRegion(new Texture("menu_button.png")));

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.pressedOffsetX = 2;
        textButtonStyle.pressedOffsetY = -2;
        textButtonStyle.font = Graphics.getFont();

        final TextButton playButton = new TextButton(PLAY_LABEL, textButtonStyle);
        playButton.getLabel().setAlignment(Align.center);
        playButton.getLabel().setFontScale(0.25f);

        Stack playStack = new Stack(new Image(buttonTexture), playButton);

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(playButton.isPressed()) {
                    bounce.setProgramState(Bounce.ProgramState.LEVEL_SELECTION);
                }
            }
        });

        final TextButton exitButton = new TextButton(EXIT_LABEL, textButtonStyle);
        exitButton.getLabel().setAlignment(Align.center);
        exitButton.getLabel().setFontScale(0.25f);

        Stack exitStack = new Stack(new Image(buttonTexture), exitButton);

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(exitButton.isPressed()) {
                    Gdx.app.exit();
                }
            }
        });

        Table buttonsTable = new Table();
        buttonsTable.center().setFillParent(true);
        buttonsTable.setDebug(true);
        buttonsTable.pad(Gdx.graphics.getHeight()/2f, Gdx.graphics.getWidth()/8f, Gdx.graphics.getHeight()/6f, Gdx.graphics.getWidth()/2f);

        buttonsTable.add(playStack).align(Align.center).expand();
        buttonsTable.add().expand();
        buttonsTable.row().uniform();
        buttonsTable.add().expand();
        buttonsTable.add().expand();

        stage.addActor(buttonsTable);

        Table exitTable = new Table();
        exitTable.align(Align.bottomRight);
        exitTable.setFillParent(true);
        exitTable.add(exitStack);
        exitTable.padRight(Gdx.graphics.getWidth()/25f).padBottom(Gdx.graphics.getHeight()/15f);

        stage.addActor(exitTable);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.setProjectionMatrix(stage.getCamera().combined);
        spriteBatch.begin();
        spriteBatch.draw(Graphics.getMenuBackgroundTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
