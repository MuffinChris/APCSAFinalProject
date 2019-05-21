package graphics;

import java.awt.Graphics;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import network.Client;
import network.ClientThread;
import network.Server;

public class Game extends JFrame implements Runnable {

	public static final int WIDTH = 1600;
	public static final int HEIGHT = 900;
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
