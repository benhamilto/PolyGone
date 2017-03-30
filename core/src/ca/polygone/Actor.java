package ca.polygone;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by John on 2017-03-05.
 */
public abstract class Actor implements Piece {
    protected Cord cords;
    protected int moveLimit;
    protected int moveLeft;
    protected boolean preventsMovement = true;
    protected String texturePath;
    protected Sprite drawSprite;


    public Cord getCords() {
        return cords;
    }

    public void setCords(Cord newCords){
        cords = newCords;
    }

    @Override
    public void reduceMoveLeft(int valueToReduce) {
        moveLeft = moveLeft - valueToReduce;
    }
    public void setMoveLeft(int newMoveLeft){
        moveLeft = newMoveLeft;
    }

    public int getMoveLimit(){
        return moveLimit;
    }
    public int getMoveLeft(){return moveLeft;}
    public void resetMoveLeft() {
        moveLeft = moveLimit;
    }

    public boolean preventsMovement(){
        return preventsMovement;
    }

    public String getTexture(){
        return texturePath;
    }


    public Sprite getSprite() {
        return drawSprite;
    }
    public void setSprite(Sprite newSprite){
        drawSprite = newSprite;
    }
}
