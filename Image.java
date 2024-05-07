import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.Color;
import java.io.File;

/**
 * Write a description of class Image here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Image extends Actor
{
    GreenfootImage savedImage = null;
    int savedImageX, savedImageY;
    
    
    public void drawText(String text)
    {
        int fontsize = 70;
        Color txtColor = Color.BLACK;
        Color transparent = new Color(0, 0, 0, 0);
        GreenfootImage txtImage = new GreenfootImage(text, fontsize, txtColor, transparent);

        txtImage.setFont(new Font("SERIF", 40));
        setImage(txtImage);
    }
    
    public void drawFileMenu(int numb) throws ClassNotFoundException {
        MyWorld myWorld = (MyWorld)MyWorld.world;
        setImage("FileFrame.png");
        
        Image info = new Image();
        myWorld.addObject(info, getX(), getY() - 50);
        
        Image fileName = new Image();
        myWorld.addObject(fileName, getX(), getY() - 300);
        
        Button chooseFile;
        switch (numb) {
            case 1:
                chooseFile = myWorld.file1Button;
                myWorld.addObject(chooseFile, getX(), getY() + 225);
                myWorld.file1Button.setImage("SelectFile_Button.png");
                
                myWorld.addObject(myWorld.deleteFile1Button, getX(), getY() + 100);
                myWorld.deleteFile1Button.setImage("DeleteFile_Button.png");
                break;
                
            case 2:
                chooseFile = myWorld.file2Button;
                myWorld.addObject(chooseFile, getX(), getY() + 225);
                myWorld.file2Button.setImage("SelectFile_Button.png");
                
                myWorld.addObject(myWorld.deleteFile2Button, getX(), getY() + 100);
                myWorld.deleteFile2Button.setImage("DeleteFile_Button.png");
                break;
                
            case 3:
                chooseFile = myWorld.file3Button;
                myWorld.addObject(chooseFile, getX(), getY() + 225);
                myWorld.file3Button.setImage("SelectFile_Button.png");
                
                myWorld.addObject(myWorld.deleteFile3Button, getX(), getY() + 100);
                myWorld.deleteFile3Button.setImage("DeleteFile_Button.png");
                break;
        }
        
        switch (numb) {
            case 1:
                File file1 = new File(System.getProperty("user.dir") + "\\progess information\\file_1.txt");
                fileName.drawText("File 1");
                if (file1.exists() == true){
                    myWorld.loadGameProgress(file1);
                    //int percent;
                    int finalNumb = 0;
                    for (int i = 0; i < myWorld.savedCompletion.length; i++){
                        if (myWorld.savedCompletion[i] == true){
                            finalNumb++;
                        }
                    }
                    //percent = ((finalNumb / myWorld.levelwon.length) * 100);
                    
                    info.drawText("Levels Completed\n" + finalNumb + " / " + myWorld.levelwon.length);
                    if (info.getImage().getWidth() > getImage().getWidth() - 50){
                        info.getImage().scale(getImage().getWidth() - 70, info.getImage().getHeight() - 20);
                    }
                    myWorld.level1Info = info;
                } else {
                    info.drawText("Empty");
                }
                break;
            case 2:
                File file2 = new File(System.getProperty("user.dir") + "\\progess information\\file_2.txt");
                fileName.drawText("File 2");
                if (file2.exists() == true){
                    myWorld.loadGameProgress(file2);
                    int finalNumb = 0;
                    for (int i = 0; i < myWorld.savedCompletion.length; i++){
                        if (myWorld.savedCompletion[i] == true){
                            finalNumb++;
                        }
                    }
                    
                    info.drawText("Levels Completed\n" + finalNumb + " / " + myWorld.levelwon.length);
                    if (info.getImage().getWidth() > getImage().getWidth() - 50){
                        info.getImage().scale(getImage().getWidth() - 70, info.getImage().getHeight() - 20);
                    }
                    myWorld.level2Info = info;
                } else {
                    info.drawText("Empty");
                }
                break;
            case 3:
                File file3 = new File(System.getProperty("user.dir") + "\\progess information\\file_3.txt");
                fileName.drawText("File 3");
                if (file3.exists() == true){
                    myWorld.loadGameProgress(file3);
                    int finalNumb = 0;
                    for (int i = 0; i < myWorld.savedCompletion.length; i++){
                        if (myWorld.savedCompletion[i] == true){
                            finalNumb++;
                        }
                    }
                    
                    info.drawText("Levels Completed\n" + finalNumb + " / " + myWorld.levelwon.length);
                    if (info.getImage().getWidth() > getImage().getWidth() - 50){
                        info.getImage().scale(getImage().getWidth() - 70, info.getImage().getHeight() - 20);
                    }
                    myWorld.level3Info = info;
                } else {
                    info.drawText("Empty");
                }
                break;
        }
        
        myWorld.addObject(myWorld.menuButton, 110, 740);
        myWorld.menuButton.setImage("Return to Menu_Button.png");
    }
    
    public void drawLevelDescription(){
        MyWorld myWorld = (MyWorld)MyWorld.world;
        setImage("LevelDescription.png");
        
        Image levelName = new Image();
        getWorld().addObject(levelName, 950, 80);
        levelName.drawText("Level " + myWorld.currentLevel);
        
        Image winStatus = new Image();
        getWorld().addObject(winStatus, 950, 180);
        if (myWorld.savedCompletion[myWorld.currentLevel - 1] == true){
            winStatus.drawText("Level Completed");
            if (winStatus.getImage().getWidth() > getImage().getWidth() - 50){
                winStatus.getImage().scale(getImage().getWidth() - 50, winStatus.getImage().getHeight());
            }
        } else if (myWorld.savedCompletion[myWorld.currentLevel - 1] == false){
            winStatus.drawText("Level Uncompleted");
            if (winStatus.getImage().getWidth() > getImage().getWidth() - 50){
                winStatus.getImage().scale(getImage().getWidth() - 50, winStatus.getImage().getHeight());
            }
        }
        
        Image completionTime = new Image();
        getWorld().addObject(completionTime, 950, 300);
        if (myWorld.savedTime[myWorld.currentLevel - 1] > 0){
            
            completionTime.drawText("Fastest Time:\n" + calculateTime(myWorld.levelCompletionTime[myWorld.currentLevel - 1]));
            if (completionTime.getImage().getWidth() > getImage().getWidth() - 50){
                completionTime.getImage().scale(getImage().getWidth() - 50, completionTime.getImage().getHeight() - 20);
            }
        } else {
            completionTime.drawText("Fastest Time:\nNone");
            if (completionTime.getImage().getWidth() > getImage().getWidth() - 50){
                completionTime.getImage().scale(getImage().getWidth() - 50, completionTime.getImage().getHeight() - 20);
            }
        }
    }
    
    public void drawSummary(){
        MyWorld myWorld = (MyWorld) getWorld();
        Button menuButton = new Button();
        myWorld.menuButton = menuButton;
        setImage("Summary_menu.png");
        
        Image deaths = new Image();
        getWorld().addObject(deaths, 600, 200);
        deaths.drawText("Deaths: " + ((MyWorld) getWorld()).deaths);
        if (deaths.getImage().getWidth() > getImage().getWidth() - 50){
            deaths.getImage().scale(getImage().getWidth() - 50, deaths.getImage().getHeight());
        }
        
        Image timeElapsed = new Image();
        getWorld().addObject(timeElapsed, 600, 300);
        
        String finalString = "Time elapsed: " + calculateTime(myWorld.time);
        timeElapsed.drawText(finalString);
        
        if (myWorld.levelCompletionTime[myWorld.currentLevel - 1] > myWorld.time || myWorld.levelCompletionTime[myWorld.currentLevel - 1] == 0){
            myWorld.levelCompletionTime[myWorld.currentLevel - 1] = myWorld.time;
        }
        
        if (timeElapsed.getImage().getWidth() > getImage().getWidth() - 50){
            timeElapsed.getImage().scale(getImage().getWidth() - 50, timeElapsed.getImage().getHeight());
        }
        
        getWorld().addObject(myWorld.menuButton, 750, 720);
        menuButton.setImage("Return to Menu_Button.png");
        
        getWorld().addObject(myWorld.returnToLevelsButton, 450, 720);
        myWorld.returnToLevelsButton.setImage("ReturnToLevels_Button.png");
        
        if (myWorld.currentLevel != myWorld.levels.length){
            getWorld().addObject(myWorld.nextLevelButton, 600, 565);
            myWorld.nextLevelButton.setImage("NextLevel_Button.png");
        }
        
        getWorld().setPaintOrder(Image.class, Platform.class);
        getWorld().setPaintOrder(Button.class, Image.class);
    }
    
    public String calculateTime(int time){
        MyWorld myWorld = (MyWorld) getWorld();
        String string = "null";
        int seconds = 0;
        int minutes = 0;
        int hours = 0;
        if (time > 3600){
            while (time > 3600){
                time = time - 60;
                hours++;
            }
        }
        if (time > 60){
            while (time > 60){
                time = time - 60;
                minutes++;
            }
        }
        seconds = time;
        if (((MyWorld) getWorld()).time > 3600){
            string = hours + "h " + minutes + "m " + seconds + "s";
        } else if (((MyWorld) getWorld()).time > 60){
            string = minutes + "m " + seconds + "s";
        } else if (((MyWorld) getWorld()).time < 60){
            string = seconds + "s";
        }
        return string;
    }
}
