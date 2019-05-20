package network;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static final int port = 25565;
	private ServerSocket server;
	private Socket client;
	
	public ServerSocket getServer() {
		return server;
	}
	
	public Socket getClient() {
		return client;
	}
	
	public void listen() {
		try {
			server = new ServerSocket(25565);
		} catch (Exception e) {
			System.out.println("Listening failed on Port " + port);
			System.exit(-1);
			//e.printStackTrace();
		}
		
		try {
			client = server.accept();
		} catch (Exception e) {
			System.out.println("Client could not be initialized on " + port);
			System.exit(-1);
		}
	}
	
}
