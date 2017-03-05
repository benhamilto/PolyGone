package ca.polygone.ca.polygone.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by bhamilto on 3/3/17.
 */
public class TestScreen extends Game implements Screen{

    private Stage stage;
    private BitmapFont font;
    private Skin skin;
    private TextureAtlas atlas;
    private Sprite backgroundSprite;
    private SpriteBatch batch;
    private Texture texture;
    private Table mainTable;
    



    public TestScreen() {


    }

    @Override
    public void create() {

    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        mainTable = new Table();
        stage = new Stage();

        mainTable.setFillParent(true);

        final TextButton buttonPlay= new TextButton("Play", skin, "default");

        buttonPlay.setWidth(200f);
        buttonPlay.setHeight(20f);
        buttonPlay.setPosition(50, 50);
        buttonPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                buttonPlay.setText("Tanis the TURD");


            }
        });
        texture = new Texture(Gdx.files.internal("TitleScreen.jpg"));
        backgroundSprite = new Sprite(texture);

        mainTable.addActor(buttonPlay);
        stage.addActor(mainTable);


        Gdx.input.setInputProcessor(stage);

//        batch = new SpriteBatch();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        backgroundSprite.draw(batch);
        //stage.draw();
        batch.end();
        stage.draw();

        mainTable.debug();
//        batch.begin();
//        backgroundSprite.draw(batch);
//        batch.end();
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
        batch.dispose();
    }
}
