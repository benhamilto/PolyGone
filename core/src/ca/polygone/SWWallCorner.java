package ca.polygone;

/**
 * Created by John on 2017-03-29.
 */
public class SWWallCorner extends StaticObstacle {
    public SWWallCorner(Cord startCords){
        cords = startCords;
        preventsMovement = true;
        texturePath = "core/assets/SWWallCorner.png";
    }

    public boolean preventsMovement(){
        return preventsMovement;
    }

    @Override
    public String getTexture() {
        return texturePath ;
    }

}
