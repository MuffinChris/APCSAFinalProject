import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class UserClient implements Runnable {

	private String host;
	private static Socket socket;
	private int id;
	
	public UserClient(String host) {
		this.host = host;
	}
	
	public static void main(String[] args) throws IOException {
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
			/*while (reader.ready()) {
				System.out.println(sockscan.nextLine());
			}*/
		}
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
