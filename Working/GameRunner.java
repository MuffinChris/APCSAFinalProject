import javax.swing.JFrame;
import java.awt.Component;

public class GameRunner extends JFrame {

  private static final int WIDTH = 800;
  private static final int HEIGHT = 600;

  public GameRunner() {
    super("GAME RUNNER");
    setSize(WIDTH,HEIGHT);

    Game theGame = new Game(WIDTH, HEIGHT);
    ((Component)theGame).setFocusable(true);

    getContentPane().add(theGame);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  public static void main(String args[]) {
    GameRunner run = new GameRunner();
  }

}