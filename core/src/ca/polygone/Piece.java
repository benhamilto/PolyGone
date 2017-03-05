package ca.polygone;

/**
 * Created by John on 2017-03-05.
 */
public interface Piece {
    String getTexture();
    boolean preventsMovement();
    Cord getCords();

}
