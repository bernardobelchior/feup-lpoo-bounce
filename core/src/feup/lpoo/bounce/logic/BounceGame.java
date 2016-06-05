package feup.lpoo.bounce.logic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;

import java.io.IOException;
import java.util.ArrayList;

import feup.lpoo.bounce.Bounce;
import feup.lpoo.bounce.Bounce.GameState;
import feup.lpoo.bounce.GameSound;

/**
 * Created by Bernardo on 30-05-2016.
 *
 * Class that handles all the Bounce Game logic.
 */
public class BounceGame extends Game {
    //World gravity
    private final static Vector2 WORLD_GRAVITY = new Vector2(0, -800);

    //Game update rate in seconds
    private final static float GAME_UPDATE_RATE = 1/300f;

    //Used to check if the phone is relatively standing still
    private final static float HORIZONTAL_ACCELERATION_TOLERANCE = 1f;

    //Movement modifiers
    private final static int HORIZONTAL_MOVEMENT_MODIFIER = 1000000;
    private final static int ATTRITION_MODIFIER = 10000;
    private final static int JUMP_HEIGHT_MODIFIER = 1400000;

    //Score that the objects below yield for the player
    private static final int GEM_SCORE = 5;
    private static final int RING_SCORE = 1;

    //Map dimensions
    private int mapWidth;
    private int mapHeight;

    private TiledMap map;
    private World world;

    private Body ball;
    private Timer gameTimer;
    private GameState gameState;
    private int score;

    private ArrayList<Body> rings;
    private ArrayList<Body> gems;

    private int level;
    private int highscore;
    private boolean canBallJump;

    private static final String HIGHSCORE_FILE_NAME = "highscore";
    private static final String HIGHSCORE_FILE_EXTENSION = ".dat";

    private ArrayList<Body> destroyNextUpdate;

    /**
     * Game constructor. Uses level to load the map file.
     * @param level level to load
     */
    public BounceGame(int level) {
        this.gameState = Bounce.GameState.PAUSED;
        this.level = level;

        map = new TmxMapLoader().load("level" + level + ".tmx");

        mapWidth = map.getProperties().get("width", Integer.class).intValue()*map.getProperties().get("tilewidth", Integer.class).intValue();
        mapHeight = map.getProperties().get("height", Integer.class).intValue()*map.getProperties().get("tileheight", Integer.class).intValue();

        gameTimer = new Timer();
        gameTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                //FIXME: Actually use the elapsed time
                update(GAME_UPDATE_RATE);
            }
        }, 0, GAME_UPDATE_RATE);

        setUpWorld();
        start();
    }

    /**
     * Loads highscore from the respective file.
     */
    private void loadHighscore() {
        FileHandle highscoreFile = Gdx.files.local(HIGHSCORE_FILE_NAME + level + HIGHSCORE_FILE_EXTENSION);

        if(highscoreFile.exists()) {
            String highscoreString = highscoreFile.readString();
            highscore = Integer.parseInt(highscoreString);
        }
        else
            highscore = 0;
    }

    /**
     * Loads the map and sets up the world.
     */
    private void setUpWorld() {
        world = new World(WORLD_GRAVITY, true);
        LevelLoader levelLoader = new LevelLoader();
        levelLoader.load(map, world);
        ball = levelLoader.getBall();
        rings = levelLoader.getRings();
        gems = levelLoader.getGems();
        mapHeight = levelLoader.getMapHeight();
        mapWidth = levelLoader.getMapWidth();

        score = 0;
        canBallJump = true;
        destroyNextUpdate = new ArrayList<Body>();

        world.setContactListener(new BounceContactListener(this));
    }

    /**
     * Starts the game if is not already running.
     * @return true if the game was started.
     */
    public boolean start() {
        if(gameState == Bounce.GameState.RUNNING)
            return false;

        loadHighscore();
        gameTimer.start();
        gameState = Bounce.GameState.RUNNING;

        return true;
    }

    /**
     * Updates the game logic.
     * @param deltaTime Delta time since the last update
     */
    public void update(float deltaTime) {
        if(!isRunning())
            return;

        //Workaround because deleting bodies while world.step
        //is running is dangerous and can lead to unexpected crashes
        for(Body toDelete : destroyNextUpdate) {
            world.destroyBody(toDelete);
        }

        destroyNextUpdate.clear();

        float horizontalAcceleration = Gdx.input.getAccelerometerY();

        //Moves the ball depending on the accelerometer
        if(Math.abs(horizontalAcceleration) > HORIZONTAL_ACCELERATION_TOLERANCE)
            ball.applyForceToCenter(horizontalAcceleration* HORIZONTAL_MOVEMENT_MODIFIER, 0, true);


        //Attrition application
        ball.applyForceToCenter(-ball.getLinearVelocity().x* ATTRITION_MODIFIER, 0, true);

        world.step(deltaTime, 6, 2);
    }

    /**
     * Gets map
     * @return Map
     */
    public TiledMap getMap() {
        return map;
    }

    /**
     * Gets world
     * @return World
     */
    public World getWorld() {
        return world;
    }

    /**
     * Gets ball
     * @return Ball
     */
    public Body getBall() {
        return ball;
    }

    /**
     * Tries to make the ball jump.
     * @return True if the ball has jumped, returning false otherwise.
     */
    public boolean ballJump() {
        if(!isRunning() || !canBallJump)
            return false;

        ball.applyForceToCenter(-WORLD_GRAVITY.x, JUMP_HEIGHT_MODIFIER *-WORLD_GRAVITY.y, true);
        canBallJump = false;
        return true;
    }

    /**
     * Creates the game
     */
    @Override
    public void create() {

    }

    /**
     * Ends the game with a loss for the player.
     */
    public void over() {
        saveScore();
        GameSound.playLossSound();
        gameState = Bounce.GameState.LOSS;
        gameTimer.stop();
    }

    /**
     * Saves the player score to the respective file, if it is higher than the highscore.
     */
    private void saveScore() {
        if(score > highscore) {
            FileHandle file = Gdx.files.local(HIGHSCORE_FILE_NAME + level + HIGHSCORE_FILE_EXTENSION);

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

    /**
     * Ends the game with a win for the player.
     */
    public void win() {
        saveScore();
        GameSound.playWinSound();
        gameState = Bounce.GameState.WIN;
        gameTimer.stop();
    }

    /**
     * Gets whether the game is running or not.
     * @return true if the game is running.
     */
    public boolean isRunning() {
        return gameState == Bounce.GameState.RUNNING;
    }

    /**
     * Gets the player score
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * Destroys the ring passed as argument in the next update process.
     * @param ring Ring to be destroyed
     */
    public void ringDestroyed(Body ring) {
        destroyNextUpdate.add(ring);
        rings.remove(ring);
        score += RING_SCORE;

        if(rings.size() == 0)
            win();
    }

    /**
     * Destroys the gem passed as argument in the next update process.
     * @param gem Gem to be destroyed
     */
    public void gemDestroyed(Body gem) {
        destroyNextUpdate.add(gem);
        gems.remove(gem);
        score += GEM_SCORE;
    }

    /**
     * Gets the rings array
     * @return Rings array
     */
    public ArrayList<Body> getRings() {
        return rings;
    }

    /**
     * Gets the gems array
     * @return Gems array
     */
    public ArrayList<Body> getGems() {
        return gems;
    }

    /**
     * Gets the game state
     * @return gameState
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Disposes the unnecessary resources.
     */
    @Override
    public void dispose() {
        super.dispose();
        map.dispose();
        world.dispose();
    }

    /**
     * Restarts the game by reloading the level.
     */
    public void restart() {
        setUpWorld();
        start();
    }

    /**
     * Tries to increment the level and load the corresponding map.
     * If this current level is the last one, returns false.
     * @return True if the level was successfully incremented
     */
    public boolean nextLevel() {
        if(level > Bounce.NUMBER_OF_LEVELS)
            return false;

        level++;
        map = new TmxMapLoader().load("level" + level + ".tmx");

        restart();

        return true;
    }

    /**
     * Pauses the game.
     */
    public void pauseGame() {
        gameState = Bounce.GameState.PAUSED;
        gameTimer.stop();
    }

    /**
     * Gets the current level
     * @return level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the map width
     * @return mapWidth
     */
    public int getMapWidth() {
        return mapWidth;
    }

    /**
     * Gets the map height
     * @return mapHeight
     */
    public int getMapHeight() {
        return mapHeight;
    }

    /**
     * Enables the ball to jump.
     */
    public void enableBallJump() {
        canBallJump = true;
    }

    /**
     * Gets the number of rings left.
     * @return Number of rings left.
     */
    public int getRingsLeft() {
        return rings.size();
    }
}
