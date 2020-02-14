import java.util.TimerTask;
import java.awt.*;

public class Task extends TimerTask
{
	pool parent;
	Graphics g;

	public Task(pool myParent){
		parent = myParent;
	}

	public void run(){
		parent.update();
	}
}
