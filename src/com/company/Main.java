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
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        panel.setLayout(null);
        frame.setVisible(true);

        loginGUI();
    }

    public static void loginGUI() {
        JLabel usernameLabel;
        JLabel passwordLabel;
        JButton button;

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

        panel.update(panel.getGraphics());
    }

    public static void mainMenu() {
        panel.removeAll();

        JButton NewRental;
        JButton ViewRentals;
        JButton EditRentals;
        JButton DeleteRental;

        JLabel welcomeLabel;
        welcomeLabel = new JLabel("Welcome to the Rental System!");
        welcomeLabel.setBounds((width/2) - 90, (height/2) - 200, 200, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(welcomeLabel);

        NewRental = new JButton("New Rental");
        NewRental.setBounds((width/2) - 100, (height/2) - 150, 200, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        NewRental.addActionListener(new Main());
        panel.add(NewRental);

        ViewRentals = new JButton("View Rentals");
        ViewRentals.setBounds((width/2) - 100, (height/2) - 100, 200, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        ViewRentals.addActionListener(new Main());
        panel.add(ViewRentals);

        EditRentals = new JButton("Edit Rentals");
        EditRentals.setBounds((width/2) - 100, (height/2) - 50, 200, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        EditRentals.addActionListener(new Main());
        panel.add(EditRentals);

        DeleteRental = new JButton("Delete Rental");
        DeleteRental.setBounds((width/2) - 100, (height/2), 200, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        DeleteRental.addActionListener(new Main());
        panel.add(DeleteRental);

        panel.update(panel.getGraphics());
    }

    public static void newRental() {
        panel.removeAll();

        JButton ReturnToMenu;

        JLabel welcomeLabel;
        welcomeLabel = new JLabel("Welcome to the Renting Section!");
        welcomeLabel.setBounds((width/2) - 90, (height/2) - 200, 200, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(welcomeLabel);

        ReturnToMenu = new JButton("Return to Main Menu");
        ReturnToMenu.setBounds(0, 0, 250, 75); //(X-POS, Y-POS, WIDTH, HEIGHT)
        ReturnToMenu.addActionListener(new Main());
        panel.add(ReturnToMenu);

        panel.update(panel.getGraphics());
    }

    public static void viewRentals() {
        panel.removeAll();

        JButton ReturnToMenu;

        JLabel welcomeLabel;
        welcomeLabel = new JLabel("Welcome to the Viewing Section!");
        welcomeLabel.setBounds((width/2) - 90, (height/2) - 200, 200, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(welcomeLabel);

        ReturnToMenu = new JButton("Return to Main Menu");
        ReturnToMenu.setBounds(0, 0, 250, 75); //(X-POS, Y-POS, WIDTH, HEIGHT)
        ReturnToMenu.addActionListener(new Main());
        panel.add(ReturnToMenu);

        panel.update(panel.getGraphics());
    }

    public static void editRentals() {
        panel.removeAll();

        JButton ReturnToMenu;

        JLabel welcomeLabel;
        welcomeLabel = new JLabel("Welcome to the Editing Section!");
        welcomeLabel.setBounds((width/2) - 90, (height/2) - 200, 200, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(welcomeLabel);

        ReturnToMenu = new JButton("Return to Main Menu");
        ReturnToMenu.setBounds(0, 0, 250, 75); //(X-POS, Y-POS, WIDTH, HEIGHT)
        ReturnToMenu.addActionListener(new Main());
        panel.add(ReturnToMenu);

        panel.update(panel.getGraphics());
    }

    public static void deleteRental() {
        panel.removeAll();

        JButton ReturnToMenu;

        JLabel welcomeLabel;
        welcomeLabel = new JLabel("Welcome to the Deleting Section!");
        welcomeLabel.setBounds((width/2) - 90, (height/2) - 200, 200, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(welcomeLabel);

        ReturnToMenu = new JButton("Return to Main Menu");
        ReturnToMenu.setBounds(0, 0, 250, 75); //(X-POS, Y-POS, WIDTH, HEIGHT)
        ReturnToMenu.addActionListener(new Main());
        panel.add(ReturnToMenu);

        panel.update(panel.getGraphics());
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (((actionEvent.toString()).contains("cmd=Login")) == true) {
            String username = (usernameText.getText()).toLowerCase(); //REMOVES CAPS SENSITIVITY FROM USERNAME
            String password = passwordText.getText();
            if (username.equals("username") && password.equals("Password")) { //REPLACE "username" & "Password" WITH VARIABLES FROM DATABASE IN FUTURE
                mainMenu();
            }
            else {
                success.setText("Username or Password is incorrect.");
            }
        }
        else if (((actionEvent.toString()).contains("cmd=New Rental")) == true) {
            newRental();
        }
        else if (((actionEvent.toString()).contains("cmd=View Rentals")) == true) {
            viewRentals();
        }
        else if (((actionEvent.toString()).contains("cmd=Edit Rentals")) == true) {
            editRentals();
        }
        else if (((actionEvent.toString()).contains("cmd=Delete Rental")) == true) {
            deleteRental();
        }
        else if (((actionEvent.toString()).contains("cmd=Return to Main Menu")) == true) {
            mainMenu();
        }
    }
}