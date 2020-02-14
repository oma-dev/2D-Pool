import javax.swing.*;

public class Scores {
  Scores(long[] skorlar){
      JFrame f= new JFrame("High Scores");
      DefaultListModel<String> l1 = new DefaultListModel<>();

        for(int a = 0; a < skorlar.length; a++) {
          if(skorlar[a] != 0) {

            l1.addElement( skorlar[a] / 1000 + " seconds");
          }
        }


        JList<String> list = new JList<>(l1);
        list.setBounds(150,100, 75,75);
        f.add(list);
        f.setSize(400,400);
        f.setLayout(null);
        f.setVisible(true);
   }
}
