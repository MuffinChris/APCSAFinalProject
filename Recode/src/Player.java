import java.io.File;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class Player extends MovingThing implements Runnable {

    private int speed;
    transient  private Image image;
    private String direction;
    private static Socket socket;
    private int id;
    private PrintWriter output;
    private String name;
    private Graphics window;
    private List<DrawPlayer> players;
    private BlockType blockList;
    private BlockType inventoryList;
    private int one;
    private int two;
    private int three;

    public void setOne(int i) {
        one = i;
    }
    public void setTwo(int i) {
        two = i;
    }
    public void setThree(int i) {
        three = i;
    }
    public int getOne() {
        return one;
    }

    public int getTwo() {
        return two;
    }

    public int getThree() {
        return three;
    }

    public BlockType getInvList() {
        return inventoryList;
    }

    public Player(){
        this(10,10,10,10,10, "LEFT");
        connect();
    }

    public Player(int x, int y){
        super(x, y);
        connect();
    }

    public Player(int x, int y, int s){
        super(x, y);
        speed = s;
        connect();
    }

    public Player(int x, int y, int w, int h, int s, String dir){
        super(x, y, w, h);
        speed=s;
        direction= dir;
        if (direction.equals("LEFT"))
        {
            try{
                URL url = getClass().getResource("playerLeft.png");
                image = ImageIO.read(url);
            }
            catch(Exception e){
            }
        }
        else
        {
            try {
                URL url = getClass().getResource("playerRight.png");
                image = ImageIO.read(url);
            }
            catch(Exception e){
            }
        }
        connect();
    }

    public void setSpeed(int s){
        speed = s;
    }

    public int getSpeed(){
        return speed;
    }

    public String getDirection()
    {
        return direction;
    }

    public void move(String dir)
    {

        if (dir.equals("LEFT")||dir.equals("RIGHT"))

            direction = dir;
        if (dir.equals("LEFT")) {
            setX(getX()-speed);
            try{
                URL url = getClass().getResource("playerLeft.png");
                image = ImageIO.read(url);
            }
            catch(Exception e){
            }
        }
        else if (dir.equals("RIGHT")) {
            setX(getX()+speed);
            try{
                URL url = getClass().getResource("playerRight.png");
                image = ImageIO.read(url);
            }
            catch(Exception e){
            }
        }
        else if (dir.equals("UP")) {
            setY(getY()-speed+1);
        }
        else if (dir.equals("DOWN")) {
            setY(getY()+speed-1);
        }

    }

    public void draw( Graphics window ){
        window.drawImage(image,getX(),getY(),getWidth(),getHeight(),null);
        broadcastMessage(getName() + "," + "moved to " + getX() + "[]" + getY() + "][" + direction);
    }

    public String toString(){
        return super.toString() + getSpeed();
    }

    public void connect() {
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
            output = new PrintWriter(socket.getOutputStream(), true);
            output.println(name);
            Thread t = new Thread(this);
            t.start();
            broadcastMessage(name + " joined the game!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread t = new Thread(new PlayerInfoUpdater(this));
        t.start();
    }

    public Graphics getWindow() {
        return window;
    }

    public void drawOthers() {
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
    public BlockType getBlockList() {
        return blockList;
    }

    public BlockType getInventoryList()
    {
        return inventoryList;
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

    public void setWindow(Graphics window) {
        this.window = window;
    }

    @Override
    public void run() {
        try {
            if (socket instanceof Socket) {
                while (true) {
                    Scanner sockscan = new Scanner(socket.getInputStream());
                    String s = sockscan.nextLine();
                    //System.out.println(s);

                    if (s.contains("Your Client ID")) {
                        id = Integer.valueOf(s.replace("Your Client ID is: ",""));
                    } else if (s.contains("CLIENT CLOSED: ")) {
                        s=s.replace("CLIENT CLOSED: ", "");
                        int index = 0;
                        boolean remove = false;
                        for (DrawPlayer pl : players) {
                            if (pl.getName().equals(s)) {
                                remove = true;
                                break;
                            }
                            index++;
                        }
                        if (remove) {
                            players.remove(index);
                        }
                    } else if (s.contains("BLOCK REMOVE: ")){
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


                    } else if (s.contains("PLAYERINFO: ")) {
                        //System.out.println(s);
                        s = s.replace("PLAYERINFO: ", "");
                        String[] list = s.split(",");
                        //System.out.println(list.toString());
                        String name = String.valueOf(list[0]);
                        //System.out.println(name + " " + getName());
                        if (name.equals(getName())) {
                            //System.out.println("we");
                            inventoryList = new BlockType();
                            int x = Integer.valueOf(list[1]);
                            int y = Integer.valueOf(list[2]);
                            if (x == -10 || y == -10) {
                                x = this.getX();
                                y = this.getY();
                            } else {
                                setX(x);
                                setY(y);
                            }
                            one = Integer.valueOf(list[3]);
                            two = Integer.valueOf(list[4]);
                            three = Integer.valueOf(list[5]);
                            for (int i = 0; i < one; i++) {
                                inventoryList.add(new Block(50, 50, 50, 50, 2, "dirt"));
                            }
                            for (int i = 0; i < two; i++) {
                                inventoryList.add(new Block(50, 50, 50, 50, 2, "stone"));
                            }
                            for (int i = 0; i < three; i++) {
                                inventoryList.add(new Block(50, 50, 50, 50, 2, "wood"));
                            }
                            //System.out.println(x + " " + y + " " + one + " " + two + " " + three + " " + inventoryList.getBlockInfo());
                        }
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
                    } else if (s.contains("BLOCKLIST: ")) {
                        String bl = s.replace("BLOCKLIST: ", "");
                        if (bl.contains("EMPTY") || bl == "" || bl == " " || bl.contains("[]")) {
                            blockList = new BlockType();
                        } else {
                            List<Block> blockl = new ArrayList<Block>();
                            List<String> bsl = new ArrayList<String>();
                            bl = bl.replace("[", "");
                            bl = bl.replace("]", "");
                            bsl = Arrays.asList(bl.split(","));
                            for (String sb : bsl) {
                                String info = sb.replace("<>", ",");
                                info = info.replace(" ", "");
                                String[] infol = info.split(",");
                                int x = Integer.valueOf(infol[0]);
                                int y = Integer.valueOf(infol[1]);
                                int height = Integer.valueOf(infol[2]);
                                int width = Integer.valueOf(infol[3]);
                                String type = String.valueOf(infol[4]);
                                Block block = new Block(x, y, width, height, 2, type);
                                blockl.add(block);
                            }

                            blockList = new BlockType();
                            blockList.setList(blockl);
                        }
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
                        System.out.println(s + " out");
                    }
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }



    @Override
    protected void finalize() {
        try {
            this.broadcastMessage("CLIENT CLOSED: " + name);
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            output.println("CLOSE CLIENT ID: " + id);
            socket.close();
        } catch (Exception e) {
            System.out.println("Failed to close socket");
            System.exit(-1);
        }
    }

}
