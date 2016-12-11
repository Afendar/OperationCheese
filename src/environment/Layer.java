package environment;

import environment.tiles.TileAtlas;
import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import operationcheese.tools.Defines;

public class Layer {
    
    private int w, h;
    private int[][] tiles;
    
    public Layer(int w, int h, int level, Layer parentLayer){
        
        this.w = w;
        this.h = h;
        
        this.tiles = new int[w][h];
        
        this.loadLayer(level);
        
    }
    
    private void loadLayer(int level){
        
        try{
            InputStream in = this.getClass().getResourceAsStream("/layers/layer" + level + ".txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = null;
            int i=0;
            while((line = br.readLine()) != null){
                for(int j=0;j<line.length();j++){
                    this.tiles[j][i] = Integer.parseInt(""+line.charAt(j));
                }
                i++;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public int getTile(int posX, int posY){
        return this.tiles[(int)posX / Defines.TILE_SIZE][(int)posY / Defines.TILE_SIZE];
    }
    
    public int getWidth(){
        return this.w * Defines.TILE_SIZE;
    }
    
    public int getHeight(){
        return this.h * Defines.TILE_SIZE;
    }
    
    public void update(double dt){
        
    }
    
    public void render(Graphics g){
        g.setColor(Color.BLACK);
        for(int i=0;i<this.tiles.length;i++){
            for(int j=0;j<this.tiles[i].length;j++){
                switch(this.tiles[i][j]){
                    case 0:
                        g.fillRect(i * Defines.TILE_SIZE, j * Defines.TILE_SIZE, Defines.TILE_SIZE, Defines.TILE_SIZE);
                        break;
                    case 1:
                        int top = 0;
                        if(j == 0){
                            top = 1;
                        }
                        if(i+1 < this.w && i == 1 && j == 0 && this.tiles[i][j+1] == 1){
                            top = 2;
                        }
                        else if(i==1){
                            top = 3;
                        }
                        TileAtlas.wall.render(g, i, j, top, false);
                        break;
                    case 2:
                        boolean light = false;
                        if(j-1 > 0 && this.tiles[i][j-1] == 5){
                            light = true;
                        }
                        TileAtlas.floor.render(g, i, j, light, false);
                        break;
                    case 3:
                        TileAtlas.corridor.render(g, i, j);
                        break;
                    case 4:
                        TileAtlas.miceRoom.render(g, i, j);
                        break;
                    case 5:
                        TileAtlas.wall.render(g, i, j, 0, true);
                        break;
                    case 6:
                        TileAtlas.floor.render(g, i, j, false, true);
                        break;
                }
            }
        }
    }
}
