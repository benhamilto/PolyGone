package ca.polygone;

/**
 * Created by John on 2017-03-05.
 */
public class Cord {
    private int[] xyCords;
    public Cord(int x,int y){
        xyCords = new int[2];
        xyCords[0] = x;
        xyCords[1] = y;
    }
    public int getX(){
        return xyCords[0];
    }
    public int getY(){
        return xyCords[1];
    }
    public int[] getXyCords(){
        return xyCords;
    }
}
