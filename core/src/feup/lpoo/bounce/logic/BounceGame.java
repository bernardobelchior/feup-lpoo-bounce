package feup.lpoo.bounce.logic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import feup.lpoo.bounce.GUI.GameScreen;

/**
 * Created by Bernardo on 30-05-2016.
 */
public class BounceGame extends Game {
    final static Vector2 GRAVITY = new Vector2(0, 0);

    TiledMap map;

    World world;

    @Override
    public void create() {
        //map = new TmxMapLoader().load("level" + level + ".tmx");
        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        world = new World(GRAVITY, true);

        setScreen(new GameScreen(this));
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }
}
