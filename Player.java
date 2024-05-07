import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.Serializable;
/**
 * This is the player, which you are supposed to control 
 * 
 * Cody S
 * 10/29/2023
 */
public class Player extends Actor implements Serializable
{
    boolean hasBox = false;
    int health = 1;
    int x;
    int y;
    CollisionBox collisionBox;
    int speed = 4;
    
    public CollisionBox getCollisionBox(){
        return collisionBox;
    }
    /**
     * Act - do whatever the Player wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (((MyWorld)getWorld()).paused == false) {
            MyWorld myWorld = (MyWorld)MyWorld.world;
            Player player = new Player();
                        
            if (hasBox == false || collisionBox == null){
                collisionBox = (CollisionBox) getOneObjectAtOffset(5, 5, CollisionBox.class);
                collisionBox.setBoxSize(player);
                hasBox = true;
                collisionBox.canPush = true;
            }
            
            // makes the player die when they touch an enemy
            Object[] objectArray4 = getObjectsInRange(getImage().getHeight() + (getImage().getHeight()/2), Enemy.class).toArray();
            for (int i = 0; i < objectArray4.length; i++){
                Actor boxObject = (Actor) objectArray4[i];
                if (getY() == (boxObject.getY() - ((boxObject.getImage().getHeight())/2 + getImage().getHeight()/2))){
                    health = 0;
                }
                if (getY() == (boxObject.getY() + ((boxObject.getImage().getHeight())/2 + getImage().getHeight()/2))){
                    health = 0;
                } 
                if (getX() == (boxObject.getX() - ((boxObject.getImage().getWidth())/2 + getImage().getWidth()/2))){
                    health = 0;
                } 
                if (getX() == (boxObject.getX() + ((boxObject.getImage().getWidth())/2 + getImage().getWidth()/2))){
                    health = 0;
                }
            }
            
            int x = collisionBox.getX();
            int y = collisionBox.getY();
            setLocation(x, y);
            
            // Player movement
            if (Greenfoot.isKeyDown("W") || Greenfoot.isKeyDown("A") || Greenfoot.isKeyDown("S") || Greenfoot.isKeyDown("D") || collisionBox.getOnGround() == false){
                collisionBox.inMotion(true);
                if ((Greenfoot.isKeyDown("W") || Greenfoot.isKeyDown("A") || Greenfoot.isKeyDown("S") || Greenfoot.isKeyDown("D")) && collisionBox.getInObject() == false){
                    if (Greenfoot.isKeyDown("W") && collisionBox.getOnGround() == true){
                        collisionBox.setUpVelocity(20);
                        collisionBox.upForce = 20;
                    }
                    if (Greenfoot.isKeyDown("A") && collisionBox.getBarrierOnLeft() == false){
                        collisionBox.setLeftVelocity(4);   
                        collisionBox.leftForce = 4;
                    } else {
                        collisionBox.setLeftVelocity(0);
                        collisionBox.leftForce = 0;
                    }
                    if (Greenfoot.isKeyDown("S") && collisionBox.getOnGround() == false){
                        collisionBox.setDownVelocity(4);
                        collisionBox.downForce = 4;
                    } else {
                        collisionBox.setDownVelocity(0); 
                        collisionBox.downForce = 0;
                    }
                    if (Greenfoot.isKeyDown("D") && collisionBox.getBarrierOnRight() == false){
                        collisionBox.setRightVelocity(4);
                        collisionBox.rightForce = 4;
                    } else {
                        collisionBox.setRightVelocity(0);
                        collisionBox.rightForce = 0;
                    }
                    collisionBox.setInsideForce(true);
                }
            } else {
                collisionBox.inMotion(false);
                collisionBox.setInsideForce(false);
                collisionBox.setRightVelocity(0);
                collisionBox.setLeftVelocity(0);
                collisionBox.setDownVelocity(0);
                collisionBox.rightForce = 0;
                collisionBox.leftForce = 0;
                collisionBox.downForce = 0;
                collisionBox.upForce = 0;
            }
        }
    }
}