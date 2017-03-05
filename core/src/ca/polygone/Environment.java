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
}
