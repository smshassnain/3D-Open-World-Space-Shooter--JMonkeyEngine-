
package mygame;


import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere; 
import java.util.Random;
import java.lang.Math;


public class Bullet_Enemy_Spawn {
    public static Random r=new Random();
    
    public static int enemySpawnRange=100;//units away from origin in each direction
    public static int enemySpeed=15;//randomized enemy speed above and below 0
    public static int enemyRotation=60;//randomized enemy rotation above and below 0
    public static int bulletSpeed=500;
    public static int itemSR=5;
    public static String[] textures={"Textures/Asteroid_Textures/AT1.jpg", "Textures/Asteroid_Textures/AT2.jpg", "Textures/Asteroid_Textures/AT3.jpg", 
                                    "Textures/Asteroid_Textures/AT4.jpg", "Textures/Asteroid_Textures/AT5.jpg","Textures/Asteroid_Textures/korosensei.png", "Textures/Asteroid_Textures/fmaal.jpg"};
    
    //--------------------------------------------------------------------------
    public static void SpawnBullet(Application app, Node rootNode, AssetManager assetManager, PhysicsSpace space, int c){
        
        
        for(int c1=0;c1<c;c1++){
        Sphere projectile = new Sphere(10,10,.8f,true,false);
        SphereCollisionShape bulletMesh = new SphereCollisionShape(.8f);
        
        Geometry bullet = new Geometry(mygame.Main.bulletTag, projectile);
        Material s=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        s.setColor("Color", ColorRGBA.Green);
        bullet.setMaterial(s);
        bullet.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        bullet.setLocalTranslation(app.getCamera().getLocation());                
        //RigidBodyControl bulletNode = new BombControl(assetManager, bulletMesh, 1);                
        RigidBodyControl bulletNode = new RigidBodyControl(bulletMesh, 1f);
        bulletNode.setLinearVelocity(app.getCamera().getDirection().mult(bulletSpeed));

        bullet.addControl(bulletNode);
        rootNode.attachChild(bullet);  
        space.add(bulletNode);
    }
    }
    //--------------------------------------------------------------------------
    public static void SpawnEnemy(Vector3f s, Application app, Node rootNode, AssetManager assetManager, PhysicsSpace space){
        //int size=r.nextInt(5)+1;
        int vertex=r.nextInt(10)+3, radius=r.nextInt(10)+1;
        Sphere b = new Sphere(vertex, vertex, radius, true, false);
        Geometry Asteroid= new Geometry(mygame.Main.enemyTag, b) ;
        //BoxCollisionShape bbox=new BoxCollisionShape(new Vector3f(b1, b2, b3));
        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //mat.getAdditionalRenderState().setWireframe(true);//show wireframe
        //mat.setColor("Color", ColorRGBA.randomColor());
        Asteroid.setMaterial(mat);
        //mat.setColor("Color", ColorRGBA.randomColor());
        if(Main.Sakamoto==false){
        mat.setTexture("ColorMap", assetManager.loadTexture(textures[r.nextInt(textures.length)]));
        }
        else{
        mat.setTexture("ColorMap", assetManager.loadTexture("Textures/Asteroid_Textures/sakamoto2.jpg"));
        }
        
        
        //mat.setTexture(null, null);
        //Spatial Asteroid=assetManager.loadModel("Models/ASTEROID.obj");
        
        //GImpactCollisionShape collider=new GImpactCollisionShape();
        Asteroid.setMaterial(mat);
        Asteroid.scale(r.nextInt(4)+1);
        
        Asteroid.setName(mygame.Main.enemyTag);
        CollisionShape AsteroidMesh=CollisionShapeFactory.createDynamicMeshShape(Asteroid);
        float pX,pY,pZ;
        pX=r.nextInt(enemySpawnRange*2)+s.x;
        pY=r.nextInt(enemySpawnRange*2)+s.y;
        pZ=r.nextInt(enemySpawnRange*2)+s.z;
        Asteroid.setLocalTranslation(pX,pY,pZ);//spawn point
        
        
        RigidBodyControl rbBox=new RigidBodyControl(AsteroidMesh,1);
        
        float vX, vY, vZ;
        vX=r.nextInt(enemySpeed*2)-enemySpeed*Main.Minutes;
        vY=r.nextInt(enemySpeed*2)-enemySpeed*Main.Minutes;
        vZ=r.nextInt(enemySpeed*2)-enemySpeed*Main.Minutes;
        rbBox.setLinearVelocity(new Vector3f(vX,vY,vZ));//speed
        
        float rX, rY, rZ;
        rX=(r.nextInt(enemyRotation*2)-enemyRotation)/10;
        rY=(r.nextInt(enemyRotation*2)-enemyRotation)/10;
        rZ=(r.nextInt(enemyRotation*2)-enemyRotation)/10;
        rbBox.setAngularVelocity(new Vector3f(rX,rY,rZ));//rotation speed
        //geom.addControl(rbBox);
        Asteroid.addControl(rbBox);
        //rootNode.attachChild(geom);
        rootNode.attachChild(Asteroid);
        space.add(rbBox);
    }
    
    //--------------------------------------------------------------------------
    
    public static int sins=0;
    
    public static void itemDrops(Vector3f l, Application app, Node rootNode, AssetManager assetManager, PhysicsSpace space){
        
        float SphereSize=0.6f+Main.Minutes*0.5f;
        SphereCollisionShape itemDropMesh=new SphereCollisionShape(3f);
        
        if(Main.Minutes<=5){
        sins=r.nextInt(5+(int)Math.pow((double)2,(double)Main.Minutes))+1;//random amount of SINS
        }

        
        for(int a=0; a<sins; a++){
            Sphere item = new Sphere(10,10,SphereSize,true,false);           

            Geometry pickup = new Geometry(mygame.Main.itemPickupTag, item);
            //CollisionShape itemDropMesh=CollisionShapeFactory.createDynamicMeshShape(pickup);
            Material s=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            s.setColor("Color", ColorRGBA.Yellow);
            pickup.setMaterial(s);

            float lX, lY, lZ;

            lX=r.nextInt(itemSR)+l.x;
            lY=r.nextInt(itemSR)+l.y;
            lZ=r.nextInt(itemSR)+l.z;
            
            pickup.setLocalTranslation(lX, lY, lZ);//random location 'itemSR' units around the enemy killed                             
            RigidBodyControl pickupRBC = new RigidBodyControl(itemDropMesh, 1f);

            pickup.addControl(pickupRBC);
            rootNode.attachChild(pickup);  
            space.add(pickupRBC);
        }
    }
    
    
    
    
}

