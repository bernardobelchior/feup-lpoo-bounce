package feup.lpoo.bounce.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;

import feup.lpoo.bounce.Bounce;
import feup.lpoo.bounce.logic.BounceGame;
import feup.lpoo.bounce.logic.Monster;

/**
 * Created by Bernardo on 30-05-2016.
 */
public class GameScreen implements Screen {
    private Bounce bounce;
    private BounceGame game;

    private OrthographicCamera camera;
    private FitViewport viewport;
    private OrthogonalTiledMapRenderer renderer;
    private GameHUD gameHUD;
    private Box2DDebugRenderer b2dr;

    private SpriteBatch spriteBatch;

    public GameScreen(Bounce bounce, BounceGame game) {
        this.bounce = bounce;
        this.game = game;

        spriteBatch = new SpriteBatch();

        camera = new OrthographicCamera();
        viewport = new FitViewport(game.getMapHeight() *(float)Gdx.graphics.getWidth()/Gdx.graphics.getHeight()*BounceGame.PIXELS_PER_METER, game.getMapHeight()*BounceGame.PIXELS_PER_METER, camera);
        renderer = new OrthogonalTiledMapRenderer(game.getMap(), 1);
        b2dr = new Box2DDebugRenderer();
        gameHUD = new GameHUD(bounce, game);

        camera.position.set(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2, 0);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Sets the camera x coordinate so it follows the ball
        //The below if statements are to make sure that no black
        //bars appear on the right nor on the left.'
        float cameraX = game.getBall().getPosition().x*BounceGame.PIXELS_PER_METER - Graphics.GAME_TEXTURE_SIZE /2;

        if(cameraX < viewport.getWorldWidth()/2)
            cameraX = viewport.getWorldWidth()/2;
        else if(cameraX > game.getMapWidth()*BounceGame.PIXELS_PER_METER - viewport.getWorldWidth()/2)
            cameraX = game.getMapWidth()*BounceGame.PIXELS_PER_METER - viewport.getWorldWidth()/2;

        camera.position.set(cameraX, viewport.getWorldHeight()/2, 0);

        //Updates the camera and renders the screen
        camera.update();
        renderer.setView(camera);
        renderer.render();
        //b2dr.render(game.getWorld(), camera.combined);

        //Draws the ball on its position
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(Graphics.getBallTextureRegion(),
                game.getBall().getPosition().x*BounceGame.PIXELS_PER_METER  - Graphics.GAME_TEXTURE_SIZE/2, //x coordinate
                game.getBall().getPosition().y*BounceGame.PIXELS_PER_METER  - Graphics.GAME_TEXTURE_SIZE/2, //y coordinate
                Graphics.GAME_TEXTURE_SIZE/2, Graphics.GAME_TEXTURE_SIZE/2, //origin coordinates
                Graphics.GAME_TEXTURE_SIZE, Graphics.GAME_TEXTURE_SIZE, //texture size
                1, 1, //scaling
                game.getBall().getAngle()/(float)Math.PI*180); //rotation

        //Draws all the rings in the correct position
        for(Body ring : game.getRings()) {
            spriteBatch.draw(Graphics.getRingTextureRegion(), ring.getPosition().x*BounceGame.PIXELS_PER_METER  - Graphics.GAME_TEXTURE_SIZE /2,
                    ring.getPosition().y*BounceGame.PIXELS_PER_METER - Graphics.GAME_TEXTURE_SIZE /2);
        }

        //Draws all the gems in the correct position
        for(Body gem : game.getGems()) {
            spriteBatch.draw(Graphics.getGemTextureRegion(), gem.getPosition().x*BounceGame.PIXELS_PER_METER  - Graphics.GAME_TEXTURE_SIZE /2,
                    gem.getPosition().y*BounceGame.PIXELS_PER_METER  - Graphics.GAME_TEXTURE_SIZE /2);
        }

        //Draws all the monsters in the correct position
        for(Monster monster : game.getMonsters()) {
            spriteBatch.draw(Graphics.getMonsterTextureRegion(), (monster.getPosition().x*BounceGame.PIXELS_PER_METER - Graphics.GAME_TEXTURE_SIZE),
                    monster.getPosition().y*BounceGame.PIXELS_PER_METER  - Graphics.GAME_TEXTURE_SIZE);
        }

        spriteBatch.end();

        gameHUD.draw();
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

