package ca.polygone;

/**
 * Created by John on 2017-03-05.
 */
public class Wall extends StaticObstacle {
    public Wall(Cord startCords){
        cords = startCords;
        preventsMovement = true;
        texturePath = "core/assets/WallMiddle.png";
    }
    public Cord getCords(){
        return cords;
    }
    public boolean preventsMovement(){
        return preventsMovement;
    }

    @Override
    public String getTexture() {
        return texturePath ;
    }
}
