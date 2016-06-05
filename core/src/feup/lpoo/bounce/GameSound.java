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
    public static boolean muted = true;

    /**
     * Plays the loss sound if the game is not muted
     */
    public static void playLossSound() {
        if(muted)
            return;

        if(lossSound == null)
            lossSound = Gdx.audio.newSound(Gdx.files.internal("sounds/lose.mp3"));

        lossSound.play();
    }

    /**
     * Plays the win sound if the game is not muted
     */
    public static void playWinSound() {
        if(muted)
            return;

        if(winSound == null)
            winSound = Gdx.audio.newSound(Gdx.files.internal("sounds/win.mp3"));

        winSound.play();
    }

    /**
     * Plays the jumping sound if the game is not muted
     */
    public static void playJumpingSound() {
        if(muted)
            return;

        if(jumpingSound == null)
            jumpingSound = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.mp3"));

        jumpingSound.play();
    }

    /**
     * Plays the pick up sound if the game is not muted
     */
    public static void playPickUpSound() {
        if(muted)
            return;

        if(pickUpSound == null)
            pickUpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/pick_up_items.mp3"));

        pickUpSound.play();
    }
}
