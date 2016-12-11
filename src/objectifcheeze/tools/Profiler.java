package objectifcheeze.tools;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import objectifcheeze.GameEngine;

public class Profiler {
    
    private String[] labels = {"fps", "memory"};
    private String[] datas = {"0", "0"};
    
    private GameEngine ge;
    
    public Profiler(GameEngine ge){
        this.ge = ge;
    }
    
    public void update(String[] datas){
        this.datas = datas;
    }
    
    public void render(Graphics g){
        g.setFont(this.ge.getFontManager().arial);
        FontMetrics fm = g.getFontMetrics(this.ge.getFontManager().arial);
        
        String text = "FPS : " + this.datas[0];
        Rectangle2D rect = fm.getStringBounds(text, g);
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 30 - fm.getAscent() - 3, (int) rect.getWidth() + 40, (int)rect.getHeight() + 6);
        g.setColor(Color.WHITE);
        g.drawString(text, 30, 30);
        
        text = "Memory : " + this.datas[1] + " Mo";
        rect = fm.getStringBounds(text, g);
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 57 - fm.getAscent(), (int) rect.getWidth() + 40, (int)rect.getHeight() + 6);
        g.setColor(Color.WHITE);
        g.drawString(text, 30, 60);
    }
    
}
