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
    private final static Vector2 WORLD_GRAVITY = new Vector2(0, -10);

    //Game update rate in seconds
    private final static float GAME_UPDATE_RATE = 1/60f;

    //Used to check if the phone is relatively standing still
    private final static float HORIZONTAL_ACCELERATION_TOLERANCE = 1f;

    public final static float PIXELS_PER_METER = 64;

    //Movement modifiers
    private final static float HORIZONTAL_MOVEMENT_MODIFIER = 2f;
    private final static float ATTRITION_MODIFIER = 1.5f;
    private final static float JUMP_HEIGHT_MODIFIER = 40;

    //Score that the objects below yield for the player
    public static final int GEM_SCORE = 5;
    public static final int RING_SCORE = 3;

    //Highscore filenames
    public static final String HIGHSCORE_FILE_NAME = "highscore";
    public static final String HIGHSCORE_FILE_EXTENSION = ".dat";

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
    private ArrayList<Monster> monsters;

    private Integer level;
    private int highscore;
    private volatile boolean canBallJump;

    private ArrayList<Body> destroyNextUpdate;

    /**
     * Game constructor. Uses level to load the map file.
     * @param level level to load
     */
    public BounceGame(int level) {
        this.level = level;

        loadSounds();

        map = new TmxMapLoader().load("levels/level" + level + ".tmx");

        gameState = Bounce.GameState.PAUSED;

        gameTimer = new Timer();
        gameTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                update(GAME_UPDATE_RATE);
            }
        }, 0, GAME_UPDATE_RATE);

        setUpWorld();
    }

    /**
     * Loads sounds as they are probably not be ready to be played
     * when they are needed. As such, we need to load them first
     */
    private void loadSounds() {
        GameSound.playJumpingSound();
        GameSound.playLossSound();
        GameSound.playWinSound();
        GameSound.playPickUpSound();
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
        monsters = levelLoader.getMonsters();
        mapHeight = levelLoader.getMapHeight();
        mapWidth = levelLoader.getMapWidth();

        loadHighscore();
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

        for(Monster monster : monsters) {
            monster.move();
        }

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

        GameSound.playJumpingSound();
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
        map = new TmxMapLoader().load("levels/level" + level + ".tmx");

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

    /**
     * Gets the current highscore.
     * @return highscore
     */
    public int getHighscore() {
        return highscore;
    }

    /**
     * Gets the monsters array
     * @return monsters
     */
    public ArrayList<Monster> getMonsters() {
        return monsters;
    }
}
