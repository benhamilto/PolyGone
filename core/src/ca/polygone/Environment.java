package ca.polygone;

import java.util.HashMap;
/**
 * Created by John on 2017-03-05.
 */
public class Environment {
    private HashMap<Cord,Piece> Map;

    public Environment(){
        Map = new HashMap<Cord,Piece>();

    }

    public HashMap<Cord,Piece> getMap(){
        return Map;
    }

    public void addPieceToBoard(Piece newPiece){
        Map.put(newPiece.getCords(),newPiece);
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

}
