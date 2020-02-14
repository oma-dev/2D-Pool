
import java.awt.Graphics;
import java.awt.Color;

public class Top  {

    public static pool parent;
    public double x;
    public double y;
    private double px; // previous X
    private double py; // previous Y
    public double vx;
    public double vy;
    private double radius;
    private String color;



    public double getSpeed() {
      return vx*vx + vy*vy;
    }

    public double getRadius() {
      return radius;
    }

    public double getX() {
      return x;
    }

    public double getY() {
      return y;
    }


    public Top(double x, double y, double vx, double vy, String color) { //double
      this.x = x;
      this.y = y;
      radius = 15;
      this.vx = vx;
      this.vy = vy;
      this.color = color;



    }


    public void paint(Graphics g) {

      if(color == "white") {
        g.setColor(Color.white);
      } else if (color == "yellow") {
        g.setColor(Color.yellow);
      } else if (color == "red") {
        g.setColor(Color.red);
      }

      int diameter = (int)(radius*2);
      g.fillOval((int)x, (int)y, diameter, diameter);

    }




    public void move() {

      px = x;
      py = y;
      vy *= 0.998;
      vx *= 0.998;

      if(getSpeed() < 0.05) {
        vx = 0;
        vy = 0;
      }

      if(y>=(550 - radius*2) && vy>=0) vy*=-0.75;
      if(y<=55 && vy<=0) vy*=-0.75;
      if(x>=(1150 - radius*2) && vx>=0) vx*=-0.75;
      if(x<=57 && vx<=0) vx*=-0.75;
      x += vx;
      y += vy;





    }



    public boolean carptimi (Top other){
      double dx = x - other.x;
      double dy = y - other.y;
      return Math.sqrt(dx*dx+dy*dy) < 2*radius;
    }

    public void collides(Top other) {
      if(carptimi(other)) {

        x = px;
        y = py;
        other.x = other.px;
        other.y = other.py;
        double dx = other.x - x;
        double dy = other.y - y;
        double dist = Math.sqrt(dx*dx + dy*dy);
        if(dist == 0) {
          dx = 0;
          dy = 0;
        } else {
          dx /= dist;
          dy /= dist;
        }
        double scale = (dx*vx+dy*vy) - (dx*other.vx+dy*other.vy);
        vx -= dx*scale;
        vy -= dy*scale;
        other.vx += dx*scale;
        other.vy += dy*scale;
      }
    }



}
