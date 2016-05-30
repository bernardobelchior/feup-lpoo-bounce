package feup.lpoo.bounce.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;

import feup.lpoo.bounce.logic.BounceGame;

/**
 * Created by Bernardo on 30-05-2016.
 */
public class GameScreen implements Screen {
    private static final int PIXELS_PER_METER = 100;
    private static final int VIRTUAL_WIDTH = 640;
    private static final int VIRTUAL_HEIGHT = 640;

    BounceGame game;

    OrthographicCamera camera;
    FitViewport viewport;
    OrthogonalTiledMapRenderer renderer;
    Box2DDebugRenderer b2dr;

    public GameScreen (BounceGame game) {
        this.game = game;

        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(VIRTUAL_WIDTH/PIXELS_PER_METER, VIRTUAL_HEIGHT/PIXELS_PER_METER, camera);
        this.renderer = new OrthogonalTiledMapRenderer(game.getMap(), 1/PIXELS_PER_METER);
        this.b2dr = new Box2DDebugRenderer();

        //camera.position.set(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2, 0);
        camera.position.set(0, 0, 0);
    }

    public void update(float delta) {

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        renderer.setView(camera);

        renderer.render();

        b2dr.render(game.getWorld(), camera.combined);
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
}
