import javax.swing.*;
import java.awt.event.*;

public class CheckBoxExample extends JFrame implements ActionListener {

    JLabel l;
    JCheckBox cb1, cb2, cb3;
    JButton orderBtn, payBtn;
    JTextField paymentField;


    JComboBox<Integer> q1, q2, q3;

    float totalAmount = 0;

    CheckBoxExample() {

        l = new JLabel("Food Ordering System");
        l.setBounds(50, 50, 300, 20);

        cb1 = new JCheckBox("Pizza @ 100");
        cb1.setBounds(100, 100, 150, 20);

        cb2 = new JCheckBox("Burger @ 30");
        cb2.setBounds(100, 150, 150, 20);

        cb3 = new JCheckBox("Tea @ 10");
        cb3.setBounds(100, 200, 150, 20);

       
        Integer[] qty = {1,2,3,4,5,6,7,8,9,10};

        q1 = new JComboBox<>(qty);
        q1.setBounds(260, 100, 50, 20);

        q2 = new JComboBox<>(qty);
        q2.setBounds(260, 150, 50, 20);

        q3 = new JComboBox<>(qty);
        q3.setBounds(260, 200, 50, 20);

        orderBtn = new JButton("Order");
        orderBtn.setBounds(100, 250, 80, 30);
        orderBtn.addActionListener(this);

        paymentField = new JTextField();
        paymentField.setBounds(100, 300, 100, 25);

        payBtn = new JButton("Pay");
        payBtn.setBounds(210, 300, 80, 25);
        payBtn.addActionListener(this);

        add(l);
        add(cb1); add(q1);   
        add(cb2); add(q2);
        add(cb3); add(q3);
        add(orderBtn);
        add(paymentField);
        add(payBtn);

        setSize(400, 450);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {

        
        if (e.getSource() == orderBtn) {
            totalAmount = 0;
            String msg = "";

            if (cb1.isSelected()) {
                int qty = (int) q1.getSelectedItem();   
                float cost = 100 * qty;                 
                totalAmount += cost;
                msg += "Pizza x" + qty + ": " + cost + "\n";
            }

            if (cb2.isSelected()) {
                int qty = (int) q2.getSelectedItem();
                float cost = 30 * qty;
                totalAmount += cost;
                msg += "Burger x" + qty + ": " + cost + "\n";
            }

            if (cb3.isSelected()) {
                int qty = (int) q3.getSelectedItem();
                float cost = 10 * qty;
                totalAmount += cost;
                msg += "Tea x" + qty + ": " + cost + "\n";
            }

            msg += "-----------------\n";
            msg += "Total: " + totalAmount;

            JOptionPane.showMessageDialog(this, msg);
        }

        // PAY BUTTON LOGIC (unchanged)
        if (e.getSource() == payBtn) {
            try {
                float paidAmount = Float.parseFloat(paymentField.getText());

                if (paidAmount >= totalAmount) {
                    float balance = paidAmount - totalAmount;
                    JOptionPane.showMessageDialog(this,
                            "Success! Balance is: " + balance);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Failed transaction, insufficient funds");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Enter a valid amount");
            }
        }
    }

    public static void main(String[] args) {
        new CheckBoxExample();
    }
}