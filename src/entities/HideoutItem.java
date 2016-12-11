package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import objectifcheeze.tools.Defines;

public class HideoutItem extends ItemEntity {
    
    protected BufferedImage spritesheet, sprite;
    
    public HideoutItem(int x, int y, BufferedImage spritesheet){
        super(x, y);
        this.spritesheet = spritesheet;
        this.sprite = this.spritesheet.getSubimage(0, 0, Defines.TILE_SIZE, Defines.TILE_SIZE);
    }
    
    @Override
    public void update(double dt) {
        
    }
    
    @Override
    public void render(Graphics g) {
        g.drawImage(this.sprite, this.x, this.y, null);
    }
}
