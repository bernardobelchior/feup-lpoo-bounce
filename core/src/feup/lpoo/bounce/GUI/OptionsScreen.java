package feup.lpoo.bounce.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import feup.lpoo.bounce.Bounce;
import feup.lpoo.bounce.GameSound;
import feup.lpoo.bounce.Utils;

/**
 * Created by Bernardo on 06/06/2016.
 */
public class OptionsScreen implements Screen {
    public static final String SOUND_LABEL = "Sound";
    public static final String MUSIC_LABEL = "Music";
    private Stage stage;
    private FitViewport viewport;
    private SpriteBatch spriteBatch;

    public OptionsScreen(Bounce bounce) {
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        spriteBatch = new SpriteBatch();
        stage = new Stage(viewport, spriteBatch);

        createMenu();
        Gdx.input.setInputProcessor(stage);
    }

    private void createMenu() {
        Label.LabelStyle labelStyle = new Label.LabelStyle(Graphics.getFont(), Color.WHITE);

        Label soundLabel = new Label(SOUND_LABEL, labelStyle);

        final ImageButton soundButton;

        if(GameSound.soundMuted)
            soundButton =  Utils.createButtonWithImage(Graphics.getMusicOffButtonTextureRegion());
        else
            soundButton =  Utils.createButtonWithImage(Graphics.getMusicOnButtonTextureRegion());

        final Image image = soundButton.getImage();

        //FIXME: Image not changing
        soundButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(soundButton.isPressed()) {
                    GameSound.soundMuted = !GameSound.soundMuted;

                    if(GameSound.soundMuted)
                        image.setDrawable(Graphics.getMusicOffButtonTextureRegion());
                    else
                        image.setDrawable(Graphics.getMusicOnButtonTextureRegion());
                }

            }
        });

        Label musicLabel = new Label(MUSIC_LABEL, labelStyle);

        final ImageButton musicButton =  Utils.createButtonWithImage(Graphics.getMusicOffButtonTextureRegion());

        musicButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(musicButton.isPressed()) {
                    GameSound.setMusicMuted(!GameSound.getMusicMuted());

                    if(GameSound.getMusicMuted())
                        musicButton.getImage().setDrawable(Graphics.getMusicOffButtonTextureRegion());
                    else
                        musicButton.getImage().setDrawable(Graphics.getMusicOnButtonTextureRegion());
                }

            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(Graphics.getMenuBackgroundTextureRegion());
        table.setDebug(true);

        table.add(soundLabel);
        table.add(soundButton);
        table.row();
        table.add(musicLabel);
        table.add(musicButton);
        table.row();

        stage.addActor(table);
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
