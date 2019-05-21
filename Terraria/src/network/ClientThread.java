package network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.swing.JTextArea;

public class ClientThread implements Runnable {

	private Client client;
	private JTextArea text;
	private boolean open;
	
	public ClientThread (Client client, JTextArea text) {
		this.client = client;
		this.text = text;
		open = true;
	}
	
	public Client getClient() {
		return client;
	}

	@Override
	public void run() {
		String s;
		BufferedReader input = null;
		PrintWriter output = null;
		try {
			input = new BufferedReader(new InputStreamReader(client.getSocket().getInputStream()));
			output = new PrintWriter(client.getSocket().getOutputStream(), true);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		while (true) {
			try {
				s = input.readLine();
				output.println(s);
				text.append(s);
			} catch (Exception e) {
				finalize();
			}
		}
	}
	
	protected void finalize() {
		if (open) {
			try {
				open = false;
				System.out.println("Closed Socket || Client ID: " + client.getID());
				client.getSocket().close();
			} catch (Exception e) {
				System.out.println("Failed to close socket || Client ID: " + client.getID());
				System.exit(-1);
			}
		}
	}
	
}
