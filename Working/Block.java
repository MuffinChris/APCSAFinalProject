import java.io.File;
import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;

public class Block extends MovingThing {
  
  private int speed;
  private Image image;

  public Block() {
    this(0,0,30,30,0);
  }

  public Block(int x, int y) {
    super(x, y);
    setWidth(10);
    setHeight(10);
    speed = 1;
  }

  public Block(int x, int y, int s) {
    super(x, y);
    setWidth(10);
    setHeight(10);
    speed = s;
  }

  public Block(int x, int y, int w, int h, int s) {
    super(x, y, w, h);
    speed=s;
    try {
      URL url = getClass().getResource("grass.png");
      image = ImageIO.read(url);
    }
    catch(Exception e) {
    }
  }

  public Block(int x, int y, int w, int h, int s, String type) {
    super(x, y, w, h);
    speed=s;
    if (type.equals("dirt")) {
      try {
        URL url = getClass().getResource("dirt.png");
        image = ImageIO.read(url);
      }
      catch(Exception e) {
      }
    }
  }

  public void setSpeed(int s){
    speed = s;
  }

  public int getSpeed() {
    return speed;
  }

  public void move(String direction) {
    if (direction.equals("LEFT")) {
      setX(getX()-speed); 
    }
    else if (direction.equals("RIGHT")) {
      setX(getX()+speed); 
    }
    else if (direction.equals("UP")) {
      setY(getY()-speed); 
    }
    else if (direction.equals("DOWN")) {
      setY(getY()+speed); 
    }
  }

  public void draw( Graphics window ) {
    window.drawImage(image,getX(),getY(),getWidth(),getHeight(),null);
  }

  public String toString() {
    return super.toString();
  }

}
