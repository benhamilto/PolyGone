package ca.polygone;



/**
 * Created by John on 2017-03-05.
 */
public class WallVert extends StaticObstacle {
    public WallVert(Cord startCords){
        cords = startCords;
        preventsMovement = true;
        texturePath = "core/assets/WallMiddleVert.png";
    }

    public boolean preventsMovement(){
        return preventsMovement;
    }

    @Override
    public String getTexture() {
        return texturePath ;
    }

}
