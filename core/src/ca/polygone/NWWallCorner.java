package ca.polygone;

/**
 * Created by John on 2017-03-29.
 */
public class NWWallCorner extends   StaticObstacle {
    public NWWallCorner(Cord startCords){
        cords = startCords;
        preventsMovement = true;
        texturePath = "core/assets/NWWallCorner.png";
    }

    public boolean preventsMovement(){
        return preventsMovement;
    }

    @Override
    public String getTexture() {
        return texturePath ;
    }

}
