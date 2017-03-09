package ca.polygone.ca.polygone.screens;

import ca.polygone.*;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;

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
import com.badlogic.gdx.scenes.scene2d.*;
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

    private int[] moveArrayX ={1,0,-1,0};
    private int[] moveArrayY ={0,1,0,-1};

    private Texture purpleFade;
    private Sprite[] hourGlass = new Sprite[5];
    private SpriteBatch piecebatch;
    private final Matrix4 pieceMatrix = new Matrix4();
    private Stage stage;

    private OrthographicCamera cam;

    private Piece selectedPiece;
    private Environment currentLevel;
    private Cord selectedCord;
    private HashMap<Cord,Piece> map = new HashMap<Cord,Piece>();
    private Viewport viewPort;
    private Texture badlogictexture;
    private SpriteBatch floorbatch;
    private final Matrix4 floorMatrix = new Matrix4();
    private  Sprite[][] floor;
    private ArrayList<Sprite> pieceSpriteArray = new ArrayList<Sprite>();
    private ArrayList<Cord> listOfCords = new ArrayList<Cord>();
    private Cord lastSelectedCord;
    private Skin skin;


    public GameScreen(GraphicUserInterface game) {
        super(game);
        this.game = game;
        currentLevel = new Environment();
        currentLevel.addPieceToBoard(new HourGlass(new Cord(7,4)));
        currentLevel.addPieceToBoard(new HourGlass(new Cord(6,4)));
        for(int i  = 3; i <7;i++){
            currentLevel.addPieceToBoard(new Wall(new Cord(5,i)));
        }
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
        for (Cord key : currentLevel.getMap().keySet()) {
            currentLevel.getMap().get(key).setSprite(new Sprite(new Texture(Gdx.files.internal(currentLevel.getMap().get(key).getTexture())))) ;
            currentLevel.getMap().get(key).getSprite().setPosition(key.getX(), key.getY());
            currentLevel.getMap().get(key).getSprite().setSize(1, 1);
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
                cam.zoom += (amount/10.0);
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

        buttonConfirmMove.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                confirmMove();
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button)
            {
                event.stop();

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
        if (Gdx.input.justTouched()) {
            Ray pickRay = cam.getPickRay(Gdx.input.getX(), Gdx.input.getY());
            Intersector.intersectRayPlane(pickRay, xzPlane, intersection);
            int x = (int) intersection.x;
            int z = (int) intersection.z;
            if (x >= 0 && x < mapLength && z >= 0 && z < mapWidth) {
                if (lastSelectedTile != null) {
                    if (!listOfCords.contains(lastSelectedCord)) {
                        lastSelectedTile.setColor(1, 1, 1, 1);
                    } else {
                        lastSelectedTile.setColor(0, 1, 1, 1);
                    }

                }
                if (x >= 0 && x < mapLength && z >= 0 && z < mapWidth) {
                    selectedCord = new Cord(x, z);
                    if (lastSelectedTile != null) lastSelectedTile.setColor(1, 1, 1, 1);

                    if (currentLevel.getMap().containsKey(selectedCord)) {
                        selectedPiece = currentLevel.getMap().get(selectedCord);
                    }

                    if (selectedPiece instanceof PlayerCharecter) {
                        for (Cord drawCord : listOfCords) {
                            floor[drawCord.getX()][drawCord.getY()].setColor(1, 1, 1, 1);
                        }
                        for (Sprite E : pieceSpriteArray) {
                            if (E.getX() == x && E.getY() == z)
                                E.setColor(1, 0, 0, 1);
                        }
                        listOfCords.clear();
                        nextCord(selectedPiece.getCords(), selectedPiece.getMoveLimit());
                        for (Cord drawCord : listOfCords) {
                            floor[drawCord.getX()][drawCord.getY()].setColor(0, 1, 1, 1);
                        }

                    }
                    Sprite sprite = floor[x][z];
                    sprite.setColor(1, 0, 0, 1);
                    lastSelectedTile = sprite;
                    lastSelectedCord = selectedCord;

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
        for (Cord key : currentLevel.getMap().keySet()) {
            currentLevel.getMap().get(key).getSprite().draw(floorbatch);
        }

        floorbatch.end();
    }
    private void highlight(){

    }
    private void nextCord(Cord baseCord,int movelimit){
        movelimit--;
        if(movelimit>0) {
            for(int i = 0; i<4; i++){
                boolean preventMove = false;
                Cord newCord = new Cord(baseCord.getX() + moveArrayX[i], baseCord.getY()+ moveArrayY[i]);
                Piece tempPiece = currentLevel.checkCordForPiece(newCord);

                if (tempPiece != null) {
                    preventMove = tempPiece.preventsMovement();
                }
                if (!preventMove) {
                    if( newCord.getX() >= 0 && newCord.getY() >= 0 && newCord.getX() < mapLength && newCord.getY() < mapWidth){
                        if(!listOfCords.contains(newCord)) {
                            listOfCords.add(newCord);
                        }
                        nextCord( newCord, movelimit);
                    }
                }
            }
        }
    }

    public void confirmMove(){
        if (selectedCord != null && selectedPiece != null && selectedPiece instanceof PlayerCharecter){
            if(listOfCords.contains(selectedCord)){
                currentLevel.getMap().put(selectedCord,selectedPiece);
                currentLevel.getMap().remove(selectedPiece.getCords());
                selectedPiece.setCords(selectedCord);
                selectedPiece.getSprite().setPosition(selectedCord.getX(),selectedCord.getY());
            }
        }

    }

}
