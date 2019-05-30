import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;

public class BlockType {

  private List<Block> blocks;

  public BlockType() {
    blocks = new ArrayList<Block>();
  }

  public void add(Block block) {
    blocks.add(block);
  }

  public Block get(int index) {
    return blocks.get(index);
  }

  public int size() {
    return blocks.size();
  }

  public void remove(Block block) {
    blocks.remove(block);
  }

  public void drawEmAll(Graphics window) {
    for (Block block : blocks) {
      block.draw(window);
    }
  }

  public String toString() {
    return super.toString();
  }

}
