import static java.lang.Character.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.awt.Image.*;
import java.awt.image.*;
import java.awt.*;
import java.lang.Math;
import java.lang.Iterable;

public class Game extends Canvas implements KeyListener, Runnable {

  private Player player;
  private Block background;
  private Block blockOne;
  private BlockType blockList;
  private BlockType inventoryList;

  private int numDirt;
  private int screenWidth;
  private int screenHeight;

  private boolean[] keys;
  private BufferedImage back;
  //private int count = 0;

  public Game(int width, int height) {
    numDirt = 0;
    screenWidth = width; //access width of screen
    screenHeight = height-23; //access height of screen
    blockList = new BlockType();
    inventoryList = new BlockType();
    
    setBackground(Color.black);
    keys = new boolean[4];

    player = new Player(0, height-175, 50, 50, 1);
    background = new Block(0, 0, width, height, 1);
    
    blockOne = new Block(100, 300, 50, 50, 1, "dirt");
    blockList.add(blockOne);

    int xPos = blockOne.getX();
    int yPos = blockOne.getY();
    for (int i = 0; i < 10; i++) {
      xPos = (int)(Math.random()*(screenWidth-20)+1);
      yPos = (int)(Math.random()*(screenHeight-20)+1);
      blockList.add(new Block(xPos, yPos, 50, 50, 1, "dirt"));
    }

    this.addKeyListener(this);
    new Thread(this).start();
    setVisible(true); 
  }

  public void update(Graphics window) {
    paint(window);
  }

  public void paint(Graphics window) {
    Graphics2D twoDGraph = (Graphics2D)window;

    if(back==null) 
      back = (BufferedImage)(createImage(getWidth(),getHeight()));

    Graphics graphToBack = back.createGraphics();
    background.draw(graphToBack);

    Graphics inventoryBox = back.createGraphics();
    inventoryBox.setColor(new Color (34, 139, 34));
    inventoryBox.fillRect(0,0,140,140);

    player.draw(graphToBack);
    blockList.drawEmAll(graphToBack);

    graphToBack.drawString("INVENTORY: ", 20, 40 );
    graphToBack.drawString("DIRT x" + numDirt, 20, 70);
    graphToBack.drawString("WATER x", 20, 90);

    if(keys[0] == true && player.getX() >= 0) {
      player.move("LEFT");
      for (int i = blockList.size()-1; i >= 0; i--) {
        if ( player.getY() <= blockList.get(i).getY()+blockList.get(i).getHeight() && 
             player.getY()+player.getHeight() >= blockList.get(i).getY() &&
             player.getX() == blockList.get(i).getX()+blockList.get(i).getWidth() ) { 
          numDirt++;
          inventoryList.add(blockList.get(i));
          blockList.remove(blockList.get(i));
        }
      }
    }
    if (keys[1] && player.getX()+player.getWidth() <= screenWidth) {
      player.move("RIGHT");
      for (int i = blockList.size()-1; i > -1; i--) {
        if ( player.getY() <= blockList.get(i).getY()+blockList.get(i).getHeight() && 
             player.getY()+player.getHeight() >= blockList.get(i).getY() &&
             player.getX()+player.getWidth() == blockList.get(i).getX() ) { 
          numDirt++;
          inventoryList.add(blockList.get(i));
          blockList.remove(blockList.get(i));
        }
      }
    }
    if (keys[2] && player.getY() >= 0) {
      player.move("UP");
      for (int i = blockList.size()-1; i > -1; i--) {
        if ( player.getX() <= blockList.get(i).getX()+blockList.get(i).getWidth() && 
             player.getX()+player.getWidth() >= blockList.get(i).getX() &&
             player.getY() == blockList.get(i).getY()+blockList.get(i).getHeight() ) { 
          numDirt++;
          inventoryList.add(blockList.get(i));
          blockList.remove(blockList.get(i));
        }
      }
    }
    if (keys[3] && player.getY()+player.getHeight() <= screenHeight){
      player.move("DOWN");
      for (int i = blockList.size()-1; i > -1; i--) {
        if ( player.getX() <= blockList.get(i).getX()+blockList.get(i).getWidth() && 
             player.getX()+player.getWidth() >= blockList.get(i).getX() &&
             player.getY()+player.getHeight() == blockList.get(i).getY() ) { 
          numDirt++;
          inventoryList.add(blockList.get(i));
          blockList.remove(blockList.get(i));
        }
      }
    }

    /*
    if (keys[4] && count <= 5) {
      shots.add(new Ammo(ship.getX()+20, ship.getY()-2-count*2, 10, 4, 1));
      count++;
    }
    else if (count > 5 && keys[4]) {
      count = 0;
      keys[4] = false;
    } 
    */

    twoDGraph.drawImage(back, null, 0, 0);
  }


  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      keys[0] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      keys[1] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_UP) {
      keys[2] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_DOWN) {
      keys[3] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
      keys[4] = true;
    }
    repaint();
  }

  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      keys[0] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_RIGHT)
    {
      keys[1] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_UP) {
      keys[2] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_DOWN) {
      keys[3] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
      keys[4] = false;
    }
    repaint();
  }

  public void keyTyped(KeyEvent e) {
  }

  public void run() {
    try {
      while(true) {
        Thread.currentThread().sleep(5);
        repaint();
      }
    }
    catch(Exception e) {
    }
  }

}

