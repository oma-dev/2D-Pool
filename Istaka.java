import java.awt.Graphics;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.*;


public class Istaka
{
    private Image istaka;
    public Istaka()
    {
		try {
			istaka = ImageIO.read(getClass().getResource("/resources/CueStick.png"));
		} catch (IOException e) {}
    }

    public void paint(Graphics g ,ImageObserver observer)
    {
        g.drawImage(istaka,0,0, observer);
    }
}
