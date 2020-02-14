
import java.util.ArrayList;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.util.Timer;
import java.io.IOException;
import java.awt.image.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.*;
import java.util.Scanner;
import java.net.Socket;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Date;

public class pool extends JFrame implements MouseListener, MouseMotionListener {




  public static void main(String[] args) {



     pool masa = new pool();
	 	 masa.setSize(1200,600);
     masa.setTitle("Bilardo Keyfi");
	   masa.init();
     Timer myTimer = new Timer();

     Task myTask = new Task(masa);
     myTimer.schedule(myTask,30,5);
		 masa.setVisible(true);






  }

  public int skor = 0;
  private ArrayList<Top> toplar;
  public Top wBall;
  public Top yBall;
  public Top rBall;

  public long Hskor;
  public long[] skorlar = {0, 0, 0, 0, 0};
  public Top myBall = wBall;
  public Top otherBall = yBall;
  public long startTime;

  private Istaka istakam;
  public boolean rede = false;
  public boolean yellowa = false;

  private Image img;
  public MouseEvent press, release;
  public Graphics bufferg;
  public BufferedImage offscreen;
  public Dimension dim;

  private AffineTransform myTransform;
  private AffineTransform myTranslate;
  private AffineTransform istakaOffset;
  private double powerOffset, power, angle;
  private boolean stopped;
  private static int POST = 59090;
  private Socket socket;
  private Scanner in;
  private PrintWriter out;


  public void init(){


   //online
   // try {
   //   socket = new Socket("192.168.1.32", 59090);
   //   in = new Scanner(socket.getInputStream());
   //     // System.out.println("Server response: " + in.nextLine());
   //     out = new PrintWriter(socket.getOutputStream(), true);
   //     out.println("bende seninoccccc");
   // } catch (Exception e) {
   //   System.out.println("olmadı");
   // }
//online



JFrame c = new JFrame("Controls");
JButton b = new JButton("Finish the game");
b.setBounds(50,100,95,50);
b.addActionListener(new ActionListener(){
  public void actionPerformed(ActionEvent e) {
    System.out.println(skor);
    skor = 50;

  }
});
c.add(b);
c.setSize(400,400);
c.setLayout(null);
c.setVisible(true);



    startTime = System.currentTimeMillis();
    angle = Math.PI / 2.0;
    power = 0.0;
    powerOffset = 0.0;
    stopped = false;  // 0.0
    toplar = new ArrayList<Top>();

    istakam = new Istaka();
    wBall = new Top(800, 250, 0, 0, "white");
    yBall = new Top(750, 400, 0, 0, "yellow");
    rBall = new Top(800, 400, 0, 0, "red");
    myBall = wBall;
    otherBall = yBall;


    dim = new Dimension(1200, 600);

    offscreen = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_RGB);
    bufferg = offscreen.getGraphics();
    Graphics2D g2 = (Graphics2D)bufferg;
    RenderingHints rh = new RenderingHints(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setRenderingHints(rh);

    try {
      img = ImageIO.read(getClass().getResource("/resources/table.jpg"));
    }  catch (IOException e) {

    }

    addMouseListener(this);
    addMouseMotionListener(this);
    myTransform = new AffineTransform();
    myTransform.rotate(angle);
    myTranslate = new AffineTransform();
    istakaOffset = new AffineTransform();
    istakaOffset.setToTranslation(-6,0);


    toplar.add(wBall);
    toplar.add(rBall); //neutral top
    toplar.add(yBall);

    Top.parent = this;

  }

  public void update()
    {
    	if(toplar == null) return;

        if(skor >= 1) { //skor bitiş ayarlama, kolayca gösterebilmek için şimdilik 1
          System.out.println("burdayim");
          Hskor = (new Date()).getTime() - startTime;

          for (int i = 0; i < skorlar.length; i++) {
            if(skorlar[i] == 0) {
              skorlar[i] = Hskor;
              break;
            }


          }
          for (int i = 0; i < skorlar.length; i++) {
            System.out.println(skorlar[i]);


          }
          new Scores(skorlar);
          skor = 0;
          startTime = System.currentTimeMillis();

          wBall.x = 800;
          wBall.y = 250;
          yBall.x = 750;
          yBall.y = 400;
          rBall.x = 800;
          rBall.y = 400;
          for(Top c : toplar) {
            c.vx = 0;
            c.vy = 0;
          }

        }
        stopped = true;
        //online
        // Double tempvx = Double.parseDouble(in.nextLine());
        // Double tempvy = Double.parseDouble(in.nextLine());
        // myBall.vx = tempvx;
        // myBall.vy = tempvy;

        for(int i=0 ; i<toplar.size() ; i++){
            Top b = toplar.get(i);
            if(b.getSpeed() > 0.001) stopped = false;
            for(Top b2 : toplar){
                if(b2 != b){
                    if(myBall.carptimi(otherBall)) yellowa = true;
                    if(myBall.carptimi(rBall)) rede = true;
                    b.collides(b2);
                }
            }



        }


        for(Top b : toplar){
            if(b != myBall) b.move();

        }



        if(myBall.getSpeed() > 0.001) stopped = false;

        if(stopped){
           if (rede && yellowa) {
             System.out.println(++skor);
           }


           rede = false;
           yellowa = false;

           myTranslate.setToTranslation(myBall.getX()+10,myBall.getY()+10); //istakayi yerlestirmek icin
        }
        myBall.move();
        repaint();
    }



    public void paint(Graphics g)
   {
       Graphics2D g2d = (Graphics2D)bufferg;
       AffineTransform temp = g2d.getTransform();
       AffineTransform temp2 = new AffineTransform();

       int dx1 = 0;
       int dy1 = 0;
       int dx2 = 1200;
       int dy2 = 600;
       int sx1 = 0;
       int sy1 = 0;
       int sx2 = 600;
       int sy2 = 300;

       g2d.clearRect(0,0,dim.width,dim.height);
       g2d.setColor(Color.black);

       g2d.drawImage(img,dx1,dy1,dx2,dy2,sx1,sy1,sx2,sy2, this);
       g2d.drawString("Score: " + skor, 1050, 530);
       g2d.drawString("Speed: X(" + myBall.vx + ")" + " Y(" + myBall.vy + ")" ,80, 80);
       long et = (new Date()).getTime() - startTime;
       g2d.drawString("Time: " + et / 1000 + " seconds", 530, 530);

       for(Top b : toplar) b.paint(g2d);


       temp2.concatenate(myTranslate);
       temp2.concatenate(myTransform);
       temp2.concatenate(istakaOffset);

       g2d.setTransform(temp2);
       if(stopped)istakam.paint(bufferg,this);
       g2d.setTransform(temp);

       if(stopped){
         bufferg.setColor(Color.black);
         int dx = (int)(1000*Math.cos(angle-Math.PI/2));
         int dy = (int)(1000*Math.sin(angle-Math.PI/2));
         int size=10;
         bufferg.drawLine((int)myBall.getX()+size, (int)myBall.getY()+size, (int)myBall.getX()+dx+size, (int)myBall.getY()+dy+size);
       }

       g.drawImage(offscreen,0,0,this);
   }



   public void mouseClicked(MouseEvent e){}
   public void mouseEntered(MouseEvent e){}
   public void mouseExited(MouseEvent e){}

   public void mousePressed(MouseEvent e){ //pressed
       double dx = e.getX() - myBall.getX();
       double dy = e.getY() - myBall.getY();
       powerOffset = Math.sqrt(dx*dx+dy*dy);

   }

   public double distance(MouseEvent e, Top b){
       int x1 = e.getX();
       int y1 = e.getY();
       double x2 = b.getX();
       double y2 = b.getY();
       double dx = x1-x2;
       double dy = y1-y2;
       return Math.sqrt(dx*dx-dy*dy);
   }

   public void mouseReleased(MouseEvent e){
       if(stopped){
           myBall.vx = power* Math.cos(angle - Math.PI/2)*0.1;
           myBall.vy = power* Math.sin(angle - Math.PI/2)*0.1;




       }
       angle = 0;
       myTransform.setToTranslation(0,0);
   }



   public void mouseDragged(MouseEvent e){
       double dx = e.getX() - myBall.getX();
       double dy = e.getY() - myBall.getY();
       power = Math.sqrt(dx*dx+dy*dy) - powerOffset;
       angle = Math.atan2(dy,dx);
       myTransform.setToTranslation(power*Math.cos(angle),power*Math.sin(angle));
       angle -= Math.PI/2;
       myTransform.rotate(angle);
   }
   public void mouseMoved(MouseEvent e){}

   public void keyPressed(KeyEvent e){}
   public void keyReleased(KeyEvent e){}
   public void keyTyped(KeyEvent e){}


}
