package com.cauldron.bodyconquest;

import com.cauldron.bodyconquest.gamestates.GameStateManager;

public class Game implements Runnable {

    // Dimensions
//    public static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
//    public static final int SCREEN_WIDTH = gd.getDisplayMode().getWidth();
//    public static final int SCREEN_HEIGHT = gd.getDisplayMode().getHeight();
//    public static final int WIDTH = 320;
//    public static final int HEIGHT = 256;
//    public static final int SCALE = 3; 	// Scale allows me to change the size of the game and game window easily

    // Fullscreen
    //private int fsm;

    // Game thread
    private Thread thread;
    private boolean running;
    private int FPS = 60; // The number of frames the game will load per second
    private long targetTime = 1000 / FPS;

    // Game state manager
    private GameStateManager gsm;

    // Constructor
  public Game(){}
//    public GamePanel(int fsm) {
//      super();
//      this.fsm = fsm;
//      setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
//      setFocusable(true);
//      requestFocus();
//    }
//
//    public void addNotify() {
//      super.addNotify();
//      if (thread == null) {
//        thread = new Thread(this);
//        addKeyListener(this);
//        thread.start();
//      }
//    }

    private void init() { // Sets the image and image sizes

      //image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
      //border = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
      //g = (Graphics2D) image.getGraphics();

      //g.fillRect(0, 0, (SCREEN_WIDTH - (WIDTH * SCALE)) / 2, SCREEN_HEIGHT);

      running = true;

      gsm = new GameStateManager();
    }

    public void run() { // What initially occurs when the game runs from frame to frame

      init();

      long start;
      long elapsed;
      long wait;

      // Game loop
      while (running) {

        start = System.nanoTime();

        update();
        //draw();
        //drawToScreen();

        elapsed = System.nanoTime() - start;
        // This makes it so that once the target time has passed, then the code will continue running
        wait = targetTime - elapsed / 1000000;
        if (wait < 0)
          wait = 5;

        try {
          Thread.sleep(wait);
        } catch (Exception e) {
          e.printStackTrace();
        }

      }

    }

    private void update() {
      gsm.update();
    }

//    private void draw() {
//      gsm.draw(g);
//    }
//


}
