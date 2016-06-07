package feup.lpoo.bounce.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import feup.lpoo.bounce.Bounce;
import feup.lpoo.bounce.GameSound;
import feup.lpoo.bounce.Utils;

/**
 * Created by Bernardo on 07-06-2016.
 */
public class HowToPlayScreen implements Screen{

    private static final String HOW_TO_PLAY_LABEL =
            "This game is divided in levels.\n" +
            "To pass each level you have to catch all the rings.\n" +
            "In order to get higher scores catch as many gems as you can.\n" +
            "Avoid spikes, barbed wire and monsters because they will kill you.\n" +
            "Lean your phone left and right to move in these directions, and tap the screen to jump.\n" +
            "\n" +
            "Good luck!";
    private static final String BACK_LABEL = "Back";
    private Stage stage;
    private FitViewport viewport;
    private SpriteBatch spriteBatch;
    private Bounce bounce;
    private Table table;

    public HowToPlayScreen(Bounce bounce) {
        this.bounce = bounce;

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        spriteBatch = new SpriteBatch();
        stage = new Stage(viewport, spriteBatch);

        createMenu();
        Gdx.input.setInputProcessor(stage);
    }

    private void createMenu() {
        Label.LabelStyle labelStyle = new Label.LabelStyle(Graphics.getFont(), Color.WHITE);

        Label howToPlayLabel = new Label(HOW_TO_PLAY_LABEL, labelStyle);
        howToPlayLabel.setFontScale(Graphics.BITMAP_FONT_SCALING*0.4f);
        howToPlayLabel.setWrap(true);
        howToPlayLabel.setAlignment(Align.center);

        Stack howToPlayStack = new Stack(new Image(Graphics.getPausedGameBackgroundTextureRegion()), howToPlayLabel);

        final ImageButton backButton = Utils.createButtonWithImage(Graphics.getBackButtonTextureRegionDrawable());

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(backButton.isPressed()) {
                    bounce.setProgramState(Bounce.ProgramState.MAIN_MENU);
                    GameSound.playButtonClickSound();
                }

            }
        });

        table = new Table();
        table.setFillParent(true);
        table.setBackground(Graphics.getMenuBackgroundTextureRegion());
        table.align(Align.center);
        table.pad(Gdx.graphics.getHeight()/3.25f, Gdx.graphics.getWidth()/7f,
                Gdx.graphics.getHeight()/3.5f, Gdx.graphics.getWidth()/7f);

        table.add(howToPlayStack).expandX().fill(true, false);

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
