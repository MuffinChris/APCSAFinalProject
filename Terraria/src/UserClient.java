import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class UserClient implements Runnable {

	private static Socket socket;
	private int id;
	private PrintWriter output;
	private String name;
	private Graphics window;
	private List<DrawPlayer> players;

	public Graphics getWindow() {
		return window;
	}

	public void draw() {
		for (DrawPlayer pl : players) {
			if (pl.getImage() instanceof Image) {
				window.drawImage(pl.getImage(), pl.getX(), pl.getY(), 50, 50, null);
			}
		}
	}

	public void draw(Graphics window, String direction, int x, int y, String name) {
		while (!(window instanceof Graphics)) {
			return;
		}
		URL url = getClass().getResource("playerRight.png");
		boolean go = false;
		if (direction.equals("RIGHT")) {
			url = getClass().getResource("playerRight.png");
			go = true;
		} else if (direction.equals("LEFT")) {
			url = getClass().getResource("playerLeft.png");
			go = true;
		}
		Image image = null;
		if (go) {
			try {
				image = ImageIO.read(url);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (!this.name.equals(name)) {
			//window.drawImage(image, x, y, 50, 50, null);
			for (DrawPlayer pl : players) {
				if (pl.getName().equals(name)) {
					pl.setX(x);
					pl.setY(y);
					if (go) {
						pl.setImage(image);
					}
				}
			}
		}
	}

	public UserClient() {
		players = new ArrayList<DrawPlayer>();
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Enter a Hostname Address: ");
		String hostname = keyboard.nextLine();
		System.out.println("Enter a Username: ");
		String name = keyboard.nextLine();
		this.name = name;
		if (!(hostname instanceof String)) {
			System.out.println("Invalid Hostname Arguments");
			return;
		}
		try {
			socket = new Socket(hostname, Server.port);
			//Scanner input = new Scanner(socket.getInputStream());
			//System.out.println("Server: " + input.nextLine());
			output = new PrintWriter(socket.getOutputStream(), true);
			//Scanner sockscan = new Scanner(socket.getInputStream());
			//Thread t = new Thread(new UserClient(hostname));
			Thread t = new Thread(this);
			t.start();
			//while (true) {
				//BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				broadcastMessage(name + " joined the game!");
				/*if (keyboard.hasNextLine()) {
					broadcastMessage(name, keyboard.nextLine());
				}*/
				// fix printing multiples, and print overlapping from multiples issue
				/*while (reader.ready()) {
					System.out.println(sockscan.nextLine());
				}*/
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void broadcastMessage(String name, String s) {
		output.println(name + ": " + s);
	}
	public void broadcastMessage(String s) {
		output.println(s);
	}

	public String getName() {
		return name;
	}

	/*public static void main(String[] args) throws IOException {
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Enter a Hostname Address: ");
		String hostname = keyboard.nextLine();
		System.out.println("Enter a Username: ");
		String name = keyboard.nextLine();
		if (!(hostname instanceof String)) {
			System.out.println("Invalid Hostname Arguments");
			return;
		}
		socket = new Socket(hostname, Server.port);
		//Scanner input = new Scanner(socket.getInputStream());
		//System.out.println("Server: " + input.nextLine());
		PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
		//Scanner sockscan = new Scanner(socket.getInputStream());
		Thread t = new Thread(new UserClient(hostname));
		t.start();
		while (true) {
			//BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			if (keyboard.hasNextLine()) {
				output.println(name + ": " + keyboard.nextLine());
			}
			// fix printing multiples, and print overlapping from multiples issue
			//while (reader.ready()) {
			//	System.out.println(sockscan.nextLine());
			//}
		}
	}*/

	public void setWindow(Graphics window) {
		this.window = window;
	}
	
	@Override
	public void run() {
		System.out.println("Runnable Created");
		try {
			if (socket instanceof Socket) {
				//socket = new Socket(host, Server.port);
				while (true) {
					//if (socket != null && socket.getInputStream() != null) {
						Scanner sockscan = new Scanner(socket.getInputStream());
						//if (sockscan.nextLine() instanceof String) {
						//if (sockscan.hasNextLine()) {
					String s = sockscan.nextLine();
					if (s.contains("Your Client ID")) {
						id = Integer.valueOf(s.replace("Your Client ID is: ",""));
					} else if (s.contains("moved to") && !s.contains(":")) {
						String position = s.replace("moved to ", "");
						position = position.replace("[]", ",");

						position = position.replace("][", ",");
						String name = String.valueOf(position.split(",")[0]);
						int x = Integer.valueOf(position.split(",")[1]);
						int y = Integer.valueOf(position.split(",")[2]);
						String direction = String.valueOf(position.split(",")[3]);

						boolean has = false;
						for (DrawPlayer pl : players) {
							URL url = getClass().getResource("playerRight.png");
							boolean go = false;
							if (direction.equals("RIGHT")) {
								url = getClass().getResource("playerRight.png");
								go = true;
							} else if (direction.equals("LEFT")) {
								url = getClass().getResource("playerLeft.png");
								go = true;
							}
							Image image = null;
							if (go) {
								try {
									image = ImageIO.read(url);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							if (pl.getName().equals(name)) {
								has = true;
								if (go) {
									pl.setImage(image);
								}
							}
						}

						if (!has && !getName().equals(name)) {
							players.add(new DrawPlayer(x, y, name));
						}


						this.draw(window, direction, x, y, name);
					} else {
						System.out.println(s);
					}
						//sockscan.close();
					//}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	@Override
	protected void finalize() {
		try {
			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
			output.println("CLOSE CLIENT ID: " + id);
			socket.close();
		} catch (Exception e) {
			System.out.println("Failed to close socket");
			System.exit(-1);
		}
	}
}
