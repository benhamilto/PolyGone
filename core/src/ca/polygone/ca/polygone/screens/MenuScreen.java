package ca.polygone.ca.polygone.screens;

import ca.polygone.GraphicUserInterface;
import ca.polygone.ca.polygone.*;
import ca.polygone.MyInputProcessor;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by bhamilto on 3/3/17.
 */
public class MenuScreen extends PolyGoneScreen {

    private GraphicUserInterface game;

    private Stage stage;
    private BitmapFont font;
    private Skin skin;
    private TextureAtlas atlas;
    private Sprite backgroundSprite;
    private Texture texture;
    private Table mainTable;
    private VerticalGroup buttonGroup;


    public MenuScreen(GraphicUserInterface game) {
        super(game);
        this.game = game;
    }


    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("core/assets/uiskin.json"));
        mainTable = new Table();
        buttonGroup = new VerticalGroup();
        stage = new Stage();

        mainTable.setFillParent(true);

        final TextButton buttonPlay = new TextButton("Play", skin, "default");
        final TextButton buttonOptions = new TextButton("Options", skin, "default");

        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                game.setScreen(new GameScreen(game));
            }
        });
        buttonOptions.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new OptionsScreen(game));
            }
        });
        texture = new Texture(Gdx.files.internal("core/assets/TitleScreen.jpg"));
        backgroundSprite = new Sprite(texture);

        buttonGroup.addActor(buttonPlay);
        buttonGroup.addActor(buttonOptions);
        buttonGroup.setHeight(100f);
        buttonGroup.setWidth(200f);
        buttonGroup.columnLeft();

        mainTable.addActor(buttonGroup);

        stage.addActor(mainTable);


        Gdx.input.setInputProcessor(stage);

//        batch = new SpriteBatch();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        backgroundSprite.draw(game.batch);
        game.batch.end();
        stage.draw();

        mainTable.debug();

    }

    @Override
    public void resize(int width, int height) {

    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        game.batch.dispose();
    }
}
