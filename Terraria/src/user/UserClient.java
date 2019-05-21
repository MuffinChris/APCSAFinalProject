package user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class UserClient implements Runnable {

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
		Socket socket = new Socket(hostname, 4321);
		//Scanner input = new Scanner(socket.getInputStream());
		//System.out.println("Server: " + input.nextLine());
		PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
		//Scanner sockscan = new Scanner(socket.getInputStream());
		Thread t = new Thread(new UserClient());
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
			socket = new Socket("localhost", 4321);
			while (true) {
				Scanner sockscan = new Scanner(socket.getInputStream());
				System.out.println(sockscan.nextLine());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
