import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.Serializable;

/**
 * Write a description of class Enemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Enemy extends Actor implements Serializable 
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
            Enemy enemy = new Enemy();
            int speed = 3;
            int x = 0;
            int y = 0;
            
            
            if (x > -1 && y > -1){
                finalX = getX();
                finalY = getY();
            }
            
            
            if (hasBox == false){
                collisionBox = (CollisionBox) getOneObjectAtOffset(5, 5, CollisionBox.class);
                collisionBox.setBoxSize(enemy);
                collisionBox.setSpeed(3);
                hasBox = true;
            }
            
            x = collisionBox.getX();
            y = collisionBox.getY();
            
            setLocation(x, y);
            
            if(hitWall == false){
                collisionBox.setLeftVelocity(speed);
                collisionBox.leftForce = speed;
                collisionBox.setInsideForce(true);
            }
            
            if (collisionBox.leftVelocity > 0 || collisionBox.rightVelocity > 0){
                collisionBox.inMotion(true);
                collisionBox.setInsideForce(true);
            } else {
                collisionBox.inMotion(false);
                collisionBox.setInsideForce(false);
                hitWall = false;
            }
            
            if (collisionBox.getBarrierOnRight() == true || getX() == 1199){
                collisionBox.setLeftVelocity(speed);
                collisionBox.leftForce = speed;
                collisionBox.setRightVelocity(0);
                collisionBox.rightForce = 0;
                collisionBox.setInsideForce(true);
                hitWall = true;
            }
            if (collisionBox.getBarrierOnLeft() == true || getX() == 0){
                collisionBox.setRightVelocity(speed);
                collisionBox.rightForce = speed;
                collisionBox.setLeftVelocity(0);
                collisionBox.leftForce = 0;
                collisionBox.setInsideForce(true);
                hitWall = true;
            }
        }
    }
}