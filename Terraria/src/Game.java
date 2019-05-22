import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Game extends JFrame implements Runnable {

	public static final int WIDTH = 600;
	public static final int HEIGHT = 400;
	private Server server;
	
	public Game() {
		super("Terraria Java");
		setSize(WIDTH, HEIGHT);
		
		JTextArea textArea = new JTextArea();
		
		getContentPane().add(textArea);
		
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