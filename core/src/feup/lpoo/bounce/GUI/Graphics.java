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

    //Program font
    private static BitmapFont font;

    //Tilesets for the game and buttons
    private static Texture gameTileset;
    private static Texture buttonTileset;

    //Game sprites
    private static TextureRegion ballTextureRegion;
    private static TextureRegion ringTextureRegion;
    private static TextureRegion trimmedRingTextureRegion;
    private static TextureRegion gemTextureRegion;

    //Buttons
    private static TextureRegionDrawable backButtonTextureRegion;
    private static TextureRegionDrawable nextButtonTextureRegion;
    private static TextureRegionDrawable retryButtonTextureRegion;
    private static TextureRegionDrawable pauseButtonTextureRegion;
    private static TextureRegionDrawable emptyButtonTextureRegion;


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
}
