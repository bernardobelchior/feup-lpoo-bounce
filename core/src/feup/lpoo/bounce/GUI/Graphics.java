package feup.lpoo.bounce.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Bernardo on 05-06-2016.
 */
public class Graphics {
    public static final int GAME_TEXTURE_SIZE = 64;
    public static final int BUTTON_TEXTURE_SIZE = 100;

    private static final String BITMAP_FONT = "comfortaa.fnt";
    private static final String GAME_TILESET_FILENAME = "tileset.png";
    private static final String BUTTON_TILESET_FILENAME = "button_tileset.png";

    public static final float BITMAP_FONT_SCALING = 0.75f;
    public static final float BACK_LABEL_SCALING = 0.66f;

    //Program font
    private static BitmapFont font;

    //Tilesets for the game and buttons
    private static Texture gameTileset;
    private static Texture buttonTileset;

    //Background
    private static Texture menuBackgroundTexture;
    private static TextureRegionDrawable menuBackgroundTextureRegion;
    private static TextureRegion pausedGameBackgroundTextureRegion;

    //Game sprites
    private static TextureRegion ballTextureRegion;
    private static TextureRegion ringTextureRegion;
    private static TextureRegion trimmedRingTextureRegion;
    private static TextureRegion gemTextureRegion;
    private static TextureRegion monsterTextureRegion;

    //Buttons
    private static TextureRegionDrawable backButtonTextureRegion;
    private static TextureRegionDrawable nextButtonTextureRegion;
    private static TextureRegionDrawable retryButtonTextureRegion;
    private static TextureRegionDrawable pauseButtonTextureRegion;
    private static TextureRegionDrawable emptyButtonTextureRegion;
    private static TextureRegionDrawable menuButtonTextureRegion;
    private static TextureRegionDrawable musicOnButtonTextureRegion;
    private static TextureRegionDrawable musicOffButtonTextureRegion;


    public static BitmapFont getFont() {
        if(font == null)
            font = new BitmapFont(Gdx.files.internal(BITMAP_FONT));

        return font;
    }

    public static Texture getGameTileset() {
        if(gameTileset == null)
            gameTileset= new Texture(Gdx.files.internal(GAME_TILESET_FILENAME));

        return gameTileset;
    }

    public static Texture getButtonTileset() {
        if(buttonTileset == null)
            buttonTileset = new Texture(Gdx.files.internal(BUTTON_TILESET_FILENAME));

        return buttonTileset;
    }

    public static TextureRegion getBallTextureRegion() {
        if(ballTextureRegion == null)
            ballTextureRegion = new TextureRegion(getGameTileset(), 2*GAME_TEXTURE_SIZE, GAME_TEXTURE_SIZE, GAME_TEXTURE_SIZE, GAME_TEXTURE_SIZE);

        return ballTextureRegion;
    }

    public static TextureRegion getRingTextureRegion() {
        if(ringTextureRegion == null)
            ringTextureRegion = new TextureRegion(getGameTileset(), 0, GAME_TEXTURE_SIZE, GAME_TEXTURE_SIZE, GAME_TEXTURE_SIZE);

        return ringTextureRegion;
    }

    public static TextureRegion getGemTextureRegion() {
        if(gemTextureRegion == null)
            gemTextureRegion = new TextureRegion(getGameTileset(), GAME_TEXTURE_SIZE, GAME_TEXTURE_SIZE, GAME_TEXTURE_SIZE, GAME_TEXTURE_SIZE);

        return gemTextureRegion;
    }

    public static TextureRegion getTrimmedRingTextureRegion() {
        if(trimmedRingTextureRegion == null)
            trimmedRingTextureRegion = new TextureRegion(getGameTileset(), 16, GAME_TEXTURE_SIZE, GAME_TEXTURE_SIZE/2, GAME_TEXTURE_SIZE);

        return trimmedRingTextureRegion;
    }

    public static TextureRegionDrawable getEmptyButtonTextureRegionDrawable() {
        if(emptyButtonTextureRegion == null)
            emptyButtonTextureRegion = new TextureRegionDrawable(new TextureRegion(getButtonTileset(), 0, 3*BUTTON_TEXTURE_SIZE, BUTTON_TEXTURE_SIZE, BUTTON_TEXTURE_SIZE));

        return emptyButtonTextureRegion;
    }

    public static TextureRegionDrawable getBackButtonTextureRegionDrawable() {
        if(backButtonTextureRegion == null)
            backButtonTextureRegion = new TextureRegionDrawable(new TextureRegion(getButtonTileset(), 0, 0, BUTTON_TEXTURE_SIZE, BUTTON_TEXTURE_SIZE));

        return backButtonTextureRegion;
    }

    public static TextureRegionDrawable getNextButtonTextureRegionDrawable() {
        if(nextButtonTextureRegion == null)
            nextButtonTextureRegion = new TextureRegionDrawable(new TextureRegion(getButtonTileset(), BUTTON_TEXTURE_SIZE, 0, BUTTON_TEXTURE_SIZE, BUTTON_TEXTURE_SIZE));

        return nextButtonTextureRegion;
    }

    public static TextureRegionDrawable getRetryButtonTextureRegionDrawable() {
        if(retryButtonTextureRegion == null)
            retryButtonTextureRegion = new TextureRegionDrawable(new TextureRegion(getButtonTileset(), BUTTON_TEXTURE_SIZE, BUTTON_TEXTURE_SIZE, BUTTON_TEXTURE_SIZE, BUTTON_TEXTURE_SIZE));

        return retryButtonTextureRegion;
    }

    public static TextureRegionDrawable getPauseButtonTextureRegionDrawable() {
        if(pauseButtonTextureRegion == null)
            pauseButtonTextureRegion = new TextureRegionDrawable(new TextureRegion(getButtonTileset(), 0, BUTTON_TEXTURE_SIZE, BUTTON_TEXTURE_SIZE, BUTTON_TEXTURE_SIZE));

        return pauseButtonTextureRegion;
    }

    public static TextureRegionDrawable getMenuButtonTextureRegion() {
        if(menuButtonTextureRegion == null)
            menuButtonTextureRegion = new TextureRegionDrawable(new TextureRegion(new Texture("menu_button.png")));

        return menuButtonTextureRegion;
    }

    public static Texture getMenuBackgroundTexture() {
        if(menuBackgroundTexture == null)
            menuBackgroundTexture = new Texture(Gdx.files.internal("menu_background.png"));

        return menuBackgroundTexture;
    }

    public static TextureRegionDrawable getMenuBackgroundTextureRegion() {
        if(menuBackgroundTextureRegion == null)
            menuBackgroundTextureRegion = new TextureRegionDrawable(new TextureRegion(getMenuBackgroundTexture()));

        return menuBackgroundTextureRegion;
    }

    public static TextureRegion getMonsterTextureRegion() {
        if(monsterTextureRegion == null)
            monsterTextureRegion = new TextureRegion(getGameTileset(), 0, 4*GAME_TEXTURE_SIZE, 2*GAME_TEXTURE_SIZE, 2*GAME_TEXTURE_SIZE);

        return monsterTextureRegion;
    }

    public static TextureRegion getPausedGameBackgroundTextureRegion() {
        if (pausedGameBackgroundTextureRegion == null)
            pausedGameBackgroundTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("paused_game_background.png")));

        return pausedGameBackgroundTextureRegion;
    }

    public static TextureRegionDrawable getMusicOffButtonTextureRegion() {
        if(musicOffButtonTextureRegion == null)
            musicOffButtonTextureRegion = new TextureRegionDrawable(new TextureRegion(getButtonTileset(), BUTTON_TEXTURE_SIZE, 2*BUTTON_TEXTURE_SIZE, BUTTON_TEXTURE_SIZE, BUTTON_TEXTURE_SIZE));

        return musicOffButtonTextureRegion;
    }

    public static TextureRegionDrawable getMusicOnButtonTextureRegion() {
        if(musicOnButtonTextureRegion == null)
            musicOnButtonTextureRegion = new TextureRegionDrawable(new TextureRegion(getButtonTileset(), 0, 2*BUTTON_TEXTURE_SIZE, BUTTON_TEXTURE_SIZE, BUTTON_TEXTURE_SIZE));

        return musicOnButtonTextureRegion;
    }
}
