import java.awt.*;
import java.awt.image.BufferedImage;

public class TerrariaGame extends Canvas implements Runnable {

    private Block block;

    public TerrariaGame() {
        setBackground(Color.CYAN);
        block = new Block(new BlockType("Grass"), new Location(10, 10));
        setVisible(true);
    }


    private BufferedImage big;

    @Override
    public void paint(Graphics g) {
        Graphics2D gD = (Graphics2D)g;
        if(big==null)
            big = (BufferedImage)(createImage(getWidth(),getHeight()));
        Graphics gDBIG = big.createGraphics();
        block.draw(gDBIG);
        System.out.println("paint time");
        gD.drawImage(big, null, 0, 0);
    }

    public void run() {
        while (true) {
            repaint();
        }
    }

}
