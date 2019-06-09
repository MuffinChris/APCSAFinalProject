public class BlockLocation {

  private int x, y;
  public BlockLocation(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public void setLocation(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX(){
    return x;
  }

  public int getY(){
    return y;
  }

  public String toString() {
    return "Location x: " + x + ", y: " + y;
  }

}

