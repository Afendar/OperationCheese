package objectifcheeze.tools;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import objectifcheeze.GameEngine;

public class InputListener implements KeyListener, MouseMotionListener, MouseListener {
    
    public class Action{
        
        public boolean enabled, typed;
        public int pressCpt, absorbCpt;
        
        public Action(){
            actions.add(this);
        }
        
        public void switched(boolean enabled){
            if(enabled != this.enabled)
                this.enabled = enabled;
            if(enabled)
                this.pressCpt++;
        }
        
        public void update(){
            if(this.absorbCpt < this.pressCpt){
                this.absorbCpt++;
                this.typed = true;
            }
            else{
                this.typed = false;
            }
        }
    }
    
    public boolean[] keys;
    public ArrayList<Action> actions = new ArrayList<Action>();
    
    public Action left = new Action();
    public Action right = new Action();
    public Action forward = new Action();
    public Action backward = new Action();
    public Action start = new Action();
    public int mouseX, mouseY, mouseClicCount;
    public boolean mouseExited, mousePressed;
    public KeyEvent e = null;
    
    public InputListener(GameEngine ge){
        
        ge.addKeyListener(this);
        ge.addMouseMotionListener(this);
        ge.addMouseListener(this);
        
        this.mouseX = this.mouseY = this.mouseClicCount = 0;
        this.mouseExited = true;
        this.mousePressed = false;
        this.keys = new boolean[KeyEvent.KEY_LAST];
    }
    
    public void update(){
        this.mouseClicCount = 0;
        for(int i=0;i<this.actions.size();i++){
            actions.get(i).update();
        }
    }
    
    public void processKey(KeyEvent e, boolean enabled){
        
        switch(e.getKeyCode()){
            case KeyEvent.VK_LEFT:
                this.left.switched(enabled);
                break;
            case KeyEvent.VK_RIGHT:
                this.right.switched(enabled);
                break;
            case KeyEvent.VK_UP:
                this.forward.switched(enabled);
                break;
            case KeyEvent.VK_DOWN:
                this.backward.switched(enabled);
                break;
            case KeyEvent.VK_SPACE:
                this.start.switched(enabled);
                break;
        }
        
    }
    
    @Override
    public void keyTyped(KeyEvent e){
        
    }
    
    @Override
    public void keyPressed(KeyEvent e){
        this.processKey(e, true);
        this.e = e;
    }
    
    @Override
    public void keyReleased(KeyEvent e){
        this.processKey(e, false);
        this.e = null;
    }
    
    @Override
    public void mouseDragged(MouseEvent e){
        this.mouseX = e.getX();
        this.mouseY = e.getY();
    }
    
    @Override
    public void mouseMoved(MouseEvent e){
        this.mouseX = e.getX();
        this.mouseY = e.getY();
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.mousePressed = true;
        this.mouseClicCount = e.getClickCount();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.mousePressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.mouseExited = false;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.mouseExited = true;
    }
}
