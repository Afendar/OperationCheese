package environment.tiles;

import java.awt.Graphics;
import objectifcheeze.tools.Defines;

public class Floor extends Tile {
    
    public Floor(int imgX, int imgY, int ID){
        super(imgX, imgY, ID);
    }
    
    public void render(Graphics g, int x, int y, boolean window, boolean gamelle){
        if(window){
            this.tile = this.tileset.getSubimage(4 * Defines.TILE_SIZE, 1 * Defines.TILE_SIZE, Defines.TILE_SIZE, Defines.TILE_SIZE);
        }
        else if(gamelle){
            this.tile = this.tileset.getSubimage(2*Defines.TILE_SIZE, Defines.TILE_SIZE, Defines.TILE_SIZE, Defines.TILE_SIZE);
        }
        else{
            this.tile = this.tileset.getSubimage(this.imgX * Defines.TILE_SIZE, this.imgY * Defines.TILE_SIZE, Defines.TILE_SIZE, Defines.TILE_SIZE);
        }
        g.drawImage(this.tile, x * Defines.TILE_SIZE, y * Defines.TILE_SIZE, null);
    }
}
