package environment.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import objectifcheeze.tools.Defines;

public abstract class Tile {
    
    protected int imgX, imgY;
    protected final int ID;
    protected BufferedImage tileset, tile;
    
    public Tile(int imgX, int imgY, int ID){
        this.imgX = imgX;
        this.imgY = imgY;
        this.ID = ID;
        
        try{
            URL url = this.getClass().getResource("/images/tileset.png");
            this.tileset = ImageIO.read(url);
            this.init();
        }catch(IOException e){
           e.printStackTrace();
        }
        
        TileAtlas.atlas.add(this);
    }
    
    public void init(){
        this.tile = this.tileset.getSubimage(this.imgX * Defines.TILE_SIZE, this.imgY * Defines.TILE_SIZE, Defines.TILE_SIZE, Defines.TILE_SIZE);
    }
    
    public int getId(){
        return this.ID;
    }
    
    public void render(Graphics g, int x, int y){
        g.drawImage(this.tile, x * Defines.TILE_SIZE, y * Defines.TILE_SIZE, null);
    }
}
