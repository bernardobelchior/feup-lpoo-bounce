package feup.lpoo.bounce.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.viewport.FitViewport;

import feup.lpoo.bounce.logic.BounceGame;

/**
 * Created by Bernardo on 30-05-2016.
 */
public class GameScreen implements Screen, InputProcessor {
    private BounceGame game;

    private OrthographicCamera camera;
    private FitViewport viewport;
    private OrthogonalTiledMapRenderer renderer;
    private GameHUD gameHUD;
    private Box2DDebugRenderer b2dr;

    private Texture tileset;
    private static final int TEXTURE_SIZE = 64;

    //Texture regions
    private TextureRegion ballTextureRegion;
    private TextureRegion ringTextureRegion;
    private TextureRegion gemTextureRegion;


    private SpriteBatch spriteBatch;
    private float aspectRatio;


    public GameScreen (BounceGame game) {
        this.game = game;

        aspectRatio = (float)Gdx.graphics.getWidth()/Gdx.graphics.getHeight();

        spriteBatch = new SpriteBatch();
        tileset = new Texture("tileset.png");
        ballTextureRegion = new TextureRegion(tileset, 0, 0, TEXTURE_SIZE, TEXTURE_SIZE);
        ringTextureRegion = new TextureRegion(tileset, 0, TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE);
        gemTextureRegion = new TextureRegion(tileset, TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE);

        camera = new OrthographicCamera();
        viewport = new FitViewport(game.mapHeight*aspectRatio, game.mapHeight, camera);
        renderer = new OrthogonalTiledMapRenderer(game.getMap(), 1);
        b2dr = new Box2DDebugRenderer();
        gameHUD = new GameHUD(game, spriteBatch);

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

        Shape ballShape = game.getBall().getFixtureList().get(0).getShape();

        //Sets the camera x coordinate so it follows the ball
        //The below if statements are to make sure that no black
        //bars appear on the right nor on the left.'
        float cameraX = game.getBall().getPosition().x - ballShape.getRadius();

        if(cameraX < viewport.getWorldWidth()/2)
            cameraX = viewport.getWorldWidth()/2;
        else if(cameraX > game.mapWidth - viewport.getWorldWidth()/2)
            cameraX = game.mapWidth - viewport.getWorldWidth()/2;

        camera.position.set(cameraX, viewport.getWorldHeight()/2, 0);
        
        camera.update();
        renderer.setView(camera);

        renderer.render();
        b2dr.render(game.getWorld(), camera.combined);

        gameHUD.render();

        //Draws the ball on its position
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(ballTextureRegion, game.getBall().getPosition().x - ballShape.getRadius(),
                game.getBall().getPosition().y - ballShape.getRadius());

        for(Body ring : game.getRings()) {
            spriteBatch.draw(ringTextureRegion, ring.getPosition().x - ring.getFixtureList().get(0).getShape().getRadius(),
                    ring.getPosition().y - ring.getFixtureList().get(0).getShape().getRadius());
        }

        for(Body gem : game.getGems()) {
            spriteBatch.draw(gemTextureRegion, gem.getPosition().x - gem.getFixtureList().get(0).getShape().getRadius(),
                    gem.getPosition().y - gem.getFixtureList().get(0).getShape().getRadius());
        }

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

