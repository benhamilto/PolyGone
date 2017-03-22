package ca.polygone.ca.polygone.screens;

import ca.polygone.GraphicUserInterface;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.Random;

/**
 * Created by Ben on 2017-03-20.
 */
public class GameOverScreen extends PolyGoneScreen {

    private ShapeRenderer renderer;

    private GraphicUserInterface game;

    Random rand = new Random();
    float x, y, xdir, ydir;
    float speed;
    float r;

    private Stage stage;
    private BitmapFont font;
    private Skin skin;
    private TextureAtlas atlas;
    private Sprite backgroundSprite;
    private Texture texture;
    private Table mainTable;
    private VerticalGroup buttonGroup;
    TextButton buttonGameOver;
    Label labelGameOver;



    public GameOverScreen(GraphicUserInterface game) {
        super(game);
        this.game = game;
        renderer = new ShapeRenderer();
        r = 30;
        x = Gdx.graphics.getWidth()/2;
        y = Gdx.graphics.getHeight()/2;
        xdir = rand.nextFloat()*2 - 1;
        ydir = rand.nextFloat()*2 - 1;

        speed = 3;

        skin = new Skin(Gdx.files.internal("core/assets/uiskin.json"));
        mainTable = new Table();
        buttonGroup = new VerticalGroup();
        stage = new Stage();

        mainTable.setFillParent(true);
        Gdx.input.setInputProcessor(stage);




    }

    @Override
    public void render(float delta) {
        Color c = new Color();
        c.set(Color.GRAY);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        buttonGameOver.setPosition(x,y);


        x += speed*xdir;
        y += speed*ydir;

        if (x > Gdx.graphics.getWidth() - buttonGameOver.getWidth()   || x < 0) {
            xdir = -xdir;
        }
        if( y > Gdx.graphics.getHeight() - buttonGameOver.getHeight() || y < 0){
            ydir = -ydir;
        }

        stage.draw();


    }

    @Override
    public void show() {
        labelGameOver = new Label("Game Over", skin);
        buttonGameOver = new TextButton("EXIT", skin, "menutext");
        buttonGameOver.getLabel().setFontScale(2.0f);
        buttonGameOver.setColor(0.5f,0.5f,1,1f);
        buttonGameOver.setHeight(40f);
        buttonGameOver.setWidth(70f);

        labelGameOver.setFontScale(4f);

        labelGameOver.setPosition(Gdx.graphics.getWidth()/2-labelGameOver.getWidth()*2, Gdx.graphics.getHeight()/2);
        stage.addActor(buttonGameOver);
        stage.addActor(labelGameOver);
        buttonGameOver.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getScreen().dispose();
                game.setScreen(new MenuScreen(game));
            }
        });

    }



}
