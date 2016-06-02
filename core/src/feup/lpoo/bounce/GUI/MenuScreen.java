package feup.lpoo.bounce.GUI;

/**
 * Created by Margarida on 01-Jun-16.
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import feup.lpoo.bounce.Bounce;
import feup.lpoo.bounce.logic.BounceGame;

/**
 * Created by Jose on 09/05/2016.
 */
public class MenuScreen implements Screen {

    private BounceGame game;
    private OrthographicCamera menuCam;
    private Viewport menuPort;

    private ImageButton playButton;
    private ImageButton instructionsButton;
    private ImageButton settingsButton;
    private ImageButton highScoresButton;
    private ImageButton exitButton;
    private TextureAtlas buttonsPack;
    private Skin skin;
    private ImageButton.ImageButtonStyle style;
    private Texture background;
    private Stage stage;
    private float width;
    private float height;

    public MenuScreen(BounceGame game){

        this.game=game;

        background = new Texture("backgroundMenu.png");
        width = background.getWidth();
        height = background.getHeight();
        menuCam=new OrthographicCamera();
        menuPort = new StretchViewport(background.getWidth(),background.getHeight(),menuCam);
        stage = new Stage(menuPort);
        stage.clear();
        Gdx.input.setInputProcessor(stage);

        //buttons
        buttonsPack = new TextureAtlas("MenuButtons.atlas");
        skin = new Skin();
        skin.addRegions(buttonsPack);
        style = new ImageButton.ImageButtonStyle();

        style = new ImageButton.ImageButtonStyle();
        style.up = skin.getDrawable("Play");
        style.down = skin.getDrawable("Play");
        playButton = new ImageButton(style);
        playButton.setPosition(width/2-playButton.getWidth()/2,height/2+100);

        style = new ImageButton.ImageButtonStyle();
        style.up = skin.getDrawable("How to Play");
        style.down = skin.getDrawable("How to Play");
        playButton = new ImageButton(style);
        playButton.setPosition(width/2-playButton.getWidth()/2,height/2);


        style = new ImageButton.ImageButtonStyle();
        style.up = skin.getDrawable("Settings");
        style.down = skin.getDrawable("Settings");
        highScoresButton = new ImageButton(style);
        highScoresButton.setPosition(width/2-playButton.getWidth()/2,height/2-100);

        style = new ImageButton.ImageButtonStyle();
        style.up = skin.getDrawable("High Scores");
        style.down = skin.getDrawable("High Scores");
        settingsButton = new ImageButton(style);
        settingsButton.setPosition(width/2-playButton.getWidth()/2,height/2-200);

        style = new ImageButton.ImageButtonStyle();
        style.up = skin.getDrawable("Exit");
        style.down = skin.getDrawable("Exit");
        exitButton = new ImageButton(style);
        exitButton.setPosition(width/2-playButton.getWidth()/2+200, height/2-200);


        stage.addActor(playButton);
        stage.addActor(instructionsButton);
        stage.addActor(highScoresButton);
        stage.addActor(settingsButton);
        stage.addActor(exitButton);


    }

    public void handleInput(float delta){

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Screen screen = new GameScreen(game);
                game.setScreen(screen);
                /*game.screens.push(screen);
                game.setScreen(game.screens.peek());*/
                dispose();
            }
        });

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                Gdx.app.exit();
            }
        });

    }

    public void update(float delta){
        handleInput(delta);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        update(delta);

        menuCam.update();
        game.getBatch().setProjectionMatrix(menuCam.combined);
        game.getBatch().begin();
        game.getBatch().draw(background, 0, 0);
        game.getBatch().end();
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
        background.dispose();
        stage.dispose();
        buttonsPack.dispose();
        skin.dispose();
    }
}