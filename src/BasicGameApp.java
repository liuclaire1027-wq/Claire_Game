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

    Dino dino;
    Image dinoImage;
    Trex trex;
    Image trexImage;
    Alien[] aliens;
    Image alienImage;
    Fern[] ferns;
    Image fernImage;

    Image meteorImage;
    Meteor[] meteorShower;
    Image forest = Toolkit.getDefaultToolkit().getImage("forest.jpg");
    Image boom = Toolkit.getDefaultToolkit().getImage("boom.png");
    Portal portal1;
    Portal portal2;
    Image portalImage;
    boolean level1;
    boolean level2;
    boolean collision = true;
    boolean level3;

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
        level2 = false;
        pressingKey = false;
        dino = new Dino("dino.png", 600, 500);
        dinoImage = Toolkit.getDefaultToolkit().getImage("dino.png");
        trex = new Trex("trex.png", 300, 500);
        trexImage = Toolkit.getDefaultToolkit().getImage("trex.png");
        portal1 = new Portal("portal.png", 700,600);
        portal2 = new Portal("portal.png", 100,100);
        meteorImage = Toolkit.getDefaultToolkit().getImage("meteor.png");
        meteorShower = new Meteor[6];
        for (int i = 0; i < meteorShower.length; i ++) {
            meteorShower[i] = new Meteor("meteor " + i, (int) (Math.random()*WIDTH) + 100, (int)(Math.random()*100));

        }
        alienImage = Toolkit.getDefaultToolkit().getImage("alien.png");
        aliens = new Alien[10];
        for(int i = 0; i <aliens.length; i++){
            aliens[i] = new Alien("aliens" + i, (int)(Math.random() * WIDTH) + 100, (int)(Math.random()* HEIGHT) + 100);
        }
        fernImage = Toolkit.getDefaultToolkit().getImage("fern.png");
        while(collision == true){
            ferns = new Fern[12];
            for (int i = 0; i < ferns.length; i++) {
                ferns[i] = new Fern("ferns" + i, (int) (Math.random() * WIDTH) + 100, (int) (Math.random() * HEIGHT) + 100);
            }
            collision = false;
            for(int x = 0; x < aliens.length; ++x){
                for(int j = 0; j < ferns.length; ++j){
                    if(ferns[j].xpos == aliens[x].xpos && ferns[j].ypos == aliens[x].ypos){
                        collision = true;
                    }
                }
            }


        }
//        ferns = new Fern[12];
//        for (int i = 0; i < ferns.length; i++){
//            ferns[i] = new Fern("ferns" + i,(int)(Math.random() * WIDTH) + 100, (int)(Math.random()* HEIGHT) + 100 );
//            if(ferns[i].xpos  == aliens[i].xpos && ferns[i].ypos == aliens[i].ypos ){
//                ferns[i].xpos += 20;
//                ferns[i].ypos += 20;
//            }
//        }
        portalImage = Toolkit.getDefaultToolkit().getImage("portal.png");
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


            render();  // paint the graphics
            pause(30); // sleep for 10 ms
        }
    }

    public void moveThings() {
        if(pressingKey){
            dino.move();
            trex.move();
        }
        for(int i = 0; i < meteorShower.length; ++i){
            meteorShower[i].move();
        }


        dinometeorcheckCrash();
        dinotrexcheckCrash();
        portaltrexcheckCrash();
        portaldinocheckCrash();
        dinoferncheckCrash();
        trexferncheckCrash();
    }
    public void portaltrexcheckCrash(){
        if (portal1.rect.intersects(trex.rect)){
            portal1.isAlive = false;
        }else if (portal2.rect.intersects(trex.rect)){
            portal2.isAlive = false;
        }
    }

    public void portaldinocheckCrash(){
        if(portal2.rect.intersects(dino.rect)){
            portal2.isAlive = false;
        } else if(portal1.rect.intersects(dino.rect)){
            portal1.isAlive = false;
        }
    }

    public void dinometeorcheckCrash(){
        for(int i = 0 ; i<meteorShower.length; ++i){
            if (meteorShower[i].rect.intersects(dino.rect) && firstCrash == true) {
                firstCrash = false;
                dino.isAlive = false;
                dino.dx = 0;
                dino.dy = 0;
            }
        }

    }
    public void dinotrexcheckCrash(){
        for(int i = 0; i < meteorShower.length; ++i){
            if (meteorShower[i].rect.intersects(trex.rect) && firstCrash == true) {
                firstCrash = false;
                trex.isAlive = false;
            }
        }
            trex.dx = 0;
            trex.dy = 0;

            if(dino.rect.intersects(trex.rect) == false){
                firstCrash = true;
        }
    }
    int ferncounter = 0;
    public void dinoferncheckCrash(){
        for(int i = 0; i < ferns.length; ++i){
            if(ferns[i].rect.intersects(dino.rect) && firstCrash == true){
                firstCrash = false;
                ferncounter += 1;
            }
        }
    }

    public void trexferncheckCrash(){
        for(int i = 0; i < ferns.length; ++i){
            if(ferns[i].rect.intersects(trex.rect) && firstCrash == true){
                firstCrash = false;
                ferncounter += 1;
            }
        }
    }

    //Paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);
        //g.drawImage(forest,0,0,WIDTH,HEIGHT, null);

        if(level1 == true) {
            g.drawImage(forest,0,0,WIDTH,HEIGHT, null);
            for(int i = 0; i < meteorShower.length; ++i){
                g.drawImage(meteorImage, meteorShower[i].xpos, meteorShower[i].ypos, meteorShower[i].width, meteorShower[i].height, null);;
            }
            g.drawImage(portalImage, portal1.xpos, portal1.ypos, portal1.width, portal1.height, null);
            g.drawImage(portalImage, portal2.xpos, portal2.ypos, portal2.width, portal2.height, null);
            g.drawImage(dinoImage, dino.xpos, dino.ypos, dino.width, dino.height, null);
            g.drawImage(trexImage, trex.xpos, trex.ypos, trex.width, trex.height, null);
        }


        if(dino.isAlive == false){
            dinoImage = boom;
        }

        if(trex.isAlive == false){
            trexImage = boom;
        }

        if(portal1.isAlive == false && portal2.isAlive == false){
            level1 = false;
            level2 = true;
            g.clearRect(0, 0, WIDTH, HEIGHT);
            for(int i = 0; i < meteorShower.length; ++i){
                meteorShower[i] = null;
            }

            g.drawImage(dinoImage,dino.xpos, dino.ypos, dino.width, dino.height,null);
            g.drawImage(trexImage, trex.xpos, trex.ypos, trex.width, trex.height, null);

        }
        if(level2 == true){
            for(int i = 0; i < aliens.length; ++i){
                g.drawImage(alienImage, aliens[i].xpos, aliens[i]. ypos, aliens[i].width, aliens[i].height, null);
            }
            for(int i = 0; i < ferns.length; ++i){
                g.drawImage(fernImage, ferns[i]. xpos, ferns[i].ypos, ferns[i].width, ferns[i].height, null);
            }
            g.drawImage(dinoImage, dino.xpos, dino.ypos, dino.width, dino.height, null);
            g.drawImage(trexImage, trex.xpos, trex.ypos, trex.width, trex.height, null);

        }
        if(ferncounter == ferns.length){
            level1 = false;
            level2 = false;
            level3 = true;
            g.clearRect(0, 0, WIDTH, HEIGHT);
            for(int i = 0; i < ferns.length; ++i){
                ferns[i] = null;
            }
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
            trex.dy = -5;
            trex.dx = 0;
            if(trex.isAlive == false){
                trex.dy = 0;
                trex.dx = 0;
            }
        } else if (e.getKeyCode() == 37) {
            trex.dx = -10;
            trex.dy = 0;
            if(trex.isAlive == false){
                trex.dy = 0;
                trex.dx = 0;
            }
        } else if(e.getKeyCode() == 39){
            trex.dx = 10;
            trex.dy = 0;
            if(trex.isAlive == false){
                trex.dy = 0;
                trex.dx = 0;
            }
        } else if(e.getKeyCode() == 40){
            trex.dy = 10;
            trex.dx = 0;
            if(trex.isAlive == false){
                trex.dy = 0;
                trex.dx = 0;
            }
        } else if(e.getKeyCode() == 87){
           dino.dy = -10;
           dino.dx = 0;
           if(dino.isAlive == false){
               dino.dy = 0;
               dino.dx = 0;
           }
        } else if(e.getKeyCode() == 65){
            dino.dx = -10;
            dino.dy = 0;
            if(dino.isAlive == false){
                dino.dy = 0;
                dino.dx = 0;
            }
        } else if(e.getKeyCode() == 68){
            dino.dx = 10;
            dino.dy = 0;
            if(dino.isAlive == false){
                dino.dy = 0;
                dino.dx = 0;
            }
        } else if (e.getKeyCode() == 83){
            dino.dy = 10;
            dino.dx = 0;
            if(dino.isAlive == false){
                dino.dy = 0;
                dino.dx = 0;
            }

        }


        trex.rect = new Rectangle(trex.xpos, trex.ypos, trex.width, trex.height);
        dino.rect = new Rectangle(dino.xpos, dino.ypos, dino.width, dino.height);

    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressingKey = false;
        if(e.getKeyCode() == 38){
            trex.dy = 0;
            trex.dx = 0;
        } else if (e.getKeyCode() == 37) {
            trex.dx = 0;
            trex.dy = 0;
        } else if(e.getKeyCode() == 39){
            trex.dx = 0;
            trex.dy = 0;
        } else if(e.getKeyCode() == 40){
            trex.dy = 0;
            trex.dx = 0;
        } else if(e.getKeyCode() == 87){
            dino.dy = 0;
            dino.dx = 0;
        } else if(e.getKeyCode() == 65){
            dino.dx = 0;
            dino.dy = 0;
        } else if(e.getKeyCode() == 68){
            dino.dx = 0;
            dino.dy = 0;
        } else if (e.getKeyCode() == 83){
            dino.dy = 0;
            dino.dx = 0;

        }
        trex.rect = new Rectangle(trex.xpos, trex.ypos, trex.width, trex.height);
        dino.rect = new Rectangle(dino.xpos, dino.ypos, dino.width, dino.height);


    }
}
