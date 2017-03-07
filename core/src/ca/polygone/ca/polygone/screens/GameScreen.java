package ca.polygone.ca.polygone.screens;

import ca.polygone.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
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
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ben on 2017-03-05.
 */
public class GameScreen extends PolyGoneScreen {
    GraphicUserInterface game;

    int mapLenght;
    int mapWidth;
    int[] moveArrayX = {1,-1,0,0};
    int[] moveArrayY = {0,0,1,-1};
    ArrayList<Cord> listOfCords = new ArrayList<>();
    Cord selectCord;
    Cord lastSelectedCord;

    OrthographicCamera cam;
    Sprite selectSprite;
    Piece selectPiece;
    Environment currentLevel;
    Texture badlogictexture;
    SpriteBatch floorbatch;
    final Matrix4 floorMatrix = new Matrix4();
    Sprite[][] floor;
    ArrayList<Sprite> pieceSpriteArray = new ArrayList<>();


    public GameScreen(GraphicUserInterface game) {
        super(game);
        this.game = game;
        currentLevel = new Environment();
        currentLevel.addPieceToBoard(new HourGlass(new Cord(1,1)));

        cam = new OrthographicCamera(10,10 * (Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));
        cam.near = 1;
        cam.far = 100;
        cam.position.set(5, 5, 10);
        cam.direction.set(-1, -1, -1);
        cam.zoom = 1;

        mapLenght = 10;
        mapWidth = 10;
        floor = new Sprite[mapLenght][mapWidth];
        badlogictexture = new Texture(Gdx.files.internal("core/assets/GroundGrey.png"));
        floorMatrix.setToRotation(new Vector3(1, 0, 0), 90);
        for(int z = 0; z < mapLenght; z++) {
            for(int x = 0; x < mapWidth; x++) {
                floor[x][z] = new Sprite(badlogictexture);
                floor[x][z].setPosition(x,z);
                floor[x][z].setSize(1, 1);
            }
        }
        for (Cord key : currentLevel.getMap().keySet()) {
            Sprite newSprite = new Sprite(new Texture(Gdx.files.internal(currentLevel.getMap().get(key).getTexture())));
            newSprite.setPosition(key.getX(), key.getY());
            newSprite.setSize(1, 1);
            pieceSpriteArray.add(newSprite);
        }
        floorbatch = new SpriteBatch();


        Gdx.input.setInputProcessor(new InputAdapter(){
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
    }

    @Override
    public void show() {

    }

    @Override public void render(float delta) {
        drawMap();
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
            selectCord = new Cord(x, z);
            if (x >= 0 && x < mapLenght && z >= 0 && z < mapWidth) {
                if (lastSelectedTile != null){
                    if(!listOfCords.contains(lastSelectedCord)) {
                        lastSelectedTile.setColor(1, 1, 1, 1);
                    }
                     else {
                        lastSelectedTile.setColor(0,1,1,1);
                    }

                }
                Sprite sprite = floor[x][z];
                sprite.setColor(1, 0, 0, 1);
                lastSelectedTile = sprite;
                lastSelectedCord = selectCord;


                selectPiece = currentLevel.getMap().get(selectCord);
                if (currentLevel.getMap().containsKey(selectCord)) {
                    selectPiece = currentLevel.getMap().get(selectCord);
                }

                if (selectPiece instanceof PlayerCharecter) {
                    for(Cord drawCord : listOfCords){
                        floor[drawCord.getX()][drawCord.getY()].setColor(1,1,1,1);
                    }
                    for (Sprite E : pieceSpriteArray) {
                        if (E.getX() == x && E.getY() == z)
                            E.setColor(1, 0, 0, 1);
                    }
                    listOfCords.clear();
                    nextCord(selectCord,selectPiece.getMoveLimit());
                    for(Cord drawCord : listOfCords){
                        floor[drawCord.getX()][drawCord.getY()].setColor(0,1,1,1);
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

        for (int z = 0; z < mapLenght; z++) {
            for (int x = 0; x < mapWidth; x++) {
                floor[x][z].draw(floorbatch);
            }
        }

        for (Sprite toSprite : pieceSpriteArray) {
            toSprite.draw(floorbatch);
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
                if (!listOfCords.contains(newCord) && !preventMove && newCord.getX() >= 0 && newCord.getY() >= 0) {
                    listOfCords.add(newCord);
                    nextCord( newCord, movelimit);
                }
            }
        }
    }
    public void confirmMove(){
        if(listOfCords.contains(selectCord)){
            currentLevel.getMap().put(selectCord,selectPiece);
            currentLevel.getMap().remove(selectPiece.getCords());
        }
    }

}
