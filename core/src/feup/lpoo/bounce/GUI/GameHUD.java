package feup.lpoo.bounce.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
public class GameHUD {
    public static final float RING_SPRITE_SCALING = 0.5f;

    private Sprite ringSprite;

    private Label scoreLabel;
    private ImageButton pauseButton;
    private Label ringsLabel;
    private Table table;

    private FitViewport viewport;
    private SpriteBatch spriteBatch;
    private Stage stage;

    private BounceGame game;
    private Bounce bounce;

    public GameHUD(Bounce bounce, final BounceGame game) {
        this.bounce = bounce;
        this.game = game;

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        spriteBatch = new SpriteBatch();
        stage = new Stage(viewport, spriteBatch);

        Gdx.input.setInputProcessor(stage);
        createHUD();
    }

    private void createHUD() {
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
                if(pauseButton.isPressed()) {
                    game.pauseGame();
                    bounce.setProgramState(ProgramState.GAME_PAUSED);
                    GameSound.playButtonClickSound();
                }
            }
        });

        scoreLabel = new Label(String.format("%06d", game.getScore()), new Label.LabelStyle(Graphics.getFont(), Color.WHITE));
        scoreLabel.setFontScale(Graphics.BITMAP_FONT_SCALING);

        table = new Table();
        table.setFillParent(true);
        table.padTop(Gdx.graphics.getHeight()/44f);
        table.top();

        table.add(ringsImage).padLeft(Gdx.graphics.getWidth()/40f).center();
        table.add(ringsLabel).padLeft(Gdx.graphics.getWidth()/240f).center();
        table.add(scoreLabel).align(Align.center).expandX().center();
        table.add(pauseButton).padRight(Gdx.graphics.getWidth()/44f).align(Align.right).uniform().center();

        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!pauseButton.isPressed()) {
                    GameSound.playJumpingSound();
                    game.ballJump();
                }
            }
        });

        stage.addActor(table);
    }

    public void draw() {
        scoreLabel.setText(String.format("%06d", game.getScore()));
        ringsLabel.setText(String.format("%02d", game.getRingsLeft()));

        stage.draw();
    }
}
