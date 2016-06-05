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

        Table table = new Table();
        table.setFillParent(true);
        table.top().left();
        table.padBottom(Gdx.graphics.getHeight()/16f).padTop(Gdx.graphics.getHeight()/6f);

        Label.LabelStyle labelStyle = new Label.LabelStyle(Graphics.getFont(), Color.GOLD);

        Label selectLevel = new Label("Select a level", labelStyle);
        selectLevel.setAlignment(Align.center);
        selectLevel.setFontScale(Graphics.BITMAP_FONT_SCALING);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = Graphics.getFont();
        textButtonStyle.fontColor = Color.WHITE;
        textButtonStyle.pressedOffsetY = -2;
        textButtonStyle.pressedOffsetX = 2;

        table.add(selectLevel).expandX().center().colspan(15);
        table.row();

        for(int i = 0; i < Bounce.NUMBER_OF_LEVELS; i++) {
            final TextButton levelTextButton = new TextButton("" + (i+1), textButtonStyle);
            levelTextButton.setUserObject(new Integer(i+1));
            levelTextButton.getLabel().setFontScale(Graphics.BITMAP_FONT_SCALING);

            levelTextButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if(levelTextButton.isPressed()) {
                        bounce.launchGame((Integer) levelTextButton.getUserObject());
                    }
                }
            });

            Stack levelStack = new Stack(new Image(Graphics.getEmptyButtonTextureRegionDrawable()), levelTextButton);

            table.add(levelStack).uniform().padLeft(Gdx.graphics.getWidth()/11).padTop(Gdx.graphics.getHeight()/11f);
            if((i+1) % 5 == 0)
                table.row();
        }

        table.row().expandY();
        table.add();

        table.row();

        final TextButton backButton = new TextButton("Back", textButtonStyle);
        backButton.getLabel().setFontScale(Graphics.BITMAP_FONT_SCALING*4/6f);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(backButton.isPressed())
                    bounce.setProgramState(Bounce.ProgramState.MAIN_MENU);
            }
        });

        Stack backStack = new Stack(new Image(Graphics.getMenuButtonTextureRegion()), backButton);
        table.add(backStack).expandX().colspan(15).align(Align.right).padRight(Gdx.graphics.getWidth()/18f);

       // table.setDebug(true);
        table.setBackground(Graphics.getMenuBackgroundTextureRegion());

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
