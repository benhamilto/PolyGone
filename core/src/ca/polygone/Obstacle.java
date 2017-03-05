package ca.polygone;

/**
 * Created by John on 2017-03-05.
 */
public abstract class Obstacle implements Piece {
    protected Cord cords;
    protected boolean preventsMovement = true;
    protected String texturePath;
}
