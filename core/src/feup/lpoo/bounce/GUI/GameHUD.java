package feup.lpoo.bounce.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import feup.lpoo.bounce.Bounce;
import feup.lpoo.bounce.Bounce.ProgramState;
import feup.lpoo.bounce.Utils;
import feup.lpoo.bounce.logic.BounceGame;
import feup.lpoo.bounce.Bounce.GameState;

/**
 * Created by Bernardo on 02-06-2016.
 */
public class GameHUD extends Stage implements InputProcessor {
    private static final float FONT_SCALE = 5;
    public static final float RING_SPRITE_SCALING = 0.5f;

    private SpriteBatch spriteBatch;

    private Texture tileset;

    private TextureRegion ballTextureRegion;
    private Sprite ringSprite;

    private Label scoreLabel;
    private ImageButton pauseButton;
    private Table table;

    private BounceGame game;
    private Bounce bounce;

    public GameHUD(Bounce bounce, final BounceGame game) {
        super(new FitViewport(game.getMapHeight() *(float)Gdx.graphics.getWidth()/Gdx.graphics.getHeight(), game.getMapHeight(), new OrthographicCamera()), new SpriteBatch());
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
        //FIXME: GameScreen has the same variables. Find a way to avoid having a two copies of the same thing.
        TextureRegionDrawable pauseTexture = new TextureRegionDrawable(new TextureRegion(new Texture("pause.png")));
        tileset = new Texture("tileset.png");
        ballTextureRegion = new TextureRegion(tileset, 0, 0, Bounce.TEXTURE_SIZE, Bounce.TEXTURE_SIZE);
        TextureRegion ringTextureRegion = new TextureRegion(tileset, 0, Bounce.TEXTURE_SIZE, Bounce.TEXTURE_SIZE, Bounce.TEXTURE_SIZE);
        ringSprite = new Sprite(ringTextureRegion);
        ringSprite.scale(RING_SPRITE_SCALING);

        pauseButton = Utils.createButtonWithImage(pauseTexture);

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

        scoreLabel = new Label(String.format("%06d", game.getScore()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel.setFontScale(FONT_SCALE);

        table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        table.top();
        table.add().uniform();
        table.add(scoreLabel).align(Align.center).expandX();
        table.add(pauseButton).padRight(Gdx.graphics.getWidth()/44f).align(Align.right).uniform();

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

        spriteBatch.begin();

        for(int i = 0; i < game.getRings().size(); i++) {
            spriteBatch.draw(ringSprite, Gdx.graphics.getWidth()/15f + ringSprite.getWidth()*i,
                    Gdx.graphics.getHeight() - Bounce.TEXTURE_SIZE - Gdx.graphics.getHeight()/50f);
        }

        spriteBatch.end();
    }
}
