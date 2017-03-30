package ca.polygone;

/**
 * Created by John on 2017-03-29.
 */
public class SEWallCorner extends Obstacle {
    public SEWallCorner(Cord startCords){
        cords = startCords;
        preventsMovement = true;
        texturePath = "core/assets/SEWallCorner.png";
    }

    public boolean preventsMovement(){
        return preventsMovement;
    }

    @Override
    public String getTexture() {
        return texturePath ;
    }

}

