import javax.swing.*;
import java.awt.Component;
import java.awt.event.WindowEvent;

public class GameRunner extends JFrame implements Runnable {

  public static final int WIDTH = 1200;
  public static final int HEIGHT = 800;
  private UserClient client;

  public UserClient getClient() {
    return client;
  }

  public static void main(String[] args) {
    GameRunner game = new GameRunner();
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

    Game theGame = new Game(WIDTH, HEIGHT);
    ((Component)theGame).setFocusable(true);

    getContentPane().add(theGame);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    setState(JFrame.ICONIFIED);
    /*Thread t = new Thread(this);
    t.start();*/
    client = new UserClient();
    theGame.setClient(client);
    setState(JFrame.NORMAL);

  }

  @Override
  public void run() {
    client = new UserClient();
  }

  public void finalize() {
    client.broadcastMessage("CLIENT CLOSED: " + client.getName());
  }

}