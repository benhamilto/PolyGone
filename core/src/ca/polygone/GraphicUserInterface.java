package ca.polygone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.graphics.OrthographicCamera;

import java.util.ArrayList;
import java.util.HashMap;

public class GraphicUserInterface extends ApplicationAdapter implements InputProcessor {
    Environment Level;
    HashMap<Cord,Piece> map = new HashMap<Cord,Piece>();
    Texture badlogictexture;
    SpriteBatch floorbatch;
    final Matrix4 floorMatrix = new Matrix4();
    Sprite[][] floor;

    int mapLenght;
    int mapWidth;

    Texture purpleFade;
    Sprite[] hourGlass = new Sprite[5];
    final Matrix4 pieceMatrix = new Matrix4();

    ArrayList<Sprite> pieceSpriteArray = new ArrayList<Sprite>();

    OrthographicCamera cam;
    Sprite selectedPiece;




    @Override public void create() {
        cam = new OrthographicCamera(10,10 * (Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));
        cam.near = 1;
        cam.far = 100;
        cam.position.set(5, 5, 10);
        cam.direction.set(-1, -1, -1);
        cam.zoom = 1;

        mapLenght = 6;
        mapWidth = 6;


        HourGlass pro1 = new HourGlass(new Cord(1,1));
        map.put(pro1.getCords(),pro1);

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


        floorbatch = new SpriteBatch();

        Gdx.input.setInputProcessor(this);
    }

    @Override public void render() {

        drawMap();
        checkTileTouched();

    }

    final Plane xzPlane = new Plane(new Vector3(0, 1, 0), 0);
    final Vector3 intersection = new Vector3();
    Sprite lastSelectedTile = null;

    final Vector3 curr = new Vector3();
    final Vector3 last = new Vector3(-1, -1, -1);
    final Vector3 delta = new Vector3();

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



    private void checkTileTouched() {
        if(Gdx.input.justTouched()) {
            Ray pickRay = cam.getPickRay(Gdx.input.getX(), Gdx.input.getY());
            Intersector.intersectRayPlane(pickRay, xzPlane, intersection);
            int x = (int)intersection.x;
            int z = (int)intersection.z;
            if(x >= 0 && x < mapLenght && z >= 0 && z < mapWidth) {
                if(lastSelectedTile != null) lastSelectedTile.setColor(1, 1, 1, 1);
                Sprite sprite = floor[x][z];
                sprite.setColor(1, 0, 0, 1);
                lastSelectedTile = sprite;

                /*for (Sprite E : hourGlass){
                    if(E.getX() == x && E.getY() == z){
                        selectedPiece.setColor(1, 1, 1, 1);
                        selectedPiece = E;
                        E.setColor(1, 0, 0, 1);
                    }
                }*/

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
