package feup.lpoo.bounce.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import feup.lpoo.bounce.Bounce;
import feup.lpoo.bounce.Bounce.ProgramState;
import feup.lpoo.bounce.GameSound;
import feup.lpoo.bounce.Utils;
import feup.lpoo.bounce.logic.BounceGame;

/**
 * Created by Bernardo on 06/06/2016.
 */
public class OptionsScreen implements Screen {
    private static final String SOUND_LABEL = "Sound";
    private static final String MUSIC_LABEL = "Music";
    private static final String BACK_LABEL = "Back";
    private static final String RESET_HIGHSCORES_LABEL = "Reset highscores";

    private Stage stage;
    private FitViewport viewport;
    private SpriteBatch spriteBatch;
    private Bounce bounce;

    public OptionsScreen(Bounce bounce) {
        this.bounce = bounce;

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        spriteBatch = new SpriteBatch();
        stage = new Stage(viewport, spriteBatch);

        Gdx.input.setInputProcessor(stage);
        createMenu();
    }

    private void createMenu() {
        Label.LabelStyle labelStyle = new Label.LabelStyle(Graphics.getFont(), Color.WHITE);

        Label soundLabel = new Label(SOUND_LABEL, labelStyle);
        soundLabel.setFontScale(Graphics.BITMAP_FONT_SCALING);
        soundLabel.setAlignment(Align.center);

        final ImageButton soundButton;

        ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.pressedOffsetX = 2;
        imageButtonStyle.pressedOffsetY = -2;

        if(GameSound.soundMuted) {
            imageButtonStyle.imageDown = Graphics.getMusicOffButtonTextureRegion();
            imageButtonStyle.imageUp = Graphics.getMusicOffButtonTextureRegion();
        } else {
            imageButtonStyle.imageDown = Graphics.getMusicOnButtonTextureRegion();
            imageButtonStyle.imageUp = Graphics.getMusicOnButtonTextureRegion();
        }

        soundButton = new ImageButton(imageButtonStyle);

        soundButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(soundButton.isPressed()) {
                    GameSound.soundMuted = !GameSound.soundMuted;

                    if(GameSound.soundMuted) {
                        soundButton.getStyle().imageDown = Graphics.getMusicOffButtonTextureRegion();
                        soundButton.getStyle().imageUp = Graphics.getMusicOffButtonTextureRegion();
                    } else {
                        soundButton.getStyle().imageDown = Graphics.getMusicOnButtonTextureRegion();
                        soundButton.getStyle().imageUp = Graphics.getMusicOnButtonTextureRegion();
                    }

                    GameSound.playButtonClickSound();
                }

            }
        });

        Label musicLabel = new Label(MUSIC_LABEL, labelStyle);
        musicLabel.setFontScale(Graphics.BITMAP_FONT_SCALING);
        musicLabel.setAlignment(Align.center);

        imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.pressedOffsetX = 2;
        imageButtonStyle.pressedOffsetY = -2;

        if(GameSound.getMusicMuted()) {
            imageButtonStyle.imageDown = Graphics.getMusicOffButtonTextureRegion();
            imageButtonStyle.imageUp = Graphics.getMusicOffButtonTextureRegion();
        } else {
            imageButtonStyle.imageDown = Graphics.getMusicOnButtonTextureRegion();
            imageButtonStyle.imageUp = Graphics.getMusicOnButtonTextureRegion();
        }

        final ImageButton musicButton = new ImageButton(imageButtonStyle);

        musicButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(musicButton.isPressed()) {
                    GameSound.setMusicMuted(!GameSound.getMusicMuted());
                    GameSound.playButtonClickSound();

                    if(GameSound.getMusicMuted()) {
                        musicButton.getStyle().imageDown = Graphics.getMusicOffButtonTextureRegion();
                        musicButton.getStyle().imageUp = Graphics.getMusicOffButtonTextureRegion();
                    } else {
                        musicButton.getStyle().imageDown = Graphics.getMusicOnButtonTextureRegion();
                        musicButton.getStyle().imageUp = Graphics.getMusicOnButtonTextureRegion();
                    }
                }

            }
        });

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = Graphics.getFont();
        textButtonStyle.pressedOffsetX = 2;
        textButtonStyle.pressedOffsetY = -2;

        final TextButton resetHighscoresButton = new TextButton(RESET_HIGHSCORES_LABEL, textButtonStyle);
        resetHighscoresButton.getLabel().setAlignment(Align.center);
        resetHighscoresButton.getLabel().setFontScale(Graphics.BITMAP_FONT_SCALING*Graphics.BACK_LABEL_SCALING*0.75f);

        resetHighscoresButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(resetHighscoresButton.isPressed()) {
                    for(int i = 1; i <= Bounce.NUMBER_OF_LEVELS; i++) {
                        Gdx.files.local(BounceGame.HIGHSCORE_FILE_NAME + i + BounceGame.HIGHSCORE_FILE_EXTENSION).delete();
                    }
                }
            }
        });

        Stack resetStack = new Stack(new Image(Graphics.getMenuButtonTextureRegion()), resetHighscoresButton);

        final TextButton backButton =  new TextButton(BACK_LABEL, textButtonStyle);
        backButton.getLabel().setAlignment(Align.center);
        backButton.getLabel().setFontScale(Graphics.BITMAP_FONT_SCALING*Graphics.BACK_LABEL_SCALING);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(backButton.isPressed()) {
                    bounce.setProgramState(ProgramState.MAIN_MENU);
                    GameSound.playButtonClickSound();
                }

            }
        });

        Stack backStack = new Stack(new Image(Graphics.getMenuButtonTextureRegion()), backButton);

        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(Graphics.getMenuBackgroundTextureRegion());

        table.add(soundLabel).padBottom(Gdx.graphics.getHeight()/20f);
        table.add(soundButton).padLeft(Gdx.graphics.getWidth()/20f).padBottom(Gdx.graphics.getHeight()/20f);
        table.row();
        table.add(musicLabel);
        table.add(musicButton).padLeft(Gdx.graphics.getWidth()/20f).padBottom(Gdx.graphics.getHeight()/20f);
        table.row();
        table.add(resetStack).center().colspan(2);

        Table backTable = new Table();
        backTable.setFillParent(true);
        backTable.align(Align.bottomRight);
        backTable.padRight(Gdx.graphics.getWidth()/25f).padBottom(Gdx.graphics.getHeight()/15f);

        backTable.add(backStack);

        stage.addActor(table);
        stage.addActor(backTable);
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
