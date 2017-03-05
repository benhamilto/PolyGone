package ca.polygone;

import java.util.HashMap;

/**
 * Created by John on 2017-03-05.
 */
public interface Artificialntelligence {
    Movement chooseMove(HashMap<Cord,Piece> map);
}
