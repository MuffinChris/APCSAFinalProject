import javax.swing.*;
import java.awt.Component;
import java.awt.event.WindowEvent;

public class GameRunner extends JFrame implements Runnable {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    private static BlockType blockList;
    private static BlockType inventoryList;
    private static Player player;

    public static void main(String[] args) {
        GameRunner game = new GameRunner();
    }

    public void setBlockList(BlockType bl) {
        blockList = bl;
    }

    public void setInventoryList(BlockType in)
    {
        inventoryList = in;
    }

    public void setPlayer(Player p)
    {
        player = p;
    }


    public GameRunner() {
        super("Terraria Java");
        setSize(WIDTH, HEIGHT);

        JTextArea textArea = new JTextArea();
        //TerrariaGame TGame = new TerrariaGame();
        //((Component)TGame).setFocusable(true);
        //Thread world = new Thread(TGame);
        //world.start();

        getContentPane().add(textArea);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH,HEIGHT);
    /*Thread t = new Thread(this);
    t.start();*/
        //client = new UserClient();
        player = new Player(0, HEIGHT-175-23, 50, 50, 2, "LEFT");
        while (player.getBlockList() == null) {
            System.out.println("Connecting...");
            try {
                Thread.sleep(1000);
            } catch (Exception e) {

            }
        }
        blockList = player.getBlockList();
        Game theGame = new Game(WIDTH, HEIGHT, blockList,inventoryList, player);
        ((Component)theGame).setFocusable(true);

        getContentPane().add(theGame);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setState(JFrame.NORMAL);

    }

    @Override
    public void run() {
        player = new Player();
    }

    public void finalize() {
        player.broadcastMessage("CLIENT CLOSED: " + player.getName());
    }

}
