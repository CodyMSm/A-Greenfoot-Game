import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class clock here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Clock extends Actor
{
    long timer = System.currentTimeMillis();
    int stopwatch = 0;
    int delayNumber = 1000;
    boolean paused = false;
    boolean timePassed = false;
    
    public void setDelay(int delayNumber){
        this.delayNumber = delayNumber;
    }
    public void setTimePaused(boolean paused){
        this.paused = paused;
    }
    public void setTimePassed(boolean timePassed){
        this.timePassed = timePassed;
    }
    public void resetTime(){
        stopwatch = 0;
    }
    public int getTime(){
        return stopwatch;
    }
    
    /**
     * Act - do whatever the clock wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (paused == false){
            long curTime = System.currentTimeMillis();
            int delay = delayNumber;
            if (curTime >= timer + delay){
                delay += delayNumber;
                timer = System.currentTimeMillis();
                        
                stopwatch = stopwatch + 1;
                    
                timePassed = true;
            }
        }
    }
}
