import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.swing.JTextArea;

public class ClientThread implements Runnable {

	private Client client;
	private JTextArea text;
	private boolean open;
	private String name;
	private Server server;
	
	public ClientThread (Client client, JTextArea text, Server server, String name) {
		this.client = client;
		this.text = text;
		open = true;
		this.name = name;
		this.server = server;
	}
	
	public Client getClient() {
		return client;
	}

	public boolean isOpen() {
		return open;
	}
	
	@Override
	public void run() {
		String s;
		while (open) {
			BufferedReader input = null;
			PrintWriter output = null;
			try {
				input = new BufferedReader(new InputStreamReader(client.getSocket().getInputStream()));
				output = new PrintWriter(client.getSocket().getOutputStream(), true);
				int id;
				s = input.readLine();
				if (s.contains("CLOSE CLIENT ID")) {
					id = Integer.valueOf(s.replace("CLOSE CLIENT ID: ", ""));
					if (getClient().getID() == id) {
						getClient().getSocket().close();
						open = false;
					}
				}
				output.println(s);
				server.globalMessage(s, this, open);
				text.append(s + "\n");
			} catch (Exception e) {
				e.printStackTrace();
				finalize();
			}
		}
	}
	
	protected void finalize() {
		if (open) {
			try {
				open = false;
				System.out.println(">> Closed Socket || Client ID: " + client.getID());
				//client.getSocket().getOutputStream().close();
				//client.getSocket().getInputStream().close();
				server.globalMessage("CLIENT CLOSED: " + name, this, true);
				client.getSocket().close();
			} catch (Exception e) {
				System.out.println("Failed to close socket || Client ID: " + client.getID());
				open = false;
				//System.exit(-1);
			}
		}
	}
	
}
