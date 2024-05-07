 import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.Serializable;

/**
 * Write a description of class Block here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Block extends Actor implements Serializable
{
    boolean hasCollision = true;
    boolean hitWall = false;
    boolean hasBox = false;
    CollisionBox collisionBox;
    int finalX;
    int finalY;
    /**
     * Act - do whatever the Enemy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (((MyWorld)getWorld()).paused == false) {
            MyWorld myWorld = (MyWorld)MyWorld.world;
            Block block = new Block();
            
            if (hasBox == false){
                collisionBox = (CollisionBox) getOneObjectAtOffset(5, 5, CollisionBox.class);
                collisionBox.setBoxSize(block);
                collisionBox.setDraggable(true);
                hasBox = true;
            }
            int x = 0;
            int y = 0;
            
            
            if (x > -1 && y > -1){
                finalX = getX();
                finalY = getY();
            }
            
            //System.out.println("Right velocity: " + collisionBox.rightVelocity);
            
            x = collisionBox.getX();
            y = collisionBox.getY();
            
            setLocation(x, y);
        }
    }
}
