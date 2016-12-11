package entities;

import java.awt.Graphics;

public class ItemEntity extends Entity {

    protected Player player;
    
    public ItemEntity(int x, int y){
        super(x, y);
    }
    
    @Override
    protected void touchedBy(Entity e){
        if(e instanceof Player){
            System.out.println("touched item entity");
        }
    }
    
    @Override
    public void update(double dt) {
    }

    @Override
    public void render(Graphics g) {
    }
    
    public void hide(Player player, boolean hidden){
        this.player.setHidden(hidden);
    }
    
    public void take(Player player){
        this.player = player;
    }
}
