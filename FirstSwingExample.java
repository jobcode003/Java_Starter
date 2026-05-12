import javax.swing.*;
public class FirstSwingExample {
    public static void main(String[] args) {
        JFrame f =new JFrame();
        JButton b= new JButton("CLICK HERE");
        b.setBounds(100,100,150,90);
        f.add(b);
        f.setSize(400,500);
        f.setLayout(null);
        f.setVisible(true);
    }
    
}
