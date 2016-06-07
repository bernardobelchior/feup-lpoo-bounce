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
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import feup.lpoo.bounce.Bounce;
import feup.lpoo.bounce.Bounce.BallType;
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
    private static final String BALL_LABEL = "Ball:";

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
            imageButtonStyle.imageDown = Graphics.getSoundOffButtonTextureRegion();
            imageButtonStyle.imageUp = Graphics.getSoundOffButtonTextureRegion();
        } else {
            imageButtonStyle.imageDown = Graphics.getSoundOnButtonTextureRegion();
            imageButtonStyle.imageUp = Graphics.getSoundOnButtonTextureRegion();
        }

        soundButton = new ImageButton(imageButtonStyle);

        soundButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(soundButton.isPressed()) {
                    GameSound.soundMuted = !GameSound.soundMuted;

                    if(GameSound.soundMuted) {
                        soundButton.getStyle().imageDown = Graphics.getSoundOffButtonTextureRegion();
                        soundButton.getStyle().imageUp = Graphics.getSoundOffButtonTextureRegion();
                    } else {
                        soundButton.getStyle().imageDown = Graphics.getSoundOnButtonTextureRegion();
                        soundButton.getStyle().imageUp = Graphics.getSoundOnButtonTextureRegion();
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

        Label ballLabel = new Label(BALL_LABEL, labelStyle);
        ballLabel.setFontScale(Graphics.BITMAP_FONT_SCALING);
        ballLabel.setAlignment(Align.center);

        imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.pressedOffsetX = 2;
        imageButtonStyle.pressedOffsetY = -2;
        imageButtonStyle.imageDown = Graphics.getBallDrawable();
        imageButtonStyle.imageUp = Graphics.getBallDrawable();

        final ImageButton ballButton = new ImageButton(imageButtonStyle);

        ballButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(ballButton.isPressed()) {
                    if(Bounce.currentBall == BallType.RED)
                        Bounce.currentBall = BallType.BLUE;
                    else
                        Bounce.currentBall = BallType.RED;

                    ballButton.getStyle().imageDown = Graphics.getBallDrawable();
                    ballButton.getStyle().imageUp = Graphics.getBallDrawable();
                }
            }
        });

        Stack ballStack = new Stack(new Image(Graphics.getEmptyButtonTextureRegionDrawable()), ballButton);

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
                GameSound.playButtonClickSound();
                if(resetHighscoresButton.isPressed()) {
                    for(int i = 1; i <= Bounce.NUMBER_OF_LEVELS; i++) {
                        Gdx.files.local(BounceGame.HIGHSCORE_FILE_NAME + i + BounceGame.HIGHSCORE_FILE_EXTENSION).delete();
                    }
                }
            }
        });

        Stack resetStack = new Stack(new Image(Graphics.getMenuButtonTextureRegion()), resetHighscoresButton);

        final ImageButton backButton = Utils.createButtonWithImage(Graphics.getBackButtonTextureRegionDrawable());

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(backButton.isPressed()) {
                        bounce.setProgramState(ProgramState.MAIN_MENU);
                        GameSound.playButtonClickSound();
                }
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(Graphics.getMenuBackgroundTextureRegion());

        table.add(soundLabel).padBottom(Gdx.graphics.getHeight()/40f);
        table.add(soundButton).padLeft(Gdx.graphics.getWidth()/20f).padBottom(Gdx.graphics.getHeight()/40f);
        table.row();
        table.add(musicLabel).padBottom(Gdx.graphics.getHeight()/40f);
        table.add(musicButton).padLeft(Gdx.graphics.getWidth()/20f).padBottom(Gdx.graphics.getHeight()/40f);
        table.row();
        table.add(ballLabel).padBottom(Gdx.graphics.getHeight()/40f);
        table.add(ballStack).padLeft(Gdx.graphics.getWidth()/20f).padBottom(Gdx.graphics.getHeight()/40f);
        table.row();
        table.add(resetStack).center().colspan(2);

        Table backTable = new Table();
        backTable.setFillParent(true);
        backTable.align(Align.bottomRight);
        backTable.padRight(Gdx.graphics.getWidth()/25f).padBottom(Gdx.graphics.getHeight()/15f);

        backTable.add(backButton);

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
