package feup.lpoo.bounce.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by Bernardo on 05-06-2016.
 */
public class Graphics {
    public static final int TEXTURE_SIZE = 64;
    private static final String BITMAP_FONT = "comfortaa.fnt";

    public static final float BITMAP_FONT_SCALING = 0.75f;

    private static BitmapFont font;
    private static Texture tileset;

    public static BitmapFont getFont() {
        if(font == null)
            font = new BitmapFont(Gdx.files.internal(BITMAP_FONT));

        return font;
    }

    public static Texture getTileset() {
        if(tileset == null)
            tileset = new Texture(Gdx.files.internal("tileset.png"));

        return tileset;
    }

}
