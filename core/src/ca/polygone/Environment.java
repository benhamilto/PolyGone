package ca.polygone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * Created by John on 2017-03-05.
 */
public class Environment {
    private int mapLength;
    private int mapWidth;
    private HashMap<Cord,Piece> Map;
    private ArrayList<Piece> playerPieces = new ArrayList<Piece>();
    private ArrayList<Piece> nonPlayerPieces = new ArrayList<Piece>();
    private int[] moveArrayX = {1, 0, -1, 0};
    private int[] moveArrayY = {0, 1, 0, -1};
    Sprite lastSelectedTile = null;
    private HashMap<Cord,Sprite> floor;
    private Piece selectedPiece;
    private Cord selectedCord;
    private ArrayList<Cord> listOfCords = new ArrayList<Cord>();
    private ArrayList<Cord> victoryCords = new ArrayList<Cord>();
    private Cord lastSelectedCord;
    private Texture badlogictexture;

    public Environment(int newLength,int newWidth){
        mapLength = newLength;
        mapWidth = newWidth;
        for (int z = 1; z < 3; z++) {
            for (int x = 1; x < 3; x++) {
                victoryCords.add(new Cord(x,z));
            }
        }
        victoryCords.add(new Cord(1,1));
        badlogictexture = new Texture(Gdx.files.internal("core/assets/GroundGrey.png"));
        floor = new  HashMap<Cord,Sprite>();
        Map = new HashMap<Cord,Piece>();
        for (int z = 0; z < mapLength; z++) {
            for (int x = 0; x < mapWidth; x++) {
                Cord tempCord = new Cord(x,z);
                floor.put(tempCord,new Sprite(badlogictexture));
                floor.get(tempCord).setPosition(x, z);
                floor.get(tempCord).setSize(1, 1);
                if(victoryCords.contains(tempCord)){
                    floor.get(tempCord).setColor(1,1,0,1 );
                }
            }
        }

    }
    public HashMap<Cord,Sprite> getFloor(){return floor;}

    public HashMap<Cord,Piece> getMap(){
        return Map;
    }

    public ArrayList<Cord> getVictory(){return victoryCords;}

    public void addPieceToBoard(Piece newPiece){
        Map.put(newPiece.getCords(),newPiece);
        if(newPiece instanceof PlayerCharecter){
            playerPieces.add(newPiece);
        }
        if(newPiece instanceof NonPlayerCharacter){
            nonPlayerPieces.add(newPiece);
        }

    }

    public void movePiece(Piece pieceToMove, Cord newCords){

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
        selectedPiece = null;
    }


    public void select(int x,int z){


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
        Sprite sprite = floor.get(selectedCord);
        sprite.setColor(1, 0, 0, 1);
        lastSelectedTile = sprite;
        lastSelectedCord = selectedCord;
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
