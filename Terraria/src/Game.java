import javax.swing.*;
import java.awt.*;

public class Game extends JFrame implements Runnable {

	public static final int WIDTH = 1200;
	public static final int HEIGHT = 800;
	private Server server;
	
	public Game() {
		super("Terraria Java");
		setSize(WIDTH, HEIGHT);
		
		JTextArea textArea = new JTextArea();
		TerrariaGame TGame = new TerrariaGame();
		((Component)TGame).setFocusable(true);
		Thread world = new Thread(TGame);
		world.start();

		getContentPane().add(textArea);
		getContentPane().add(TGame);

		server = new Server(textArea);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		Game run = new Game();
		Thread thread = new Thread(run);
		thread.start();
	}
	
	@Override
	public void run() {
		server.listen();
	}
	
}
