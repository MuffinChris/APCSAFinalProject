import static java.lang.Character.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.awt.Image.*;
import java.awt.image.*;
import java.awt.*;
import java.lang.Math;
import java.lang.Iterable;
import java.net.URL;
import java.io.*;
import java.util.Random;
import javax.imageio.*;
import javax.swing.*;
import java.awt.Graphics;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.List;

public class Game extends Canvas implements KeyListener, MouseListener, Runnable {

    //player & background
    private int speed = 2;
    private Player player;
    private Block background;
    private BufferedImage back;

    //blocks
    private int numDirt;
    private int numStone;
    private int numWood;
    private int screenWidth;
    private int screenHeight;
    private Block blockOne;
    private BlockType blockList;
    private BlockType inventoryList;
    private int blockVal;

    //key listener
    private boolean[] keys;

    //mouse listener
    private int xPos;
    private int yPos;
    private String str;
    private MouseEvent mouseEvent;
    private boolean mouseClicked;

    public Game(int width, int height, BlockType blocklist,BlockType inventorylist, Player p, int one, int two, int three) {

        numDirt = one;
        numStone = two;
        numWood = three;
        blockVal = 0;
        screenWidth = width;
        screenHeight = height-23;
        inventoryList = inventorylist;

        setBackground(Color.black);
        keys = new boolean[8];

        player = p;
        background = new Block(0, 0, width, height, speed);

        blockList = blocklist;

        this.addKeyListener(this);
        new Thread(this).start();
        setVisible(true);


        this.addMouseListener(this);
        new Thread(this).start();
        setVisible(true);
    }

    public void update(Graphics window) {
        paint(window);
    }

    public void removeBlock(Block b) {
        player.broadcastMessage("BLOCK REMOVE: " + b.getX() + "," + b.getY());
    }

    public void placeBlock(Block b) {
        player.broadcastMessage("BLOCK PLACE: " + b.getX() + "," + b.getY() + "," + b.getWidth() + "," + b.getHeight() + "," + b.getSpeed() + "," + b.getType());
    }

    public void paint(Graphics window) {

        if (!(player instanceof Player)) {
            return;
        }
        Graphics2D twoDGraph = (Graphics2D)window;

        if(back==null)
            back = (BufferedImage)(createImage(getWidth(),getHeight()));

        Graphics graphToBack = back.createGraphics();
        background.draw(graphToBack);

        Graphics inventoryBox = back.createGraphics();

        if (!(player.getWindow() instanceof Graphics)) {
            player.setWindow(graphToBack);
        }

        inventoryBox.setColor(new Color (245, 245, 220));
        inventoryBox.fillRect(0,0,140,140);

        Graphics clickedBlock = back.createGraphics();
        player.draw(graphToBack);
        blockList = player.getBlockList();
        blockList.drawEmAll(graphToBack);
        BlockType inventoryList = player.getInvList();
        if (!(inventoryList instanceof BlockType)) {
            return;
        }
        numDirt=0;
        for (int i = 0; i < inventoryList.size();i++)
        {
            if (inventoryList.get(i).getType().equals("dirt"))
            {
                numDirt++;
            }
        }

        numStone = 0;
        for (int i = 0; i < inventoryList.size();i++)
        {
            if (inventoryList.get(i).getType().equals("stone"))
            {
                numStone++;
            }
        }
        numWood = 0;
        for (int i = 0; i < inventoryList.size();i++)
        {
            if (inventoryList.get(i).getType().equals("wood"))
            {
                numWood++;
            }
        }
        graphToBack.drawString("INVENTORY", 20, 40);
        if (blockVal == 0) {
            graphToBack.drawString("[1] DIRT x" + numDirt, 20, 70);
            graphToBack.drawString("[2] STONE x" + numStone, 20, 90);
            graphToBack.drawString("[3] WOOD x" + numWood, 20, 110);
        }
        if (blockVal == 1) {
            graphToBack.drawString("<[1] DIRT x" + numDirt + ">", 20, 70);
            graphToBack.drawString("[2] STONE x" + numStone, 20, 90);
            graphToBack.drawString("[3] WOOD x" + numWood, 20, 110);
        }
        if (blockVal == 2) {
            graphToBack.drawString("[1] DIRT x" + numDirt, 20, 70);
            graphToBack.drawString("<[2] STONE x" + numStone + ">", 20, 90);
            graphToBack.drawString("[3] WOOD x" + numWood, 20, 110);
        }
        if (blockVal == 3) {
            graphToBack.drawString("[1] DIRT x" + numDirt, 20, 70);
            graphToBack.drawString("[2] STONE x" + numStone, 20, 90);
            graphToBack.drawString("<[3] WOOD x" + numWood + ">", 20, 110);
        }

        if(keys[0] == true && player.getX() >= 0) {
            player.move("LEFT");
            for (int i = blockList.size()-1; i >= 0; i--) {
                if ( player.getY() <= blockList.get(i).getY()+blockList.get(i).getHeight() &&
                        player.getY()+player.getHeight() >= blockList.get(i).getY() &&
                        player.getX() <= blockList.get(i).getX()+blockList.get(i).getWidth() &&
                        player.getX() >= blockList.get(i).getX()+blockList.get(i).getWidth()-speed-4) {

                    if (blockList.get(i).getType().equals("dirt")) {
                        numDirt++;
                    }
                    else if (blockList.get(i).getType().equals("stone")) {
                        numStone++;
                    }
                    else if (blockList.get(i).getType().equals("wood")) {
                        numWood++;
                    }

                    inventoryList.add(blockList.get(i));
                    removeBlock(blockList.get(i));
                    blockList.remove(blockList.get(i));
                }
            }
        }
        if (keys[1] && player.getX()+player.getWidth() <= screenWidth) {
            player.move("RIGHT");
            for (int i = blockList.size()-1; i > -1; i--) {
                if ( player.getY() <= blockList.get(i).getY()+blockList.get(i).getHeight() &&
                        player.getY()+player.getHeight() >= blockList.get(i).getY() &&
                        player.getX()+player.getWidth() >= blockList.get(i).getX() &&
                        player.getX()+player.getWidth() <= blockList.get(i).getX()+speed+4 ) {

                    if (blockList.get(i).getType().equals("dirt")) {
                        numDirt++;
                    }
                    else if (blockList.get(i).getType().equals("stone")) {
                        numStone++;
                    }
                    else if (blockList.get(i).getType().equals("wood")) {
                        numWood++;
                    }

                    inventoryList.add(blockList.get(i));
                    removeBlock(blockList.get(i));
                    blockList.remove(blockList.get(i));
                }
            }
        }
        if (keys[2] && player.getY() >= 0) {
            player.move("UP");
            for (int i = blockList.size()-1; i > -1; i--) {
                if ( player.getX() <= blockList.get(i).getX()+blockList.get(i).getWidth() &&
                        player.getX()+player.getWidth() >= blockList.get(i).getX() &&
                        player.getY() == blockList.get(i).getY()+blockList.get(i).getHeight()+speed+4) {

                    if (blockList.get(i).getType().equals("dirt")) {
                        numDirt++;
                    }
                    else if (blockList.get(i).getType().equals("stone")) {
                        numStone++;
                    }
                    else if (blockList.get(i).getType().equals("wood")) {
                        numWood++;
                    }

                    inventoryList.add(blockList.get(i));
                    removeBlock(blockList.get(i));
                    blockList.remove(blockList.get(i));
                }
            }
        }
        if (keys[3] && player.getY()+player.getHeight() <= screenHeight){
            player.move("DOWN");
            for (int i = blockList.size()-1; i > -1; i--) {
                if ( player.getX() <= blockList.get(i).getX()+blockList.get(i).getWidth() &&
                        player.getX()+player.getWidth() >= blockList.get(i).getX() &&
                        player.getY()+player.getHeight() == blockList.get(i).getY()-speed-4) {

                    if (blockList.get(i).getType().equals("dirt")) {
                        numDirt++;
                    }
                    else if (blockList.get(i).getType().equals("stone")) {
                        numStone++;
                    }
                    else if (blockList.get(i).getType().equals("wood")) {
                        numWood++;
                    }

                    inventoryList.add(blockList.get(i));
                    removeBlock(blockList.get(i));
                    blockList.remove(blockList.get(i));
                }
            }
        }


        player.drawOthers();
        player.setOne(numDirt);
        player.setTwo(numStone);
        player.setThree(numWood);
        twoDGraph.drawImage(back, null, 0, 0);
    }

    public void mouseClicked(MouseEvent e) {
        if (inventoryList.size() == 0) {
            mouseClicked = false;
        }
        else {
            mouseEvent = e;
            mouseClicked = true;
            String blockType;
            if (inventoryList.size() > 0) {
                if (blockVal == 1 && numDirt > 0) {
                    blockType = "dirt";
                    Block place = new Block(e.getX(), e.getY(), 50, 50, speed, blockType);
                    //blockList.add(place);
                    inventoryList.remove(inventoryList.get(0));
                    numDirt--;
                    placeBlock(place);
                }
                if (blockVal == 2 && numStone > 0) {
                    blockType = "stone";
                    Block place = new Block(e.getX(), e.getY(), 50, 50, speed, blockType);
                    //blockList.add(place);
                    inventoryList.remove(inventoryList.get(0));
                    numStone--;
                    placeBlock(place);
                }
                if (blockVal == 3 && numWood > 0) {
                    blockType = "wood";
                    Block place = new Block(e.getX(), e.getY(), 50, 50, speed, blockType);
                    //blockList.add(place);
                    inventoryList.remove(inventoryList.get(0));
                    numWood--;
                    placeBlock(place);
                }
            }
        }
    }
    /*public void actionPerformed(ActionEvent a)
    {
        if ("click".equals(a.getActionCommand()))
        {
            try (FileOutputStream fos = new FileOutputStream("blockList.data");
                 ObjectOutputStream oos = new ObjectOutputStream(fos))
            {

                oos.writeObject(blockList);
            }catch (IOException e){
                e.printStackTrace();
            }


            try(FileOutputStream fos = new FileOutputStream("inventoryList.data");
                ObjectOutputStream oos = new ObjectOutputStream(fos))
            {
                oos.writeObject(inventoryList);
            }
            catch(IOException e){
                e.printStackTrace();
            }
            try(FileOutputStream fos = new FileOutputStream("player.data");
                ObjectOutputStream oos = new ObjectOutputStream(fos))
            {
                oos.writeObject(player);
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }*/


    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}

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
        if (e.getKeyCode() == KeyEvent.VK_1) {
            keys[4] = true;
            blockVal = 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_2) {
            keys[5] = true;
            blockVal = 2;
        }
        if (e.getKeyCode() == KeyEvent.VK_3) {
            keys[6] = true;
            blockVal = 3;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            keys[7] = true;
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
        if (e.getKeyCode() == KeyEvent.VK_1) {
            keys[4] = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_2) {
            keys[5] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_3) {
            keys[6] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            keys[7] = false;
        }
        repaint();
    }

    public void keyTyped(KeyEvent e) {}

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

