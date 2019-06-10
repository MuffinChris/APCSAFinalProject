import javax.swing.*;

public class GameServer implements Runnable {

    private Server server;
    private static BlockType blockList;

    public void run() {
        JTextArea textarea = new JTextArea();
        server = new Server(textarea, blockList);
        server.listen();
    }

    public static void main(String args[]) {
        blockList = new BlockType();
        int screenWidth = GameRunner.WIDTH;
        int screenHeight = GameRunner.HEIGHT;
        int speed = 2;
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
        }
        GameServer run = new GameServer();
        //Game run = new Game();
        Thread thread = new Thread(run);
        thread.start();
    }

}
