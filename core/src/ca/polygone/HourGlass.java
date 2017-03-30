package ca.polygone;

/**
 * Created by John on 2017-03-05.
 */
public class HourGlass  extends PlayerCharecter{
    public HourGlass(Cord startCords){
        cords = startCords;
        texturePath = "core/assets/NewHourGlass.png";
        moveLimit = 5;
        moveLeft = moveLimit;
        vision = 3;

    }

}
