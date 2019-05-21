package network;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextArea;

public class Server {

	public static final int port = 4321;
	private ServerSocket server;
	private Socket client;
	private JTextArea textArea;
	private int currentId = 0;
	private InetAddress address;
	
	public Server(JTextArea textArea) {
		this.textArea = textArea;
	}
	
	public ServerSocket getServer() {
		return server;
	}
	
	public Socket getClient() {
		return client;
	}
	
	public InetAddress getAddress() {
		return address;
	}
	
	public void listen() {
		currentId++;
		try {
			System.out.println("Listening for clients.");
			server = new ServerSocket(port);
			address = server.getInetAddress();
		} catch (Exception e) {
			System.out.println("Listening failed on Port " + port);
			System.exit(-1);
			//e.printStackTrace();
		}
		while (true) {
			ClientThread w;
			try {
				w = new ClientThread(new Client(server.accept(), currentId), textArea);
				Thread thread = new Thread(w);
				thread.start();
				System.out.println(">> Created new Client Thread");
			} catch (Exception e) {
				System.out.println("ClientThread could not be initialized on " + port);
				System.exit(-1);
			}
		}
	}
	
}
