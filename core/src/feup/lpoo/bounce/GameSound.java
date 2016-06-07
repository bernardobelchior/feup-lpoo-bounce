package feup.lpoo.bounce;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Bernardo on 05-06-2016.
 */
public class GameSound {

    private static Sound lossSound;
    private static Sound winSound;
    private static Sound jumpingSound;
    private static Sound pickUpSound;
    private static Music music;
    public static boolean soundMuted = false;
    private static boolean musicMuted = false;

    /**
     * Plays the loss sound if the sound is not muted
     */
    public static void playLossSound() {
        if(soundMuted)
            return;

        if(lossSound == null)
            lossSound = Gdx.audio.newSound(Gdx.files.internal("sounds/lose.mp3"));

        lossSound.play();
    }

    /**
     * Plays the win sound if the sound is not muted
     */
    public static void playWinSound() {
        if(soundMuted)
            return;

        if(winSound == null)
            winSound = Gdx.audio.newSound(Gdx.files.internal("sounds/win.mp3"));

        winSound.play();
    }

    /**
     * Plays the jumping sound if the sound is not muted
     */
    public static void playJumpingSound() {
        if(soundMuted)
            return;

        if(jumpingSound == null)
            jumpingSound = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.mp3"));

        jumpingSound.play();
    }

    /**
     * Plays the pick up sound if the sound is not muted
     */
    public static void playPickUpSound() {
        if(soundMuted)
            return;

        if(pickUpSound == null)
            pickUpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/pick_up_items.mp3"));

        pickUpSound.play();
    }

    private static void playMusic() {
        if(musicMuted)
            return;

        music.setLooping(true);
        music.play();
    }

    private static void stopMusic() {
         music.stop();
    }

    public static boolean getMusicMuted() {
        return musicMuted;
    }

    public static void setMusicMuted(boolean musicMuted) {
        GameSound.musicMuted = musicMuted;

        if(music == null)
            music = Gdx.audio.newMusic(Gdx.files.internal("sounds/menu_music_1.wav"));

//        if(music == null)
  //          music = Gdx.audio.newSound(Gdx.files.internal("sounds/menu_music_1.mp3"));

        if(musicMuted) {
            stopMusic();
        } else
            playMusic();
    }
}
