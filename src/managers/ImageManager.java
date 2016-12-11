package managers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class ImageManager extends AssetManager {
    
    public BufferedImage avatar, items, effectsCat, guiSpritesheet;
    public ImageManager(){
        super();
        
        try{
            URL url = this.getClass().getResource("/images/items.png");
            this.items = ImageIO.read(url);
            url = this.getClass().getResource("/images/effects-cat.png");
            this.effectsCat = ImageIO.read(url);
            url = this.getClass().getResource("/images/gui.png");
            this.guiSpritesheet = ImageIO.read(url);
        }
        catch(IOException e){
            e.getStackTrace();
        }
        
    }
    
}
