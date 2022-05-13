package com.company;

import java.time.LocalDate;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Main implements ActionListener {

    private static JPanel panel = new JPanel();
    private static JFrame frame = new JFrame();
    private static JTextField usernameText;
    private static JPasswordField passwordText;
    private static JLabel success;

    public static void main(String[] args) {

        loginGUI();

        // CREATES LOCALDATE OBJECT
        // OBJECT FORMAT: Year, Month, Day
        //LocalDate date = LocalDate.of(2022, 5, 9);

        // PRINTS FULL DATE
        // DATE FORMAT: YYYY-MM-DD
        //System.out.println("Date: " + date);

        // LOCALDATE METHODS
        //
        // .getYear() - Gets the year as an 'int'. Eg "2022"
        //
        // .getMonth() - Gets the month as a 'month'. Eg "MAY"
        // .getMonthValue - Gets the month as an 'int'. Eg "5"
        //
        // .getDayOfWeek() - Gets the day was a 'string'. Eg "Monday"
        // .getDayOfMonth() - Gets the day as an 'int' in range 1-31. Eg "9"
        // .getDayOfYear() - Gets the day as an 'int' in range 1-365. Eg "35"
    }

    public static void loginGUI() {
        JLabel usernameLabel;
        JLabel passwordLabel;
        JButton button;

        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        panel.setLayout(null);

        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(10, 20, 80, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(usernameLabel);

        usernameText = new JTextField(20);
        usernameText.setBounds(100, 20, 165, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(usernameText);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(passwordLabel);

        passwordText = new JPasswordField();
        passwordText.setBounds(100, 50, 165, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(passwordText);

        button = new JButton("Login");
        button.setBounds(10, 80, 80, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        button.addActionListener(new Main());
        panel.add(button);

        success = new JLabel();
        success.setBounds(10, 110, 300, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(success);

        frame.setVisible(true);
    }

    public static void mainGUI() {
        panel.removeAll();
        frame.setSize(500, 500);

        JLabel welcomeLabel;
        welcomeLabel = new JLabel("Welcome to the main GUI!");
        welcomeLabel.setBounds(150, 200, 200, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(welcomeLabel);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String username = (usernameText.getText()).toLowerCase(); //REMOVES CAPS SENSITIVITY FROM USERNAME
        String password = passwordText.getText();
        if (username.equals("username") && password.equals("Password")) { //REPLACE "username" & "Password" WITH VARIABLES FROM DATABASE IN FUTURE
            mainGUI();
        }
        else {
            success.setText("Username or Password is incorrect.");
        }
    }
}
