import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class ExamPractice  {
    ExamPractice(){
        JFrame f= new JFrame("text area");
       // JTextArea ta= new JTextArea();
        JButton b= new JButton("click");
        JLabel l= new JLabel();
        JCheckBox c1= new JCheckBox("java");
        c1.setBounds(10,10,100,50);
        JCheckBox c2= new JCheckBox("python");

       // ta.setBounds(10,30,400,400);
        //f.add(ta);
        f.add(c1);
        f.add(c2);  
        f.add(l);
        f.add(b);
        f.setSize(400,400);
        f.setLayout(null);
        f.setVisible(true);

    }
  
    
    public static void main(String[] args) {
        new ExamPractice();
    }
}
