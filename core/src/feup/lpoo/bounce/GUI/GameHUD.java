package feup.lpoo.bounce.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import feup.lpoo.bounce.Bounce;
import feup.lpoo.bounce.Bounce.ProgramState;
import feup.lpoo.bounce.GameSound;
import feup.lpoo.bounce.Utils;
import feup.lpoo.bounce.logic.BounceGame;

/**
 * Created by Bernardo on 02-06-2016.
 */
public class GameHUD extends Stage implements InputProcessor {
    public static final float RING_SPRITE_SCALING = 0.5f;

    private SpriteBatch spriteBatch;

    private Sprite ringSprite;

    private Label scoreLabel;
    private ImageButton pauseButton;
    private Label ringsLabel;
    private Table table;

    private BounceGame game;
    private Bounce bounce;

    public GameHUD(Bounce bounce, final BounceGame game) {
        super(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera()), new SpriteBatch());
        this.bounce = bounce;
        this.game = game;

        spriteBatch = new SpriteBatch();

        addActor(createHUD());
        Gdx.input.setInputProcessor(this);
    }

    //FIXME: LISTENER NOT WORKING
    private void pauseButtonClicked() {
        game.pauseGame();
        bounce.setProgramState(ProgramState.GAME_PAUSED);
    }

    private Table createHUD() {
        ringSprite = new Sprite(Graphics.getTrimmedRingTextureRegion());
        ringSprite.scale(RING_SPRITE_SCALING);

        Label.LabelStyle labelStyle = new Label.LabelStyle(Graphics.getFont(), Color.WHITE);

        Image ringsImage = new Image(Graphics.getRingTextureRegion());
        ringsLabel = new Label(String.format("%02d", game.getRingsLeft()), labelStyle);
        ringsLabel.setFontScale(Graphics.BITMAP_FONT_SCALING*0.75f);

        pauseButton = Utils.createButtonWithImage(Graphics.getPauseButtonTextureRegionDrawable());

        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log("Listener", "CALLED!!!!!");
                if(pauseButton.isPressed()) {
                    game.pauseGame();
                    bounce.setProgramState(ProgramState.GAME_PAUSED);
                }
            }
        });

        scoreLabel = new Label(String.format("%06d", game.getScore()), new Label.LabelStyle(Graphics.getFont(), Color.WHITE));
        scoreLabel.setFontScale(Graphics.BITMAP_FONT_SCALING);

        table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        table.padTop(Gdx.graphics.getHeight()/44f);
        table.top();

        table.add(ringsImage).padLeft(Gdx.graphics.getWidth()/20f).center();
        table.add(ringsLabel).padLeft(Gdx.graphics.getWidth()/120f).center();
        table.add(scoreLabel).align(Align.center).expandX().center();
        table.add(pauseButton).padRight(Gdx.graphics.getWidth()/44f).align(Align.right).uniform().center();

        return table;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(!super.touchDown(screenX, screenY, pointer, button)) {
            GameSound.playJumpingSound();
            game.ballJump();
            return true;
        }
        else if(pauseButton.isPressed()) {
            //FIXME: LISTENER NOT WORKING
            pauseButtonClicked();
            return true;
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void draw() {
        super.draw();
        scoreLabel.setText(String.format("%06d", game.getScore()));
        ringsLabel.setText(String.format("%02d", game.getRingsLeft()));
    }
}
