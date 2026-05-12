import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ScientificCalculator implements ActionListener {

    JFrame frame;
    JTextField display;

    JButton[] numberButtons = new JButton[10];

    JButton addButton, subButton, mulButton, divButton, eqButton;
    JButton decButton, clrButton;

    JButton sinButton, cosButton, tanButton, logButton, sqrtButton, squareButton, piButton;

    double num1 = 0, num2 = 0, result = 0;
    char operator;

    ScientificCalculator() {

        frame = new JFrame("Scientific Calculator");

        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBackground(new Color(000));
        display.setForeground(new Color(255,255,255));
       
      


        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setBackground(new Color(105,105,105));
            numberButtons[i].setForeground(new Color(255,255,255));
            numberButtons[i].setFont(new Font("Calibri", Font.PLAIN,25));
        }

        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("*");
        divButton = new JButton("/");

        eqButton = new JButton("=");

        decButton = new JButton(".");
        clrButton = new JButton("C");

        sinButton = new JButton("sin");
        cosButton = new JButton("cos");
        tanButton = new JButton("tan");
        logButton = new JButton("log");
        sqrtButton = new JButton("√");
        squareButton = new JButton("x²");
        piButton = new JButton("π");

        JButton[] funcButtons = {
                addButton, subButton, mulButton, divButton, eqButton,
                decButton, clrButton,
                sinButton, cosButton, tanButton, logButton,
                sqrtButton, squareButton, piButton
        };


        for (JButton b : funcButtons) {
            b.addActionListener(this);
            b.setBackground(new Color(150,150,150));
            b.setFont(new Font("Arial",Font.BOLD,27));
            
        }

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 4, 4, 4 ));

        panel.add(sinButton);
        panel.
        add(cosButton);
        panel.add(tanButton);
        panel.add(logButton);

        panel.add(sqrtButton);
        panel.add(squareButton);
        panel.add(piButton);
        panel.add(divButton);

        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(mulButton);

        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subButton);

        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(addButton);

        panel.add(numberButtons[0]);
        panel.add(decButton);
        panel.add(eqButton);
        panel.add(clrButton);

        frame.setLayout(new BorderLayout());
        frame.add(display, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);


        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numberButtons[i]) {
                display.setText(display.getText() + i);
                return;

            }
        }
        if (e.getSource() == decButton) {
            display.setText(display.getText() + ".");
        }

        else if (e.getSource() == addButton) {
            num1 = Double.parseDouble(display.getText());
            operator = '+';
            display.setText("");
        }

        else if (e.getSource() == subButton) {
            num1 = Double.parseDouble(display.getText());
            operator = '-';
            display.setText("");
        }

        else if (e.getSource() == mulButton) {
            num1 = Double.parseDouble(display.getText());
            operator= '*';
            display.setText("");
        }

        else if (e.getSource() == divButton) {
            num1 = Double.parseDouble(display.getText());
            operator = '/';
            display.setText("");
        }

        else if (e.getSource() == eqButton) {

            num2 = Double.parseDouble(display.getText());

            switch (operator) {

                case '+':
                    result = num1 + num2;
                    break;

                case '-':
                    result = num1 - num2;
                    break;

                case '*':
                    result = num1 * num2;
                    break;

                case '/':
                    if (num2 == 0) {
                        display.setText("Error");
                        return;
                    }
                    result = num1 / num2;
                    break;
            }

            display.setText(String.valueOf(result));
        }

        else if (e.getSource() == clrButton) {
            display.setText("");
            num1 = num2 = result = 0;
        }

        else if (e.getSource() == sinButton) {
            double value = Double.parseDouble(display.getText());
            result = Math.sin(Math.toRadians(value));
            display.setText(String.valueOf(result));
        }

        else if (e.getSource() == cosButton) {
            double value = Double.parseDouble(display.getText());
            result = Math.cos(Math.toRadians(value));
            display.setText(String.valueOf(result));
        }

        else if (e.getSource() == tanButton) {
            double value = Double.parseDouble(display.getText());
            result = Math.tan(Math.toRadians(value));
            display.setText(String.valueOf(result));
        }

        else if (e.getSource() == logButton) {
            double value = Double.parseDouble(display.getText());
            result = Math.log10(value);
            display.setText(String.valueOf(result));
        }

        else if (e.getSource() == sqrtButton) {
            double value = Double.parseDouble(display.getText());
            result = Math.sqrt(value);
            display.setText(String.valueOf(result));
        }

        else if (e.getSource() == squareButton) {
            double value = Double.parseDouble(display.getText());
            result = value * value;
            display.setText(String.valueOf(result));
        }

        else if (e.getSource() == piButton) {
            display.setText(display.getText() + Math.PI);
        }
    }

    public static void main(String[] args) {
        new ScientificCalculator();
    }
}