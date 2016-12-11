package entities;

import java.awt.Graphics;
import objectifcheeze.tools.Defines;

public abstract class Entity {
    
    protected int x, y;
    
    public Entity(int x, int y){
        this.x = x * Defines.TILE_SIZE;
        this.y = y * Defines.TILE_SIZE;
    }
    
    public void setX(int x){
        this.x = x * Defines.TILE_SIZE;
    }
    
    public void setY(int y){
        this.y = y * Defines.TILE_SIZE;
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
    
    public boolean intersects(int x, int y){
        
        if(this.x < x && this.x + Defines.TILE_SIZE > x &&
                this.y < y && this.y + Defines.TILE_SIZE > y)
            return true;
        
        return false;
    }
    
    protected void touchedBy(Entity e){
    }
    
    public abstract void update(double dt);
    public abstract void render(Graphics g);
}
