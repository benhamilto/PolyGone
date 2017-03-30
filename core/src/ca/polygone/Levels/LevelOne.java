package ca.polygone.Levels;

import ca.polygone.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ben on 2017-03-29.
 */
public class LevelOne extends PolyGoneLevel {

    public LevelOne() {
        mapWidth = 10;
        mapLength = 10;


        isFinalLevel = false;
        victoryCords = new ArrayList<Cord>();
        Map = new HashMap<Cord, Piece>();

        playerPieces = new ArrayList<Piece>();
        nonPlayerPieces = new ArrayList<Piece>();

        playerPieces.add(new HourGlass(new Cord(0, 0)));
        playerPieces.add(new HourGlass(new Cord(0, 1)));

        nonPlayerPieces.add(new Circle(new Cord(5, 1)));
        nonPlayerPieces.add(new Circle(new Cord(2, 6)));

        for (Piece p : playerPieces) {
            Map.put(p.getCords(), p);
        }
        for (Piece p : nonPlayerPieces) {
            Map.put(p.getCords(), p);
        }

        Map.put(new Cord(1, 3), new SEWallCorner(new Cord(1, 3)));
        Map.put(new Cord(5, 3), new NEWallCorner(new Cord(5,3)) );
        Map.put(new Cord(1, 5), new SWWallCorner(new Cord(1, 5)));
        Map.put(new Cord(1, 7), new SEWallCorner(new Cord(1, 7)));
        Map.put(new Cord(6, 5), new SWWallCorner(new Cord(1, 5)));
        Map.put(new Cord(4, 7), new NWWallCorner(new Cord(4, 7)));
        Map.put(new Cord(4, 8), new SEWallCorner(new Cord(4, 8)));
        Map.put(new Cord(8, 8), new NEWallCorner(new Cord(8,8)) );

        Map.put(new Cord(1, 0), new WallVert(new Cord(1, 0)));
        Map.put(new Cord(1, 1), new WallVert(new Cord(1, 1)));
        Map.put(new Cord(1, 2), new WallVert(new Cord(1, 2)));
        Map.put(new Cord(1, 6), new WallVert(new Cord(1, 6)));
        Map.put(new Cord(5, 2), new WallVert(new Cord(5, 2)));
        Map.put(new Cord(6, 6), new WallVert(new Cord(6, 6)));
        Map.put(new Cord(7, 0), new WallVert(new Cord(7, 0)));
        Map.put(new Cord(7, 1), new WallVert(new Cord(7, 1)));
        Map.put(new Cord(7, 2), new WallVert(new Cord(7, 2)));
        Map.put(new Cord(8, 7), new WallVert(new Cord(8, 7)));

        Map.put(new Cord(2, 3), new WallHoriz(new Cord(2, 3)));
        Map.put(new Cord(3, 3), new WallHoriz(new Cord(3, 3)));
        Map.put(new Cord(4, 3), new WallHoriz(new Cord(4, 3)));
        Map.put(new Cord(2, 5), new WallHoriz(new Cord(2, 5)));
        Map.put(new Cord(3, 5), new WallHoriz(new Cord(3, 5)));
        Map.put(new Cord(4, 5), new WallHoriz(new Cord(4, 5)));
        Map.put(new Cord(2, 7), new WallHoriz(new Cord(2, 7)));
        Map.put(new Cord(3, 7), new WallHoriz(new Cord(3, 7)));
        Map.put(new Cord(5, 8), new WallHoriz(new Cord(5, 8)));
        Map.put(new Cord(6, 8), new WallHoriz(new Cord(6, 8)));
        Map.put(new Cord(7, 8), new WallHoriz(new Cord(7, 8)));
        Map.put(new Cord(7, 5), new WallHoriz(new Cord(7, 5)));


        victoryCords.add(new Cord(9, 0));
        victoryCords.add(new Cord(8, 0));
        victoryCords.add(new Cord(8, 1));
        victoryCords.add(new Cord(9, 1));


    }



}