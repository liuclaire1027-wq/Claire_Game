import java.awt.*;

/**
 * Created by chales on 11/6/2017.
 * Edits by mblair on 10/27/2025
 */
public class Meteor {

    //VARIABLE DECLARATION SECTION
    //Here's where you state which variables you are going to use.
    public String name;               //name of the hero
    public int xpos;                  //the x position
    public int ypos;                  //the y position
    public int dx;                    //the speed of the hero in the x direction
    public int dy;                    //the speed of the hero in the y direction
    public int width;                 //the width of the hero image
    public int height;                //the height of the hero image
    public boolean isAlive;
    public Rectangle rect;//a boolean to denote if the hero is alive or dead
    public int health;
    public double successRate;


    //This is a constructor that takes 3 parameters.
    // This allows us to specify the hero's name and position when we build it.
    public Meteor(String pName, int pXpos, int pYpos) {
        name = pName;
        xpos = pXpos;
        ypos = pYpos;
        dx = 2;
        dy = (int)(Math.random()*10);
        width = 100;
        height = 100;
        isAlive = true;
        rect = new Rectangle(xpos, ypos, width, height);

        health = 100;
 
    }


    public void move() {
        //xpos = xpos + dx;
        ypos = ypos + dy;
        rect = new Rectangle(xpos, ypos, width, height);
        if(ypos >= 700){
            ypos = 0;
        }

//        if(xpos > 1000 -width){
//            dx = -dx;
//            xpos +=dx;
//        }
//        else if (xpos < width){
//            dx=-dx;
//        }
//        if(ypos > 700 - height){
//            dy=-dy;
//            ypos += dy;
//        }
//        else if(ypos < height) {
//            dy = -dy;
//        }
    }


}






