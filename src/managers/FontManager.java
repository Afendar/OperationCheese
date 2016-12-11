package managers;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.net.URL;

public class FontManager extends AssetManager {
    
    public Font arial, dynamix, grinched;
    
    public FontManager(){
        super();
        
        try{
            URL url = this.getClass().getResource("/fonts/arial.ttf");
            this.arial = Font.createFont(Font.TRUETYPE_FONT, url.openStream());
            this.arial = this.arial.deriveFont(Font.PLAIN, 18.0f);
            
            url = this.getClass().getResource("/fonts/foliamixregular.ttf");
            this.dynamix = Font.createFont(Font.TRUETYPE_FONT, url.openStream());
            this.dynamix = this.dynamix.deriveFont(Font.PLAIN, 58.0f);
            
            url = this.getClass().getResource("/fonts/grinchedregular.ttf");
            this.grinched = Font.createFont(Font.TRUETYPE_FONT, url.openStream());
            this.grinched = this.grinched.deriveFont(Font.PLAIN, 18.0f);
        }
        catch(FontFormatException|IOException e){
            e.getMessage();
        }
    }
}
