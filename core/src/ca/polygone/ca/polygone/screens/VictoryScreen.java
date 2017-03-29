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
public class VictoryScreen extends PolyGoneScreen {

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
    private Label labelVictory;



    public VictoryScreen(GraphicUserInterface game) {
        super(game);
        this.game = game;


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

        stage.draw();


    }

    @Override
    public void show() {
        labelVictory = new Label("Victory!", skin);
        labelVictory.setColor(Color.GOLDENROD);
        labelVictory.setFontScale(4f);
        labelVictory.setPosition(Gdx.graphics.getWidth()/2-labelVictory.getWidth()*2, Gdx.graphics.getHeight()/2);

        final TextButton buttonMenu = new TextButton("Return to Menu", skin);

        buttonMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getScreen().dispose();
                game.setScreen(new MenuScreen(game));
            }
        });

        buttonGroup.addActor(buttonMenu);

        buttonGroup.space(3f);
        buttonGroup.setHeight(100f);
        buttonGroup.setWidth(200f);
        buttonGroup.columnLeft();


        stage.addActor(labelVictory);
        stage.addActor(buttonGroup);
    }


}