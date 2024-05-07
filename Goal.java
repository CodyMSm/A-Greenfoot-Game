import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.Color;
/**
 * Write a description of class Goal here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Goal extends Actor
{
    Clock clock = new Clock();
    boolean spawned = false;
    boolean won = false;
    Image counter = new Image();
    public boolean won(){
        return won;
    }
    /**
     * Act - do whatever the Goal wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (((MyWorld)getWorld()).paused == false) {
            getWorld().addObject(clock, 0, 0);
            clock.setTimePaused(false);
            Player player = ((MyWorld)getWorld()).hero;
            if (intersects(player) == true && won == false){        
                // creates the images actor
                if (spawned == false){
                    getWorld().addObject(counter, getWorld().getObjects(Goal.class).get(0).getX(), getWorld().getObjects(Goal.class).get(0).getY() - 75);
                    spawned = true;
                }
                // 1000ms = 1s
                // creates a variable than increases by 1 every second
                
                if (clock.getTime() == 3){
                    counter.setImage("Counter_3.png");
                } else if (clock.getTime() == 2){
                    counter.setImage("Counter_2.png");
                } else if (clock.getTime() == 1){
                    counter.setImage("Counter_1.png");
                }
                
                //System.out.println(stopwatch);
                
                if (clock.getTime() >= 4){
                    //System.out.println("You Win!");
                    clock.resetTime();
                    Image summaryImage = new Image();
                    getWorld().addObject(summaryImage, 600, 400);
                    summaryImage.drawSummary();
                    won = true;
                }
            } else {
                counter.setImage("Transparent Texture.png");
                Actor actor = (Actor) counter;
                getWorld().removeObject(actor);
                clock.resetTime();
                spawned = false;
            }
        } else {
            clock.setTimePaused(true);
        }
    }
}
