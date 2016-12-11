package scenes;

import java.awt.Graphics;
import operationcheese.GameEngine;

public abstract class Scene {
    
    protected int w, h;
    protected GameEngine ge;
    protected Class runtimeClass;
    
    public Scene(int w, int h, GameEngine ge){
        
        this.w = w;
        this.h = h;
        this.ge = ge;
        this.runtimeClass = this.getClass();
    }
    
    public abstract Scene update(double dt);
    public abstract void render(Graphics g);
    
}
