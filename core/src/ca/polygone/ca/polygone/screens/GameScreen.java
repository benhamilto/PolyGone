package ca.polygone.ca.polygone.screens;

import ca.polygone.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ben on 2017-03-05.
 */
public class GameScreen extends PolyGoneScreen {
    GraphicUserInterface game;

    private int mapLength;
    private int mapWidth;

    private Texture purpleFade;
    private Sprite[] hourGlass = new Sprite[5];
    private SpriteBatch piecebatch;
    private final Matrix4 pieceMatrix = new Matrix4();
    private Stage stage;

    private OrthographicCamera cam;
    private Sprite selectedPiece;
    private Environment Level;
    private HashMap<Cord,Piece> map = new HashMap<Cord,Piece>();
    private Viewport viewPort;
    private Texture badlogictexture;
    private SpriteBatch floorbatch;
    private final Matrix4 floorMatrix = new Matrix4();
    private  Sprite[][] floor;
    private ArrayList<Sprite> pieceSpriteArray = new ArrayList<Sprite>();
    private Skin skin;


    public GameScreen(GraphicUserInterface game) {
        super(game);
        this.game = game;
        stage = new Stage();



        cam = new OrthographicCamera(10,10 * (Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));
        cam.near = 1;
        cam.far = 100;
        cam.position.set(5, 5, 10);
        cam.direction.set(-1, -1, -1);
        cam.zoom = 1;


        mapLength = 10;
        mapWidth = 10;
        floor = new Sprite[mapLength][mapWidth];
        badlogictexture = new Texture(Gdx.files.internal("core/assets/GroundGrey.png"));
        floorMatrix.setToRotation(new Vector3(1, 0, 0), 90);
        for(int z = 0; z < mapLength; z++) {
            for(int x = 0; x < mapWidth; x++) {
                floor[x][z] = new Sprite(badlogictexture);
                floor[x][z].setPosition(x,z);
                floor[x][z].setSize(1, 1);
            }
        }
        floorbatch = new SpriteBatch();

        InputMultiplexer multiplexer = new InputMultiplexer();


         multiplexer.addProcessor(new InputAdapter() {
            @Override public boolean touchDragged (int x, int y, int pointer) {
                Ray pickRay = cam.getPickRay(x, y);
                Intersector.intersectRayPlane(pickRay, xzPlane, curr);

                if(!(last.x == -1 && last.y == -1 && last.z == -1)) {
                    pickRay = cam.getPickRay(last.x, last.y);
                    Intersector.intersectRayPlane(pickRay, xzPlane, delta);
                    delta.sub(curr);
                    cam.position.add(delta.x, delta.y, delta.z);
                }
                last.set(x, y, 0);
                return false;
            }


            @Override public boolean touchUp(int x, int y, int pointer, int button) {
                last.set(-1, -1, -1);
                return false;
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
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                if ((amount > 0 && cam.zoom <= 0.25) || (amount < 0 && cam.zoom >= 5)) {
                    return false;
                }
                cam.zoom -= (amount/10.0);
                cam.update();
                return false;

            }
        });
        multiplexer.addProcessor(stage);

        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("core/assets/uiskin.json"));


        final TextButton buttonConfirmMove = new TextButton("Confirm Move", skin);
        buttonConfirmMove.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("it works");
            }
        });

        buttonConfirmMove.setWidth(200f);
        buttonConfirmMove.setHeight(20f);
        buttonConfirmMove.setPosition(Gdx.graphics.getWidth()-200f, 200f);

        stage.addActor(buttonConfirmMove);
    }

    @Override public void render(float delta) {

        drawMap();
        stage.draw();

        checkTileTouched();

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

    }

    final Plane xzPlane = new Plane(new Vector3(0, 1, 0), 0);
    final Vector3 intersection = new Vector3();
    Sprite lastSelectedTile = null;

    final Vector3 curr = new Vector3();
    final Vector3 last = new Vector3(-1, -1, -1);
    final Vector3 delta = new Vector3();





    private void checkTileTouched() {
        if(Gdx.input.justTouched()) {
            Ray pickRay = cam.getPickRay(Gdx.input.getX(), Gdx.input.getY());
            Intersector.intersectRayPlane(pickRay, xzPlane, intersection);
            int x = (int)intersection.x;
            int z = (int)intersection.z;
            if(x >= 0 && x < mapLength && z >= 0 && z < mapWidth) {
                if(lastSelectedTile != null) lastSelectedTile.setColor(1, 1, 1, 1);
                Sprite sprite = floor[x][z];
                sprite.setColor(1, 0, 0, 1);
                lastSelectedTile = sprite;

                for (Sprite E : pieceSpriteArray){
                    if( map.containsKey(new Cord(Math.round(E.getX()), Math.round(E.getY()) ))){

                    }
                }

            }
        }
    }
    private void drawMap(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        cam.update();

        floorbatch.setProjectionMatrix(cam.combined);
        floorbatch.setTransformMatrix(floorMatrix);
        floorbatch.begin();

        for (int z = 0; z < mapLength; z++) {
            for (int x = 0; x < mapWidth; x++) {
                floor[x][z].draw(floorbatch);
            }
        }

        for (Cord key : map.keySet()) {
            Sprite newSprite = new Sprite(new Texture(Gdx.files.internal(map.get(key).getTexture())));
            newSprite.setPosition(key.getX(),key.getY());
            newSprite.setSize(1, 1);
            newSprite.draw(floorbatch);
            pieceSpriteArray.add(newSprite);

        }
        floorbatch.end();
    }

}
