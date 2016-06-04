package feup.lpoo.bounce.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import feup.lpoo.bounce.Bounce;

/**
 * Created by Bernardo on 04-06-2016.
 */
public class MainMenuScreen implements Screen {
    private Bounce bounce;

    private Stage stage;
    private SpriteBatch spriteBatch;

    public MainMenuScreen(Bounce bounce) {
        this.bounce = bounce;

        FitViewport viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        spriteBatch = new SpriteBatch();
        stage = new Stage(viewport, spriteBatch);

        stage.addActor(createMenu());
        Gdx.input.setInputProcessor(stage);
    }

    public Table createMenu() {
        final ImageTextButton playButton = createMainMenuButton(null, "Play");
        playButton.getLabel().setFontScale(Bounce.BITMAP_FONT_SCALING);

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(playButton.isPressed()) {
                    bounce.setProgramState(Bounce.ProgramState.LEVEL_SELECTION);
                }
            }
        });

        final ImageTextButton exitButton = createMainMenuButton(null, "Exit");
        exitButton.getLabel().setFontScale(Bounce.BITMAP_FONT_SCALING);

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(exitButton.isPressed()) {
                    Gdx.app.exit();
                }
            }
        });

        Table table = new Table();
        table.center().setFillParent(true);
        table.setDebug(true);

        table.add(playButton).align(Align.center);
        table.row();
        table.add(exitButton).align(Align.center);

        return table;
    }

    private ImageTextButton createMainMenuButton(TextureRegionDrawable image, String text) {
        ImageTextButton.ImageTextButtonStyle imageTextButtonStyle= new ImageTextButton.ImageTextButtonStyle();
        imageTextButtonStyle.font = new BitmapFont();
        //imageTextButtonStyle.imageUp = ;
        //imageTextButtonStyle.imageDown = ;

        return new ImageTextButton(text, imageTextButtonStyle);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();

        spriteBatch.setProjectionMatrix(stage.getCamera().combined);
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
