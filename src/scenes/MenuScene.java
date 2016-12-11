package scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import managers.FontManager;
import managers.ImageManager;
import operationcheese.GameEngine;

public class MenuScene extends Scene {

    private Font fontL, font, fontM, fontSm;
    private String title, lvl1, lvl2, lvl3, label1, label2, label3,
            credits1, credits2;
    private BufferedImage bgBtn, bgBtnH, afendarIcon, mouseCharacter, mouseCharacter2;
    private int selectedItem;
    
    public MenuScene(int w, int h, GameEngine ge){
        super(w, h, ge);
        
        FontManager fm = ge.getFontManager();
        ImageManager im = ge.getImageManager();
        
        this.fontL = fm.dynamix;
        this.font = fm.grinched;
        this.fontM = this.font.deriveFont(Font.PLAIN, 28.0f);
        this.fontSm = fm.arial;
        this.fontSm = this.fontSm.deriveFont(Font.PLAIN, 14.0f);
        
        this.title = "OPERATION : CHEESE";
        this.lvl1 = "Easy";
        this.lvl2 = "Medium";
        this.lvl3 = "Hard";
        
        this.label1 = "Collect 10 cheeses in 4 minutes.";
        this.label2 = "Collect 10 cheeses in 3 minutes.";
        this.label3 = "Collect 10 cheeses in 2 minutes.";
        
        this.credits1 = "A 48h game made by Afendar";
        this.credits2 = "Twitter : @Afendar_";
        
        this.bgBtn = im.guiSpritesheet.getSubimage(0, 0, 64, 64);
        this.bgBtnH = im.guiSpritesheet.getSubimage(0, 64, 64, 64);
        this.afendarIcon = im.guiSpritesheet.getSubimage(64, 64, 32, 32);
        this.mouseCharacter = im.guiSpritesheet.getSubimage(0, 440, 155, 200);
        
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-this.mouseCharacter.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        this.mouseCharacter2 = op.filter(this.mouseCharacter, null);
        
        this.selectedItem = 0;
    }
    
    @Override
    public Scene update(double dt) {
        
        processHover();
        
        return processClick();
    }

    public Scene processClick(){
        Scene currentScene = this;
        
        if(this.ge.getInputListener().mousePressed && this.ge.getInputListener().mouseClicCount == 1){
            switch(this.selectedItem){
                case 1:
                    currentScene = new GameScene(this.w, this.h, this.ge, 0);
                    break;
                case 2:
                    currentScene = new GameScene(this.w, this.h, this.ge, 1);
                    break;
                case 3:
                    currentScene = new GameScene(this.w, this.h, this.ge, 2);
                    break;
                default:
                    currentScene = this;
            }
        }
        
        return currentScene;
    }
    
    public void processHover(){
        
        int mouseX = this.ge.getInputListener().mouseX;
        int mouseY = this.ge.getInputListener().mouseY;
        
        //this.w/3 - 30, 243, 276, 64
        if(mouseX > this.w/3 - 30 && mouseX < this.w/3 + 233 && mouseY > 243 && mouseY < 243 + 64){
            this.selectedItem = 1;
        }
        //this.w/3, 324, 276, 64
        else if(mouseX > this.w/3 && mouseX < this.w/3 + 276 && mouseY > 324 && mouseY < 324 + 64){
            this.selectedItem = 2;
        }
        //this.w/3 + 32, 404, 276, 64
        else if(mouseX > this.w/3 + 32 && mouseX < this.w/3 + 298 && mouseY > 404 && mouseY < 468){
            this.selectedItem = 3;
        }
        else{
            this.selectedItem = 0;
        }
    }
    
    @Override
    public void render(Graphics g) {
        g.setColor(new Color(255, 240, 213));
        g.fillRect(0, 0, this.w, this.h);
        
        g.drawImage(this.mouseCharacter, this.w - 155, this.h - 199, null);
        g.drawImage(this.mouseCharacter2, 0, this.h - 199, null);
        
        g.setColor(Color.BLACK);
        FontMetrics fm = g.getFontMetrics(this.fontL);
        g.setFont(this.fontL);
        int titleLength = fm.stringWidth(this.title);
        g.drawString(this.title, this.w/2 - titleLength/2, 150);
        
        //btn1
        if(this.selectedItem == 1){
            g.drawImage(this.bgBtnH, this.w/3 - 32, 250, null);
            g.setColor(new Color(128,0,0));
        }
        else{
            g.drawImage(this.bgBtn, this.w/3 - 32, 250, null);
            g.setColor(Color.BLACK);
        }
        g.setFont(this.fontM);
        g.drawString(this.lvl1, this.w/3 + 48, 270);
        g.setFont(this.font);
        g.drawString(this.label1, this.w/3 + 48, 300);
        
        //btn2
        if(this.selectedItem == 2){
            g.drawImage(this.bgBtnH, this.w/3, 330, null);
            g.setColor(new Color(128,0,0));
        }
        else{
            g.drawImage(this.bgBtn, this.w/3, 330, null);
            g.setColor(Color.BLACK);
        }
        g.setFont(this.fontM);
        g.drawString(this.lvl2, this.w/3 + 80, 350);
        g.setFont(this.font);
        g.drawString(this.label2, this.w/3 + 80, 380);
        
        //btn3
        if(this.selectedItem == 3){
            g.drawImage(this.bgBtnH, this.w/3 + 32, 410, null);
            g.setColor(new Color(128,0,0));
        }
        else{
            g.drawImage(this.bgBtn, this.w/3 + 32, 410, null);
            g.setColor(Color.BLACK);
        }
        g.setFont(this.fontM);
        g.drawString(this.lvl3, this.w/3 + 112, 430);
        g.setFont(this.font);
        g.drawString(this.label3, this.w/3 + 112, 460);

        g.drawImage(this.afendarIcon, this.w/2 - 16, this.h - 85, null);
        
        //credits
        g.setColor(Color.BLACK);
        g.setFont(this.fontSm);
        fm = g.getFontMetrics(this.fontSm);
        int credits1Length = fm.stringWidth(this.credits1);
        g.drawString(this.credits1, this.w/2 - credits1Length/2, this.h - 40);
        int credits2Length = fm.stringWidth(this.credits2);
        g.drawString(this.credits2, this.w/2 - credits2Length/2, this.h - 20);
    }    
}
