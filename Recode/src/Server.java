import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

public class Server implements Runnable {

    public static final int port = 4320;
    private ServerSocket server;
    private Socket client;
    private JTextArea textArea;
    private int currentId = 0;
    private InetAddress address;
    private List<ClientThread> clients;
    private BlockType blockList;

    public Server(JTextArea textArea, BlockType blocklist) {
        this.textArea = textArea;
        blockList = blocklist;
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
        }
        while (true) {
            ClientThread w;
            String name;
            int x = -10;
            int y = -10;
            int one = 0;
            int two = 0;
            int three = 0;
            try {
                Socket socket = server.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                name = reader.readLine();
                System.out.println(name + " joined the Server.");
                //File file = new File(".");
                //for(String fileNames : file.list()) System.out.println(fileNames);
                File pinf = new File("data/" + name + ".txt");
                if (!pinf.exists()) {
                    try {
                        pinf.createNewFile();
                        try {
                            FileReader freader = new FileReader(pinf);
                            BufferedReader br = new BufferedReader(freader);
                            String pos = br.readLine();
                            if (pos == null || pos == "") {
                                FileWriter writer = new FileWriter(pinf);
                                BufferedWriter bw = new BufferedWriter(writer);
                                bw.write(name + ",-10,-10,0,0,0");
                                bw.flush();
                                bw.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                    FileReader freader = new FileReader(pinf);
                    BufferedReader br = new BufferedReader(freader);
                    String line = br.readLine();
                    String[] lines = line.split(",");
                    name = String.valueOf(lines[0]);
                    x = Integer.valueOf(lines[1]);
                    y = Integer.valueOf(lines[2]);
                    one = Integer.valueOf(lines[3]);
                    two = Integer.valueOf(lines[4]);
                    three = Integer.valueOf(lines[5]);

                w = new ClientThread(new Client(socket, currentId), textArea, this, name);
                clients.add(w);
                Thread thread = new Thread(w);
                thread.start();
                System.out.println(">> Created new Client Thread (" + w.getClient().getID() + ")");
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                output.println("BLOCKLIST: " + blockList.getBlockInfo().toString());
                /*globalMessage("PLAYERINFO: " + name + "," + x + "," + y + "," + one + "," + two + "," + three, new ClientThread(), true);
                globalMessage("PLAYERINFO: " + name + "," + x + "," + y + "," + one + "," + two + "," + three, new ClientThread(), true);
                globalMessage("PLAYERINFO: " + name + "," + x + "," + y + "," + one + "," + two + "," + three, new ClientThread(), true);
                globalMessage("PLAYERINFO: " + name + "," + x + "," + y + "," + one + "," + two + "," + three, new ClientThread(), true);
                globalMessage("PLAYERINFO: " + name + "," + x + "," + y + "," + one + "," + two + "," + three, new ClientThread(), true);
                globalMessage("PLAYERINFO: " + name + "," + x + "," + y + "," + one + "," + two + "," + three, new ClientThread(), true);
                globalMessage("PLAYERINFO: " + name + "," + x + "," + y + "," + one + "," + two + "," + three, new ClientThread(), true);
                globalMessage("PLAYERINFO: " + name + "," + x + "," + y + "," + one + "," + two + "," + three, new ClientThread(), true);
                globalMessage("PLAYERINFO: " + name + "," + x + "," + y + "," + one + "," + two + "," + three, new ClientThread(), true);
                globalMessage("PLAYERINFO: " + name + "," + x + "," + y + "," + one + "," + two + "," + three, new ClientThread(), true);
                globalMessage("PLAYERINFO: " + name + "," + x + "," + y + "," + one + "," + two + "," + three, new ClientThread(), true);
                globalMessage("PLAYERINFO: " + name + "," + x + "," + y + "," + one + "," + two + "," + three, new ClientThread(), true);
                globalMessage("PLAYERINFO: " + name + "," + x + "," + y + "," + one + "," + two + "," + three, new ClientThread(), true);
                globalMessage("PLAYERINFO: " + name + "," + x + "," + y + "," + one + "," + two + "," + three, new ClientThread(), true);
                globalMessage("PLAYERINFO: " + name + "," + x + "," + y + "," + one + "," + two + "," + three, new ClientThread(), true);
                globalMessage("PLAYERINFO: " + name + "," + x + "," + y + "," + one + "," + two + "," + three, new ClientThread(), true);
*/
                //System.out.println("PLAYERINFO: " + name + "," + x + "," + y + "," + one + "," + two + "," + three);
                for (int i = 0; i < 200; i++) {
                    output.println("PLAYERINFO: " + name + "," + x + "," + y + "," + one + "," + two + "," + three);
                }
                output.println("You have joined the Server! Welcome!!!");
                output.println("Your Client ID is: " + currentId);
                currentId++;
            } catch (Exception e) {
                System.out.println("ClientThread could not be initialized on " + port);
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    public void globalMessage(String s, ClientThread exclude, boolean open) {
        if (open) {
            PrintWriter outputCT = null;
            if (s.contains("BLOCK REMOVE: ")) {
                //System.out.println(s);
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
            } else if (s.contains("SERVERINFO: ")) {
                //System.out.println(s);
                s = s.replace("SERVERINFO: ", "");
                String[] list = s.split(",");
                //System.out.println(list.toString());
                String name = String.valueOf(list[0]);
                //System.out.println(name + " " + getName());
                    //System.out.println("we");
                    int x = Integer.valueOf(list[1]);
                    int y = Integer.valueOf(list[2]);
                    int one = Integer.valueOf(list[3]);
                    int two = Integer.valueOf(list[4]);
                    int three = Integer.valueOf(list[5]);

                try {
                    File pinf = new File("data/" + name + ".txt");
                    pinf.delete();
                    pinf.createNewFile();
                        FileWriter writer = new FileWriter(pinf);
                        BufferedWriter bw = new BufferedWriter(writer);
                        bw.write(name + "," + x + "," + y  + "," + one + "," + two + "," + three);
                        bw.flush();
                        bw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(name + "," + x + "," + y  + "," + one + "," + two + "," + three);
            } else if (s.contains("BLOCK PLACE: ")) {
                s = s.replace("BLOCK PLACE: ", "");
                String[] list = s.split(",");
                int x = Integer.valueOf(list[0]);
                int y = Integer.valueOf(list[1]);
                int w = Integer.valueOf(list[2]);
                int h = Integer.valueOf(list[3]);
                int speed = Integer.valueOf(list[4]);
                String type = String.valueOf(list[5]);
                blockList.add(new Block(x, y, w, h, speed, type));
            }
            for (ClientThread ct : clients) {
                if (!ct.equals(exclude) && ct.isOpen()) {
                    try {
                        outputCT = new PrintWriter(ct.getClient().getSocket().getOutputStream(), true);
                        outputCT.println(s);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
