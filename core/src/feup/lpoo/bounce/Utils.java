package feup.lpoo.bounce;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Bernardo on 03-06-2016.
 */
public class Utils {

    public static ImageButton createButtonWithImage(TextureRegionDrawable textureRegionDrawable) {
        ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.pressedOffsetX = 2;
        imageButtonStyle.pressedOffsetY = -2;
        imageButtonStyle.imageUp = textureRegionDrawable;
        imageButtonStyle.imageDown = textureRegionDrawable;
        return new ImageButton(imageButtonStyle);
    }
}
