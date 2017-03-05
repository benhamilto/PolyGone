package ca.polygone;

/**
 * Created by John on 2017-03-05.
 */
public abstract class Actor implements Piece {
    protected Cord cords;
    protected int moveLimit;
    protected boolean preventsMovement = true;
    protected String texturePath;

    public Cord getCords() {
        return cords;
    }

    public void setCords(Cord newCords){
        cords = newCords;
    }

    public int getMoveLimit(){
        return moveLimit;
    }

    public boolean preventsMovement(){
        return preventsMovement;
    }

    public String getTexture(){
        return texturePath;
    }
}
