import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OnScreenDisplay {
	public static void main(String[] args) {
		JFrame frame = new JFrame("On Screen Display");
		JPanel panel = new JPanel();
		panel.setLayout(null);

		JLabel nameLabel = new JLabel("Name: ");
		nameLabel.setBounds(50, 10, 200, 20);
		JTextField nameField = new JTextField();
		nameField.setBounds(50, 30, 200, 30);

		JLabel phoneLabel = new JLabel("Phone:");
		phoneLabel.setBounds(50, 60, 200, 20);
		JTextField phoneField = new JTextField();
		phoneField.setBounds(50, 80, 200, 30);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(50, 110, 200, 20);
		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(50, 130, 200, 30);

		JButton button;
		try {
			ImageIcon icon = new ImageIcon("smile.jpg");
			Image img = icon.getImage();
			Image newImg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			icon = new ImageIcon(newImg);
			button = new JButton("Submit", icon);
		} catch (Exception e) {
			button = new JButton("Submit");
		}
		button.setBounds(90, 180, 120, 40);

		panel.add(nameLabel);
		panel.add(nameField);
		panel.add(phoneLabel);
		panel.add(phoneField);
		panel.add(passwordLabel);
		panel.add(passwordField);
		panel.add(button);

		frame.add(panel);
		frame.setSize(320, 270);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
