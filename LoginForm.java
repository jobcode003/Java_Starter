import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class LoginForm {
    public static void main(String[] args) {
      JFrame frame = new JFrame("Login Form"); 
      
      JPanel panel =new JPanel() ;
      JTextField usernameField = new JTextField(20);
     
     JPasswordField passwordField = new JPasswordField(20);
    passwordField.setForeground(Color.GREEN);
      ImageIcon icon= new ImageIcon("/home/job/Downloads/icon (1).png");
      JButton loginButton = new JButton("login",icon);
      loginButton.setBounds(100,100,150,30);

        JLabel userLabel = new JLabel("Username: ");
        userLabel.setForeground(Color.WHITE);

        JLabel passLabel = new JLabel("Password: ");
        passLabel.setForeground(Color.WHITE);

     
     panel.add(userLabel);
    panel.add(usernameField);
    panel.add(passLabel);
    panel.add(passwordField);
    panel.add(loginButton);
     panel.setBackground(Color.MAGENTA);
     
    loginButton.addActionListener(new ActionListener() {
        @Override
    public void actionPerformed(ActionEvent e) {
    String username = usernameField.getText();
    String password = new String(passwordField.getPassword());
    if (username.equals("admin") && password.equals("password")) {
    JOptionPane.showMessageDialog(frame, "Login successful!");
    } else {
    JOptionPane.showMessageDialog(frame, "Invalid username or password");
}
}
});
    frame.add(panel);
    frame.setSize(300, 200);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
}
    
    
}