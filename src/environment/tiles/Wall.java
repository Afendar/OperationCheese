package environment.tiles;

import java.awt.Color;
import java.awt.Graphics;
import objectifcheeze.tools.Defines;

public class Wall extends Tile {
    
    public Wall(int imgX, int imgY, int ID){
        super(imgX, imgY, ID);
    }
    
    public void render(Graphics g, int x, int y, int top, boolean window){
        
        x *= Defines.TILE_SIZE;
        y *= Defines.TILE_SIZE;
        g.setColor(Color.BLACK);
        
        if(top == 1){
            this.tile = this.tileset.getSubimage((this.imgX + 1) * Defines.TILE_SIZE + 16, this.imgY * Defines.TILE_SIZE + 32, Defines.TILE_SIZE / 2, Defines.TILE_SIZE/2);
            g.fillRect(x, y, Defines.TILE_SIZE, Defines.TILE_SIZE/2);
            g.drawImage(this.tile, x, y + (Defines.TILE_SIZE/2), null);
            g.drawImage(this.tile, x + (Defines.TILE_SIZE/2), y + (Defines.TILE_SIZE/2), null);
        }
        else if(top == 2){
            g.fillRect(x, y, Defines.TILE_SIZE, Defines.TILE_SIZE);
            this.tile = this.tileset.getSubimage((this.imgX + 2) * Defines.TILE_SIZE + 32, this.imgY * Defines.TILE_SIZE + 32, Defines.TILE_SIZE / 2, Defines.TILE_SIZE/2);
            g.drawImage(this.tile, x + (Defines.TILE_SIZE/2), y + (Defines.TILE_SIZE/2), null);
        }
        else if(top == 3){
            g.fillRect(x, y, Defines.TILE_SIZE, Defines.TILE_SIZE);
            this.tile = this.tileset.getSubimage((this.imgX + 1) * Defines.TILE_SIZE + 32, this.imgY * Defines.TILE_SIZE + 8, Defines.TILE_SIZE / 2, Defines.TILE_SIZE/2);
            g.drawImage(this.tile, x + (Defines.TILE_SIZE/2), y, null);
            g.drawImage(this.tile, x + (Defines.TILE_SIZE/2), y + (Defines.TILE_SIZE/2), null);
        }
        else if(window){
            this.tile = this.tileset.getSubimage((this.imgX + 3) * Defines.TILE_SIZE, this.imgY * Defines.TILE_SIZE, Defines.TILE_SIZE, Defines.TILE_SIZE);
            g.drawImage(this.tile, x, y, null);
        }
        else
        {
            this.tile = this.tileset.getSubimage(this.imgX * Defines.TILE_SIZE, this.imgY * Defines.TILE_SIZE, Defines.TILE_SIZE, Defines.TILE_SIZE);
            g.drawImage(this.tile, x, y, null);
        }
        
    }
}
