package ca.polygone;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.HashMap;

/**
 * Created by John on 2017-03-05.
 */
public interface Artificialntelligence {
   Cord chooseMove(HashMap<Cord,Piece> map, HashMap<Cord,Sprite> floor);
}
