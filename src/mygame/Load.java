
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.app.Application;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;

public class Load extends Main {
    
    public static Texture West;
    public static Texture East;
    public static Texture North;
    public static Texture South;
    public static Texture Up;
    public static Texture Down;
    public static Spatial Sky ;

    
    
    public void InitSky(){ 
        West  = assetManager.loadTexture("Scenes/sky_left.png");
        East  = assetManager.loadTexture("Scenes/sky_right.png");
        North = assetManager.loadTexture("Scenes/sky_front.png");
        South = assetManager.loadTexture("Scenes/sky_back.png");
        Up    = assetManager.loadTexture("Scenes/sky_top.png");
        Down  = assetManager.loadTexture("Scenes/sky_bottom.png");

        ///Sky = SkyFactory.createSky(assetManager, West, East, North, South, Up, Down);
        rootNode.attachChild(SkyFactory.createSky(assetManager, West, East, North, South, Up, Down));
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
