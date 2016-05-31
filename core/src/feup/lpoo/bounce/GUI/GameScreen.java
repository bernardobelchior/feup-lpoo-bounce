package feup.lpoo.bounce.GUI;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import feup.lpoo.bounce.logic.BounceGame;

/**
 * Created by Bernardo on 30-05-2016.
 */
public class GameScreen implements Screen, InputProcessor {

    private static final int VIRTUAL_WIDTH = 1280;
    private static final int VIRTUAL_HEIGHT = 640;

    private BounceGame game;

    private OrthographicCamera camera;
    private FitViewport viewport;
    private OrthogonalTiledMapRenderer renderer;
    private Box2DDebugRenderer b2dr;

    private Texture ballTexture;
    private SpriteBatch spriteBatch;

    public GameScreen (BounceGame game) {
        this.game = game;

        this.camera = new OrthographicCamera();
        //this.viewport = new FitViewport(VIRTUAL_WIDTH/PIXELS_PER_METER, VIRTUAL_HEIGHT/PIXELS_PER_METER, camera);
        this.viewport = new FitViewport(Gdx.graphics.getWidth()/BounceGame.PIXELS_PER_METER, Gdx.graphics.getHeight()/BounceGame.PIXELS_PER_METER, camera);
        this.renderer = new OrthogonalTiledMapRenderer(game.getMap(), 1/BounceGame.PIXELS_PER_METER);
        this.b2dr = new Box2DDebugRenderer();

        this.spriteBatch = new SpriteBatch();
        this.ballTexture = new Texture("ball.png");

        camera.position.set(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2, 0);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        renderer.setView(camera);

        renderer.render();

        b2dr.render(game.getWorld(), camera.combined);

        Shape ballShape = game.getBall().getFixtureList().get(0).getShape();

        //spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(new Sprite(ballTexture), game.getBall().getPosition().x - ballShape.getRadius()/2,
                game.getBall().getPosition().y - ballShape.getRadius()/2);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        game.ballJump();
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

