package feup.lpoo.bounce.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import feup.lpoo.bounce.Bounce;

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

        Texture levelButtonBackground = new Texture("level_button_background.png");

        Table table = new Table();
        table.setFillParent(true);
        table.top().left();

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = Graphics.getFont();
        textButtonStyle.fontColor = Color.GOLD;
        textButtonStyle.pressedOffsetY = -2;
        textButtonStyle.pressedOffsetX = 2;

        for(int i = 0; i < Bounce.NUMBER_OF_LEVELS; i++) {
            final TextButton levelTextButton = new TextButton("" + (i+1), textButtonStyle);
            levelTextButton.setUserObject(new Integer(i+1));
            levelTextButton.getLabel().setFontScale(Bounce.BITMAP_FONT_SCALING);

            levelTextButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if(levelTextButton.isPressed()) {
                        bounce.launchGame((Integer) levelTextButton.getUserObject());
                    }
                }
            });

            Stack levelStack = new Stack(new Image(levelButtonBackground), levelTextButton);

            table.add(levelStack).uniform().padLeft(Gdx.graphics.getWidth()/11).padTop(Gdx.graphics.getHeight()/11f);
            if((i+1) % 5 == 0)
                table.row().uniform();
        }

        table.setDebug(true);

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
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
