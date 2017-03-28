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
    private Cord lastSelectedCord;
    private Texture badlogictexture;

    public Environment(int newLength,int newWidth){
        mapLength = newLength;
        mapWidth = newWidth;
        badlogictexture = new Texture(Gdx.files.internal("core/assets/GroundGrey.png"));
        floor = new  HashMap<Cord,Sprite>();
        Map = new HashMap<Cord,Piece>();
        for (int z = 0; z < mapLength; z++) {
            for (int x = 0; x < mapWidth; x++) {
                floor.put(new Cord(x,z),new Sprite(badlogictexture));
                floor.get(new Cord(x,z)).setPosition(x, z);
                floor.get(new Cord(x,z)).setSize(1, 1);
            }
        }

    }
    public HashMap<Cord,Sprite> getFloor(){return floor;}

    public HashMap<Cord,Piece> getMap(){
        return Map;
    }

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
                Map.put(selectedCord, selectedPiece);
                Map.remove(selectedPiece.getCords());
                selectedPiece.setCords(selectedCord);
                selectedPiece.getSprite().setPosition(selectedCord.getX(), selectedCord.getY());
                for (Cord drawCord : listOfCords) {
                    floor.get(drawCord).setColor(1, 1, 1, 1);
                }
                selectedPiece = null;
            }
        }

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
                selectedPiece = Map.get(selectedCord);
        }

        if (selectedPiece instanceof PlayerCharecter) {
            for (Cord drawCord : listOfCords) {
                floor.get(drawCord).setColor(1, 1, 1, 1);
            }
            for (Piece E : playerPieces) {
                if (E.getCords().getX() == x && E.getCords().getX() == z)
                    E.getSprite().setColor(1, 0, 0, 1);
            }
            listOfCords.clear();
            nextCord(selectedPiece.getCords(), selectedPiece.getMoveLimit());
            for (Cord drawCord : listOfCords) {
                floor.get(drawCord).setColor(0, 1, 1, 1);
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
    private void nonPlayerTurn(){
        for(Piece E : nonPlayerPieces){
            
        }
    }

}
