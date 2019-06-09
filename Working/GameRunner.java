import javax.swing.JFrame;
import java.awt.Component;
import javax.swing.*;
public class GameRunner extends JFrame {

  private static final int WIDTH = 800;
  private static final int HEIGHT = 600;

  public GameRunner() {
    super("GAME RUNNER");
    setSize(WIDTH,HEIGHT);

    Game theGame = new Game(WIDTH, HEIGHT);
    ((Component)theGame).setFocusable(true);

   getContentPane().add(theGame);
	setVisible(true);
/*

	JPanel panel = new JPanel();
	  JButton b = new JButton();
        b.setBounds(100,100,140,40);
	panel.add(b);
       getContentPane().add(panel);
	setVisible(true);
   	getContentPane().add(theGame);
	     setVisible(true);
*/
	  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }

  public static void main(String args[]) {
    GameRunner run = new GameRunner();
  }

}
