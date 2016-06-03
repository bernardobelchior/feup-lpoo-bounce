package feup.lpoo.bounce.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import feup.lpoo.bounce.Bounce;
import feup.lpoo.bounce.Utils;
import feup.lpoo.bounce.logic.BounceGame;
import feup.lpoo.bounce.Bounce.GameState;

/**
 * Created by Bernardo on 02-06-2016.
 */
public class GameHUD {
    private static final float FONT_SCALE = 5;
    public static final float RING_SPRITE_SCALING = 0.5f;

    private Stage stage;
    private FitViewport viewport;

    private SpriteBatch spriteBatch;

    private Texture tileset;

    private TextureRegion ballTextureRegion;
    private Sprite ringSprite;

    private float aspectRatio;

    private Label scoreLabel;
    private Table table;

    private BounceGame game;

    public GameHUD(final BounceGame game, SpriteBatch spriteBatch) {
        this.game = game;
        this.spriteBatch = spriteBatch;

        aspectRatio = (float)Gdx.graphics.getWidth()/Gdx.graphics.getHeight();

        viewport = new FitViewport(game.getMapHeight() *aspectRatio, Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);

        table = new Table();
        table.setFillParent(true);

        //FIXME: GameScreen has the same variables. Find a way to avoid having a two copies of the same thing.
        tileset = new Texture("tileset.png");
        ballTextureRegion = new TextureRegion(tileset, 0, 0, Bounce.TEXTURE_SIZE, Bounce.TEXTURE_SIZE);
        TextureRegion ringTextureRegion = new TextureRegion(tileset, 0, Bounce.TEXTURE_SIZE, Bounce.TEXTURE_SIZE, Bounce.TEXTURE_SIZE);
        ringSprite = new Sprite(ringTextureRegion);
        ringSprite.scale(RING_SPRITE_SCALING);

        scoreLabel = new Label(String.format("%06d", game.getScore()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel.setFontScale(FONT_SCALE);
        table.top().right();
        table.add(scoreLabel).padRight(Gdx.graphics.getWidth()/6f).expandX().align(Align.right);

        stage.addActor(table);
    }

    public void render() {
        scoreLabel.setText(String.format("%06d", game.getScore()));
        spriteBatch.setProjectionMatrix(stage.getCamera().combined);
        stage.draw();

        spriteBatch.begin();

        for(int i = 0; i < game.getRings().size(); i++) {
            spriteBatch.draw(ringSprite, Gdx.graphics.getWidth()/15f + ringSprite.getWidth()*i,
                    Gdx.graphics.getHeight() - Bounce.TEXTURE_SIZE - Gdx.graphics.getHeight()/50f);
        }

        spriteBatch.end();
    }
}
