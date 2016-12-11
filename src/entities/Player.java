package entities;

import environment.Layer;
import environment.Room;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import operationcheese.tools.Defines;
import operationcheese.tools.InputListener;

public class Player extends Entity {

    private int velX, velY;
    private Room room;
    private InputListener listener;
    private EntityCheese cheese;
    private BufferedImage sprite, spritesheet, tickIcon;
    public boolean win, loose, hidden, displayTick;
    public int nbCheese, delta, offset, timeAnim, direction;
    public Rectangle posCheese;
    
    public Player(int x, int y, Room r, InputListener l){
        super(x, y);
        this.room = r;
        this.posCheese = new Rectangle(r.cheese.x + 16, r.cheese.y + 16, Defines.TILE_SIZE - 32, Defines.TILE_SIZE - 32);
        this.listener = l;
        this.velX = this.velY = 0;
        this.win = this.loose = this.hidden = false;
        this.nbCheese = 0;
        this.delta = 0;
        this.offset = 0;
        this.timeAnim = 0;
        this.direction = 0;
        this.displayTick = false;
        
        try{
            URL url = this.getClass().getResource("/images/mousetileset.png");
            this.spritesheet = ImageIO.read(url);
            this.sprite = this.spritesheet.getSubimage(0, 0, Defines.TILE_SIZE, Defines.TILE_SIZE);
            this.tickIcon = this.spritesheet.getSubimage(0, 128, 44, 32);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void update(double dt) {
        
        if(this.listener.left.enabled){
            this.velX = -Defines.PLAYER_SPEED;
            if(this.direction != 1){
                this.direction = 1;
                this.timeAnim = 0;
                this.offset = 0;
            }
            this.timeAnim += dt;
            if(this.timeAnim > 8){
                this.timeAnim = 0;
                this.offset++;
                if(this.offset > 3){
                    this.offset = 0;
                }
                this.sprite = this.spritesheet.getSubimage(this.offset * Defines.TILE_SIZE, 0, Defines.TILE_SIZE, Defines.TILE_SIZE);
            }
        }
        else if(this.listener.right.enabled){
            this.velX = Defines.PLAYER_SPEED;
            if(this.direction != 0){
                this.direction = 0;
                this.timeAnim = 0;
                this.offset = 0;
            }
            this.timeAnim += dt;
            if(this.timeAnim > 8){
                this.timeAnim = 0;
                this.offset++;
                if(this.offset > 3){
                    this.offset = 0;
                }
                this.sprite = this.spritesheet.getSubimage(this.offset * Defines.TILE_SIZE, Defines.TILE_SIZE, Defines.TILE_SIZE, Defines.TILE_SIZE);
            }
        }
        else{
            this.velX = 0;
        }
        
        if(this.listener.forward.enabled){
            this.velY = -Defines.PLAYER_SPEED;
        }
        else if(this.listener.backward.enabled){
            this.velY = Defines.PLAYER_SPEED;
        }
        else{
            this.velY = 0;
        }
        
        if(this.velX != 0 || this.velY != 0){
            if(this.room.cat.noise < 200){
                this.delta += dt;
                if(this.delta >= 5){
                    this.delta = 0;
                    this.room.cat.addNoise(7);
                }
            }
            this.move();
        }
        
        if(this.listener.mousePressed){
            this.use();
        }
    }

    private void move(){
        this.x += this.velX;
        this.y += this.velY;
        
        Layer floor = this.room.getLayer(0);
        
        if(this.x < 0){
            this.x = 0;
        }
        if(this.y < Defines.TILE_SIZE){
            this.y = Defines.TILE_SIZE;
        }
        if(this.x + Defines.PLAYER_SIZE > floor.getWidth()){
            this.x = floor.getWidth() - Defines.PLAYER_SIZE;
        }
        if(this.y + Defines.PLAYER_SIZE > floor.getHeight()){
            this.y = floor.getHeight() - Defines.PLAYER_SIZE;
        }
        
        if(this.room.getTile(0, this.x + 16, this.y + 16) == 1 || 
                this.room.getTile(0, this.x + Defines.PLAYER_SIZE - 16, this.y + 16) == 1 ||
                this.room.getTile(0, this.x + Defines.PLAYER_SIZE - 16, this.y + Defines.PLAYER_SIZE - 16) == 1 ||
                this.room.getTile(0, this.x + 16, this.y + Defines.PLAYER_SIZE -16) == 1){
            this.x -= velX;
            this.y -= velY;
        }
        else if(this.room.getTile(0, this.x + 16, this.y + 16) == 4 && this.cheese != null){
            this.cheese = null;
            this.nbCheese++;
            this.displayTick = false;
            if(this.nbCheese == Defines.WIN_NB_CHEESE){
                this.win = true;
            }
            else{
                this.room.replaceCheese();
                this.posCheese = new Rectangle(this.room.cheese.x + 16, this.room.cheese.y + 16, Defines.TILE_SIZE - 32, Defines.TILE_SIZE - 32);
            }
        }
        
        if(this.getBounds().intersects(posCheese)){
            this.displayTick = true;
        }
    }
    
    private boolean use(){
        //left
        if(this.direction == 1 && ( use(x + 16, y + 16) || use(x + 16, y + Defines.TILE_SIZE - 16))) return true;
        //right
        else if(this.direction == 0 && (use(x + Defines.TILE_SIZE - 16, y + 16) || use(x + Defines.TILE_SIZE - 16, y + Defines.TILE_SIZE - 16))) return true;
        return false;
    }
    
    private boolean use(int x0, int y0){
        ArrayList<Entity> entities = this.room.getEntities(x0, y0);
        
        entities.stream().filter((e) -> (e != this)).forEach((e) -> {
            if(e instanceof EntityCheese){
                this.cheese = (EntityCheese) e;
            }
            else if(e instanceof HideoutItem){
                this.hidden = !this.hidden;
            }
        });
        
        return false;
    }
    
    public void setHidden(boolean hidden){
        this.hidden = hidden;
    }
    
    public boolean isHidden(){
        return this.hidden;
    }
    
    public Rectangle getBounds(){
        Rectangle bounds;
        bounds = new Rectangle((int)this.x + 16, (int)this.y + 16, Defines.PLAYER_SIZE - 32, Defines.PLAYER_SIZE - 32);
        return bounds;
    }
    
    @Override
    public void render(Graphics g) {
        g.drawImage(this.sprite, this.x, this.y, null);
        if(this.cheese != null){
            this.cheese.x = this.x;
            this.cheese.y = this.y;
        }
        if(this.displayTick && this.cheese == null){
            g.drawImage(this.tickIcon, this.x + 32, this.y, null);
        }
    }
    
    public boolean hasWon(){
        return this.win;
    }
    
    public boolean hasLost(){
        return this.loose;
    }
    
}
