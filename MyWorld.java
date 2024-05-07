import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

/**
 * Write a description of class MyWorld here.
 * 
 * @Cody
 * 10/23/2023
 */
public class MyWorld extends World
{
    public static MyWorld world;
    
    public Player hero = new Player();
    
    Button startButton = new Button();
    Button controlsButton = new Button();
    Button playButton = new Button();
    Button menuButton = new Button();
    Button returnToLevelsButton = new Button();
    Button nextLevelButton = new Button();
    Button file1Button = new Button();
    Button file2Button = new Button();
    Button file3Button = new Button();
    Button deleteFile1Button = new Button();
    Button deleteFile2Button = new Button();
    Button deleteFile3Button = new Button();
    
    Button level1Button = new Button();
    Button level2Button = new Button();
    
    Image pausedImage = new Image();
    
    Image level1Info = new Image();
    Image level2Info = new Image();
    Image level3Info = new Image();
    
    
    Clock worldTime = new Clock();
    
    String levelDirectory;
    int[] levels = new int[2];
    int currentLevel = 0;
    
    long timer = System.currentTimeMillis();
    int time = 0;
    
    int mapTileNum[][];
    int[][] mapLevel[];
    int worldWidth = 1200;
    int worldHieght = 800;
    int levelArea = 0;
    int amountOfAreas = 0;
    
    boolean inLevel = false;
    boolean paused = false;
    boolean pressing = false;
    
    int respawnArea;
    int deaths = 0;
    boolean won = false;
    
    boolean[] levelwon = new boolean[levels.length];
    int[] levelCompletionTime = new int[levels.length];
    
    String currentFile;
    
    boolean spawned = false;
    boolean[] areaVisited;
    
    boolean saved = false;
    
    boolean[] savedCompletion;
    int[] savedTime;
    
    List<Enemy> savedEnemies;
    List<Block> savedBlocks;
    List<CollisionBox> savedCollisionBoxes;
    public MyWorld() throws ClassNotFoundException
    {
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1200, 800, 1);
        
        mapTileNum = new int[12][8];
        
        world = this;
        
        createMenu();
        
        for (int i = 0; i > levelwon.length; i++){
            levelwon[i] = false;
            levelCompletionTime[i] = 0;
        }
    }
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    public void prepare()
    {
        //System.out.println(levelDirectory);
        loadLevel(levelDirectory);
        draw();
        addObject(worldTime, 0, 0);
        addObject(pausedImage, 600, 100);
        saved = false;
    }
    public void createMenu(){
        addObject(controlsButton, 600, 510);
        controlsButton.setImage("Controls_Button.png");
        addObject(playButton, 600, 400);
        playButton.setImage("Play_Button.png");
    }
    public void act()
    {
        buttonDetect();
        
        if (inLevel == true){
            
            if (spawned == true){
                try{
                    nextAreaDetect();
                }
                catch (IOException ioe){
                    ioe.printStackTrace();
                }
                respawnDetection();
            }
            
            if (getObjects(Goal.class).isEmpty() == false){
                Goal goal = getObjects(Goal.class).get(0);
                won = goal.won();
            }
            if (won == false){
                time = worldTime.getTime();
                if (Greenfoot.isKeyDown("P") && paused == false){
                    if (pressing == false){
                        paused = true;
                    }
                    pressing = true;
                } else if (Greenfoot.isKeyDown("P") && paused == true){
                    if (pressing == false){
                        paused = false;
                    }
                    pressing = true;
                } else {
                    pausedImage.setImage("Transparent Texture.png");
                    pressing = false;
                }
                
                if (paused == true){
                    pausedImage.setImage("Paused.png");
                    addObject(menuButton, 1075, 70);
                    addObject(returnToLevelsButton, 125, 70);
                    returnToLevelsButton.setImage("ReturnToLevels_Button.png");
                } else if (paused != true){
                    pausedImage.setImage("Transparent Texture.png");
                    removeObject(menuButton);
                    removeObject(returnToLevelsButton);
                }
            }
            if (won == true){
                worldTime.resetTime();
                removeObject(worldTime);
                paused = true;
                levelwon[currentLevel - 1] = true;
                
                if (saved == false){
                    File file = new File(System.getProperty("user.dir") + "\\progess information\\" + currentFile + ".txt");
                    saveGameProgressToFile(file);
                    saved = true;
                }
            }
        }
    }
    public void buttonDetect(){
        startButton.startGame();
        
        menuButton.returnToMenu();
        
        controlsButton.goToControls();
        
        playButton.goToFileSelect();
        //goToLevelSelect("Play");
        
        returnToLevelsButton.goToLevelSelect("Return");
        
        nextLevelButton.nextLevel();
        
        file1Button.chooseFile(1);
        file2Button.chooseFile(2);
        file3Button.chooseFile(3);
        
        deleteFile1Button.deleteFile(1);
        deleteFile2Button.deleteFile(2);
        deleteFile3Button.deleteFile(3);
        
        level1Button.setLevelDirectoryClick(1);
        level2Button.setLevelDirectoryClick(2);
    }
    public void saveGameProgressToFile(File file){
        try {   
            FileOutputStream fileStream = new FileOutputStream(file);   
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);   
            
            
            objectStream.writeObject(levelwon);   
            objectStream.writeObject(levelCompletionTime);        
            
            
            objectStream.close();   
            fileStream.close();   
            
            //System.out.println("Saved game progress successfully.");  
        } catch (Exception e) {   
            System.out.println("Failed to save game progress.");   
            System.out.println(e);
        }           
    }
    public void loadGameProgress(File file) throws ClassNotFoundException{
        try{
            FileInputStream fileStream = new FileInputStream(file);   
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);   
            
            savedCompletion = (boolean[]) objectStream.readObject();
            savedTime = (int[]) objectStream.readObject();
            
            levelwon = savedCompletion;
            levelCompletionTime = savedTime;
                        
            //System.out.println("Loaded game progress successfully.");
        } catch (Exception e) {
            System.out.println("Failed to load game progress.");   
            System.out.println(e);
        }
    }
    public void saveGameDataToFile(File file) {   
        try {   
            FileOutputStream fileStream = new FileOutputStream(file);   
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);   
            
            
            objectStream.writeObject(getObjects(Enemy.class)); 
            objectStream.writeObject(getObjects(Block.class)); 
            objectStream.writeObject(getObjects(CollisionBox.class));        
            
            
            objectStream.close();   
            fileStream.close();   
    
            //System.out.println("Saved game state successfully.");  
        } catch (Exception e) {   
            System.out.println("Failed to save game state.");   
            System.out.println(e);
        }   
    }
    public void loadGameData(File file) throws ClassNotFoundException{
        try{
            FileInputStream fileStream = new FileInputStream(file);   
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);   
        
            savedEnemies = (List<Enemy>) objectStream.readObject();
            savedBlocks = (List<Block>) objectStream.readObject();
            savedCollisionBoxes = (List<CollisionBox>) objectStream.readObject();
            
            //System.out.println("Loaded game state successfully.");
        } catch (Exception e) {
            System.out.println("Failed to load game state.");   
            System.out.println(e);
        }
    }
    public void loadLevel(String filePath){
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
            int col = 0;
            int row = 0;
            
            String levelLines[] = new String[12];
            
            for (int i = 0; i < 8; i++){
                String entireLines = br.readLine();
                System.out.println(entireLines);
                levelLines[i] = entireLines;
            }
            String[] areas = new String[100];
            areas = levelLines[0].split("  ");
            String[][] levelAreas = new String[areas.length][8];
            mapLevel = new int[areas.length][12][8];
            amountOfAreas = areas.length;
            for (int i = 0; i < areas.length; i++){
                for (int rows = 0; rows < 8; rows++){
                    String[] rowArea = levelLines[rows].split("   "); 
                    levelAreas[i][rows] = rowArea[i];
                    //System.out.println("Level Line: " + levelAreas[i][rows] + "  i = " + i);
                    //System.out.println("Row Area: " + rowArea[i]);
                }
            }
            
            for (int i = 0; i < areas.length; i++){
                while (col < 12 && row < 8){
                    String line = levelAreas[i][row];
                    
                     while (col < 12){
                        String numbers[] = line.split(" ");
    
                        // Changes the string to an integer
                        int num = Integer.parseInt(numbers[col]);
                            
                        mapLevel[i][col][row] = num;
                        //System.out.println("Map Level " + mapLevel[i][col][row] + " i = " + i);
                        col++;
                        
                    }
                    if (col == 12){
                        col = 0;
                        row++;
                    }
                }
                //System.out.println("i = " + i);
                col = 0;
                row = 0;
            }
            areaVisited = new boolean[amountOfAreas];
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void draw(){
        int col = 0;
        int row = 0;
        int respawnX = 0;
        int respawnY = 0;
        
        addObject(pausedImage, 600, 100);
        while (col < 12 && row < 8){
            while (col < 12){
                if (mapLevel[levelArea][col][row] == 1){
                    addObject(new Platform(), (col * new Platform().getImage().getHeight()) + (new Platform().getImage().getHeight()/2), (row * new Platform().getImage().getWidth()) + (new Platform().getImage().getWidth()/2));
                }
                if (mapLevel[levelArea][col][row] == 2){
                    addObject(new Goal(), (col * new Goal().getImage().getHeight()) + (new Goal().getImage().getHeight()/2), (row * new Goal().getImage().getWidth()) + (new Goal().getImage().getWidth()/2));
                }
                if (mapLevel[levelArea][col][row] == 3){
                    if (areaVisited[levelArea] == false || getObjects(Player.class).get(0).health == 0){
                        addObject(new Enemy(), (col * new Platform().getImage().getHeight()) + (new Platform().getImage().getHeight()/2), (row * new Platform().getImage().getWidth()) + (new Platform().getImage().getWidth()/2) + (new Enemy().getImage().getHeight()/2));
                    }
                }
                if (mapLevel[levelArea][col][row] == 4){
                    RespawnTile respawnTile = new RespawnTile();
                    addObject(respawnTile, (col * new Goal().getImage().getHeight()) + (new Goal().getImage().getHeight()/2), (row * new Goal().getImage().getWidth()) + (new Goal().getImage().getWidth()/2));
                    
                    if (spawned == false){
                        addObject(hero, (col * new Goal().getImage().getHeight()) + (new Goal().getImage().getHeight()/2), (row * new Goal().getImage().getWidth()) + (new Goal().getImage().getWidth()/2));
                        spawned = true;
                    }
                }
                if (mapLevel[levelArea][col][row] == 5){
                    if (areaVisited[levelArea] == false || getObjects(Player.class).get(0).health == 0){
                        addObject(new Block(), (col * new Platform().getImage().getHeight()) + (new Platform().getImage().getHeight()/2), (row * new Platform().getImage().getWidth()) + (new Platform().getImage().getWidth()/2) + (new Enemy().getImage().getHeight()/2));
                    }
                }
                // System.out.println("Level Area: " + levelArea + ". number: " + mapLevel[levelArea][col][row]);
                col++;
              }
            if (col == 12){
                col = 0;
                row++;
            }
        }
        
        if (areaVisited[levelArea] == true && getObjects(Player.class).get(0).health == 1){
            for (int i = 0; i < savedEnemies.size(); i++){
                Actor actor = (Actor) savedEnemies.get(i);
                addObject(actor, ((Enemy) actor).finalX, ((Enemy) actor).finalY);
                addObject(((Enemy) actor).collisionBox, ((Enemy) actor).finalX, ((Enemy) actor).finalY);
            }
            for (int i = 0; i < savedBlocks.size(); i++){
                Actor actor = (Actor) savedBlocks.get(i);
                addObject(actor, ((Block) actor).finalX, ((Block) actor).finalY);
                addObject(((Block) actor).collisionBox, ((Block) actor).finalX, ((Block) actor).finalY);
            }
        }
        
        setPaintOrder(Player.class, Goal.class);
        setPaintOrder(Image.class, Player.class);
        setPaintOrder(Button.class, Image.class);
        
        createCollisionBoxes();
        
        areaVisited[levelArea] = true;
    }
    public void eraseAll(){
        java.util.List<Actor> actors = getObjects(null);
        if (spawned == true){
            actors.removeAll(getObjects(Player.class));
            actors.removeAll(getObjects(Clock.class));
            actors.remove((Object) getObjects(Player.class).get(0).collisionBox);
        }
        removeObjects(actors);
    }
    public void createFile(int area) throws IOException{
        String prefix = "area_" + area;
        String suffix = ".txt";
        File directoryPath = new File(System.getProperty("user.dir") + "\\temporary storage\\");
        
        File tempFile = File.createTempFile(prefix, suffix, directoryPath);
    }
    public void nextAreaDetect() throws IOException{
        if (inLevel == true){
            int playerX = hero.getX();
            int playerY = hero.getY(); 
            
            if (playerX == worldWidth - 1 && levelArea != amountOfAreas - 1){
                File file = new File(System.getProperty("user.dir") + "\\temporary storage\\area_" + levelArea + ".txt");
                saveGameDataToFile(file);
                if (areaVisited[levelArea] == false){
                    createFile(levelArea);
                    areaVisited[levelArea] = true;
                } else if (areaVisited[levelArea] == true){
                    try{
                        //System.out.println("\nArea Detection Loading Successful");
                        levelArea = levelArea + 1;
                        file = new File(System.getProperty("user.dir") + "\\temporary storage\\area_" + levelArea + ".txt");
                        loadGameData(file);
                        levelArea = levelArea - 1;
                    } catch (Exception e){
                        //System.out.println("\nArea Detection Loading Failed");  
                        System.out.println(e);
                    }
                }
                
                if (levelArea + 1 != mapLevel.length){
                    levelArea++;
                }
                
                eraseAll();
                draw();
                getObjects(Player.class).get(0).collisionBox.setLocation(hero.getImage().getWidth()/2, playerY);
                //System.out.println(levelArea);
            }
            if (playerX == 0 && levelArea != 0){
                File file = new File(System.getProperty("user.dir") + "\\temporary storage\\area_" + levelArea + ".txt");
                saveGameDataToFile(file);
                if (areaVisited[levelArea] == false){
                    createFile(levelArea);
                    areaVisited[levelArea] = true;
                } else if (areaVisited[levelArea] == true){
                    try{
                        //System.out.println("\nArea Detection Loading Successful");
                        levelArea = levelArea - 1;
                        file = new File(System.getProperty("user.dir") + "\\temporary storage\\area_" + levelArea + ".txt");
                        loadGameData(file);
                        levelArea = levelArea + 1;
                    } catch (Exception e){
                        System.out.println("\nArea Detection Loading Failed");  
                        System.out.println(e);
                    }
                }
                
                if (levelArea != 0){
                    levelArea--;
                }
                
                eraseAll();
                draw();
                getObjects(Player.class).get(0).collisionBox.setLocation(1200 - hero.getImage().getWidth()/2, playerY);
                //System.out.println(levelArea);
            }
        }
    }
    
    public void createCollisionBoxes(){
        List<Actor> actors = new ArrayList<Actor>();
        if (getObjects(Player.class).get(0).collisionBox == null){
            for (Object obj : getObjects(Player.class)) {
                actors.add((Actor)obj);
            }
        }
        if (getObjects(Enemy.class).size() != 0){
            if (getObjects(Enemy.class).get(0).collisionBox == null){
                for (Object obj : getObjects(Enemy.class)){
                    actors.add((Actor)obj);
                }
            }
        }
        if (getObjects(Block.class).size() != 0){
            if (getObjects(Block.class).get(0).collisionBox == null){
                for (Object obj : getObjects(Block.class)) {
                    actors.add((Actor)obj);
                }
            }
        }
        for (int i = 0; i < actors.size(); i++){
            Actor actor = (Actor) actors.get(i);
            addObject(new CollisionBox(), actor.getX(), actor.getY());
        }
    }
    
    public void respawnDetection(){
        if (getObjects(Player.class).get(0).health == 0){
            for (int i = 0; i < areaVisited.length; i++){
                areaVisited[i] = false;
            }
            eraseAll();
            levelArea = respawnArea;
            draw();
            
            Object[] objectArray = getObjects(RespawnTile.class).toArray();
            Actor object = (Actor) objectArray[0];
            int respawnTileX = object.getX();
            int respawnTileY = object.getY();
                        
            getObjects(Player.class).get(0).collisionBox.setLocation(respawnTileX, respawnTileY);
            
            getObjects(Player.class).get(0).health = 1;
            deaths++;
        }
    }
}
