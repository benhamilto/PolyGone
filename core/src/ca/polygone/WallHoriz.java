package ca.polygone;



/**
 * Created by John on 2017-03-05.
 */
public class WallHoriz extends StaticObstacle {
    public WallHoriz(Cord startCords){
        cords = startCords;
        preventsMovement = true;
        texturePath = "core/assets/WallMiddle.png";
    }

    public boolean preventsMovement(){
        return preventsMovement;
    }

    @Override
    public String getTexture() {
        return texturePath ;
    }

}
