import java.io.File;
import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.*;
public class Block extends MovingThing implements Serializable {
  
  private int speed;
	transient  private Image image;
  private String type;

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
      this.type = type;
      try {
        URL url = getClass().getResource("dirt.png");
        image = ImageIO.read(url);
      }
      catch(Exception e) {
      }
    }
    else if (type.equals("wood")) {
      this.type = type;
      try {
        URL url = getClass().getResource("wood.png");
        image = ImageIO.read(url);
      }
      catch(Exception e) {
      }
    }
    else if (type.equals("stone")) {
      this.type = type;
      try {
        URL url = getClass().getResource("stone.png");
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

  public String getType() {
    return type;
  }

  public void move(String direction){
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

  public void draw( Graphics window ){
    window.drawImage(image,getX(),getY(),getWidth(),getHeight(),null);
  }

  public void draw( Graphics window, Color col) {
    window.setColor(Color.WHITE);
    window.fillRect(getX(), getY(), getWidth(), getHeight());
  }

  public String toString()
  {
    return super.toString();
  }

}
