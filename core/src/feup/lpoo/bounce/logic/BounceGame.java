/**
 * Created by Bernardo on 30-05-2016.
 */

package feup.lpoo.bounce.logic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import feup.lpoo.bounce.Bounce;

public class BounceGame extends Game {
    //Map dimensions
    private int mapWidth;
    private int mapHeight;

    private TiledMap map;
    private World world;

    private Body ball;
    private Timer gameTimer;
    private Bounce.GameState gameState;
    private int score;

    private ArrayList<Body> rings;
    private ArrayList<Body> gems;

    private int level;
    private int highscore;

    private final Sound LOSS_SOUND;
    private final Sound WIN_SOUND;

    public BounceGame(int level) {
        this.gameState = Bounce.GameState.PAUSED;
        this.level = level;

        LOSS_SOUND = Gdx.audio.newSound(Gdx.files.internal("sounds/lose.mp3"));
        WIN_SOUND = Gdx.audio.newSound(Gdx.files.internal("sounds/win.mp3"));

        map = new TmxMapLoader().load("level" + level + ".tmx");

        mapWidth = map.getProperties().get("width", Integer.class).intValue()*map.getProperties().get("tilewidth", Integer.class).intValue();
        mapHeight = map.getProperties().get("height", Integer.class).intValue()*map.getProperties().get("tileheight", Integer.class).intValue();

        gameTimer = new Timer();
        gameTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                //FIXME: Actually use the elapsed time
                update(Bounce.GAME_UPDATE_RATE);
            }
        }, 0, Bounce.GAME_UPDATE_RATE);

        setUpWorld();
        start();
    }

    private void loadHighscore() {
        FileHandle highscoreFile = Gdx.files.local("highscore.dat");

        if(highscoreFile.exists()) {
            String highscoreString = highscoreFile.readString();
            highscore = Integer.parseInt(highscoreString);
        }
        else
            highscore = 0;
    }

    private void setUpWorld() {
        world = new World(Bounce.WORLD_GRAVITY, true);
        LevelLoader levelLoader = new LevelLoader();
        levelLoader.load(map, world);
        ball = levelLoader.getBall();
        rings = levelLoader.getRings();
        gems = levelLoader.getGems();
        mapHeight = levelLoader.getMapHeight();
        mapWidth = levelLoader.getMapWidth();

        score = 0;

        world.setContactListener(new BounceContactListener(this));
    }

    public boolean start() {
        if(gameState == Bounce.GameState.RUNNING)
            return false;

        loadHighscore();
        gameTimer.start();
        gameState = Bounce.GameState.RUNNING;

        return true;
    }

    public void update(float deltaTime) {
        if(!isRunning())
            return;

        float horizontalAcceleration = Gdx.input.getAccelerometerY();

        //Moves the ball depending on the accelerometer
        if(Math.abs(horizontalAcceleration) > Bounce.HORIZONTAL_ACCELERATION_TOLERANCE)
            ball.applyForceToCenter(horizontalAcceleration* Bounce.HORIZONTAL_MOVEMENT_MODIFIER, 0, true);

        //Attrition application
        ball.applyForceToCenter(-ball.getLinearVelocity().x* Bounce.ATTRITION_MODIFIER, 0, true);

        world.step(deltaTime, 6, 2);
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    public Body getBall() {
        return ball;
    }

    public boolean ballJump() {
        if(!isRunning())
            return false;

        ball.applyForceToCenter(-Bounce.WORLD_GRAVITY.x, Bounce.JUMP_HEIGHT_MODIFIER *-Bounce.WORLD_GRAVITY.y, true);

        return true;
    }

    @Override
    public void create() {

    }

    public void over() {
        saveScore();
        LOSS_SOUND.play();
        gameState = Bounce.GameState.LOSS;
        gameTimer.stop();
        Gdx.app.log("Game", "Lost");
    }

    private void saveScore() {
        if(score > highscore) {
            FileHandle file = Gdx.files.local("highscore.dat");

            try {
                if(!file.exists()) {
                    file.file().createNewFile();
                }

                file.writeString(new Integer(score).toString(), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void win() {
        saveScore();
        WIN_SOUND.play();
        gameState = Bounce.GameState.WIN;
        gameTimer.stop();
        Gdx.app.log("Game", "Won");
    }

    public boolean isRunning() {
        return gameState == Bounce.GameState.RUNNING;
    }

    public int getScore() {
        return score;
    }

    public void ringDestroyed(Body ring) {
        world.destroyBody(ring);
        rings.remove(ring);
        score += Bounce.RING_SCORE;

        if(rings.size() == 0)
            win();
    }

    public void gemDestroyed(Body gem) {
        world.destroyBody(gem);
        gems.remove(gem);
        score += Bounce.GEM_SCORE;
    }

    public ArrayList<Body> getRings() {
        return rings;
    }

    public ArrayList<Body> getGems() {
        return gems;
    }

    public Bounce.GameState getGameState() {
        return gameState;
    }

    @Override
    public void dispose() {
        super.dispose();
        map.dispose();
        world.dispose();
    }

    public void restart() {
        setUpWorld();
        start();
    }

    public boolean nextLevel() {
        if(level > Bounce.NUMBER_OF_LEVELS)
            return false;

        level++;
        map = new TmxMapLoader().load("level" + level + ".tmx");

        restart();

        return true;
    }

    public void pauseGame() {
        gameState = Bounce.GameState.PAUSED;
        gameTimer.stop();
    }

    public int getLevel() {
        return level;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }
}
