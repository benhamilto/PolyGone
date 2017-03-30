package ca.polygone;

/**
 * Created by John on 2017-03-29.
 */
public class NEWallCorner extends   StaticObstacle {
    public NEWallCorner(Cord startCords){
        cords = startCords;
        preventsMovement = true;
        texturePath = "core/assets/NEWallCorner.png";
    }

    public boolean preventsMovement(){
        return preventsMovement;
    }

    @Override
    public String getTexture() {
        return texturePath ;
    }

}
