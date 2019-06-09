import java.io.File;
import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;

public class Player extends MovingThing {

  private int speed;
  transient  private Image image;
  private UserClient client;
  private String direction;

  public Player(){
    this(10,10,10,10,10, "LEFT",null);
  }

  public Player(int x, int y){
    super(x, y);
  }

  public Player(int x, int y, int s){
    super(x, y);
    speed = s;
  }

  public Player(int x, int y, int w, int h, int s, String dir, UserClient client){
    super(x, y, w, h);
    speed=s;
    this.client = client;
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

/*  try{
      URL url = getClass().getResource("playerLeft.png");
      image = ImageIO.read(url);
    }
    catch(Exception e){
    }
 */
  }

  public void setSpeed(int s){
    speed = s;
  }

  public int getSpeed(){
    return speed;
  }

  public void setClient(UserClient client) {
    this.client = client;
  }

	public String getDirection()
	{
		return direction;
	}
  public void move(String dir)
  {
/*
    if (direction.equals("LEFT")) {
      setX(getX()-speed); 
      try{
        URL url = getClass().getResource("playerLeft.png");
        image = ImageIO.read(url);
      }
      catch(Exception e){
      }
    }
    else if (direction.equals("RIGHT")) {
      setX(getX()+speed); 
      try{
        URL url = getClass().getResource("playerRight.png");
        image = ImageIO.read(url);
      }
      catch(Exception e){
      }
    }
    else if (direction.equals("UP")) {
      setY(getY()-speed+1); 
    }
    else if (direction.equals("DOWN")) {
      setY(getY()+speed-1); 
    }
    this.direction = direction;
*/

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
    client.broadcastMessage(client.getName() + "," + "moved to " + getX() + "[]" + getY() + "][" + direction);
  }

  public String toString(){
    return super.toString() + getSpeed();
  }

}
