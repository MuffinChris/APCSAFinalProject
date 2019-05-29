import javax.swing.*;

public class GameServer implements Runnable {

    private Server server;

    public void run() {
        JTextArea textarea = new JTextArea();
        server = new Server(textarea);
        server.listen();
    }

    public static void main(String args[]) {
        GameServer run = new GameServer();
        //Game run = new Game();
        Thread thread = new Thread(run);
        thread.start();
    }

}
