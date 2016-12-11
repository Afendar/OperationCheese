package environment;

import entities.Cat;
import entities.Entity;
import entities.EntityCheese;
import entities.Player;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import managers.ImageManager;
import objectifcheeze.tools.Defines;

public class Room {
    
    public Random rand = new Random();
    
    public int w, h;
    public double b;
    public Layer[] layers;
    public Player player;
    public EntityCheese cheese;
    public Cat cat;
    public ArrayList<Entity>[] entities;
    public ImageManager im;
    private Line2D l1;
    
    public Room(int w, int h, ImageManager im){
        
        this.w = w;
        this.h = h;
        this.layers = new Layer[1];
        this.im = im;
        
        this.init();
    }
    
    public void init(){
        this.entities = new ArrayList[this.w * this.h];
        
        for(int i=0;i<this.w * this.h;i++){
            this.entities[i] = new ArrayList<>();
        }
        
        this.layers[0] = new Layer(this.w, this.h, 0, null);
        
        int posX = rand.nextInt(this.w - 8) + 8;
        int posY = rand.nextInt(this.h - 2) + 2;
        
        while(posX == this.w - 3 && posY == this.h - 3){
            posX = rand.nextInt(this.w - 8) + 8;
            posY = rand.nextInt(this.h - 2) + 2;
        }
        
        BufferedImage sprite = this.im.items.getSubimage(0, 0, Defines.TILE_SIZE, Defines.TILE_SIZE);
        
        this.cheese = new EntityCheese(posX, posY, sprite);
        this.entities[posX + posY].add(this.cheese);
        
        this.cat = new Cat(this.w - 3, this.h - 3, this.im.items, this.im.effectsCat);
        this.b = ((this.cat.getY() + 16) - Math.cos(this.cat.face) * (this.cat.getX() + 16));
    }
    
    public Layer getLayer(int layerLevel){
        return this.layers[layerLevel];
    }
    
    public void addPlayer(Player p){
        this.player = p;
    }
    
    public void update(double dt){
        this.player.update(dt);
        this.cat.update(dt);
        
        if(this.cat.isWakeUp){
            this.b = ((this.cat.getY() + 16) - Math.cos(this.cat.face) * (this.cat.getX() + 16));
            for(double i = 0 ; i < 3*Defines.TILE_SIZE; i += Defines.TILE_SIZE){
                double xA = this.cat.getX() + 16;
                double yA = this.cat.getY() + 16;
                double xB = -Math.cos(this.cat.face + 0.4) * (i + 3*Defines.TILE_SIZE + 48) + xA;
                double yB = -Math.sin(this.cat.face + 0.4) * (i + 3*Defines.TILE_SIZE + 48) + yA;

                this.l1 = new Line2D.Double(xA, yA, xB, yB);
                Rectangle r1 = this.player.getBounds();
                
                if(this.l1.intersects(r1) && !this.player.isHidden()){
                    this.player.loose = true;
                }
            }
        }
    }
    
    public int getTile(int nbLayer, int posX, int posY){
        return this.layers[nbLayer].getTile(posX, posY);
    }
    
    public void replaceCheese(){
        int posX = rand.nextInt(this.w - 8) + 8;
        int posY = rand.nextInt(this.h - 2) + 2;
        
        while(posX == this.w - 3 && posY == this.h - 3){
            posX = rand.nextInt(this.w - 8) + 8;
            posY = rand.nextInt(this.h - 2) + 2;
        }
        
        this.cheese.setX(posX);
        this.cheese.setY(posY);
    }
    
    public ArrayList<Entity> getEntities(int x, int y){
        ArrayList<Entity> result = new ArrayList<>();
        
        for(int i=0;i<this.w;i++){
            for(int j=0;j<this.h;j++){
                ArrayList<Entity> list = this.entities[i + j];
                for(int k = 0;k<list.size();k++){
                    Entity e = list.get(k);
                    if(e.intersects(x, y)){
                        result.add(e);
                    }
                }
            }
        }
        return result;
    }
    
    public void render(Graphics g){
        
        this.layers[0].render(g);
        this.player.render(g);
        this.cheese.render(g);
        this.cat.render(g);
    }
}
