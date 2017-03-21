package mygame;
////A&ASD
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.*;
import com.jme3.input.controls.*;
import com.jme3.light.AmbientLight;
import com.jme3.math.Quaternion;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import com.jme3.ui.Picture;
import com.jme3.util.SkyFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static mygame.Main.IncreaseDecreaseSpeed;
import static mygame.Main.Minutes;
import static mygame.Main.PlayerDamageText;
import static mygame.Main.PlayerKillText;
import static mygame.Main.PlayerSinGetText;
import static mygame.Main.Sakamoto;
import static mygame.Main.ScreenHeight;
import static mygame.Main.ScreenWidth;
import static mygame.Main.ShipRBC;
import static mygame.Main.ShipVelocity;
import static mygame.Main.SinGain;
import static mygame.Main.SpaceShip;
import static mygame.Main.bulletAppState;
import static mygame.Main.bulletTag;
import static mygame.Main.count;
import static mygame.Main.enemyTag;
import static mygame.Main.itemPickupTag;
import static mygame.Main.playerTag;
import static mygame.Main.shipG;
import static mygame.Main.thisApp;


///Constructor
public class Main extends SimpleApplication implements PhysicsCollisionListener {
    
    
    ///random number generator
    public Random r=new Random();
    
    ///Music
    public AudioNode Track_1; ///must initailize in the main class, it's declayered here to make it global
    public AudioNode Track_2; ///
    public AudioNode Track_69;///Sakamoto music
    boolean violence=false;///audio trigger

    
    ///Sound Effects
    public float[] KineticBang_SEcycle={0.5f,0.25f,0.37f,0.13f};///loudness of sound effect (SE)
    public float[] KineticBang_SEcycle1={1f,1.1f,0.9f,1.2f};///speed of SE
    
    public AudioNode Notification_SE;
    public AudioNode Ping_SE;
    public AudioNode SinGet_SE;
    public AudioNode PlayerEngine_SE;
    public AudioNode Reload_SE;

    
    //Game related variables
    
    public long c=0;//main counter
    public long Accumulator=1;
    public static Application thisApp;

    
    ///Game clock variables
    public int Seconds=0;
    public static int Minutes=0;///to modify spawn and other things
    public static int SinGain=1;
    
    
    ///PAUSEING
    public boolean Paused=false;  
    public long LastPause=0;
    public long PauseDelay=50;
    public static int PauseIconWidth=0;
    public static int PauseIconHeight=PauseIconWidth;
    

    ///public guiNode GuiNodePause;
   
    //Checking Run Stats
    public boolean RunCheckOn=true;
    
    public static float ScreenWidth=0;
    public static float ScreenHeight=0;
    
    
    ///Gameplay related variables
    public int Difficulty=0;
    public int SpawnRate=0;
     
    ///player Charater variables
    
    ///HULL
    public int PlayerCurrentHealth=100000;
    public int PlayerMaxHealth=100000;
    
    ///WEPONS
    public float PlayerWeaponUpgrades=0f;
    public long LastFired=0;
    public float FireRate=75f-(PlayerWeaponUpgrades*0.001f);///millisecond pause between firing each bullet 100=1 second
    public float BulletDamage=1f+(PlayerWeaponUpgrades*0.1f);
    public float BulletVelocity=100+(PlayerWeaponUpgrades*0.01f);
    public float BulletSize=0.01f+(PlayerWeaponUpgrades*0.001f);
    ///SHIELD
    public float PlayerShieldUpgrades=0f;
    public float PlayerShieldMax=100000+PlayerShieldUpgrades*10;
    public float PlayerCurrentShield=PlayerShieldMax;
    public float PlayerShieldRegenRate=(PlayerShieldMax/50f)+(PlayerShieldUpgrades*0.1f);
    ///SPEED
    public float PlayerEngineUpgrades=0f;
    public float PlayerEngine=0.5f;
    public float PlayerBoostEngine=1000+(PlayerEngineUpgrades);
    public float PlayerBoostDrain=-25f-(PlayerEngineUpgrades*0.8f);
    public long LastStrafe=0;
    public long StrafeRate=200-((long)PlayerEngineUpgrades/10);
    public float StrafeDistance=2f;
    ///ENERGY
    public String PlayerEnergySwitchSymbol="-";
    public float PlayerEnergyUpgrades=0f;
    public float PlayerCurrentEnergy=(100+PlayerEnergyUpgrades);
    public float PlayerMaxEnergy=(100+PlayerEnergyUpgrades);
    public float PlayerEnergyRegenRate=(PlayerCurrentEnergy/25f)+(PlayerEnergyUpgrades*0.01f);//energy regenrated per second
    public float PlayerEnergySwitch=-1; ///used to mod the values that your taking away from
                                        ///-1 is taking away 1 is giving
    
    ///Bullet variables
    public AudioNode KineticBang_SE;
    public static int count=0;
    public static Geometry bullet;
    public static BulletAppState bulletAppState;
    public static SphereCollisionShape bulletMesh;
    public static Sphere projectile;

    ///HIT BOX BS
    public static String enemyTag="Enemy", playerTag="Player", bulletTag="Bullet";
    public static String itemPickupTag="SIN";

    ///SHIP PHYSICAIL APPERANCE BS
    public static Geometry shipG;
    public static RigidBodyControl ShipRBC;
    public static BoxCollisionShape playerMesh;
    public static float ShipVelocity=0;
    public static float IncreaseDecreaseSpeed=.1f;

    
    ///Text variables
    ///for dialog function
    public BitmapText DialogText;  
    ///for print text function
    public BitmapText ScreenText;  
    ///for clock text function
    public BitmapText ClockText;
    ///for pause text funtion
    ////lol to hard
    ///for storing and printing the energy values and upgrades
    public BitmapText ShipSystemsText;  

    
    ///initilize text arrays
    public static ArrayList <String> PlayerKillText=new ArrayList();
    public static ArrayList <String> PlayerDamageText=new ArrayList();
    public static ArrayList <String> PlayerSinGetText=new ArrayList();
    
    public static Spatial SpaceShip;
    
    public Geometry SideNG, SideSG, SideTG, SideBG, SideWG, SideEG;
    public RigidBodyControl SideNR, SideSR, SideTR, SideBR, SideWR, SideER;
  

    ///Dank meme variables
    public static Boolean Sakamoto=false;
    public static int SakamotoCountDown=0;
    
    public static boolean  GameModes[]={false, false, false, false,false,false};
    
    //SCORE
    public int Kills=0;
    public long Score=0;
    
    
    public static void main(String[] args) throws FileNotFoundException, IOException{
       
        
        
      ///GETTING TEXT ARRAYS INITILIZED    
        Scanner inputPKT=new Scanner (new File ("PlayerKillDialog.txt"));
        Scanner inputPDT=new Scanner (new File ("PlayerDamageDialog.txt"));
        Scanner inputPSGT=new Scanner (new File ("PlayerSinGetDialog.txt"));
        ///player kill text
        while (inputPKT.hasNextLine()){///while the next stream is not empty
                PlayerKillText.add(inputPKT.nextLine());///add text from thing     
        }
        inputPKT.close();
        ///player geting hit text
        while (inputPDT.hasNextLine()){///while the next stream is not empty
            String s=inputPDT.nextLine();
                PlayerDamageText.add(s);///add text from thing
                System.out.println (s);
        }
        inputPDT.close();
        ///player sin get text
        while (inputPSGT.hasNextLine()){///while the next stream is not empty
                PlayerSinGetText.add(inputPSGT.nextLine());///add text from thing
        }
        inputPSGT.close();

        
       Main app = new Main();
       AppSettings settings = new AppSettings(true);
       
       
       settings.setTitle("ARMED AND ANGRY-LXIX-SATANS DEMISE");
       settings.setSettingsDialogImage("Interface/ARMED AND ANGRY.png");
       settings.setFrameRate(60);
       ///settings.setIcons("Interface/logo.png");
       ///settings.setIcons(logos); ///needs "buffered aray list of images"
       
      
       
       app.setSettings(settings);   
       app.start();
       
       ///graping values
       ScreenWidth=settings.getWidth();
       ScreenHeight=settings.getHeight();
       PauseIconWidth=settings.getWidth();
       PauseIconHeight=PauseIconWidth/2;
       
       
        
    }


    @Override
    public void simpleInitApp() {
       
       thisApp=this;
       killBox();

       ///Cam rotation modifier
       ///cam.set
               ///(cam.getRotation().mult(10));
        //PHYSICS STUFF
        bulletAppState = new BulletAppState();
        bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
        
        stateManager.attach(bulletAppState);
        getPhysicsSpace().addCollisionListener(this);//PHYSICS COLLISIONS
        
        
        //PLAYER SHIP SPAWN
        //SpaceShip=assetManager.loadModel("Models/Wraith Raider Starship.obj");//
        SpaceShip=assetManager.loadModel("Models/SpaceShip2.obj");
        SpaceShip.setName(playerTag);
        //SpaceShip.scale(.25f);
        //SpaceShip.setLocalScale(1f);//
        
        Box ship=new Box(4,1,10);
        shipG=new Geometry(playerTag, ship);
        //BoxCollisionShape shipMesh=new BoxCollisionShape(new Vector3f(4,1,10));
        CollisionShape shipMesh=CollisionShapeFactory.createDynamicMeshShape(SpaceShip);
        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //mat2.setColor("Color", ColorRGBA.Red);
        mat2.setTexture("ColorMap", assetManager.loadTexture("Textures/gar.jpg"));
        //mat2.getAdditionalRenderState().setWireframe(true);
        shipG.setMaterial(mat2);
        //mat2.getAdditionalRenderState().setWireframe(false);
        SpaceShip.setMaterial(mat2);
        cam.setLocation(new Vector3f(0,0,20));
        Vector3f v=new Vector3f(cam.getLocation());
        shipG.setLocalTranslation(v.x, v.y, v.z);
        SpaceShip.setLocalTranslation(v.x, v.y, v.z);//
        ShipRBC = new RigidBodyControl(shipMesh, 1);
        SpaceShip.rotate(0f,(float)Math.toRadians(90),0f);//
        //shipG.addControl(ShipRBC);
        SpaceShip.addControl(ShipRBC);
        
        rootNode.attachChild(SpaceShip);
        getPhysicsSpace().add(ShipRBC);

        ///initailize the text
        DialogText=new BitmapText(guiFont,false);
        ScreenText=new BitmapText(guiFont,false);
        ShipSystemsText=new BitmapText(guiFont,false);
        ClockText=new BitmapText (guiFont,false);
        
        
        //LIGHT        
        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.White.mult(10f));
        rootNode.addLight(al);
        
        getPhysicsSpace().setGravity(new Vector3f(0, 0, 0));//getting rid of stupid gravity

     
        
        ///MUSIC
       Track_1 = new AudioNode(assetManager, "Sounds/Music/Calm_BM.ogg");
       Track_2 = new AudioNode(assetManager, "Sounds/Music/Violence_BM.ogg");
       Track_69= new AudioNode(assetManager, "Sounds/Music/Track_69.ogg");

       ///note:can only use mono audio for positional audio node
       Track_1.setVolume(5f);
       Track_1.setLooping(true);
       ///Track_1.
       Track_1.play();
        
       //SOUND EFFECTS
       Notification_SE=new AudioNode(assetManager, "Sounds/Notification_SE.wav");
       Notification_SE.setVolume(5f);
       ///
       Ping_SE=new AudioNode(assetManager, "Sounds/Ping_SE.ogg");
       Ping_SE.setVolume(5f);
       Ping_SE.setPitch(0.5f);
       ///
       KineticBang_SE=new AudioNode(assetManager, "Sounds/KineticBang_SE.wav");
       ///Sound that players hear when coliding with SIN pellets
       SinGet_SE=new AudioNode (assetManager, "Sounds/SinGet_SE.ogg");
       SinGet_SE.setVolume(5f);
       SinGet_SE.setPitch(0.5f);
       ///Engine noises (scales with player speed)
       PlayerEngine_SE=new AudioNode (assetManager, "Sounds/PlayerEngine_SE.wav");
       
       ///Reload_SE=new AudioNode (assetManager, "Sounds/guncock_1.ogg") ;
       //Reload_SE.setVolume(5f);
       
       for(int a=0; a<20; a++){ 
           mygame.Bullet_Enemy_Spawn.SpawnEnemy(ShipRBC.getPhysicsLocation(), thisApp, rootNode, assetManager, getPhysicsSpace()); 
       }
       
       
       
        InitKeys();///initilize key strokes
        InitCrossHairs();///intilize crosshairs
        ///InitMusic();///initilize music
        ///InitSoundEffects();///initilize sound effect files
        InitSky();///intilize the skybox
        
        rootNode.attachChild(SideNG);rootNode.attachChild(SideSG);rootNode.attachChild(SideTG);
        rootNode.attachChild(SideBG);rootNode.attachChild(SideWG);rootNode.attachChild(SideEG);
        
        getPhysicsSpace().add(SideNR);getPhysicsSpace().add(SideSR);getPhysicsSpace().add(SideTR);
        getPhysicsSpace().add(SideBR);getPhysicsSpace().add(SideWR);getPhysicsSpace().add(SideER); 
        
        
        PrintHPBar(true);
        PrintSheildBar();
        
    }
            

    public void InitMusic(){
        ///MUSIC
       Track_1 = new AudioNode(assetManager, "Sounds/Music/Calm_BM.ogg"); ////mysterious music
       Track_2 = new AudioNode(assetManager, "Sounds/Music/Violence_BM.ogg"); ////priat music

       ///note:can only use mono audio for positional audio node
       
       
    }
    
    
    public void InitSwitchTrack (int track){
        
        
        
        if (track==1){
        Track_1.setVolume(5f);
        Track_1.setLooping(true);
        Track_1.play();
        }
        else if(track==2){
        Track_1.stop();
        Track_2.setVolume(5f);
        Track_2.setLooping(true);
        Track_2.play();
        }
        else if(track==3){
        /*
        Track_2.stop();
        Track_3.setVolume(5f);
        Track_3.setLooping(true);
        Track_3.play();
         */
        }
        else if(track==4){
        /*
        Track_3.stop();
        Track_4.setVolume(5f);
        Track_4.setLooping(true);
        Track_4.play();
         */
        }
        else if(track==5){
        /*
        Track_4.stop();
        Track_5.setVolume(5f);
        Track_5.setLooping(true);
        Track_5.play();
         */
        }
        else if(track==6){
        /*
        Track_5.stop();
        Track_6.setVolume(5f);
        Track_6.setLooping(true);
        Track_6.play();
         */
        }
        else if(track==7){
        /*
        Track_6.stop();
        Track_7.setVolume(5f);
        Track_7.setLooping(true);
        Track_7.play();
         */
        }
        
        else{
        System.out.println ("poop.");
        track=1;///make loop
        }
       
    }

    
    
    
    
    public void InitKeys(){
        
    ///IN GAME ACTION KEYS    
        ///MOVEMENT
        
        /*
        inputManager.addMapping("left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("up", new KeyTrigger(KeyInput.KEY_W));
        
        */
        inputManager.addMapping("Spawn", new KeyTrigger(KeyInput.KEY_G));
       
        inputManager.addMapping("AccelerateForward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("AccelerateBackwards", new KeyTrigger(KeyInput.KEY_S));
        

        inputManager.addMapping("rotateup", new KeyTrigger(KeyInput.KEY_I));
        inputManager.addMapping("rotatedown", new KeyTrigger(KeyInput.KEY_K));
        inputManager.addMapping("rotateleft", new KeyTrigger(KeyInput.KEY_J));        
        inputManager.addMapping("rotateright", new KeyTrigger(KeyInput.KEY_L));
        
        ///add
        ////inputManager.addMapping("boost", new KeyTrigger(KeyInput.KEY_RSHIFT));
        
        inputManager.addListener(actionListener,"left", "right", "up", "down");
        inputManager.addListener(analogListener,"rotateup", "rotatedown", "rotateleft", "rotateright");
        inputManager.addListener(analogListener, "AccelerateForward","AccelerateBackwards");
        inputManager.addListener(actionListener, "Spawn");
        
        ///ENERGY SYSTEM
        inputManager.addMapping("+/-weaponenergy", new KeyTrigger(KeyInput.KEY_1));
        inputManager.addMapping("+/-sheildenergy", new KeyTrigger(KeyInput.KEY_2));
        inputManager.addMapping("+/-engineenergy", new KeyTrigger(KeyInput.KEY_3));
        inputManager.addMapping("+/-energyswitch", new KeyTrigger(KeyInput.KEY_Q));

        
        inputManager.addListener(analogListener, "+/-weaponenergy");
        inputManager.addListener(analogListener, "+/-sheildenergy");
        inputManager.addListener(analogListener, "+/-engineenergy");
        inputManager.addListener(actionListener, "+/-energyswitch");

        
        
        ///FIRING
        inputManager.addMapping("shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(analogListener, "shoot");
        
        inputManager.addMapping("shoot2", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addListener(analogListener, "shoot2");
        
        
        inputManager.addMapping("reload", new KeyTrigger(KeyInput.KEY_R));
        
        inputManager.addListener(actionListener, "reload");
        
        

        ///OTHER
        inputManager.addMapping("pause", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("runstats", new KeyTrigger(KeyInput.KEY_F1));
        
        inputManager.addListener(actionListener, "pause");
        inputManager.addListener(actionListener, "runstats");
        
        
        //TEST
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        ///stoping Sakamoto
        if(SakamotoCountDown>(45)*60){SakamotoCountDown=0; Sakamoto=false;Track_69.stop();Track_2.play();}
        
        if(c%(30-(Minutes*2))==0){mygame.Bullet_Enemy_Spawn.SpawnEnemy(ShipRBC.getPhysicsLocation(), thisApp, rootNode, assetManager, getPhysicsSpace());}
        
        //if(c>900){mygame.Bullet_Enemy_Spawn.enemySpawnRange=5;}
        //if(c>1800){mygame.Bullet_Enemy_Spawn.enemySpawnRange=5;}
        //if(c%3600==0 && mygame.Bullet_Enemy_Spawn.enemySpawnRange>15){mygame.Bullet_Enemy_Spawn.enemySpawnRange-=5;}
        //if(Minutes>4){mygame.Bullet_Enemy_Spawn.enemySpawnRange=10;}
        
        Vector3f v=new Vector3f();//get player location
        v=SpaceShip.getLocalTranslation();
        SpaceShip.setLocalTranslation(v.x, v.y-1, v.z);
        //SpaceShip.set
        //SpaceShip.move(v.x,v.y,v.z);
        //shipG.setLocalTranslation(v.x,v.y,v.z);
        
        //
        SideNG.setLocalTranslation(v.x, v.y, v.z+1000);
        SideSG.setLocalTranslation(v.x, v.y, v.z-1000);
        SideTG.setLocalTranslation(v.x, v.y+1000, v.z);
        SideBG.setLocalTranslation(v.x, v.y-1000, v.z);
        SideWG.setLocalTranslation(v.x-1000, v.y, v.z);
        SideEG.setLocalTranslation(v.x+1000, v.y, v.z);
        
        
        SideNR.setPhysicsLocation(new Vector3f(v.x, v.y, v.z+1000));
        SideSR.setPhysicsLocation(new Vector3f(v.x, v.y, v.z-1000));
        SideTR.setPhysicsLocation(new Vector3f(v.x, v.y+1000, v.z));
        SideBR.setPhysicsLocation(new Vector3f(v.x, v.y-1000, v.z));
        SideWR.setPhysicsLocation(new Vector3f(v.x-1000, v.y, v.z));
        SideER.setPhysicsLocation(new Vector3f(v.x+1000, v.y, v.z));                
        //
        
        
        
        v=new Vector3f(v.x, v.y+3,v.z);
        cam.setLocation(v);//set camera to follow player
        
        
        Quaternion r=new Quaternion(cam.getRotation()); //get camera rotation       
        //shipG.setLocalRotation(r);
        SpaceShip.setLocalRotation(r);
        ShipRBC.setPhysicsRotation(r);//set players rotation
        //SpaceShip.rotate(0,(float)Math.toRadians(90),0f);
        
        ShipRBC.setLinearVelocity(cam.getDirection().mult(ShipVelocity));//set players speed
        
        //PrintHPBar(true);
        Score=c*Kills*((int)ShipVelocity/100);
        
        
        if(ReloadFinishTime<=c&&Reloading==true&&PlayerCurrentEnergy-MagazineSize>=0){
            BulletsFired=0;
            PlayerCurrentEnergy-=MagazineSize;
            Reloading=false;
            PrintDialog ("RELOADEDDDD!!!!1","");

        }
        
    }
    
    ///physics BS
    private PhysicsSpace getPhysicsSpace() {
        return bulletAppState.getPhysicsSpace();
    }

    
    ////input listener
    public ActionListener actionListener = new ActionListener() {
    public void onAction(String name, boolean isPressed, float tpf) {
    
            ///TOGGALING GAME RUN STATS
            if(name.equals("runstats")&&isPressed){
                if(RunCheckOn==false){
                    ///stop a bunch of things from not updating
                    setDisplayStatView(true);
                    setDisplayFps(true);
                    RunCheckOn=true; 
                }
                else{
                    ///start things back up
                    setDisplayStatView(false);
                    setDisplayFps(false);
                    RunCheckOn=false; 
                }
            }

            ///PAUSING
            if(name.equals("pause")&&isPressed){
                if(Paused==false){
                    ///stop a bunch of things from updating
                    Track_1.pause();
                    LastPause=c;
                    //PrintToScreen(0,0,"PAUSED","",3);
                    Accumulator=0;
                    Paused=true; 
                    PrintPause();
                }
                else{
                    ///start things back up
                    Track_1.play();
                    Paused=false;
                    LastPause=c;
                    Accumulator=1;
                    RemovePause();
                   // PrintToScreen(0,0,"","",3);
                }               
            }
            
            
            //reloading 
            
            if(name.equals("reload")&&isPressed){
                if(Reloading==false){
                   ///reload 
                    ReloadFinishTime=(int)c+ReloadTime;
                    Reloading=true;
                    PrintDialog ("RELOADING!!!!1","");
                }
                           
            }
            
 
            
            
            
            
      ///STRAFING/WARPING CONTROL
      if (Paused==false) {///as long as the game is not paused
          if("Spawn".equals(name)){
              mygame.Bullet_Enemy_Spawn.SpawnEnemy(ShipRBC.getPhysicsLocation(),thisApp, rootNode, assetManager, getPhysicsSpace());
          }
          
          /*
     if(c>=(LastStrafe+StrafeRate)){     
        if (name.equals("left")&&isPressed) {
            Vector3f v = geom.getLocalTranslation();
            geom.setLocalTranslation(v.x -StrafeDistance, v.y, v.z);
            cx=cx-StrafeDistance;
            LastStrafe=c;
        }
        if (name.equals("right")&&isPressed) {
            Vector3f v = geom.getLocalTranslation();
            geom.setLocalTranslation(v.x +StrafeDistance, v.y, v.z);
            cx=cx+StrafeDistance;
            LastStrafe=c;
        }
        if (name.equals("up")&&isPressed) {
            Vector3f v = geom.getLocalTranslation();
            geom.setLocalTranslation(v.x, v.y+StrafeDistance, v.z);
            cy=cy+StrafeDistance;
            LastStrafe=c;
        }
        if (name.equals("down")&&isPressed) {
            Vector3f v = geom.getLocalTranslation();
            geom.setLocalTranslation(v.x, v.y-StrafeDistance, v.z);
            cy=cy-StrafeDistance;
            LastStrafe=c;
        }
      }
     */
     
     
     }

            ///ENERGY SYSTEM
            ///+ means to change key presses to add energy to system 
            ///- means to change key presses to add remove energy from a system
            if(name.equals("+/-energyswitch")&&isPressed){
                PlayerEnergySwitch=(PlayerEnergySwitch*-1); 
                System.out.println ("PlayerEnergySwitch="+PlayerEnergySwitch);
                //
                
                if(PlayerEnergySwitchSymbol.equals("+")){
                    PlayerEnergySwitchSymbol="-";
                }
                else{
                    PlayerEnergySwitchSymbol="+";
                }
                
                
            }
            
            /*
            //spawn enemies(for now, will change to automatic spawns)
            if("Spawn".equals(name) && pressed){
                mygame.Bullet_Enemy_Spawn.SpawnEnemy(thisApp, rootNode, assetManager, getPhysicsSpace());//spawn enemies
            }
            */
            
   
    }
    
    
    };
    
    public float MaxVelocity=120+PlayerEngineUpgrades/10;
   
    ///Key listener
    public AnalogListener analogListener = new AnalogListener() {
    public void onAnalog(String name, float value, float tpf) {
     if(Paused==false){
        if("AccelerateForward".equals(name)){
            if(ShipVelocity<120+PlayerEngineUpgrades){ShipVelocity=ShipVelocity+IncreaseDecreaseSpeed;}//change players acceleration
            }
        if("AccelerateBackwards".equals(name)){
                
            if(ShipVelocity>0){ShipVelocity=ShipVelocity-IncreaseDecreaseSpeed;}//change players acceleration
            }
      }

     ////ENERGY SYSTEM!!
     ///Lower limiter                                            Upper limiter
    if(PlayerCurrentEnergy-PlayerEnergySwitch>=0&&PlayerCurrentEnergy-PlayerEnergySwitch<=PlayerMaxEnergy){ 
    if(name.equals("+/-weaponenergy")&&PlayerWeaponUpgrades+(PlayerEnergySwitch*0.1f)>=0){
    PlayerCurrentEnergy-=(PlayerEnergySwitch);
    PlayerWeaponUpgrades=PlayerWeaponUpgrades+(PlayerEnergySwitch*0.1f);
    FireRate=7.5f-(PlayerWeaponUpgrades*0.01f);///millisecond pause between firing each bullet 100=1 second
    BulletDamage=1f+(PlayerWeaponUpgrades*0.1f);
    BulletVelocity=100+(PlayerWeaponUpgrades*0.01f);
    BulletSize=0.01f+(PlayerWeaponUpgrades*0.001f);
    MagazineSize=20+(int)PlayerWeaponUpgrades;
    ReloadTime=((2)*60)-(int)PlayerWeaponUpgrades;

    Shots=10+(int)(PlayerWeaponUpgrades*0.1f);
    
    
    System.out.println("The best offence is a good offence");
    }
    if(name.equals("+/-sheildenergy")&&PlayerShieldUpgrades+(PlayerEnergySwitch*0.1f)>=0){
    PlayerCurrentEnergy-=(PlayerEnergySwitch);
    PlayerShieldUpgrades=PlayerShieldUpgrades+(PlayerEnergySwitch*0.1f);
    PlayerShieldMax=10000+PlayerShieldUpgrades*10;
    PlayerCurrentShield=PlayerShieldMax;
    PlayerShieldRegenRate=(PlayerShieldMax/50f)+(PlayerShieldUpgrades*0.1f);
    System.out.println("Protect me my sheild!");
    }
    if(name.equals("+/-engineenergy")&&PlayerEngineUpgrades+(PlayerEnergySwitch*0.1f)>=0){
    PlayerCurrentEnergy-=(PlayerEnergySwitch);
    PlayerEngineUpgrades=PlayerEngineUpgrades+(PlayerEnergySwitch*0.1f);
    PlayerEngine=0.5f;
    PlayerBoostEngine=1000+(PlayerEngineUpgrades);
    PlayerBoostDrain=-25f-(PlayerEngineUpgrades*0.8f);
    LastStrafe=0;
    StrafeRate=200-((long)PlayerEngineUpgrades/10);
    StrafeDistance=2f;
    System.out.println("All energy to engines!");
    if(IncreaseDecreaseSpeed<1){IncreaseDecreaseSpeed+=.01f;}
    }
    }

     
        ///FIREING
       if(c>=(LastFired+FireRate)&&BulletsFired<MagazineSize&&PlayerCurrentEnergy>0){///input limiter
        
       if(name.equals("shoot")){
       //PlayerCurrentEnergy--;
       if(violence==false){
       violence=true;
       Track_1.stop();
       Track_1 = new AudioNode(assetManager, "Sounds/Music/Violence_BM.ogg");
       Track_1.setLooping(true);
       Track_1.setVolume(0.25f);
       Track_1.play();
       PrintDialog("YOU ARE BIG, THERFORE YOU HAVE BIG GUTS! RIP AND TEAR!!","");
       }
       
                mygame.Bullet_Enemy_Spawn.SpawnBullet(thisApp, rootNode, assetManager, getPhysicsSpace(),1);//make bullets

                ///Sound cycling to make sexy firing sounds
                if(count>KineticBang_SEcycle.length){count=0;}
                KineticBang_SE.setVolume(KineticBang_SEcycle[count]/10f);
                KineticBang_SE.setPitch(KineticBang_SEcycle1[count]);
                KineticBang_SE.playInstance();
              
                LastFired=c;
                BulletsFired++;
            }
     }
       
       if(PlayerCurrentEnergy<0){
           ShipVelocity=0;
       }
       
       
       if(c>=(LastFired+FireRate*2)&&BulletsFired<MagazineSize&&PlayerCurrentEnergy>0){///input limiter
        
       if(name.equals("shoot2")){
       //PlayerCurrentEnergy--;
       if(violence==false){
       violence=true;
       Track_1.stop();
       Track_1 = new AudioNode(assetManager, "Sounds/Music/Violence_BM.ogg");
       Track_1.setLooping(true);
       Track_1.setVolume(0.25f);
       Track_1.play();
       PrintDialog("YOU ARE BIG, THERFORE YOU HAVE BIG GUTS! RIP AND TEAR!!","");
       }
                
                mygame.Bullet_Enemy_Spawn.SpawnBullet(thisApp, rootNode, assetManager, getPhysicsSpace(),Shots);//make bullets
                
                ///Sound cycling to make sexy firing sounds
                if(count>KineticBang_SEcycle.length){count=0;}
                KineticBang_SE.setVolume(KineticBang_SEcycle[count]/10f);
                KineticBang_SE.setPitch(KineticBang_SEcycle1[count]);
                KineticBang_SE.playInstance();
              
                LastFired=c;
                BulletsFired+=Shots;
            }
     }
       
       if(PlayerCurrentEnergy<0){
           ShipVelocity=0;
       }
       
       
       
       
       
       
       
       
       
       
       
    }
  };

public int BulletsFired=0;
public int MagazineSize=20;
public int ReloadTime=((3)*60);
public int ReloadFinishTime=0;
public boolean Reloading=false;
public int Shots=5;
    
    ///Collision
        public void collision(PhysicsCollisionEvent event) {
        
        //ENEMY AND PLAYER Collision
        if (enemyTag.equals(event.getNodeA().getName()) || enemyTag.equals(event.getNodeB().getName())) {
            if (playerTag.equals(event.getNodeA().getName()) || playerTag.equals(event.getNodeB().getName())) {
                //"if statement" to determine which collider was the ship and then remove the enemy
                System.out.println ("Box and player collision");
                if(playerTag.equals(event.getNodeA().getName())){
                    rootNode.detachChild(event.getNodeB());
                    //PlayerCurrentHealth-=10;
                    getPhysicsSpace().remove(event.getNodeB()); 
                }
                else if(playerTag.equals(event.getNodeB().getName())){
                    rootNode.detachChild(event.getNodeA());
                    //PlayerCurrentHealth-=10;
                    getPhysicsSpace().remove(event.getNodeA());
                }          
                
                //HPBarHeight-=r.nextInt(20)+15;//ACTUAL HEALTH COUNTER
                
                ///if sheild is not hit
                if(PlayerCurrentShield==0){
                    Math.pow(2, 2);
                HPBarHeight-=HPBarHeight*0.01*r.nextInt((int)Math.pow((double)Minutes+1,2));//damages randomlly depends on time
                frame1.setHeight(HPBarHeight);
                
                ///Chance to print dialog
                if(Chance(30-PlayerShieldUpgrades/10000f)){
                PrintDialog (RandomLine(PlayerDamageText),"");
                }
                
                }
                ///if the shield can take it
                else if(ShieldBarHeight-ShieldBarHeight*0.01*r.nextInt((int)Math.pow((double)Minutes+1,2))>0){
                ShieldBarHeight-=ShieldBarHeight*0.01*r.nextInt((int)Math.pow((double)Minutes+1,2));    
                SheildFrame.setHeight(ShieldBarHeight);
                }
                else{
                ShieldBarHeight=0;
                HPBarHeight-=HPBarHeight*0.01*r.nextInt((int)Math.pow((double)Minutes+1,2));//damages randomlly depends on time
                SheildFrame.setHeight(ShieldBarHeight);
                }
                //PrintHPBar(true);
                if(HPBarHeight<=0){
                    PrintGameOver();//position
                    try {
                    Thread.sleep(4000);//1000 milliseconds/second
                    } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    }
                    System.exit(0);                    
                }
                
                
                Ping_SE.playInstance(); 
            }
        }
        
        //ENEMY AND BULLET Collision
        if (enemyTag.equals(event.getNodeA().getName()) || enemyTag.equals(event.getNodeB().getName())) {
            if (bulletTag.equals(event.getNodeA().getName()) || bulletTag.equals(event.getNodeB().getName())) {
                System.out.println ("Box hit");
                rootNode.detachChild(event.getNodeA());//removing enemy/bullet
                getPhysicsSpace().remove(event.getNodeA());
                rootNode.detachChild(event.getNodeB());//removing enemy/bullet
                getPhysicsSpace().remove(event.getNodeB());
                
                mygame.Bullet_Enemy_Spawn.itemDrops(event.getNodeA().getLocalTranslation(), thisApp, rootNode, assetManager, getPhysicsSpace());
            ///PrintToScreen (float x,float y,String s, String font,int stream);
                
                Kills++;
                ///add somesort of adjuster to middigate spam later on
                if(Chance(30-c/10000000)){
                PrintDialog (RandomLine(PlayerKillText),"");
                }
            }
        }
        
        //PLAYER AND ITEM Collision
        if (playerTag.equals(event.getNodeA().getName()) || playerTag.equals(event.getNodeB().getName())) {
            if (itemPickupTag.equals(event.getNodeA().getName()) || itemPickupTag.equals(event.getNodeB().getName())) {
                //"if statement" to determine which collider was the ship and then remove the enemy
                System.out.println ("SIN collected");
                SinGet_SE.playInstance();
                if(playerTag.equals(event.getNodeA().getName())){
                    rootNode.detachChild(event.getNodeB());
                    PlayerMaxEnergy+=SinGain;///INCREASE PLAYER ENERGY
                    PlayerCurrentEnergy+=SinGain;
                    getPhysicsSpace().remove(event.getNodeB()); 
                }
                else if(playerTag.equals(event.getNodeB().getName())){
                    rootNode.detachChild(event.getNodeA());
                    PlayerMaxEnergy+=SinGain;///ENCREASE PLAYER ENERGY
                    PlayerCurrentEnergy+=SinGain;
                    getPhysicsSpace().remove(event.getNodeA());
                }    
                
                PlayerEnergyRegenRate=(PlayerMaxEnergy/50f)+(PlayerEnergyUpgrades*0.01f);
                if(Chance(10)){
                PrintDialog (RandomLine(PlayerSinGetText),"");
                }
                
            }
        }
        
        //ITEM WITH KILLBOX Collision
        if ("Despawn".equals(event.getNodeA().getName()) || "Despawn".equals(event.getNodeB().getName())) {
            if (itemPickupTag.equals(event.getNodeA().getName()) || itemPickupTag.equals(event.getNodeB().getName())) {
                //"if statement" to determine which collider was the ship and then remove the enemy
                if("Despawn".equals(event.getNodeA().getName())){
                    rootNode.detachChild(event.getNodeB());
                    getPhysicsSpace().remove(event.getNodeB()); 
                }
                else if("Despawn".equals(event.getNodeB().getName())){
                    rootNode.detachChild(event.getNodeA());
                    getPhysicsSpace().remove(event.getNodeA());
                }          
                System.out.println("Item Removed!!!");
            }
        }
        
        
        //BULLET WITH KILLBOX Collision
        if ("Despawn".equals(event.getNodeA().getName()) || "Despawn".equals(event.getNodeB().getName())) {
            if (bulletTag.equals(event.getNodeA().getName()) || bulletTag.equals(event.getNodeB().getName())) {
                //"if statement" to determine which collider was the ship and then remove the enemy
                if("Despawn".equals(event.getNodeA().getName())){
                    rootNode.detachChild(event.getNodeB());
                    getPhysicsSpace().remove(event.getNodeB()); 
                }
                else if("Despawn".equals(event.getNodeB().getName())){
                    rootNode.detachChild(event.getNodeA());
                    getPhysicsSpace().remove(event.getNodeA());
                }          
                System.out.println("Bullet Removed!!!");
            }
        }
        
        //ENEMY WITH KILLBOX Collision
        if ("Despawn".equals(event.getNodeA().getName()) || "Despawn".equals(event.getNodeB().getName())) {
            if (enemyTag.equals(event.getNodeA().getName()) || enemyTag.equals(event.getNodeB().getName())) {
                //"if statement" to determine which collider was the ship and then remove the enemy
                if("Despawn".equals(event.getNodeA().getName())){
                    rootNode.detachChild(event.getNodeB());
                    getPhysicsSpace().remove(event.getNodeB()); 
                }
                else if("Despawn".equals(event.getNodeB().getName())){
                    rootNode.detachChild(event.getNodeA());
                    getPhysicsSpace().remove(event.getNodeA());
                }          
                System.out.println("Enemy Removed!!!");
            }
        }

    }
    
    
    
    @Override
    public void simpleRender(RenderManager rm) {
        
        if(Sakamoto){SakamotoCountDown++;}
       
        c+=Accumulator;//counts every 1/[current fps] seconds
        clock();///update game clock

        
        
        ///PlayerEngine_SE.play(); <need to change to mono audio node
        
        //Print the current ships systems
        PrintShipSystems(ScreenWidth,ScreenHeight, PlayerEnergySwitchSymbol+
                                 "\nSIN:"+(int)(PlayerCurrentEnergy-1)+"/"+(int)(PlayerMaxEnergy)+
                                 "\nWeapon SIN:"+(int)PlayerWeaponUpgrades+
                                 "\nSheild SIN:"+(int)PlayerShieldUpgrades+
                                 "\nEngine SIN:"+(int)PlayerEngineUpgrades+
                                 "\nSpeed     :"+(int)ShipVelocity+
                                 "\nAmmo   :"+(MagazineSize-BulletsFired)+"/"+MagazineSize+
                                 "\nScore     :"+(int)Score,"");
        
   
        
        
        /*
        if(c==LastStrafe+StrafeRate-12){
           Ping_SE.playInstance(); 
        }
        */
    }

    
    
    public void clock(){
    ///clock
    if(Paused==false&&c%60==0){ 
        Seconds++;
        ///ENERGY REGEN
        if(PlayerCurrentEnergy<PlayerMaxEnergy){
        PlayerCurrentEnergy+=PlayerEnergyRegenRate;
        ///counteracts overgain
        while(PlayerCurrentEnergy>PlayerMaxEnergy){
            PlayerCurrentEnergy--;
        }
        }
        ///minute counter
            if(Seconds==60){///putting it here increases efficancy as opposed to outside of it
                Seconds=0;
                Minutes++;
                SinGain=(int)Math.pow(2,(double)(Minutes));

                Sakamoto=false;
                
                ///cloudly with an 50% chance of Sakamoto
                if(Chance(20)){
                    Sakamoto=true;///makes so textures are set as sakamoto in spawn class
                    ///music
                    ///stop trakes
                    Track_1.stop();///make better use function
                    Track_2.stop();
                    Track_69.setLooping(true);
                    Track_69.setVolume(3f);
                    Track_69.play();
                }
                if(Sakamoto==false){
                    Track_69.stop();
                    Track_1.play();
                }
            }
        ///printing the timer out
            if(Seconds<=9){
                PrintClock(Minutes+":0"+Seconds,"");
                System.out.println(Minutes+":0"+Seconds);
            }
            else{
                PrintClock(Minutes+":"+Seconds,"");
                System.out.println(Minutes+":"+Seconds);
            }
    }  
   }
    
    
    public void PrintClock(String s, String font){
         if(font.isEmpty()){
             font="Default.fnt";
         }

       if(ClockText!=null){
        guiNode.detachChild(ClockText);///rip off all text
       }
        ClockText.setSize(guiFont.getCharSet().getRenderedSize());
        ClockText.setText(s);
                                    //  \/Centers Text-Changes with screen size
        ClockText.setLocalTranslation((ScreenWidth/2)-(ClockText.getLineWidth()/2), ScreenHeight, 10);
        guiNode.attachChild(ClockText);
     } 
    
   
      ///prints dialog out
      ///note the font used must be in .fnt (bitmap) format
     public void PrintDialog(String s, String font){
         if(font.isEmpty()){
             font="Default.fnt";
         }

       if(DialogText!=null){
        guiNode.detachChild(DialogText);///rip off all text
       }
      
        DialogText.setSize(guiFont.getCharSet().getRenderedSize());
        DialogText.setText(s);
                                    //  \/Centers Text-Changes with screen size
        DialogText.setLocalTranslation((ScreenWidth/2)-(DialogText.getLineWidth()/2), DialogText.getLineHeight(), 10);
        guiNode.attachChildAt(DialogText,0);
        Notification_SE.play();
     } 
      
     
    public void PrintGameOver(){
        String s="GAME OVER >:(",font="Default.fnt"; 

       if(DialogText!=null){
        guiNode.detachChild(DialogText);///rip off all text
       }
      
        DialogText.setSize(guiFont.getCharSet().getRenderedSize()*ScreenWidth/100);
        DialogText.setText(s);
                                    //  \/Centers Text-Changes with screen size
        DialogText.setLocalTranslation((ScreenWidth/2)-(DialogText.getLineWidth()/2), (ScreenHeight/2)-(DialogText.getLineHeight()/2), 10);
        guiNode.attachChildAt(DialogText,0);
        Notification_SE.play();
     } 
     
    
     /*
     public void PrintToScreen (float x,float y,String s, String font,int stream){
        if(font.isEmpty()){
             font="Default.fnt";
        }
      if(ScreenText!=null){
        guiNode.detachChild(ScreenText);///rip off all text
      }
        ///note index 1 is reseved for game stats text and index 2 is for the fps counter
        ///initailize the text
        guiFont = assetManager.loadFont("Interface/Fonts/"+font);
        ScreenText.setSize(guiFont.getCharSet().getRenderedSize());
        ///adds additional functionality
        if(x==0){
           x=((ScreenWidth/2)-(ScreenText.getLineWidth()*2));///center text left and right  
       }
        x=x-ScreenText.getLineWidth();
        if(y==0){
           y=(int) ((ScreenWidth/2));///center text up and down
        }
        y=y+ScreenText.getLineHeight();
        ScreenText.setText(s);
                                    //  \/Centers Text-Changes with screen size
        ScreenText.setLocalTranslation(x, y, 0);
        guiNode.attachChildAt(ScreenText,stream);  
     }
      
     */
     
     
     public void PrintShipSystems (float x,float y,String s, String font){
        if(font.isEmpty()){
             font="Default.fnt";
        }
      if(ShipSystemsText!=null){
        guiNode.detachChild(ShipSystemsText);///rip off all text
      }
        ///note index 1 is reseved for game stats text and index 2 is for the fps counter
        ///initailize the text
        guiFont = assetManager.loadFont("Interface/Fonts/"+font);
        ShipSystemsText.setSize(guiFont.getCharSet().getRenderedSize());
        ///adds additional functionalit
        x=x-ShipSystemsText.getLineWidth();
        y=y+ShipSystemsText.getLineHeight();
        ShipSystemsText.setText(s);
                                    //  \/Centers Text-Changes with screen size
        ShipSystemsText.setLocalTranslation(x, y, 0);
        guiNode.attachChild(ShipSystemsText);  
     }
     
     
     
     //-----------------------------------------------------------------------------
     
     public String RandomLine(ArrayList <String> StringArray){
         int n=r.nextInt(StringArray.size());
         System.out.println ("Generated: "+n);
         return (StringArray.get(n));
     }
     
     //-----------------------------------------------------------------------------
          
     public boolean Chance(float PercentChance){
         boolean Occurs=false;
         ///multiplying the number to give a lower chance off falling into range
         float m=(100/PercentChance);
         ////A large modifier will mean that more decimails are concidered
         float modifier=10000;
         m=m*modifier;
         float n=r.nextInt((int)(m));
         ///checking to see if the roll is in the "occurs" range
         if(n>=0&&n<=(int)m*(PercentChance/100)){
         Occurs=true;   
         }
         return Occurs;
     }
    
        
     public int PlayerCrosshairWidth=22;
     public int PlayerCrosshairHeight=PlayerCrosshairWidth;
     
     
    //------------------------------------------------------------------------------    
    public void InitCrossHairs() {
        Picture frame = new Picture("Crosshairs");
        frame.setImage(assetManager, "Interface/Crosshair.png", true);
        frame.setPosition((ScreenWidth/2)-(PlayerCrosshairWidth/2),
                ScreenHeight/2f-PlayerCrosshairHeight/2f);
        frame.setWidth(PlayerCrosshairWidth);
        frame.setHeight(PlayerCrosshairHeight);
        guiNode.attachChild(frame);
    }
    
   //--------------------------------------------------------------------------

    public void PrintPause() {
        Picture frame = new Picture("PauseIcon");
        frame.setImage(assetManager, "Interface/PauseScreen.png", true);
        frame.setPosition((ScreenWidth/2)-(PauseIconWidth/2),
                ScreenHeight/2f-PauseIconHeight/2f);
        frame.setWidth(PauseIconWidth);
        frame.setHeight(PauseIconHeight);
        guiNode.attachChild(frame);
    }
    
    public void RemovePause(){
        guiNode.detachChildNamed("PauseIcon");
    }
    
    //--------------------------------------------------------------------------

    
    
    ///hp bar 
    public int HPIconWidth=50;
    public int HPIconHeight=HPIconWidth;
    public int HPBarWidth=50;
    public float HPBarHeight=0;
    Picture frame1;
    
    
       public void PrintHPBar(boolean PrintIcon) {
        
        HPBarHeight=(settings.getHeight()*(PlayerCurrentHealth/PlayerMaxHealth))-40;
           
        //if(PrintIcon==true){
        /*
        Picture frame = new Picture("HPBarBack");
        frame.setImage(assetManager, "Interface/HPIcon.png", true);
        frame.setPosition(10,10);
        frame.setWidth(HPIconWidth);
        frame.setHeight(HPIconHeight);
        guiNode.attachChild(frame);
          */
        
        
        Picture frame2 = new Picture("HPBarBack");
        frame2.setImage(assetManager, "Interface/HPBarBack.png", true);
        frame2.setPosition(10,30);
        frame2.setWidth(HPBarWidth);
        frame2.setHeight(HPBarHeight);
        
        guiNode.attachChild(frame2);
        //}
        //else{
        ///adjust Hp bar    
        //HPBarHeight=(int)ScreenHeight*(PlayerCurrentHealth/100000);
        frame1 = new Picture("HPBar");
        frame1.setImage(assetManager, "Interface/HPBar.png", true);
        frame1.setPosition(10,30);
        frame1.setWidth(HPBarWidth);
        frame1.setHeight(HPBarHeight);
        guiNode.attachChild(frame1);
           
        //}
        
        ///add variable bar in method with it
        
        
    }

         
       
    public int SheildIconWidth=50;
    public int SheildIconHeight=SheildIconWidth;
       
    public int ShieldBarHeight=0;
    public int ShieldBarWidth=10;
    
    public Picture SheildFrame;
       
    public void PrintSheildBar() {

        ShieldBarHeight=(settings.getHeight()*((int)PlayerCurrentShield/(int)PlayerShieldMax))-40;
       
        SheildFrame = new Picture("SheildBar");
        SheildFrame.setImage(assetManager, "Interface/ShieldBar.png", true);
        SheildFrame.setPosition((HPBarWidth),30);
        SheildFrame.setWidth(ShieldBarWidth);
        SheildFrame.setHeight(ShieldBarHeight);
        guiNode.attachChild(SheildFrame);
        ///add variable bar
        
        
        
    }
    
    public int SpeedIconWidth=50;
    public int SpeedIconHeight=SheildIconWidth;
    
    public void PrintSpeedBar() {
        Picture frame = new Picture("SpeedBarIcon");
        frame.setImage(assetManager, "Interface/SpeedIcon.png", true);
        frame.setPosition((ScreenWidth/2)-(SpeedIconWidth/2),
                ScreenHeight/2f-SpeedIconHeight/2f);
        frame.setWidth(SpeedIconWidth);
        frame.setHeight(SpeedIconHeight);
        guiNode.attachChild(frame);
              ///add variable bar
    }
    
    public int EnergyIconWidth=50;
    public int EnergyIconHeight=EnergyIconWidth;
    
        public void PrintEnergyBar() { 
        Picture frame = new Picture("EnergyIcon");
        frame.setImage(assetManager, "Interface/EnergyIcon.png", true);
        frame.setPosition((ScreenWidth/2)-(EnergyIconWidth/2),
                ScreenHeight/2f-EnergyIconHeight/2f);
        frame.setWidth(EnergyIconWidth);
        frame.setHeight(EnergyIconHeight);
        guiNode.attachChild(frame);
              ///add variable bar
    }
    
        
     
    
   public void InitSky(){ 
        Texture West, East, North, South, Up, Down;
        West  = assetManager.loadTexture("Scenes/sky_left.png");
        East  = assetManager.loadTexture("Scenes/sky_right.png");
        North = assetManager.loadTexture("Scenes/sky_front.png");
        South = assetManager.loadTexture("Scenes/sky_back.png");
        Up    = assetManager.loadTexture("Scenes/sky_top.png");
        Down  = assetManager.loadTexture("Scenes/sky_bottom.png");
        
        rootNode.attachChild(SkyFactory.createSky(assetManager, West, East, North, South, Up, Down));
    }
     
     public void killBox(){
        Box SideN= new Box(1000,1000,1);
        Box SideS= new Box(1000,1000,1);
        Box SideT= new Box(1000,1,1000);
        Box SideB= new Box(1000,1,1000);
        Box SideW= new Box(1,1000,1000);
        Box SideE= new Box(1,1000,1000);
        SideNG = new Geometry("Despawn",SideN);
        SideSG = new Geometry("Despawn",SideS);
        SideTG = new Geometry("Despawn",SideT);
        SideBG = new Geometry("Despawn",SideB);
        SideWG = new Geometry("Despawn",SideW);
        SideEG = new Geometry("Despawn",SideE);
        
        Material SideMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        SideMat.setColor("Color", ColorRGBA.Black);
        SideMat.getAdditionalRenderState().setWireframe(true);
        //SideMat.getAdditionalRenderState().
        //SideMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        SideNG.setMaterial(SideMat);SideSG.setMaterial(SideMat);SideTG.setMaterial(SideMat);
        SideBG.setMaterial(SideMat);SideWG.setMaterial(SideMat);SideEG.setMaterial(SideMat);
        
        SideNR=new RigidBodyControl(CollisionShapeFactory.createDynamicMeshShape(SideNG),0f);
        SideSR=new RigidBodyControl(CollisionShapeFactory.createDynamicMeshShape(SideSG),0f);
        SideTR=new RigidBodyControl(CollisionShapeFactory.createDynamicMeshShape(SideTG),0f);
        SideBR=new RigidBodyControl(CollisionShapeFactory.createDynamicMeshShape(SideBG),0f);
        SideWR=new RigidBodyControl(CollisionShapeFactory.createDynamicMeshShape(SideWG),0f);
        SideER=new RigidBodyControl(CollisionShapeFactory.createDynamicMeshShape(SideEG),0f);
        
        SideNG.addControl(SideNR);SideSG.addControl(SideSR);SideTG.addControl(SideTR);
        SideBG.addControl(SideBR);SideWG.addControl(SideWR);SideEG.addControl(SideER);
     }
     
     
     
     
     
     
     
     
     
     
      
      
}
    