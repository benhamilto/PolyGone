package ca.polygone;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by John on 2017-03-05.
 */
public abstract class Obstacle implements Piece {
    protected Cord cords;
    protected boolean preventsMovement = true;
    protected String texturePath;
    protected  Sprite drawSprite;
    public Cord getCords(){
        return cords;
    }
    public void setCords(Cord newCord){
        cords = newCord;
    }
    public int getMoveLimit(){
        return 0;
    }
    public Sprite getSprite() {
        return drawSprite;
    }
    public void setSprite(Sprite newSprite){
        drawSprite = newSprite;
    }
}
