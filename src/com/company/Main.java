package com.company;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Main implements ActionListener {

    public static JPanel panel = new JPanel();
    public static JFrame frame = new JFrame();
    public static JTextField usernameText;
    public static JPasswordField passwordText;
    public static JLabel success;
    public static Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
    public static int width = resolution.width;
    public static int height = resolution.height;

    public static void main(String[] args) {
        loginGUI();
    }

    public static void loginGUI() {
        JLabel usernameLabel;
        JLabel passwordLabel;
        JButton button;

        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        panel.setLayout(null);

        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds((width/2) - 162, (height/2) - 150, 80, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(usernameLabel);

        usernameText = new JTextField(20);
        usernameText.setBounds((width/2) - 82, (height/2) - 150, 165, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(usernameText);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds((width/2) - 162, (height/2) - 100, 80, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(passwordLabel);

        passwordText = new JPasswordField();
        passwordText.setBounds((width/2) - 82, (height/2) - 100, 165, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(passwordText);

        button = new JButton("Login");
        button.setBounds((width/2) - 40, (height/2) - 50, 80, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        button.addActionListener(new Main());
        panel.add(button);

        success = new JLabel();
        success.setBounds((width/2) - 100, (height/2)+25, 300, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(success);

        frame.setVisible(true);
    }

    public static void mainGUI() {
        panel.removeAll();
        panel.update(panel.getGraphics());

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