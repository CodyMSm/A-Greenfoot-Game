import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.Serializable;

/**
 * Write a description of class CollisionBox here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CollisionBox extends Actor implements Serializable
{
    int speed = 4;
    int mass = 10;
    
    int upVelocity = 0;
    int downVelocity = 0;
    int leftVelocity = 0;
    int rightVelocity = 0;
    
    int upForce = 0;
    int downForce = 0;
    int leftForce = 0;
    int rightForce = 0;
    
    int x;
    int y;
    
    int mouseX = 0;
    int mouseY = 0;
    boolean mouseDown = false;
    boolean mouseUsed = false;
    
    long lastAdded = System.currentTimeMillis();
    long lastAdded2 = System.currentTimeMillis();
    
    int gravitationalVelocity = 0;
    
    int distancePrediction = 0;
    
    boolean gravityOn = true;
    boolean inMotion = false;
    boolean outsideForce = false;
    boolean insideForce = false;
    boolean visisble = false;
    
    boolean onGround = false;
    boolean onCeiling = false;
    boolean barrierOnRight = false;
    boolean barrierOnLeft = false;
    boolean boxOnRight = false;
    boolean boxOnLeft = false;
    boolean boxOnBottom = false;
    boolean boxOnTop = false;
    boolean inObject = false;
    
    boolean dangerous = false;
    boolean draggable = false;
    boolean canPush = false;
    
    Actor actor = new Enemy();
    public CollisionBox(){
    }
    public void setBoxSize(Actor actor){
        this.actor = actor;
        if (actor == new Enemy()){
            dangerous = true;
        }
    }
    public void setSpeed(int speed){
        this.speed = speed;
    }
    public void setUpVelocity(int upVelocity){
        this.upVelocity = upVelocity;
    }
    public void setDownVelocity(int downVelocity){
        this.downVelocity = downVelocity;
    }
    public void setLeftVelocity(int leftVelocity){
        this.leftVelocity = leftVelocity;
    }
    public void setRightVelocity(int rightVelocity){
        this.rightVelocity = rightVelocity;
    }
    public void setGravityOn(boolean gravityOn){
        this.gravityOn = gravityOn;
    }
    public void inMotion(boolean inMotion){
        this.inMotion = inMotion;
    }
    public void setVisible(boolean visisble){
        this.visisble = visisble;
    }
    public void setDraggable(boolean draggable){
        this.draggable = draggable;
    }
    public void setOutsideForce(boolean outsideForce){
        this.outsideForce = outsideForce;
    }
    public void setInsideForce(boolean insideForce){
        this.insideForce = insideForce;
    }
    
    public boolean getBarrierOnRight(){
        return barrierOnRight;
    }
    public boolean getBarrierOnLeft(){
        return barrierOnLeft;
    }
    public boolean getOnGround(){
        return onGround;
    }
    public boolean getInObject(){
        return inObject;
    }
    public int getUpVelocity(){
        return this.upVelocity;
    }
    public int getDownVelocity(){
        return this.downVelocity;
    }
    public int getLeftVelocity(){
        return this.leftVelocity;
    }
    public int getRightVelocity(){
        return this.rightVelocity;
    }
    public int getMass(){
        return this.mass;
    }
    
    public void act()
    {
        if (((MyWorld)getWorld()).paused == false) {
            int x = getX();
            int y = getY();
            
            int platformX;
            int platformY;
            int platformImageHeight = getWorld().getObjects(Platform.class).get(0).platformImageHeight;
            int platformImageWidth = getWorld().getObjects(Platform.class).get(0).platformImageWidth;
            
            long curTime  = System.currentTimeMillis();
            
            int edgeX = getX();
            int edgeY = getY(); 
            
            int mouseYDistance = 100;
            int mouseXDistance = 100;
            boolean noObstacles = true;
            // set size
            GreenfootImage image = getImage();
            image.scale(actor.getImage().getWidth(), actor.getImage().getHeight());
            setImage(image);
            
            // Allows this actor to be moved by the mouse
            MouseInfo mouse = Greenfoot.getMouseInfo();
            Object[] platformObstacles = getObjectsInRange(0, Platform.class).toArray();
            if(mouse != null) { // May be null if the mouse is outside of the world bounds
                mouseX = mouse.getX();
                mouseY = mouse.getY();
            }
            if(Greenfoot.mousePressed(this)) {
                mouseDown = true;
            }
            else if(Greenfoot.mouseClicked(null)) {
                mouseDown = false;
            }
            if ((Greenfoot.mouseDragged(this) == true || Greenfoot.mousePressed(this) == true || mouseDown == true) && draggable == true){
                gravityOn = false;
                if (Math.abs(mouseYDistance) > 0 || Math.abs(mouseXDistance) > 0){
                    for (int i = 0; i < 100; i++) move("mouse");
                } else {
                    noObstacles = false;
                }
                setOutsideForce(true);
                mouseUsed = true; 
            } else if (Greenfoot.mouseDragged(this) == false && Greenfoot.mousePressed(this) == false && mouseDown == false){
                if (mouseUsed == true){
                    downVelocity = 0;
                    upVelocity = 0;
                    rightVelocity = 0;
                    leftVelocity = 0;
                    setOutsideForce(true);
                    if (mouseY > y){
                        downVelocity = downVelocity + (mouseY - y)/4;
                    } else if (y > mouseY){
                        upVelocity = upVelocity + (y - mouseY)/4;
                    }
                    if (mouseX > x){
                        rightVelocity = rightVelocity + (mouseX - x)/4;
                    } else if (x > mouseX){
                        leftVelocity = leftVelocity + (x - mouseX)/4;
                    }
                    setOutsideForce(false);
                }
                mouseXDistance = 100;
                mouseYDistance = 100;
                mouseUsed = false;
                gravityOn = true;
            }
            
            
            if (getOneIntersectingObject(Platform.class) != null){
                inObject = true;
            } else {
                inObject = false;
            }
            
            
            // movement
            if (inMotion == true || onGround == false || outsideForce == true || insideForce == true){
                // loses jump momentum
                if (curTime >= lastAdded2 + 100) //1000ms = 1s
                {
                    int i = 1;
                    if (upVelocity > 0){
                        upVelocity = upVelocity - i;
                        i++;
                    }
                }
                // gravity
                if (gravityOn == true){
                    if (getOneIntersectingObject(Platform.class) == null && onGround == false && upVelocity == 0){
                        int i = 1;
                        if (curTime >= lastAdded + 100) //1000ms = 1s
                        {
                            gravitationalVelocity = gravitationalVelocity + i;
                            i++;
                        }
                    } else {
                            gravitationalVelocity = 0;
                    }
                } else {
                    gravitationalVelocity = 0;
                }
                
                if ((onGround == true && outsideForce == true) || (onGround == false) || (insideForce == true)){
                    for (int i = 0; i < upVelocity; i++) move("upVelocity");
                    for (int i = 0; i < downVelocity; i++) move("downVelocity");
                    for (int i = 0; i < leftVelocity; i++) move("leftVelocity");
                    for (int i = 0; i < rightVelocity; i++) move("rightVelocity");
                }
                for (int i = 0; i < gravitationalVelocity; i++) setLocation(getX(), getY() + 1);
            } else {
                upForce = 0;
                downForce = 0;
                leftForce = 0;
                rightForce = 0;
                
                upVelocity = 0;
                downVelocity = 0;
                leftVelocity = 0;
                rightVelocity = 0;
            }
            
            // collision (sets location)
            if (getOneIntersectingObject(Platform.class) != null || getOneIntersectingObject(CollisionBox.class) != null || isAtEdge() == true){
                inObject = true;
                gravitationalVelocity = 0;
                Object[] platformObjectArray = getIntersectingObjects(Platform.class).toArray();
                Object[] boxObjectArray = getObjectsInRange(getImage().getHeight() + (getImage().getHeight()/2), CollisionBox.class).toArray();
                
                // Collision for Platforms
                for (int i = 0; i < platformObjectArray.length; i++){
                    Actor object = (Actor) platformObjectArray[i];
                    platformX = object.getX();
                    platformY = object.getY();
                    
                    edgeX = getX();
                    edgeY = getY();
                    if (x <= platformX && (Math.abs(object.getY() - y) < Math.abs(object.getX() - x))){
                        edgeX = platformX - ((object.getImage().getWidth())/2 + getImage().getWidth()/2);
                        rightVelocity = 0;
                    }
                    if (x >= platformX && (Math.abs(object.getY() - y) < Math.abs(object.getX() - x))){
                        edgeX = platformX + ((object.getImage().getWidth())/2 + getImage().getWidth()/2);
                        leftVelocity = 0;
                    }
                    if (y <= platformY && (Math.abs(object.getY() - y) > Math.abs(object.getX() - x))){
                        edgeY = platformY - ((object.getImage().getHeight())/2 + getImage().getHeight()/2);
                        downVelocity = 0;
                    }
                    if (y >= platformY && (Math.abs(object.getY() - y) > Math.abs(object.getX() - x))){
                        edgeY = platformY + ((object.getImage().getHeight())/2 + getImage().getHeight()/2);
                        upVelocity = 0;
                    }
                    setLocation(edgeX, edgeY);
                    // System.out.println("Setting location... " + i);
                }
                // Collision for Boxes
                for (int i = 0; i < boxObjectArray.length; i++){
                    Actor object = (Actor) boxObjectArray[i];
                    CollisionBox box = (CollisionBox) object;
                    int boxX = object.getX();
                    int boxY = object.getY();
                    
                    edgeX = getX();
                    edgeY = getY();
                    
                
                    //if ((getX() == (boxX + ((object.getImage().getWidth())/2 + getImage().getWidth()/2))) || 
                    //    (x <= boxX && (Math.abs(object.getY() - y) < Math.abs(object.getX() - x)))){
                    //    edgeX = boxX - ((object.getImage().getWidth())/2 + getImage().getWidth()/2);
                    //    rightVelocity = 0;
                    //}
                    //if ((getX() == (boxX - ((object.getImage().getWidth())/2 + getImage().getWidth()/2))) || 
                    //    (x >= boxX && (Math.abs(object.getY() - y) < Math.abs(object.getX() - x)))){
                    //    edgeX = boxX + ((object.getImage().getWidth())/2 + getImage().getWidth()/2);
                    //    leftVelocity = 0;              
                    //}
                    if ((getY() == (boxY + ((object.getImage().getHeight())/2 + getImage().getHeight()/2))) ||
                        (y <= boxY && (Math.abs(object.getY() - y) > Math.abs(object.getX() - x)))){
                        edgeY = boxY - ((object.getImage().getHeight())/2 + getImage().getHeight()/2);
                        downVelocity = 0;
                    }
                    if ((getY() == (boxY - ((object.getImage().getHeight())/2 + getImage().getHeight()/2))) || 
                        (y >= boxY && (Math.abs(object.getY() - y) > Math.abs(object.getX() - x)))){
                        edgeY = boxY + ((object.getImage().getHeight())/2 + getImage().getHeight()/2);
                        upVelocity = 0;
                    }
                    setLocation(edgeX, edgeY);
                    // System.out.println("Setting location... " + i);
                } 
                
                if (x == 0){
                    leftVelocity = 0;
                }
                if (x == 1190){
                    rightVelocity = 0;
                }
                if (y == 0){
                    upVelocity = 0;
                }
                if (y == 799){
                    downVelocity = 0;
                }
                
                inObject = false;
            } else {
                inObject = false;
            }
            
            // detects if the player is on the ground or on a wall
            Object[] objectArray2 = getObjectsInRange(platformImageHeight, Platform.class).toArray();
            Object[] objectArray4 = getObjectsInRange(getImage().getHeight() + (getImage().getHeight()/2), CollisionBox.class).toArray();
            if (objectArray2.length > 0 || objectArray4.length > 0){
                int count1 = 0;
                int count2 = 0;
                int count3 = 0;
                int count4 = 0;
                boolean pushed = false;
                
                // Collision detection for Platforms
                for (int i = 0; i < objectArray2.length; i++){
                    Actor object = (Actor) objectArray2[i];
                    // System.out.println("Object in question: " + object);
                    platformX = object.getX();
                    platformY = object.getY();
                    if ((y == (platformY - ((object.getImage().getHeight())/2 + getImage().getHeight()/2)))){
                        if (gravitationalVelocity == 0){
                            onGround = true;
                            count1++;
                        }
                    }
                    if (count1 == 0){
                        onGround = false;
                    }
                    
                    if (y == (platformY + ((object.getImage().getHeight())/2 + getImage().getHeight()/2))){
                        if (gravitationalVelocity == 0){
                            onCeiling = true;
                            count4++;
                        }
                    }
                    if (count4 == 0){
                        onCeiling = false;
                    }
                    
                    if (x == (platformX - ((object.getImage().getWidth())/2 + getImage().getWidth()/2))){
                        barrierOnRight = true;
                        count2++;
                    }
                    if (count2 == 0){
                        barrierOnRight = false;
                    }
                    
                    if (x == (platformX + ((object.getImage().getWidth())/2 + getImage().getWidth()/2))){
                        barrierOnLeft = true;
                        count3++;
                    }
                    if (count3 == 0){
                        barrierOnLeft = false;
                    }
                    // System.out.println("On Ground? " + onGround);
                }
                
                // Collision detection for CollisionBoxes
                for (int i = 0; i < objectArray4.length; i++){
                    Actor boxObject = (Actor) objectArray4[i];
                    CollisionBox box = (CollisionBox) boxObject;
                    // System.out.println("Object in question: " + object);
                    if (y == (boxObject.getY() - ((boxObject.getImage().getHeight())/2 + getImage().getHeight()/2))){
                        boxOnBottom = true;
                        if (gravitationalVelocity == 0){
                            onGround = true;
                            count1++;
                        }
                    } else {
                        boxOnBottom = false;
                    }
                    if (count1 == 0){
                        onGround = false;
                    }
                    if (boxOnBottom == true && box.upForce > 0){
                        outsideForce = true;
                        upVelocity = box.upForce;
                        upForce = box.upForce;
                    } else if (boxOnBottom == false || box.upForce <= 0){
                        outsideForce = false;
                    }
                    
                    if (y == (boxObject.getY() + ((boxObject.getImage().getHeight())/2 + getImage().getHeight()/2))){
                        if (gravitationalVelocity == 0){
                            boxOnTop = true;
                            onCeiling = true;
                            count4++;
                        }
                    }
                    if (count4 == 0){
                        boxOnTop = false;
                        onCeiling = false;
                    }
                    
                    if (x == (boxObject.getX() - ((boxObject.getImage().getWidth())/2 + getImage().getWidth()/2))){
                        boxOnRight = true;
                    } else {
                        boxOnRight = false;
                    }
                    
                    if (x == (boxObject.getX() + ((boxObject.getImage().getWidth())/2 + getImage().getWidth()/2))){
                        boxOnLeft = true;
                    } else {
                        boxOnLeft = false;
                    }
                    
                    if (boxOnRight == true && box.leftForce > 0){
                        outsideForce = true;
                        leftVelocity = box.leftForce / 2;
                        leftForce = box.leftForce / 2;
                        rightVelocity = 0; 
                        rightForce = 0;
                        // box.setLeftVelocity(box.leftForce);
                    }
                    if (boxOnLeft == true && box.rightForce > 0){
                        outsideForce = true;
                        rightVelocity = box.rightForce / 2; 
                        rightForce = box.rightForce / 2;
                        leftVelocity = 0;
                        leftForce = 0;
                        // box.setRightVelocity(box.rightForce);
                    }

                    if ((boxOnRight == false && boxOnLeft == false) && (box.leftForce > 0 && box.rightForce > 0))
                        outsideForce = false;
                    
                    // System.out.println("On Ground? " + onGround);
                }
                if (objectArray4.length <= 0)
                    outsideForce = false;
            } else {
                onGround = false;
                barrierOnRight = false;
                barrierOnLeft = false;
            }
            
            visibility();
        }
    }
    
    public void visibility(){
        if (visisble == false){
            GreenfootImage picture = new GreenfootImage("Transparent Texture.png");
            picture.scale(actor.getImage().getWidth(), actor.getImage().getHeight());
            setImage(picture);
        } else {
            setImage("Collision Box.png");
            GreenfootImage picture = getImage();
            picture.scale(actor.getImage().getWidth(), actor.getImage().getHeight());
            setImage(picture);
        }
    }
    
    /**
     * Method move:
     * checks for keypresses and moves the robot one unit unless a barrier is present;
     * will be called multiple times when the speed is increased
     */
    public void move(String word)
    {
        int dx = 0, dy = 0;
        switch (word) {
            case "mouse":
                if (mouseY < getY()) dy--;
                if (mouseY > getY()) dy++;
                if (mouseX < getX()) dx--;
                if (mouseX > getX()) dx++;
                break;
            case "upVelocity":
                if (upVelocity > 0) dy--;
                break;
            case "downVelocity":
                if (downVelocity > 0) dy++;
                break;
            case "leftVelocity":
                if (leftVelocity > 0) dx--;
                break;
            case "rightVelocity":
                if (rightVelocity > 0) dx++;
                break;
        }
        
        setLocation(getX() + dx, getY());
        if (getOneIntersectingObject(Platform.class) != null || getOneIntersectingObject(CollisionBox.class) != null) 
        {
           if (dy == 0)
           {
               setLocation(getX(), getY() + 1);
               if (getOneIntersectingObject(Platform.class) == null && getOneIntersectingObject(CollisionBox.class) == null) return;
               setLocation(getX(), getY() - 2);
               if (getOneIntersectingObject(Platform.class) == null && getOneIntersectingObject(CollisionBox.class) == null) return;
               setLocation(getX() - dx, getY() + 1);
               return;
            }
           setLocation(getX() - dx, getY());
        }
        setLocation(getX(), getY() + dy);
        if (getOneIntersectingObject(Platform.class) != null || getOneIntersectingObject(CollisionBox.class) != null)
        {
            if (dx == 0)
            {
                setLocation(getX() + 1, getY());
                if (getOneIntersectingObject(Platform.class) == null && getOneIntersectingObject(CollisionBox.class) == null) return;
                setLocation(getX() - 2, getY());
                if (getOneIntersectingObject(Platform.class) == null && getOneIntersectingObject(CollisionBox.class) == null) return;
                setLocation(getX() + 1, getY() - dy);
                return;
            }
            setLocation(getX(), getY() - dy);
        }
    }
}
