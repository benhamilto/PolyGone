package ca.polygone;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by John on 2017-03-05.
 */
public interface Piece {

    String getTexture();
    boolean preventsMovement();
    Cord getCords();
    void setCords(Cord newCord);
    
    int getMoveLimit();
    int getMoveLeft();
    void reduceMoveLeft(int valueToReduce);

    void resetMoveLeft();
    Sprite getSprite();
    void setSprite(Sprite newSprite);

}
