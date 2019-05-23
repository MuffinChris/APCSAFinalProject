import java.awt.*;

public class Block {

    private BlockType type;
    private Location location;


    public Block(BlockType type, Location loc) {
        this.type = type;
        location = loc;
    }

    public Location getLoc() {
        return location;
    }

    public BlockType getType() {
        return type;
    }

    public void draw(Graphics window) {
        int width = Game.WIDTH / 16;
        int height = Game.HEIGHT / 16;
        int x = location.getX() * width;
        int y = location.getY() * height;
        window.setColor(Color.GREEN);
        window.fillRect(x,y,width,height);
    }

}
