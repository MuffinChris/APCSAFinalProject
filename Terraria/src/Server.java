import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

public class Server implements Runnable {

	public static final int port = 4321;
	private ServerSocket server;
	private Socket client;
	private JTextArea textArea;
	private int currentId = 0;
	private InetAddress address;
	private List<ClientThread> clients;
	private BlockType blockList;

	private BlockType inventoryList;
	private Player player;
	
	public Server(JTextArea textArea, BlockType blocklist,BlockType inventorylist, Player p) {
		this.textArea = textArea;
		blockList = blocklist;
		inventoryList = inventorylist;
		player = p;
	}

	public void run() {
		while(true) {
			try {
				Thread.currentThread().sleep(500);
				if (blockList.getBlockInfo().size() == 0) {
					globalMessage("BLOCKLIST: EMPTY", new ClientThread(), true);
				} else {
					globalMessage("BLOCKLIST: " + blockList.getBlockInfo().toString(), new ClientThread(), true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
		Thread t = new Thread(this);
		t.start();
		clients = new ArrayList<ClientThread>();
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
				Socket socket = server.accept();
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String name = reader.readLine();
				System.out.println(name);
				w = new ClientThread(new Client(socket, currentId), textArea, this, name);
				clients.add(w);
				Thread thread = new Thread(w);
				thread.start();
				System.out.println(">> Created new Client Thread (" + w.getClient().getID() + ")");
				PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
				output.println("BLOCKLIST: " + blockList.getBlockInfo().toString());
				output.println("You have joined the Server! Welcome!!!");
				// issue with outputting info... fix later
				output.println("Your Client ID is: " + currentId);
				currentId++;
			} catch (Exception e) {
				System.out.println("ClientThread could not be initialized on " + port);
				System.exit(-1);
			}
		}
	}
	
	public void globalMessage(String s, ClientThread exclude, boolean open) {
		if (open) {
			PrintWriter outputCT = null;
			//if (s == null) {
			//	return;
			//}
			if (s.contains("BLOCK REMOVE: ")) {
				System.out.println(s);
				s = s.replace("BLOCK REMOVE: ", "");
				String[] list = s.split(",");
				int x = Integer.valueOf(list[0]);
				int y = Integer.valueOf(list[1]);
				for (int i = blockList.size() - 1; i>=0; i--) {
					Block b = blockList.get(i);
					if (b.getX() == x && b.getY() == y) {
						blockList.remove(b);
					}
				}
			}
			for (ClientThread ct : clients) {
				if (!ct.equals(exclude) && ct.isOpen()) {
					try {
						//if (ct.getClient().getSocket() != null && ct.getClient().getSocket().getOutputStream() != null) {
						outputCT = new PrintWriter(ct.getClient().getSocket().getOutputStream(), true);
						outputCT.println(s);
						//System.out.println(s);

						//}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
}
