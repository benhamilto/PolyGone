package ca.polygone;

/**
 * Created by John on 2017-03-05.
 */
public abstract class NonPlayerCharacter extends Actor {
    protected Artificialntelligence AI;
    public Artificialntelligence getAI(){
        return AI;
    }
}
