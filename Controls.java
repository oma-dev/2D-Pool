import javax.swing.*;
import java.awt.event.*;
public class Controls extends pool {
  Controls(){
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
  }
}
