package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class EntityCheese extends ItemEntity {
    
    BufferedImage sprite;
    
    public EntityCheese(int x, int y, BufferedImage sprite){
        super(x, y);
        this.sprite = sprite;
    }

    @Override
    public void update(double dt) {
        
    }
    
    @Override
    public void render(Graphics g) {
        g.drawImage(this.sprite, this.x, this.y, null);
    }
}
