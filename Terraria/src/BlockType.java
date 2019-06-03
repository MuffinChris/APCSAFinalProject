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

  public void drawEmAll( Graphics window ) {
    for (Block block : blocks) {
      block.draw(window);
    }
  }

  public void setList(List<Block> blockz) {
    blocks = blockz;
  }

  public List<Block> getBlocks() {
    return blocks;
  }

  public List<String> getBlockInfo() {
    List<String> output = new ArrayList<String>();

    for (Block b : blocks) {
      output.add(b.getX()+"[]"+b.getY()+"[]"+b.getHeight()+"[]"+b.getWidth());
    }

    return output;
  }

  public String toString() {
    return super.toString();
  }

}
