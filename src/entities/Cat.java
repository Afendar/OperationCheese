package entities;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import operationcheese.tools.Defines;

public class Cat extends Entity {
    
    public int noise, delta, delta2, dreamFrame;
    public double face, phi;
    private BufferedImage spritesheet, sprite, effects, effectDream;
    public boolean isWakeUp, dream;
    
    public Cat(int x, int y, BufferedImage spritesheet, BufferedImage effects){
        super(x, y);
        
        this.noise = 0;
        this.spritesheet = spritesheet;
        this.sprite = this.spritesheet.getSubimage(64, 0, 82, 64);
        this.delta = this.delta2 = this.dreamFrame = 0;
        this.isWakeUp = false;
        this.dream = false;
        this.effects = effects;
        this.face = this.phi = 0;
    }
    
    public void addNoise(int noise){
        this.noise += noise;
        if(this.noise > 200){
            this.noise = 200;
        }
    }
    
    @Override
    public void update(double dt){
        if(!this.isWakeUp){
            if(this.noise >= 200){
                this.isWakeUp = true;
                this.delta = 0;
                this.sprite = this.spritesheet.getSubimage(3*Defines.TILE_SIZE, 0, Defines.TILE_SIZE, 86);
            }
            else{
                this.delta += dt;
                if(this.delta >= 20 && this.noise != 0){
                    this.delta = 0;
                    this.noise -= 4;
                }
            }
            
            if(this.noise >= 100 && this.dreamFrame != 2){
                this.dream = true;
                this.delta2 += dt;
                
                if(this.delta2 >= 20){
                    this.delta2 = 0;
                    this.dreamFrame++;
                    this.effectDream = this.effects.getSubimage(this.dreamFrame * Defines.TILE_SIZE, 0, Defines.TILE_SIZE, Defines.TILE_SIZE);
                }
            }
            else if(this.noise < 100 && this.noise > 90){
                this.dream = false;
                this.dreamFrame = 0;
                this.effectDream = this.effects.getSubimage(this.dreamFrame * Defines.TILE_SIZE, 0, Defines.TILE_SIZE, Defines.TILE_SIZE);
            }
        }
        else{
            this.delta += dt;
            if(this.delta > 10){
                this.face = 1.5 * Math.cos(this.phi/8);
            }
            this.phi += 0.08;
        }
    }
    
    @Override
    public void render(Graphics g){
        if(!this.isWakeUp){
            g.drawImage(this.sprite, this.x, this.y, null);
            if(this.dream){
                g.drawImage(this.effectDream, this.x - 48, this.y - 32, null);
            }
        }
        else{
            g.setColor(new Color(176, 0, 27));
            Graphics2D g2d = (Graphics2D)g.create();
            g2d.rotate(this.face, this.x + 16, this.y + 16);
            Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
            g2d.setStroke(dashed);
            g2d.drawLine(this.x - 5*Defines.TILE_SIZE, this.x - 5*Defines.TILE_SIZE, this.x + 16, this.y + 16);
            g2d.rotate(-this.face, this.x + 16, this.y + 16);
            g.drawImage(this.sprite, this.x, this.y - 22, null);
        }
    }
}
