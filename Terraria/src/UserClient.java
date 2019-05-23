import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class UserClient implements Runnable {

	private String host;
	
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
		Socket socket = new Socket(hostname, Server.port);
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
		Socket socket;
		System.out.println("Runnable Created");
		try {
			if (new Socket(host, Server.port) instanceof Socket) {
				socket = new Socket(host, Server.port);
				while (true) {
					//if (socket != null && socket.getInputStream() != null) {
						Scanner sockscan = new Scanner(socket.getInputStream());
						//if (sockscan.nextLine() instanceof String) {
						if (sockscan.hasNextLine()) {
							System.out.println(sockscan.nextLine());
						}
					//}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
