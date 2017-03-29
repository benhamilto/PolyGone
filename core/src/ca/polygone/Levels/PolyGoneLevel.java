package ca.polygone.Levels;

import ca.polygone.Cord;
import ca.polygone.Piece;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ben on 2017-03-29.
 */
public abstract class PolyGoneLevel {

    protected PolyGoneLevel nextLevel;
    protected HashMap<Cord, Piece> Map;
    protected ArrayList<Cord> victoryCords;
    protected ArrayList<Piece> playerPieces = new ArrayList<Piece>();
    protected ArrayList<Piece> nonPlayerPieces = new ArrayList<Piece>();
    protected boolean isFinalLevel;
    private int mapWidth = 10, mapLength = 10;

    public HashMap<Cord, Piece> getMap() {
        return Map;
    }

    public boolean isFinalLevel() {
        return isFinalLevel;
    }

    public ArrayList<Cord> getVictoryCords(){
        return victoryCords;
    }

    public PolyGoneLevel getNextLevel() {
        return nextLevel;
    }


    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapLength() {
        return mapLength;
    }

    public ArrayList<Piece> getPlayerPieces() {
        return playerPieces;
    }
}
