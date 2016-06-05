package feup.lpoo.bounce;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Bernardo on 05-06-2016.
 */
public class GameSound {

    private static Sound lossSound;
    private static Sound winSound;
    private static Sound jumpingSound;
    private static Sound pickUpSound;

    public static Sound getLossSound() {
        if(lossSound == null)
            lossSound = Gdx.audio.newSound(Gdx.files.internal("sounds/lose.mp3"));

        return lossSound;
    }

    public static Sound getWinSound() {
        if(winSound == null)
            winSound = Gdx.audio.newSound(Gdx.files.internal("sounds/win.mp3"));

        return winSound;
    }

    public static Sound getJumpingSound() {
        if(jumpingSound == null)
            jumpingSound = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.mp3"));

        return jumpingSound;
    }

    public static Sound getPickUpSound() {
        if(pickUpSound == null)
            pickUpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/pick_up_items.mp3"));

        return pickUpSound;
    }
}
