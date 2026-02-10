//Basic Game Application
// Basic Object, Image, Movement
// Threaded

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

//*******************************************************************************

public class BasicGameApp implements Runnable, KeyListener {

    //Variable Definition Section
    //Declare the variables used in the program
    //You can set their initial values too

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;

    Alien alien;
    Image alienImage;
    Cat cat;
    Image catImage;
    Meteor meteor1;
    Meteor meteor2;
    Meteor meteor3;
    Meteor meteor4;
    Meteor meteor5;
    Image metorImage;
    Image space = Toolkit.getDefaultToolkit().getImage("space.png");
    Image boom = Toolkit.getDefaultToolkit().getImage("boom.png");
    Portal portal1;
    Portal portal2;
    Image portalImage;
    boolean level1;
    boolean level2;

    public boolean firstCrash;
    public boolean pressingKey;


    // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
        new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method
    }


    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.
    public BasicGameApp() { // BasicGameApp constructor

        setUpGraphics();
        firstCrash = true;
        level1 = true;
        pressingKey = false;
        alien = new Alien("alien.png", 600, 200, 0.75);
        alienImage = Toolkit.getDefaultToolkit().getImage("alien.png");
//        cat = new Cat("cat.png", 300, 500, 0.25);
//        catImage = Toolkit.getDefaultToolkit().getImage("cat.png");
        portal1 = new Portal("portal.png", 700,600,0.5);
        portal2 = new Portal("portal.png", 100,100,0.5);
        meteor1 = new Meteor("meteor.png",100, 100, 0.5);
        meteor2 = new Meteor("meteor.png",600, 600, 0.5);
        meteor3 = new Meteor("meteor.png",500, 300, 0.5);
        meteor4 = new Meteor("meteor.png",200, 500, 0.5);
        meteor5 = new Meteor("meteor.png",300, 200, 0.5);
        meteor5.dx = -meteor5.dx;
        meteor5.dy = -meteor5.dy;
        meteor2.dx = -meteor2.dx;
        meteor2.dy = -meteor2.dy;
        portalImage = Toolkit.getDefaultToolkit().getImage("portal.png");
        metorImage = Toolkit.getDefaultToolkit().getImage("meteor.png");

        cat = new Cat("cat.png",200, 200, 0.5);
        for(int i = 0; i < 10; i++) {
            Cat cat = new Cat("cat.png", 500, 200, 0.75);
        }
        catImage = Toolkit.getDefaultToolkit().getImage("cat.png");

        run();


    } // end BasicGameApp constructor


//*******************************************************************************
//User Method Section
// put your code to do things here.

    // main thread
    // this is the code that plays the game after you set things up
    public void run() {
        //for the moment we will loop things forever.
        while (true) {
            moveThings();//move all the game objects
//            if(astro.isAlive == false){
//                astro.width += 10;
//                astro.height += 10;
//            }

            render();  // paint the graphics
            pause(30); // sleep for 10 ms
        }
    }

    public void moveThings() {
        if(pressingKey){
            alien.move();
        }
        meteor1.move();
        meteor2.move();
        meteor3.move();
        meteor4.move();
        meteor5.move();
        //cat.wrap();
        if(pressingKey){
            for(int x = 0; x < 10; ++x) {
                cat.move();
            }
        }

        alienmeteorcheckCrash();
        aliencatcheckCrash();
        portalcatcheckCrash();
        portalaliencheckCrash();
    }
    public void portalcatcheckCrash(){
        if (portal1.rect.intersects(cat.rect)){
            portal1.isAlive = false;
        }else if (portal2.rect.intersects(cat.rect)){
            portal2.isAlive = false;
        }
    }

    public void portalaliencheckCrash(){
        if(portal2.rect.intersects(alien.rect)){
            portal2.isAlive = false;
        } else if(portal1.rect.intersects(alien.rect)){
            portal1.isAlive = false;
        }
    }

    public void alienmeteorcheckCrash(){
        if (meteor1.rect.intersects(alien.rect) || meteor2.rect.intersects(alien.rect) || meteor3.rect.intersects(alien.rect) || meteor4.rect.intersects(alien.rect) || meteor5.rect.intersects(alien.rect) && firstCrash == true){
            firstCrash = false;
            alien.isAlive = false;
            alien.dx = 0;
            alien.dy = 0;
            if(meteor1.rect.intersects(alien.rect)) {
                meteor1.dx = -meteor1.dx;
                meteor1.dy = -meteor1.dy;
            } else if(meteor2.rect.intersects(alien.rect)) {
                meteor2.dx = -meteor2.dx;
                meteor2.dy = -meteor2.dy;
            } else if(meteor3.rect.intersects(alien.rect)) {
                meteor3.dx = -meteor3.dx;
                meteor3.dy = -meteor3.dy;
            } else if(meteor4.rect.intersects(alien.rect)) {
                meteor4.dx = -meteor4.dx;
                meteor4.dy = -meteor4.dy;
            } else if(meteor5.rect.intersects(alien.rect)) {
                meteor5.dx = -meteor5.dx;
                meteor5.dy = -meteor5.dy;
            }
//            meteor1.height += 10;
//            meteor1.width +=10;
//            alien.health -=5;

        }
        if(alien.rect.intersects(meteor1.rect) == false){
            firstCrash = true;
        }
    }
    public void aliencatcheckCrash(){
        if (meteor1.rect.intersects(cat.rect) || meteor2.rect.intersects(cat.rect) || meteor3.rect.intersects(cat.rect) || meteor4.rect.intersects(cat.rect) || meteor5.rect.intersects(cat.rect) && firstCrash == true) {
            firstCrash = false;
            cat.isAlive = false;
            if (meteor1.rect.intersects(cat.rect)){
                meteor1.dx = -meteor1.dx;
                meteor1.dy = -meteor1.dy;
            } else if (meteor2.rect.intersects(cat.rect)) {
                meteor2.dx = -meteor2.dx;
                meteor2.dy = -meteor2.dy;
            } else if (meteor3.rect.intersects(cat.rect)){
                meteor3.dx = -meteor3.dx;
                meteor3.dy = -meteor3.dy;
            } else if (meteor4.rect.intersects(cat.rect)) {
                meteor4.dx = -meteor4.dx;
                meteor4.dy = -meteor4.dy;
            } else if (meteor5.rect.intersects(cat.rect)) {
                meteor5.dx = -meteor5.dx;
                meteor5.dy = -meteor5.dy;
            }
            cat.dx = 0;
            cat.dy = 0;
//            meteor1.height += 10;
//            meteor1.width +=10;
//            alien.health -=5;

        }
        if(alien.rect.intersects(cat.rect) == false){
            firstCrash = true;
        }
    }

    //Paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.drawImage(space,0,0,WIDTH,HEIGHT, null);

        //draw the image
//        if(astro.width < 1000) {

//        g.setColor(new Color(250,0,0));
//        g.fillRect(800,20,15,100);
//        g.setColor(new Color(0,200,0));
//        g.fillRect(800,20+(100-alien.health),15,alien.health);


        if(level1 == true) {
            g.drawImage(metorImage, meteor1.xpos, meteor1.ypos, meteor1.width, meteor1.height, null);
            g.drawImage(metorImage, meteor2.xpos, meteor2.ypos, meteor2.width, meteor2.height, null);
            g.drawImage(metorImage, meteor3.xpos, meteor3.ypos, meteor3.width, meteor3.height, null);
            g.drawImage(metorImage, meteor4.xpos, meteor4.ypos, meteor4.width, meteor4.height, null);
            g.drawImage(metorImage, meteor5.xpos, meteor5.ypos, meteor5.width, meteor5.height, null);
            g.drawImage(portalImage, portal1.xpos, portal1.ypos, portal1.width, portal1.height, null);
            g.drawImage(portalImage, portal2.xpos, portal2.ypos, portal2.width, portal2.height, null);
            g.drawImage(alienImage, alien.xpos, alien.ypos, alien.width, alien.height, null);
            g.drawImage(catImage, cat.xpos, cat.ypos, cat.width, cat.height, null);
        }
        if(alien.isAlive == false){
            alienImage = boom;
        }

        if(cat.isAlive == false){
            catImage = boom;
        }

        if(portal1.isAlive == false && portal2.isAlive == false){
            level1 = false;
            level2 = true;
            g.clearRect(0, 0, WIDTH, HEIGHT);
            meteor1 = null;
            meteor2 = null;
            meteor3 = null;
            meteor4 = null;
            meteor5 = null;
            g.drawImage(alienImage,alien.xpos, alien.ypos, alien.width, alien.height,null);
            g.drawImage(catImage, cat.xpos, cat.ypos, cat.width, cat.height, null);

        }

        g.dispose();
        bufferStrategy.show();
    }

    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time ) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    //Graphics setup method
    private void setUpGraphics() {
        frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        canvas.addKeyListener(this);
        System.out.println("DONE graphic setup");
    }

    @Override
    public void keyTyped(KeyEvent e) {


    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        pressingKey = true;
        //while(pressingKey){
        if(e.getKeyCode() == 38){
            cat.dy = -10;
            cat.dx = 0;
            if(cat.isAlive == false){
                cat.dy = 0;
                cat.dx = 0;
            }
        } else if (e.getKeyCode() == 37) {
            cat.dx = -10;
            cat.dy = 0;
            if(cat.isAlive == false){
                cat.dy = 0;
                cat.dx = 0;
            }
        } else if(e.getKeyCode() == 39){
            cat.dx = 10;
            cat.dy = 0;
            if(cat.isAlive == false){
                cat.dy = 0;
                cat.dx = 0;
            }
        } else if(e.getKeyCode() == 40){
            cat.dy = 10;
            cat.dx = 0;
            if(cat.isAlive == false){
                cat.dy = 0;
                cat.dx = 0;
            }
        } else if(e.getKeyCode() == 87){
           alien.dy = -10;
           alien.dx = 0;
           if(alien.isAlive == false){
               alien.dy = 0;
               alien.dx = 0;
           }
        } else if(e.getKeyCode() == 65){
            alien.dx = -10;
            alien.dy = 0;
            if(alien.isAlive == false){
                alien.dy = 0;
                alien.dx = 0;
            }
        } else if(e.getKeyCode() == 68){
            alien.dx = 10;
            alien.dy = 0;
            if(alien.isAlive == false){
                alien.dy = 0;
                alien.dx = 0;
            }
        } else if (e.getKeyCode() == 83){
            alien.dy = 10;
            alien.dx = 0;
            if(alien.isAlive == false){
                alien.dy = 0;
                alien.dx = 0;
            }

        }


        cat.rect = new Rectangle(cat.xpos, cat.ypos, cat.width, cat.height);
        alien.rect = new Rectangle(alien.xpos, alien.ypos, alien.width, alien.height);

    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressingKey = false;
        if(e.getKeyCode() == 38){
            cat.dy = 0;
            cat.dx = 0;
        } else if (e.getKeyCode() == 37) {
            cat.dx = 0;
            cat.dy = 0;
        } else if(e.getKeyCode() == 39){
            cat.dx = 0;
            cat.dy = 0;
        } else if(e.getKeyCode() == 40){
            cat.dy = 0;
            cat.dx = 0;
        } else if(e.getKeyCode() == 87){
            alien.dy = 0;
            alien.dx = 0;
        } else if(e.getKeyCode() == 65){
            alien.dx = 0;
            alien.dy = 0;
        } else if(e.getKeyCode() == 68){
            alien.dx = 0;
            alien.dy = 0;
        } else if (e.getKeyCode() == 83){
            alien.dy = 0;
            alien.dx = 0;

        }


        cat.rect = new Rectangle(cat.xpos, cat.ypos, cat.width, cat.height);
        alien.rect = new Rectangle(alien.xpos, alien.ypos, alien.width, alien.height);


    }
}
