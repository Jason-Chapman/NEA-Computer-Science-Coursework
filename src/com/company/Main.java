package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Main implements ActionListener {

    public static JPanel panel = new JPanel();
    public static JFrame frame = new JFrame();

    public static JTextField usernameText;
    public static JPasswordField passwordText;
    public static JLabel success;
    public static JButton ReturnToMenu;
    public static JButton Labels;
    public static JButton Buttons;
    public static JButton TextFields;
    public static JButton SANS_SERIF;
    public static JButton SERIF;
    public static JButton MONOSPACED;
    public static JSlider fontSize;
    public static JTextField textfieldExample;

    public static Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
    public static int width = resolution.width;
    public static int height = resolution.height;

    public static String Type = "Default";
    public static ComponentFontSettings labels = new ComponentFontSettings("SANS_SERIF", 12);
    public static ComponentFontSettings buttons = new ComponentFontSettings("SANS_SERIF", 12);
    public static ComponentFontSettings textfields = new ComponentFontSettings("SANS_SERIF", 12);

    public static String Username;
    public static String Password;
    public static int AccessLevel;

    public static void main(String[] args) {

        String ConnectionURL = "jdbc:sqlserver://movierentalserver.database.windows.net:1433;database=movieRentalDatabase;user=jc210762@movierentalserver;password={Cooper27};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

        ResultSet rs;

        // Open a connection
        try(Connection conn = DriverManager.getConnection(ConnectionURL);
            Statement stmt = conn.createStatement();
        )
        {
            String sql = "SELECT Username, Password, AccessLevel FROM [dbo].[tblEmployees]";
            rs = stmt.executeQuery("SELECT Username, Password, AccessLevel FROM [dbo].[tblEmployees]");

            while (rs.next()) {
                Username = rs.getString(1);
                Password = rs.getString(2);
                AccessLevel = rs.getInt(3);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        panel.setLayout(null);
        frame.setVisible(true);

        login();
    }

    public static void login() {
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
        JButton EditRentals;
        JButton DeleteRental;
        JButton FontEdit;

        JLabel welcomeLabel;
        welcomeLabel = new JLabel("Welcome to the Rental System!", SwingConstants.CENTER);
        welcomeLabel.setBounds(0, (height/2) - 200, width, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(welcomeLabel);

        NewRental = new JButton("New Rental (N)");
        NewRental.setBounds(0, 0, width/4, 50); //(X-POS, Y-POS, WIDTH, HEIGHT)
        NewRental.addActionListener(new Main());
        panel.add(NewRental);

        EditRentals = new JButton("Edit Rentals (E)");
        EditRentals.setBounds(width/4, 0, width/4, 50); //(X-POS, Y-POS, WIDTH, HEIGHT)
        EditRentals.addActionListener(new Main());
        panel.add(EditRentals);

        DeleteRental = new JButton("Delete Rental (Del)");
        DeleteRental.setBounds(width/2, 0, width/4, 50); //(X-POS, Y-POS, WIDTH, HEIGHT)
        DeleteRental.addActionListener(new Main());
        panel.add(DeleteRental);

        FontEdit = new JButton("Settings (Esc)");
        FontEdit.setBounds((width/4)*3, 0, width/4, 50); //(X-POS, Y-POS, WIDTH, HEIGHT)
        FontEdit.addActionListener(new Main());
        panel.add(FontEdit);

        panel.update(panel.getGraphics());
    }

    public static void newRental() {
        panel.removeAll();

        JLabel welcomeLabel;
        welcomeLabel = new JLabel("Welcome to the Renting Section!", SwingConstants.CENTER);
        welcomeLabel.setBounds(0, (height/2) - 200, width, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(welcomeLabel);

        ReturnToMenu = new JButton("Return to Main Menu");
        ReturnToMenu.setBounds((width/2) - 100, (height/2) - 150, 200, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        ReturnToMenu.addActionListener(new Main());
        panel.add(ReturnToMenu);

        panel.update(panel.getGraphics());
    }

    public static void editRentals() {
        panel.removeAll();

        JLabel welcomeLabel;
        welcomeLabel = new JLabel("Welcome to the Editing Section!", SwingConstants.CENTER);
        welcomeLabel.setBounds(0, (height/2) - 200, width, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(welcomeLabel);

        ReturnToMenu = new JButton("Return to Main Menu");
        ReturnToMenu.setBounds((width/2) - 100, (height/2) - 150, 200, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        ReturnToMenu.addActionListener(new Main());
        panel.add(ReturnToMenu);

        panel.update(panel.getGraphics());
    }

    public static void deleteRental() {
        panel.removeAll();

        JLabel welcomeLabel;
        welcomeLabel = new JLabel("Welcome to the Deleting Section!", SwingConstants.CENTER);
        welcomeLabel.setBounds(0, (height/2) - 200, width, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(welcomeLabel);

        ReturnToMenu = new JButton("Return to Main Menu");
        ReturnToMenu.setBounds((width/2) - 100, (height/2) - 150, 200, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        ReturnToMenu.addActionListener(new Main());
        panel.add(ReturnToMenu);

        panel.update(panel.getGraphics());
    }

    public void FontEdit() {
        panel.removeAll();

        JLabel ComponentTypeChoice;
        ComponentTypeChoice = new JLabel("Select component to modify:", SwingConstants.CENTER);
        ComponentTypeChoice.setBounds(0, (height/2) - 250, width, 50); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(ComponentTypeChoice);

        Labels = new JButton("Labels");
        Labels.setBounds((width/2) - 225, (height/2) - 200, 150, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        Labels.addActionListener(new Main());
        panel.add(Labels);

        Buttons = new JButton("Buttons");
        Buttons.setBounds((width/2) - 75, (height/2) - 200, 150, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        Buttons.addActionListener(new Main());
        panel.add(Buttons);

        TextFields = new JButton("TextFields");
        TextFields.setBounds((width/2) + 75, (height/2) - 200, 150, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        TextFields.addActionListener(new Main());
        panel.add(TextFields);

        JLabel SelectFont;
        SelectFont = new JLabel("Select Font:", SwingConstants.CENTER);
        SelectFont.setBounds(0, (height/2) - 150, width, 50); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(SelectFont);

        JLabel FontExample1;
        FontExample1 = new JLabel("AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz", SwingConstants.CENTER);
        FontExample1.setBounds(0, (height/2) + 125, width, 50); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(FontExample1);

        JLabel FontExample2;
        FontExample2 = new JLabel("0123456789", SwingConstants.CENTER);
        FontExample2.setBounds(0, (height/2) + 150, width, 50); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(FontExample2);

        SANS_SERIF = new JButton("Sans Serif");
        SANS_SERIF.setBounds((width/2) - 225, (height/2) - 100, 150, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        SANS_SERIF.addActionListener(new Main());
        panel.add(SANS_SERIF);

        SERIF = new JButton("Serif");
        SERIF.setBounds((width/2) - 75, (height/2) - 100, 150, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        SERIF.addActionListener(new Main());
        panel.add(SERIF);

        MONOSPACED = new JButton("Monospaced");
        MONOSPACED.setBounds((width/2) + 75, (height/2) - 100, 150, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        MONOSPACED.addActionListener(new Main());
        panel.add(MONOSPACED);

        ReturnToMenu = new JButton("Return to Main Menu");
        ReturnToMenu.setBounds((width/2) - 100, (height/2) - 400, 200, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        ReturnToMenu.addActionListener(new Main());
        panel.add(ReturnToMenu);

        JLabel SelectSize;
        SelectSize = new JLabel("Select Font Size:", SwingConstants.CENTER);
        SelectSize.setBounds(0, (height/2) - 50, width, 50); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(SelectSize);

        textfieldExample = new JTextField(20);
        textfieldExample.setBounds((width/2) - 100, (height/2) + 200, 200, 50); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(textfieldExample);

        fontSize = new JSlider(JSlider.HORIZONTAL, 8, 16, 12);
        fontSize.setMinorTickSpacing(1);
        fontSize.setMajorTickSpacing(2);
        fontSize.setPaintTicks(true);
        fontSize.setPaintLabels(true);
        fontSize.setLabelTable(fontSize.createStandardLabels(4));
        fontSize.setBounds((width/2) - 200, (height/2), 400, 50); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(fontSize);
        fontSize.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e) {
                if (Type == "Label") {
                    labels.setFontSize(fontSize.getValue());
                } else if (Type == "Button") {
                    buttons.setFontSize(fontSize.getValue());
                }
                else if (Type == "Textfield") {
                    textfields.setFontSize(fontSize.getValue());
                }
            }
        });

        JButton Update;
        Update = new JButton("Update");
        Update.setBounds((width/2) - 100, (height/2) + 75, 200, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        Update.addActionListener(new Main());
        panel.add(Update);

        panel.update(panel.getGraphics());
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if ((actionEvent.toString()).contains("cmd=Login")) {
            String username = (usernameText.getText()).toLowerCase(); //REMOVES CAPS SENSITIVITY FROM USERNAME
            String password = passwordText.getText();
            if (username.equals(Username) && password.equals(Password)) { //REPLACE "username" & "Password" WITH VARIABLES FROM DATABASE IN FUTURE
                mainMenu();
            }
            else {
                success.setText("Username or Password is incorrect.");
            }
        }
        else if ((actionEvent.toString()).contains("cmd=New Rental")) {
            newRental();
        }
        else if ((actionEvent.toString()).contains("cmd=Edit Rentals")) {
            editRentals();
        }
        else if ((actionEvent.toString()).contains("cmd=Delete Rental")) {
            deleteRental();
        }
        else if ((actionEvent.toString()).contains("cmd=Return to Main Menu")) {
            mainMenu();
        }
        else if ((actionEvent.toString()).contains("cmd=Quit")) {
            frame.dispose();
        }
        else if ((actionEvent.toString()).contains("cmd=Settings (Esc)")) {
            FontEdit();
        }

        else if ((actionEvent.toString()).contains("cmd=Labels")) {
            Type = "Label";
            Labels.setEnabled(false);
            Buttons.setEnabled(true);
            TextFields.setEnabled(true);
            SANS_SERIF.setEnabled(true);
            SERIF.setEnabled(true);
            MONOSPACED.setEnabled(true);
            fontSize.setEnabled(true);
            fontSize.setValue(labels.getFontSize());
            if (labels.getFont() == "SANS_SERIF") {
                SANS_SERIF.setEnabled((false));
            } else if (labels.getFont() == "SERIF") {
                SERIF.setEnabled(false);
            } else if (labels.getFont() == "MONOSPACED") {
                MONOSPACED.setEnabled(false);
            }
        }
        else if ((actionEvent.toString()).contains("cmd=Buttons")) {
            Type = "Button";
            Buttons.setEnabled(false);
            Labels.setEnabled(true);
            TextFields.setEnabled(true);
            SANS_SERIF.setEnabled(true);
            SERIF.setEnabled(true);
            MONOSPACED.setEnabled(true);
            fontSize.setEnabled(true);
            fontSize.setValue(buttons.getFontSize());
            if (buttons.getFont() == "SANS_SERIF") {
                SANS_SERIF.setEnabled((false));
            } else if (buttons.getFont() == "SERIF") {
                SERIF.setEnabled(false);
            } else if (buttons.getFont() == "MONOSPACED") {
                MONOSPACED.setEnabled(false);
            }
        }
        else if ((actionEvent.toString()).contains("cmd=TextFields")) {
            Type = "TextField";
            TextFields.setEnabled(false);
            Labels.setEnabled(true);
            Buttons.setEnabled(true);
            SANS_SERIF.setEnabled(true);
            SERIF.setEnabled(true);
            MONOSPACED.setEnabled(false);
            fontSize.setEnabled(false);
            fontSize.setValue(textfields.getFontSize());
            if (textfields.getFont() == "SANS_SERIF") {
                SANS_SERIF.setEnabled((false));
            } else if (textfields.getFont() == "SERIF") {
                SERIF.setEnabled(false);
            } else if (textfields.getFont() == "MONOSPACED") {
                MONOSPACED.setEnabled(false);
            }
        }

        else if ((actionEvent.toString()).contains("cmd=Sans Serif")) {
            SANS_SERIF.setEnabled(false);
            SERIF.setEnabled(true);
            MONOSPACED.setEnabled(true);
            if (Type == "Label") {
                labels.setFont("SANS_SERIF");
            } else if (Type == "Button") {
                buttons.setFont("SANS_SERIF");
            }
            else if (Type == "TextField") {
                MONOSPACED.setEnabled(false);
                textfields.setFont("SANS_SERIF");
            }
        }
        else if ((actionEvent.toString()).contains("cmd=Serif")) {
            SANS_SERIF.setEnabled(true);
            SERIF.setEnabled(false);
            MONOSPACED.setEnabled(true);
            if (Type == "Label") {
                labels.setFont("SERIF");
            } else if (Type == "Button") {
                buttons.setFont("SERIF");
            }
            else if (Type == "TextField") {
                MONOSPACED.setEnabled(false);
                textfields.setFont("SERIF");
            }
        }
        else if ((actionEvent.toString()).contains("cmd=Monospaced")) {
            SANS_SERIF.setEnabled(true);
            SERIF.setEnabled(true);
            MONOSPACED.setEnabled(false);
            if (Type == "Label") {
                labels.setFont("MONOSPACED");
            } else if (Type == "Button") {
                buttons.setFont("MONOSPACED");
            }
            else if (Type == "Textfield") {
                textfields.setFont("MONOSPACED");
            }
        }
        else if ((actionEvent.toString()).contains("cmd=Update")) {
            if (Type == "Label") {
                UIManager.put(Type+".font", new FontUIResource(labels.getFont(), Font.BOLD, labels.fontSize));
            } else if (Type == "Button") {
                UIManager.put(Type+".font", new FontUIResource(buttons.getFont(), Font.BOLD, buttons.fontSize));
            } else if (Type == "TextField") {
                UIManager.put(Type+".font", new FontUIResource(textfields.getFont(), Font.BOLD, textfields.fontSize));
            }
            FontEdit();
        }
    }
}