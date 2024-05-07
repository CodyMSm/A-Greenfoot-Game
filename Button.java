import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;
import java.nio.file.Path;
import java.util.List;

/**
 * Write a description of class Button here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Button extends Actor
{
    boolean clicked = false;
    
    /**
     * Act - do whatever the Button wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {  
        if (Greenfoot.mouseClicked(this) == true){
            clicked = true;
        }
    }
    
    public void setLevelDirectoryClick(int numb)
    {
        if (Greenfoot.mouseClicked(this) == true){
            MyWorld myWorld = (MyWorld)MyWorld.world;
            Image levelDescritpionImage = new Image();
            
            getImage().scale(getImage().getWidth() - 10, getImage().getHeight() - 10);
            Greenfoot.delay(10);
            switch (numb) {
                case 1:
                    myWorld.levelDirectory = ("\\levels\\Multi Area Level.txt");
                    myWorld.currentLevel = 1;
                    break;
                case 2:
                    myWorld.levelDirectory = ("\\levels\\Platform Layout.txt");
                    myWorld.currentLevel = 2;
                    break;
            }
            
            myWorld.removeObjects(myWorld.getObjects(Image.class));
            
            myWorld.addObject(levelDescritpionImage, 950, 325);
            levelDescritpionImage.drawLevelDescription();
            
            getImage().scale(getImage().getWidth() + 10, getImage().getHeight() + 10);
            
            //System.out.println(myWorld.currentFile);
            
            
            clicked = false;
        }
    }
    
    public void setLevelDirectory(int numb)
    {
        MyWorld myWorld = (MyWorld)MyWorld.world;
        switch (numb) {
            case 1:
                myWorld.levelDirectory = ("\\levels\\Multi Area Level.txt");
                myWorld.currentLevel = 1;
                break;
            case 2:
                myWorld.levelDirectory = ("\\levels\\Platform Layout.txt");
                myWorld.currentLevel = 2;
                break;
        }
    }
    
    public void goToFileSelect(){
        if (clicked == true){
            MyWorld myWorld = (MyWorld)MyWorld.world;
            
            setImage("Play_Button2.png");
            getImage().scale(getImage().getWidth() - 10, getImage().getHeight() - 10);
            Greenfoot.delay(10);
            
            Player player = myWorld.hero;
            
            player.hasBox = false;
            
            myWorld.levelArea = 0;
            myWorld.inLevel = false;
            myWorld.removeObjects(myWorld.getObjects(null));
            myWorld.spawned = false;
            myWorld.won = false;
            
            Image file1 = new Image();
            myWorld.addObject(file1, 250, 400);
            
            Image file2 = new Image();
            myWorld.addObject(file2, 600, 400);
            
            Image file3 = new Image();
            myWorld.addObject(file3, 950, 400);
            
            try{
                file1.drawFileMenu(1);
            }
            catch (ClassNotFoundException cnfe){
                cnfe.printStackTrace();
            }
            try{
                file2.drawFileMenu(2);
            }
            catch (ClassNotFoundException cnfe){
                cnfe.printStackTrace();
            }
            try{
                file3.drawFileMenu(3);
            }
            catch (ClassNotFoundException cnfe){
                cnfe.printStackTrace();
            }
            
            clicked = false;
        }
    }
    
    public void deleteFile(int numb){
        if (clicked == true){
            MyWorld myWorld = (MyWorld)MyWorld.world;
            File file = new File(System.getProperty("user.dir") + "\\progess information\\file_" + numb + ".txt");
            
            setImage("DeleteFile_Button2.png");
            getImage().scale(getImage().getWidth() - 10, getImage().getHeight() - 10);
            Greenfoot.delay(10);
            getImage().scale(getImage().getWidth() + 10, getImage().getHeight() + 10);
            setImage("DeleteFile_Button.png");
            
            file.delete();
            
            switch (numb) {
                case 1:
                    myWorld.level1Info.drawText("Empty");
                    break;
                    
                case 2:
                    myWorld.level2Info.drawText("Empty");
                    break;
                    
                case 3:
                    myWorld.level3Info.drawText("Empty");                    
                    break;
            }
            
            clicked = false;
        }
    }
    
    public void chooseFile(int numb){
        if (clicked == true){
            MyWorld myWorld = (MyWorld)MyWorld.world;
            File file = new File(System.getProperty("user.dir") + "\\progess information\\file_" + numb + ".txt");
            switch (numb) {
                case 1:
                    myWorld.currentFile = "file_1";
                    break;
                case 2:
                    myWorld.currentFile = "file_2";
                    break;
                case 3:
                    myWorld.currentFile = "file_3";
                    break;
            }
            if (file.exists() == true){
                if (file.length() != 0){
                    try{
                        myWorld.loadGameProgress(file);
                    }
                    catch (ClassNotFoundException cnfe){
                        cnfe.printStackTrace();
                    }
                } else {
                    myWorld.levelwon = myWorld.savedCompletion;
                    myWorld.levelCompletionTime = myWorld.savedTime;
                }
            } else {
                //System.out.println(numb);
                String prefix = "file_" + numb;
                String suffix = ".txt";
                File directoryPath = new File(System.getProperty("user.dir") + "\\progess information\\");
                
                try{
                    File tempFile = File.createTempFile(prefix, suffix, directoryPath);
                    File rename = new File(System.getProperty("user.dir") + "\\progess information\\file_" + numb + ".txt");
                    tempFile.renameTo(rename);
                    
                    myWorld.levelwon = new boolean[myWorld.levels.length];
                    myWorld.levelCompletionTime = new int[myWorld.levels.length];
                    for (int i = 0; i > myWorld.levelwon.length; i++){
                        myWorld.levelwon[i] = false;
                        myWorld.levelCompletionTime[i] = 0;
                    }
                    
                    myWorld.saveGameProgressToFile(rename);
                    try{
                        myWorld.loadGameProgress(rename);
                    }
                    catch (ClassNotFoundException cnfe){
                        cnfe.printStackTrace();
                    }
                }
                catch (java.io.IOException ioe){
                    ioe.printStackTrace();
                }
                myWorld.levelwon = myWorld.savedCompletion;
                myWorld.levelCompletionTime = myWorld.savedTime;
            }
            goToLevelSelect("Choose");
            clicked = false;
        }
    }
    
    public void goToLevelSelect(String type)
    {
        if (clicked == true){
            MyWorld myWorld = (MyWorld)MyWorld.world;
            switch (type) {
                case "Play":
                    setImage("Play_Button2.png");
                    break;
                case "Return":
                    setImage("ReturnToLevels_Button2.png");
                    break;
                case "Choose":
                    setImage("SelectFile_Button2.png");
                    break;
            }
            
            getImage().scale(getImage().getWidth() - 10, getImage().getHeight() - 10);
            Greenfoot.delay(10);
            getImage().scale(getImage().getWidth() + 10, getImage().getHeight() + 10);
            
            Player player = myWorld.hero;
            
            player.hasBox = false;
            
            myWorld.levelArea = 0;
            myWorld.deaths = 0;
            myWorld.inLevel = false;
            if (myWorld.getObjects(Player.class).size() != 0){
                myWorld.getObjects(Player.class).get(0).collisionBox = null;
            }
            myWorld.removeObjects(myWorld.getObjects(null));
            myWorld.spawned = false;
            myWorld.won = false;
            
            
            int x = 100;
            int y = 100;
            for (int i = 0; i < myWorld.levels.length; i++){
                Button level = new Button();
                myWorld.addObject(level, x, y);
                level.setImage("Level_" + Integer.toString(i+1) + "_Button.png");
                
                switch (i) {
                    case 0:
                        myWorld.level1Button = level;
                        break;
                    case 1:
                        myWorld.level2Button = level;
                        break;
                }
                
                if (x != 700){
                    x = x + 150;
                } else if (x == 700){
                    y = y + 150;
                    x = 100;
                }
            }
            
            myWorld.addObject(myWorld.menuButton, 120, 730);
            myWorld.menuButton.setImage("Return to Menu_Button.png");
            
            myWorld.addObject(myWorld.startButton, 1080, 730);
            myWorld.startButton.setImage("Start Button.png");
            
            myWorld.setBackground("sandstone.jpg");
            
            myWorld.levelDirectory = null;
            
            clicked = false;
        }
    }
    
    public void startGame()
    {
        MyWorld myWorld = (MyWorld)MyWorld.world;
        if (clicked == true && myWorld.levelDirectory == null){
            setImage("Start Button2.png");
            getImage().scale(getImage().getWidth() - 10, getImage().getHeight() - 10);
            Greenfoot.delay(10);
            getImage().scale(getImage().getWidth() + 10, getImage().getHeight() + 10);
            setImage("Start Button.png");
            
            clicked = false;
        } else if (clicked == true && myWorld.levelDirectory != null){
            setImage("Start Button2.png");
            getImage().scale(getImage().getWidth() - 10, getImage().getHeight() - 10);
            Greenfoot.delay(10);
            
            myWorld.paused = false;
            myWorld.removeObjects(myWorld.getObjects(null));
            myWorld.prepare();
                
            myWorld.inLevel = true;
                
            clicked = false;
        }
    }
    
    public void nextLevel()
    {
        MyWorld myWorld = (MyWorld)MyWorld.world;
        if (clicked == true){
            setImage("NextLevel_Button2.png");
            getImage().scale(getImage().getWidth() - 10, getImage().getHeight() - 10);
            Greenfoot.delay(10);
            
            Player player = myWorld.hero;
            
            player.hasBox = false;
            
            myWorld.deaths = 0;
            myWorld.levelArea = 0;
            myWorld.inLevel = false;
            if (myWorld.getObjects(Player.class).size() != 0){
                myWorld.getObjects(Player.class).get(0).collisionBox = null;
            }
            myWorld.removeObjects(myWorld.getObjects(null));
            myWorld.spawned = false;
            myWorld.won = false;
            
            myWorld.nextLevelButton.setLevelDirectory(myWorld.currentLevel + 1);
            
            //System.out.println(myWorld.levelDirectory);
            
            myWorld.removeObjects(myWorld.getObjects(null));
            myWorld.prepare();
            
            myWorld.inLevel = true;
            myWorld.paused = false;
            
            clicked = false;
        }
    }
    
    public void returnToMenu()
    {
        if (clicked == true){
            MyWorld myWorld = (MyWorld)MyWorld.world;
            
            setImage("Return to Menu_Button2.png");
            getImage().scale(getImage().getWidth() - 10, getImage().getHeight() - 10);
            Greenfoot.delay(10);
            
            Player player = myWorld.hero;
            
            player.hasBox = false;
            
            myWorld.deaths = 0;
            myWorld.levelArea = 0;
            myWorld.inLevel = false;
            if (myWorld.getObjects(Player.class).size() != 0){
                myWorld.getObjects(Player.class).get(0).collisionBox = null;
            }
            myWorld.removeObjects(myWorld.getObjects(null));
            myWorld.spawned = false;
            myWorld.won = false;
            myWorld.createMenu();
            myWorld.setBackground("sandstone.jpg");
            
            myWorld.levelDirectory = null;
            
            myWorld.currentFile = null;
            
            myWorld.levelwon = new boolean[myWorld.levels.length];
            for (int i = 0; i > myWorld.levelwon.length; i++){
                myWorld.levelwon[i] = false;
                myWorld.levelCompletionTime[i] = 0;
            }
            
            clicked = false;
        }
    }
    
    public void goToControls(){
        if (clicked == true){
            MyWorld myWorld = (MyWorld)MyWorld.world;
            
            setImage("Controls_Button2.png");
            getImage().scale(getImage().getWidth() - 10, getImage().getHeight() - 10);
            Greenfoot.delay(10);
            
            myWorld.setBackground("Controls Diagram_2.png");
            myWorld.removeObjects(myWorld.getObjects(null));
            
            myWorld.addObject(myWorld.menuButton, 120, 730);
            myWorld.menuButton.setImage("Return to Menu_Button.png");
            
            clicked = false;
        }
    }
}
