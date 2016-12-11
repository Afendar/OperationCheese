package objectifcheeze;

import java.awt.Dimension;
import javax.swing.JFrame;
import objectifcheeze.tools.Defines;

public class Window extends JFrame{
    
    public Window(){
        GameEngine ge = new GameEngine(Defines.SCREEN_WIDTH, Defines.SCREEN_HEIGHT);
        this.init(ge);
        ge.start();
    }
    
    private void init(GameEngine ge){
        
        this.setTitle("Objectif Cheeze - v" + Defines.VERSION);
        this.add(ge);
        this.getContentPane().setPreferredSize(new Dimension(Defines.SCREEN_WIDTH, Defines.SCREEN_HEIGHT));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    
}
