package environment.tiles;

import java.util.ArrayList;

public class TileAtlas {
    
    public static ArrayList<Tile> atlas = new ArrayList<Tile>();
    
    public static Floor floor = new Floor(0, 0, 1);
    public static Wall  wall = new Wall(1, 0, 2);
    public static Corridor corridor = new Corridor(1, 1, 3);
    public static MiceRoom miceRoom = new MiceRoom(0, 1, 4);
}
