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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import feup.lpoo.bounce.Bounce;
import feup.lpoo.bounce.GameSound;

/**
 * Created by Bernardo on 03-06-2016.
 */
public class LevelSelectionScreen implements Screen {
    private Bounce bounce;

    private Stage stage;
    private FitViewport viewport;
    private SpriteBatch spriteBatch;

    public LevelSelectionScreen(final Bounce bounce) {
        this.bounce = bounce;

        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);

        createMenu();

        Gdx.input.setInputProcessor(stage);
    }

    private void createMenu() {
        Label.LabelStyle labelStyle = new Label.LabelStyle(Graphics.getFont(), Color.WHITE);

        Label selectLevel = new Label("Select a level", labelStyle);
        selectLevel.setAlignment(Align.center);
        selectLevel.setFontScale(Graphics.BITMAP_FONT_SCALING);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = Graphics.getFont();
        textButtonStyle.fontColor = Color.WHITE;
        textButtonStyle.pressedOffsetY = -2;
        textButtonStyle.pressedOffsetX = 2;

        Table levelsTable = new Table();
        levelsTable.setFillParent(true);
        levelsTable.setBackground(Graphics.getMenuBackgroundTextureRegion());
        levelsTable.align(Align.top);
        levelsTable.padBottom(Gdx.graphics.getHeight()/3f).padTop(Gdx.graphics.getHeight()/6f);
        levelsTable.add(selectLevel).expandX().center().colspan(15);
        levelsTable.row();

        for(int i = 0; i < Bounce.NUMBER_OF_LEVELS; i++) {
            final TextButton levelTextButton = new TextButton("" + (i+1), textButtonStyle);
            levelTextButton.setUserObject(new Integer(i+1));
            levelTextButton.getLabel().setFontScale(Graphics.BITMAP_FONT_SCALING);

            levelTextButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if(levelTextButton.isPressed()) {
                        bounce.launchGame((Integer) levelTextButton.getUserObject());
                        GameSound.playButtonClickSound();
                    }
                }
            });

            Stack levelStack = new Stack(new Image(Graphics.getEmptyButtonTextureRegionDrawable()), levelTextButton);

            levelsTable.add(levelStack).uniform().padLeft(Gdx.graphics.getWidth()/11).padTop(Gdx.graphics.getHeight()/11f);
            if((i+1) % 5 == 0)
                levelsTable.row();
        }

        final TextButton backButton = new TextButton("Back", textButtonStyle);
        backButton.getLabel().setFontScale(Graphics.BITMAP_FONT_SCALING*Graphics.BACK_LABEL_SCALING);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(backButton.isPressed()) {
                    bounce.setProgramState(Bounce.ProgramState.MAIN_MENU);
                    GameSound.playButtonClickSound();
                }
            }
        });

        Stack backStack = new Stack(new Image(Graphics.getMenuButtonTextureRegion()), backButton);

        Table backTable = new Table();
        backTable.align(Align.bottomRight);
        backTable.setFillParent(true);
        backTable.add(backStack).padRight(Gdx.graphics.getWidth()/25f).padBottom(Gdx.graphics.getHeight()/15f);

        stage.addActor(levelsTable);
        stage.addActor(backTable);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
