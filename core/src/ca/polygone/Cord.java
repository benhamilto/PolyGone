package ca.polygone;

import com.sun.org.apache.xpath.internal.operations.Equals;

import java.util.Arrays;

/**
 * Created by John on 2017-03-05.
 */
public class Cord extends Object{
    private int[] xyCords;

    @Override
    public int hashCode() {
        return Arrays.hashCode(xyCords);
    }

    public Cord(int x, int y){

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
    @Override
    public boolean equals(Object cord2){
        if(xyCords[0] == ((Cord)cord2).getX() && xyCords[1] == ((Cord)cord2).getY() ){
            return true;
        }else {
            return false;
        }
    }
}
