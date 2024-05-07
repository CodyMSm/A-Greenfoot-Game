import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class RespawnTile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RespawnTile extends Actor
{
    int respawnTileX;
    int respawnTileY;
    /**
     * Act - do whatever the RespawnTile wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        respawnTileX = getX();
        respawnTileY = getY();
    }
}
