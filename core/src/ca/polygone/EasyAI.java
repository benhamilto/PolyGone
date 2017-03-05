package ca.polygone;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by John on 2017-03-05.
 */
public class EasyAI implements Artificialntelligence {
    private NonPlayerCharacter Character;
    private Cord[] cordArray ;
    EasyAI(NonPlayerCharacter newCharacter){
        Character = newCharacter;
    }
    @Override
    public Movement chooseMove (HashMap<Cord,Piece> map) {
        cordArray = new Cord[1];


        Random randomno = new Random();
        if(randomno.nextBoolean()){//x or y
            if(randomno.nextBoolean()){//negative or positive
                cordArray[0] = new Cord(Character.getCords().getX()+1,Character.getCords().getY());
            }else{
                cordArray[0] = new Cord(Character.getCords().getX()-1,Character.getCords().getY());
            }
        }else{
            if(randomno.nextBoolean()){
                cordArray[0] = new Cord(Character.getCords().getX(),Character.getCords().getY()+1);
            }else {
                cordArray[0] = new Cord(Character.getCords().getX(),Character.getCords().getY()-1);
            }
        }
        cordArray[0] = Character.getCords();
        return new Movement(cordArray);
    }
}
