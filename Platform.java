import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is a platform, which you are supposed to stand on 
 * 
 * Cody S
 * 10/29/2023
 */
public class Platform extends Actor
{
    int platformX;
    int platformY;
    int platformImageHeight;
    int platformImageWidth;
    /**
     * Act - do whatever the Platform wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        platformX = getX();
        platformY = getY();
        platformImageHeight = getImage().getHeight();
        platformImageWidth = getImage().getWidth();
    }
}