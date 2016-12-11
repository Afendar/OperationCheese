package scenes;

import entities.Player;
import environment.Room;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import managers.ImageManager;
import objectifcheeze.GameEngine;
import objectifcheeze.tools.Defines;
import objectifcheeze.tools.TimerThread;

public class GameScene extends Scene {
    
    private Room room;
    private Player player;
    private Font font, fontSm, fontL;
    private int time, minutes, secondes, difficulty, endTime, timeAnim;
    private BufferedImage cheeseIcon, noiseIcon, noiseBar, mouseCharacter, reveilIcon;
    private String readyToPlay;
    private String[] instructions;
    private boolean displayInstrcutions;
    private int instructionPage, instructionStart;
    
    public GameScene(int w, int h, GameEngine ge, int difficulty){
        super(w, h, ge);
        
        this.difficulty = difficulty;
        this.displayInstrcutions = true;
        this.instructionPage = 0;
        this.instructionStart = 0;
        this.timeAnim = 0;
        
        switch(difficulty){
            case 0:
                this.endTime = 4;
                break;
            case 1:
                this.endTime = 3;
                break;
            case 2:
                this.endTime = 2;
                break;
            default:
                this.endTime = 4;
                break;
        }
        
        this.instructions = new String[6];
        this.instructions[0] = "The cat sleeps peacefully. You have " + this.endTime + " minutes to collect";
        this.instructions[1] = "10 cheeses. But be careful not to wake up the cat. !";
        this.instructions[2] = "Use Arrows keys to move the mouse.";
        this.instructions[3] = "Click to grab the piece of cheese when green tick icon appears.";
        this.instructions[4] = "The cat is awake !!! Be careful because his eyes travel the room.";
        this.instructions[5] = "You lose the game if he discovers you.";
        
        this.readyToPlay = "Click to continue";
        
        this.font = ge.getFontManager().grinched;
        this.fontSm = ge.getFontManager().arial;
        this.fontSm = this.fontSm.deriveFont(Font.PLAIN, 16.0f);
        this.fontL = this.font.deriveFont(Font.PLAIN, 36.0f);
        this.room = new Room(Defines.ROOM_WIDTH, Defines.ROOM_HEIGHT, ge.getImageManager());
        this.player = new Player(0, 7, this.room, ge.getInputListener());
        this.init();
        this.time = TimerThread.MILLI;
    }
    
    private void init(){
        this.room.addPlayer(this.player);
        
        ImageManager im = ge.getImageManager();
        
        this.cheeseIcon = im.guiSpritesheet.getSubimage(0, 0, 64, 64);
        this.noiseIcon = im.guiSpritesheet.getSubimage(64, 0, 64, 64);
        this.mouseCharacter = im.guiSpritesheet.getSubimage(0, 440, 155, 200);
        this.noiseBar = im.guiSpritesheet.getSubimage(128, 0, 200, 16);
        this.reveilIcon = im.guiSpritesheet.getSubimage(128, 64, 159, 91);
    }
    
    @Override
    public Scene update(double dt){
        
        if(this.displayInstrcutions){
            this.updateInstrcution(dt);
        }
        else{
            if(this.room.cat.isWakeUp && this.instructionPage == 2){
                this.displayInstrcutions = true;
            }
            
            if(!this.player.hasWon() && !this.player.hasLost()){
                this.room.update(dt);
                this.minutes = (int)((TimerThread.MILLI - this.time)/60000);
                this.secondes = (int)((TimerThread.MILLI - this.time - this.minutes * 60000)/1000);
            }

            if(this.minutes == this.endTime){
                this.player.loose = true;
            }
        }
        return this;
    }
    
    public void updateInstrcution(double dt){
        
        if(this.ge.getInputListener().mousePressed && this.ge.getInputListener().mouseClicCount == 1){
            switch(this.instructionPage){
                case 0:
                    break;
                case 1:
                    this.displayInstrcutions = false;
                    this.time = TimerThread.MILLI;
                    break;
                case 2:
                    this.displayInstrcutions = false;
                    break;
            }
            this.instructionPage++;
        }
    }
    
    @Override
    public void render(Graphics g){
        
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.w, this.h);
        
        this.room.render(g);
        this.renderGui(g);

        if(this.player.hasWon()){
            this.renderWinScene(g);
        }
        else if(this.player.hasLost()){
            this.renderLooseScene(g);
        }

        if(this.displayInstrcutions){
            this.renderDialogs(g);
        }
    }
    
    public void renderWinScene(Graphics g){
        String t = "You win !!!!";
        FontMetrics fm = g.getFontMetrics(this.fontL);
        g.setFont(this.fontL);
        g.setColor(new Color(103, 167, 16));
        g.drawString(t, (this.w / 2) - (fm.stringWidth(t) / 2), (this.h/2) - (fm.getAscent()/2));
    }
    
    public void renderLooseScene(Graphics g){
        String t = "You loose !!!!";
        FontMetrics fm = g.getFontMetrics(this.fontL);
        g.setFont(this.fontL);
        g.setColor(new Color(191, 0, 0));
        g.drawString(t, (this.w / 2) - (fm.stringWidth(t) / 2), (this.h/2) - (fm.getAscent()/2));
    }
    
    public void renderDialogs(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRoundRect(150, this.h - 140, this.w - 250, 130, 30, 30);
        g.setColor(Color.DARK_GRAY);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRoundRect(150, this.h - 140, this.w - 250, 130, 30, 30);
        g.drawImage(this.mouseCharacter,70, this.h - 200, null);
        switch(this.instructionPage){
            case 0:
                this.renderDial1(g);
                break;
            case 1:
                this.renderDial2(g);
                break;
            case 2:
                this.renderDial3(g);
                break;
        }
    }
    
    public void renderDial1(Graphics g){
        g.setColor(Color.BLACK);
        g.setFont(this.font);
        for(int i=0;i< 2;i++){
            g.drawString(this.instructions[i], 230, 500 + i * 32);
        }

        g.setFont(this.fontSm);
        FontMetrics fm = g.getFontMetrics(this.fontSm);
        int readyLength = fm.stringWidth(this.readyToPlay);
        g.drawString(this.readyToPlay, this.w/2 - readyLength/2, this.h - 20);
    }
    
    public void renderDial2(Graphics g){
        g.setColor(Color.BLACK);
        g.setFont(this.font);
        for(int i=2;i< 4;i++){
            g.drawString(this.instructions[i], 230, 500 + (i - 2) * 32);
        }
        
        g.setFont(this.fontSm);
        FontMetrics fm = g.getFontMetrics(this.fontSm);
        int readyLength = fm.stringWidth(this.readyToPlay);
        g.drawString(this.readyToPlay, this.w/2 - readyLength/2, this.h - 20);
    }
    
    public void renderDial3(Graphics g){
        g.setColor(Color.BLACK);
        g.setFont(this.font);
        for(int i=4;i< 6;i++){
            g.drawString(this.instructions[i], 230, 500 + (i - 4) * 32);
        }
        g.setFont(this.fontSm);
        FontMetrics fm = g.getFontMetrics(this.fontSm);
        int readyLength = fm.stringWidth(this.readyToPlay);
        g.drawString(this.readyToPlay, this.w/2 - readyLength/2, this.h - 20);
    }
    
    public void renderGui(Graphics g){
        String text = "" + this.player.nbCheese;
        FontMetrics fm = g.getFontMetrics(this.font);
        g.setFont(this.font);
        g.setColor(Color.BLACK);
        int textLength = fm.stringWidth(text);
        g.setColor(Color.WHITE);
        g.fillRoundRect(this.w - 80, 10, 60, 32, 8, 8);
        g.setColor(Color.DARK_GRAY);
        g.drawRoundRect(this.w - 80, 10, 60, 32, 8, 8);
        g.setColor(Color.BLACK);
        g.drawString(text, this.w - textLength - 30, 25 + (fm.getAscent()/2));
        g.drawImage(this.cheeseIcon, this.w - (120), 0, null);
        
        g.setColor(Color.WHITE);
        g.fillRoundRect(this.w/2 - 170, 0, 64, 64, 8, 8);
        g.drawImage(this.noiseIcon, this.w/2 - 170, 0, null);
        
        g.setColor(Color.WHITE);
        g.fillRoundRect(this.w/2 - 100, 10, 200, 16, 8, 8);
        g.setColor(Color.BLACK);
        if(this.room.cat.noise > 0){
            int noise = this.room.cat.noise;
            if(noise > 200){
                noise = 200;
            }
            BufferedImage bar = this.noiseBar.getSubimage(0, 0, noise, 16);
            g.drawImage(bar, this.w/2 - 100, 10, null);
        }
        g.setColor(Color.DARK_GRAY);
        g.drawRoundRect(this.w/2 - 100, 10, 200, 16, 8, 8);
        
        g.drawImage(this.reveilIcon, 10, 2, null);
        
        if(this.endTime - this.minutes < 2){
            g.setColor(new Color(176, 0, 27));
        }
        else{ 
            g.setColor(Color.WHITE);
        }
        text = String.format("%02d", this.minutes) + ":" + String.format("%02d", this.secondes);
        g.drawString(text, 45, (48 + fm.getAscent()/2));
    }
    
}
