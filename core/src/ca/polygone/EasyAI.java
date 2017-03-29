package ca.polygone;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by John on 2017-03-05.
 */
public class EasyAI implements Artificialntelligence {
    private NonPlayerCharacter Character;
    private Cord newCord;
    EasyAI(NonPlayerCharacter newCharacter){
        Character = newCharacter;
    }
    @Override
    public Cord chooseMove (HashMap<Cord,Piece> map) {


        Random randomno = new Random();
        for(int i = 0; i < 100; i++) {
            if (randomno.nextBoolean()) {//x or y
                if (randomno.nextBoolean()) {//negative or positive
                    newCord = new Cord(Character.getCords().getX() + 1, Character.getCords().getY());
                } else {
                    newCord = new Cord(Character.getCords().getX() - 1, Character.getCords().getY());
                }
            } else {
                if (randomno.nextBoolean()) {
                    newCord = new Cord(Character.getCords().getX(), Character.getCords().getY() + 1);
                } else {
                    newCord = new Cord(Character.getCords().getX(), Character.getCords().getY() - 1);
                }
            }
            if(map.containsKey(newCord)) {
                if (map.get(newCord) instanceof PlayerCharecter) {
                    return newCord;
                }else if (! map.get(newCord).preventsMovement()) {
                    return newCord;
                }
            } else{
                return newCord;
            }
        }
        return Character.getCords();
    }
}
