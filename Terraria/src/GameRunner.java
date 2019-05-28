import javax.swing.*;
import java.awt.Component;

public class GameRunner extends JFrame implements Runnable {

  public static void main(String args[]) {
    GameRunner run = new GameRunner();
    //Game run = new Game();
    Thread thread = new Thread(run);
    thread.start();
  }

  public static final int WIDTH = 1200;
  public static final int HEIGHT = 800;
  private Server server;

  public GameRunner() {
    super("Terraria Java");
    setSize(WIDTH, HEIGHT);

    JTextArea textArea = new JTextArea();
    //TerrariaGame TGame = new TerrariaGame();
    //((Component)TGame).setFocusable(true);
    //Thread world = new Thread(TGame);
    //world.start();

    getContentPane().add(textArea);
    //getContentPane().add(TGame);

    server = new Server(textArea);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(WIDTH,HEIGHT);

    Game theGame = new Game(WIDTH, HEIGHT);
    ((Component)theGame).setFocusable(true);

    getContentPane().add(theGame);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

  }

  public void run() {
    server.listen();
  }

}