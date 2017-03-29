package ca.polygone;

import ca.polygone.Levels.LevelOne;
import ca.polygone.Levels.PolyGoneLevel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.abs;

/**
 * Created by John on 2017-03-05.
 */
public class Environment {
    private int mapLength;
    private int mapWidth;
    private HashMap<Cord, Piece> Map;
    private ArrayList<Piece> playerPieces = new ArrayList<Piece>();
    private ArrayList<Piece> nonPlayerPieces = new ArrayList<Piece>();
    private int[] moveArrayX = {1, 0, -1, 0};
    private int[] moveArrayY = {0, 1, 0, -1};
    Sprite lastSelectedTile = null;
    private HashMap<Cord, Sprite> floor;
    private HashMap<Cord, Sprite> darkMap;
    private Piece selectedPiece;
    private Cord selectedCord;
    private ArrayList<Cord> listOfCords = new ArrayList<Cord>();
    private ArrayList<Cord> victoryCords = new ArrayList<Cord>();
    private Cord lastSelectedCord;
    private Texture badlogictexture;
    private PolyGoneLevel currentLevel;

    public Environment() {
        currentLevel = new LevelOne();
        loadLevel();
    }

    public void loadLevel(){
        int mapWidth = currentLevel.getMapWidth();
        int mapLength = currentLevel.getMapLength();
        playerPieces = currentLevel.getPlayerPieces();
        nonPlayerPieces = currentLevel.getNonPlayerPieces();
        victoryCords = currentLevel.getVictoryCords();

        badlogictexture = new Texture(Gdx.files.internal("core/assets/GroundGrey.png"));
        floor = new HashMap<Cord, Sprite>();
        darkMap = new HashMap<Cord, Sprite>();

        Map = currentLevel.getMap();
        for (int z = 0; z < mapLength; z++) {
            for (int x = 0; x < mapWidth; x++) {
                floor.put(new Cord(x, z), new Sprite(badlogictexture));
                floor.get(new Cord(x, z)).setPosition(x, z);
                floor.get(new Cord(x, z)).setSize(1, 1);
            }
        }

        for (int z = 0; z < mapLength; z++) {
            for (int x = 0; x < mapWidth; x++) {

                Cord tempCord = new Cord(x,z);
                floor.put(tempCord,new Sprite(badlogictexture));
                floor.get(tempCord).setPosition(x, z);
                floor.get(tempCord).setSize(1, 1);
                if(victoryCords.contains(tempCord)){
                    floor.get(tempCord).setColor(1,1,0,1 );
                }

                darkMap.put(new Cord(x, z), new Sprite(badlogictexture));
                darkMap.get(new Cord(x, z)).setPosition(x, z);
                darkMap.get(new Cord(x, z)).setSize(1, 1);
                darkMap.get(new Cord(x, z)).setColor(0, 0, 0, 1);

            }
        }
    }

    public HashMap<Cord, Sprite> getFloor() {
        return floor;
    }

    public HashMap<Cord, Piece> getMap() {
        return Map;
    }


    public ArrayList<Cord> getVictory(){return victoryCords;}



    public void movePiece(Piece pieceToMove, Cord newCords) {

    }

    public Piece checkCordForPiece(Cord cordToCheck) {
        if (Map.containsKey(cordToCheck)) {
            return Map.get(cordToCheck);
        } else {
            return null;
        }
    }

    public void confirmMove() {
        if (selectedCord != null && selectedPiece != null && selectedPiece instanceof PlayerCharecter) {
            if (listOfCords.contains(selectedCord)) {
                selectedPiece.getSprite().setColor(1,1,1,1);
                movePiece();
                for (Cord drawCord : listOfCords) {
                    floor.get(drawCord).setColor(1, 1, 1, 1);
                }
                this.select(selectedPiece.getCords().getX(), selectedPiece.getCords().getY());
            }
        }

    }
    public void movePiece(){
        Map.put(selectedCord, selectedPiece);
        Map.remove(selectedPiece.getCords());
        selectedPiece.reduceMoveLeft(Math.abs(selectedCord.getX()-selectedPiece.getCords().getX())+
                Math.abs(selectedCord.getY()-selectedPiece.getCords().getY()));
        selectedPiece.setCords(selectedCord);
        selectedPiece.getSprite().setPosition(selectedCord.getX(), selectedCord.getY());
    }


    public void select(int x, int z) {


        if (lastSelectedTile != null) {
            if (!listOfCords.contains(lastSelectedCord)) {
                lastSelectedTile.setColor(1, 1, 1, 1);
            } else {
                lastSelectedTile.setColor(0, 1, 1, 1);
            }

        }
        selectedCord = new Cord(x, z);
        if (lastSelectedTile != null) lastSelectedTile.setColor(1, 1, 1, 1);

        if (Map.containsKey(selectedCord)) {
            if(Map.get(selectedCord) instanceof PlayerCharecter)
                if(selectedPiece instanceof PlayerCharecter){
                    selectedPiece.getSprite().setColor(1, 1, 1, 1);
                }
                selectedPiece = Map.get(selectedCord);
        }

        if (selectedPiece instanceof PlayerCharecter) {
            selectedPiece.getSprite().setColor(1, 0, 0, 1);
            listOfCords.clear();
            nextCord(selectedPiece.getCords(), selectedPiece.getMoveLeft());
        }
        
        for (Cord key : floor.keySet()){
            if(victoryCords.contains(key) && listOfCords.contains(key)) {
                floor.get(key).setColor(0,1,0,1);
            }else if(listOfCords.contains(key)){
                floor.get(key).setColor(0, 1, 1, 1);
            }else if(victoryCords.contains(key)){
                floor.get(key).setColor(1,1,0,1);
            }else{
                floor.get(key).setColor(1,1,1,1);
            }
        }
        if(floor.containsKey(selectedCord)) {
            Sprite sprite = floor.get(selectedCord);
            sprite.setColor(1, 0, 0, 1);
            lastSelectedTile = floor.get(selectedCord);
            lastSelectedCord = selectedCord;
        }
    }

    public HashMap<Cord, Sprite> getDarkMap() {
        ArrayList<Cord> visiblecords = new ArrayList<Cord>();

        for (Piece p : playerPieces) {
            visiblecords.addAll(getVisibleForCharacter((PlayerCharecter) p)); //playerPieces only has playerCharacters
        }

        for (int z = 0; z < mapLength; z++) {
            for (int x = 0; x < mapWidth; x++) {
                Cord drawCord = new Cord(x, z);
                if (visiblecords.contains(drawCord)) {
                    if (darkMap.get(drawCord) != null) {
                        darkMap.get(drawCord).setColor(0, 0, 0, 0);
                    }
                } else {
                    if (darkMap.get(drawCord) != null)
                        darkMap.get(drawCord).setColor(0, 0, 0, 1);
                }

            }
        }
        return darkMap;
    }


    private ArrayList<Cord> getVisibleForCharacter(PlayerCharecter p) {
        ArrayList<Cord> boundaryCoords = new ArrayList<Cord>();
        ArrayList<Cord> circleCoords = new ArrayList<Cord>();
        ArrayList<Cord> toRemove = new ArrayList<Cord>();

        int x0 = p.getCords().getX();
        int y0 = p.getCords().getY();
        int x = p.getVision();
        int y = 0;
        int err = 0;

        //Midpoint circle algorithm
        while (x >= y) {
            boundaryCoords.add(new Cord(x0 + x, y0 + y));
            boundaryCoords.add(new Cord(x0 + y, y0 + x));
            boundaryCoords.add(new Cord((x0 - y), y0 + x));
            boundaryCoords.add(new Cord((x0 - x), y0 + y));
            boundaryCoords.add(new Cord((x0 - x), y0 - y));
            boundaryCoords.add(new Cord((x0 - y), y0 - x));
            boundaryCoords.add(new Cord((x0 + y), y0 - x));
            boundaryCoords.add(new Cord((x0 + x), y0 - y));

            if (err <= 0) {
                y += 1;
                err += 2 * y + 1;
            }
            if (err > 0) {
                x -= 1;
                err -= 2 * x + 1;
            }
        }

        //Bresenham's line algorithm
        for (Cord c : boundaryCoords) {

            int x1 = c.getX();
            int y1 = c.getY();
            int dx, dy, dx1, dy1, px, py, xe, ye, i;
            dx = x1 - x0;
            dy = y1 - y1;
            dx1 = abs(dx);
            dy1 = abs(dy);
            px = 2 * dy1 - dx1;
            py = 2 * dx1 - dy1;
            if (dy1 <= dx1) {
                if (dx >= 0) {
                    x = x0;
                    y = y1;
                    xe = x1;
                } else {
                    x = x1;
                    y = y1;
                    xe = x0;
                }
                if (!(checkCordForPiece(new Cord(x, y)) instanceof Obstacle))
                    circleCoords.add(new Cord(x, y));
                else {
                    toRemove.add(c);
                    circleCoords.add(new Cord(x, y));
//                    break;
                }
                for (i = 0; x < xe; i++) {
                    x = x + 1;
                    if (px < 0) {
                        px = px + 2 * dy1;
                    } else {
                        if ((dx < 0 && dy < 0) || (dx > 0 && dy > 0)) {
                            y = y + 1;
                        } else {
                            y = y - 1;
                        }
                        px = px + 2 * (dy1 - dx1);
                    }
                    if (!(checkCordForPiece(new Cord(x, y)) instanceof Obstacle))
                        circleCoords.add(new Cord(x, y));
                    else {
                        toRemove.add(c);
                        circleCoords.add(new Cord(x, y));
                        break;
                    }
                }
            } else {
                if (dy >= 0) {
                    x = x0;
                    y = y1;
                    ye = y1;
                } else {
                    x = x1;
                    y = y1;
                    ye = y1;
                }
                if (!(checkCordForPiece(new Cord(x, y)) instanceof Obstacle))
                    circleCoords.add(new Cord(x, y));
                else {
                    toRemove.add(c);
                    circleCoords.add(new Cord(x, y));
//                    break;
                }
                for (i = 0; y < ye; i++) {
                    y = y + 1;
                    if (py <= 0) {
                        py = py + 2 * dx1;
                    } else {
                        if ((dx < 0 && dy < 0) || (dx > 0 && dy > 0)) {
                            x = x + 1;
                        } else {
                            x = x - 1;
                        }
                        py = py + 2 * (dx1 - dy1);
                    }

                    if (!(checkCordForPiece(new Cord(x, y)) instanceof Obstacle))
                        circleCoords.add(new Cord(x, y));
                    else {
                        toRemove.add(c);
                        circleCoords.add(new Cord(x, y));
                        break;
                    }
                }
            }
        }

        boundaryCoords.removeAll(toRemove);
        circleCoords.removeAll(toRemove);


        return circleCoords;
    }


    private void nextCord(Cord baseCord, int movelimit) {
        movelimit--;
        if (movelimit > 0) {
            for (int i = 0; i < 4; i++) {
                boolean preventMove = false;
                Cord newCord = new Cord(baseCord.getX() + moveArrayX[i], baseCord.getY() + moveArrayY[i]);
                Piece tempPiece = this.checkCordForPiece(newCord);

                if (tempPiece != null) {
                    preventMove = tempPiece.preventsMovement();
                }
                if (!preventMove) {
                    if (newCord.getX() >= 0 && newCord.getY() >= 0 && newCord.getX() < mapLength && newCord.getY() < mapWidth) {
                        if (!listOfCords.contains(newCord)) {
                            listOfCords.add(newCord);
                        }
                        nextCord(newCord, movelimit);
                    }
                }
            }
        }
    }

    public void nonPlayerTurn(){
        for(Piece E : nonPlayerPieces){
            NonPlayerCharacter temp = (NonPlayerCharacter) E ;
            selectedCord = temp.getAI().chooseMove(Map);
            if(Map.get(selectedCord) instanceof PlayerCharecter){
                playerPieces.remove(Map.get(selectedCord));
                Map.remove(selectedCord);
            }
            selectedPiece = E;
            if(selectedPiece.getCords() != selectedCord){
                this.movePiece();
            }

        }
    }
    public Boolean isPlayerTurnDone(){
        if(playerPieces.isEmpty()){
            return false;
        }
        for(Piece E : playerPieces){
            if(E.getMoveLeft() > 1){
                return false;
            }

        }
        return true;
    }

    public void resetPlayerPieces(){
        for(Piece E : playerPieces){
            E.resetMoveLeft();
        }
    }
     boolean checkForDefeat(){
        if(playerPieces.isEmpty()){
            return true;
        }else{
            return false;
        }
     }
    boolean checkForVictory(){
        for(Piece E: playerPieces){
          if(! victoryCords.contains(E.getCords())) {
              return false;
          }
        }
        return true;
    }
}
