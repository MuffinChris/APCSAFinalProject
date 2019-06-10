import java.awt.*;

public class DrawPlayer {

    private int x;
    private int y;
    private Image image;
    private String name;

    public DrawPlayer(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setImage(Image i) {
        image = i;
    }

}
