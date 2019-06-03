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
        Block blockOne = new Block(100, 300, 50, 50, 2, "dirt");
        blockList = blockList;
        blockList.add(blockOne);

        int xPos = blockOne.getX();
        int yPos = blockOne.getY();
        for (int i = 0; i < 10; i++) {
            xPos = (int)(Math.random()*(GameRunner.WIDTH-20)+1);
            yPos = (int)(Math.random()*(GameRunner.HEIGHT-20)+1);
            blockList.add(new Block(xPos, yPos, 50, 50, 2, "dirt"));
        }
        GameServer run = new GameServer();
        //Game run = new Game();
        Thread thread = new Thread(run);
        thread.start();
    }

}
