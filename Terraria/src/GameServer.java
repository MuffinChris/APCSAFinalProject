import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
public class GameServer implements Runnable,Serializable {

    private Server server;
    private static BlockType blockList;
	private static BlockType inventoryList;
	private static Player player;
        private static Block blockOne;

    public void run() {
        JTextArea textarea = new JTextArea();
        server = new Server(textarea, blockList,inventoryList, player);
        server.listen();
    }

    public static void main(String args[]) {
//        blockList = new BlockType();

        int screenWidth = GameRunner.WIDTH;
        int screenHeight = GameRunner.HEIGHT;
        int speed = 2;
	 int choice;
                 File tmp = new File("blockList.data");

        if (tmp.exists())
        {
                JFrame frame = new JFrame();
                String[] options = new String[2];
                options[0] = new String("Yes");
                options[1]= new String("No");
                 choice =
JOptionPane.showOptionDialog(frame.getContentPane(),"Reload Data from Previous Game?","Resume Game",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null, null,null);
//              System.out.println(choice);
        }
                else
                {
                        choice =1;
                }
      if (tmp.exists()&&choice ==0)

         {
                try (FileInputStream fis = new FileInputStream("blockList.data");
                ObjectInputStream ois = new ObjectInputStream(fis)) {
                blockList = (BlockType)ois.readObject();
                for (int i = 0; i < blockList.size();i++)
                {
                        Block block = new Block(blockList.get(i).getX(), blockList.get(i).getY(), 50,50,speed,blockList.get(i).getType());
                        blockList.set(i, block);
                }
        } catch (IOException| ClassNotFoundException e){
                e.printStackTrace();
        }
        }

        else
        {
                blockList = new BlockType();
                blockOne = new Block(100, 300, 50, 50, speed, "dirt");
                 blockList.add(blockOne);

           int xPos = blockOne.getX();
            int yPos = blockOne.getY();

		  for (int i = 0; i < 2; i++) {
            xPos = (int)(Math.random()*(screenWidth-20)+1);
            yPos = (int)(Math.random()*(screenHeight-20)+1);
            blockList.add(new Block(xPos, yPos, 50, 50, speed, "dirt"));
        }

        for (int i = 0; i < 3; i++) {
            xPos = (int)(Math.random()*(screenWidth-20)+1);
            yPos = (int)(Math.random()*(screenHeight-20)+1);
            blockList.add(new Block(xPos, yPos, 50, 50, speed, "stone"));
        }

        for (int i = 0; i < 3; i++) {
            xPos = (int)(Math.random()*(screenWidth-20)+1);
            yPos = (int)(Math.random()*(screenHeight-20)+1);
            blockList.add(new Block(xPos, yPos, 50, 50, speed, "wood"));
        }

          }


        tmp = new File("inventoryList.data");
        if (tmp.exists()&&choice ==0)
        {
                try (FileInputStream fis = new FileInputStream("inventoryList.data");
                ObjectInputStream ois = new ObjectInputStream(fis)) {
                inventoryList = (BlockType)ois.readObject();
        } catch (IOException| ClassNotFoundException e){
                e.printStackTrace();
        }
        }
        else
        {
                inventoryList = new BlockType();
        }
/*	
	tmp = new File("player.data");
        if (tmp.exists()&&choice ==0)
        {
                try (FileInputStream fis = new FileInputStream("player.data");
                ObjectInputStream ois = new ObjectInputStream(fis)) {
                player = (Player)ois.readObject();
                player = new Player(player.getX(),player.getY(),player.getWidth(),player.getHeight(),speed,player.getDirection(),null);
        } catch (IOException| ClassNotFoundException e){
                e.printStackTrace();
        }
        }
        else
        {
                 player = new Player(0, screenHeight-175, 50, 50, speed,"LEFT",null);
        }

	/*
        Block blockOne = new Block(100, 300, 50, 50, speed, "dirt");
        blockList.add(blockOne);

        int xPos = blockOne.getX();
        int yPos = blockOne.getY();

        for (int i = 0; i < 2; i++) {
            xPos = (int)(Math.random()*(screenWidth-20)+1);
            yPos = (int)(Math.random()*(screenHeight-20)+1);
            blockList.add(new Block(xPos, yPos, 50, 50, speed, "dirt"));
        }

        for (int i = 0; i < 3; i++) {
            xPos = (int)(Math.random()*(screenWidth-20)+1);
            yPos = (int)(Math.random()*(screenHeight-20)+1);
            blockList.add(new Block(xPos, yPos, 50, 50, speed, "stone"));
        }

        for (int i = 0; i < 3; i++) {
            xPos = (int)(Math.random()*(screenWidth-20)+1);
            yPos = (int)(Math.random()*(screenHeight-20)+1);
            blockList.add(new Block(xPos, yPos, 50, 50, speed, "wood"));
*/    
   // }
        GameServer run = new GameServer();
        //Game run = new Game();
        Thread thread = new Thread(run);
        thread.start();
    }

}
