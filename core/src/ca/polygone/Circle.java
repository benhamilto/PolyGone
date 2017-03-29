package ca.polygone;

/**
 * Created by John on 2017-03-28.
 */
public class Circle extends NonPlayerCharacter {
    public Circle(Cord startCords){
            cords = startCords;
            texturePath = "core/assets/GreyOval.png";
            moveLimit = 1;
            AI = new EasyAI(this);
    }
}

