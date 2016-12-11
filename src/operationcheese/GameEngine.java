package operationcheese;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import managers.FontManager;
import managers.ImageManager;
import operationcheese.tools.Defines;
import operationcheese.tools.InputListener;
import operationcheese.tools.Profiler;
import operationcheese.tools.TimerThread;
import scenes.MenuScene;
import scenes.Scene;

public class GameEngine extends Canvas implements Runnable {
    
    private Thread tgame;
    private boolean running;
    private int w, h, frame;
    private Runtime instance;
    private Profiler profiler;
    private Scene scene;
    private int elapsedTime, lastTimeLoop;
    private InputListener listener;
    private FontManager fontManager;
    private ImageManager imageManager;
    
    public GameEngine(int w, int h){
        
        this.elapsedTime = 0;
        this.lastTimeLoop = 0;
        this.running = false;
        this.w = w;
        this.h = h;
        
        this.setMinimumSize(new Dimension(w, h));
        this.setMaximumSize(new Dimension(w, h));
        this.setPreferredSize(new Dimension(w, h));
        this.setSize(new Dimension(w, h));
        
        this.instance = Runtime.getRuntime();
        this.fontManager = new FontManager();
        this.imageManager = new ImageManager();
        
        this.profiler = new Profiler(this);
        this.listener = new InputListener(this);
        this.scene = new MenuScene(w, h, this);
    }
    
    public void start(){
        
        if(this.running){
            return;
        }
        
        this.running = true;
        this.tgame = new Thread(this, "gameThread");
        this.tgame.start();
    }
    
    public void stop(){
        this.running = false;
    }
    
    @Override
    public void run(){
        
        long startTime = System.currentTimeMillis();
        long lastTime = System.nanoTime();
        double nsToMs = 1000000000 / 60;
        int frameCpt = 0;
        boolean needUpdate = false;
        this.lastTimeLoop = TimerThread.MILLI;
        
        while(this.running){
            long currentTime = System.nanoTime();
            
            try{
                Thread.sleep(2);
            }
            catch(InterruptedException e){}
        
            needUpdate = false;
            double delta = (currentTime - lastTime) / nsToMs;
            
            if((currentTime - lastTime) / nsToMs >= 1){
                //one tick
                frameCpt++;
                lastTime = currentTime;
                needUpdate = true;
            }
            
            this.render();
            
            if(needUpdate){
                this.update(delta);
            }
            
            if(System.currentTimeMillis() - startTime >= 1000){
                int memory = (int)((this.instance.totalMemory() - instance.freeMemory()) / 1024) / 1024;
                this.frame = frameCpt;
                frameCpt = 0;
                startTime = System.currentTimeMillis();
                String[] datas = {
                    Integer.toString(this.frame),
                    Integer.toString(memory)
                };
                this.profiler.update(datas);
            }
        }
    }
    
    public FontManager getFontManager(){
        return this.fontManager;
    }
    
    public ImageManager getImageManager(){
        return this.imageManager;
    }
    
    public InputListener getInputListener(){
        return this.listener;
    }
    
    public void update(double dt){
        
        this.elapsedTime = TimerThread.MILLI - this.lastTimeLoop;
        this.lastTimeLoop = TimerThread.MILLI;
        if(this.hasFocus()){
            this.scene = this.scene.update(dt);
        }
            
        this.listener.update();
    }
    
    public void render(){
        
        BufferStrategy bs = this.getBufferStrategy();
        
        if(bs == null){
            this.createBufferStrategy(2);
            requestFocus();
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.w, this.h);
        
        this.scene.render(g);
        
        if(Defines.DEBUG){
            this.profiler.render(g);
        }
        
        g.dispose();
        bs.show();
    }
}
