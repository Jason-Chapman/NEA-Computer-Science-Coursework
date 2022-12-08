package com.company;

//MAKE JTABLE DISPLAY DATE AS YYYY-MM-DD INSTEAD OF DD-JAN-YYYY

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Main implements ActionListener {

    public static JPanel panel = new JPanel();
    public static JFrame frame = new JFrame();
    public static Random random = new Random();

    public static Rentals tempRental = new Rentals(0, 0, 0, null, null);
    public static JTextField searchBar;
    public static JTextField usernameText;
    public static JPasswordField passwordText;
    public static JLabel success;
    public static JButton ReturnToMenu;
    public static JButton Labels;
    public static JButton Buttons;
    public static JButton TextFields;
    public static JButton Tables;
    public static JButton SANS_SERIF;
    public static JButton SERIF;
    public static JButton MONOSPACED;
    public static JSlider fontSize;
    public static ArrayList<Rentals> RentalList = new ArrayList<Rentals>();
    public static ArrayList<String> Columns = new ArrayList<String>();
    public static RentalsTableModel model;
    public static JTable Display;
    private TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(Display.getModel());

    public static Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
    public static int width = resolution.width;
    public static int height = resolution.height;

    public static String Type = "Default";
    public static ComponentFontSettings labels = new ComponentFontSettings("SANS_SERIF", 12);
    public static ComponentFontSettings buttons = new ComponentFontSettings("SANS_SERIF", 12);
    public static ComponentFontSettings textfields = new ComponentFontSettings("SANS_SERIF", 12);
    public static ComponentFontSettings tables = new ComponentFontSettings("SANS_SERIF", 12);

    public static String Username;
    public static String Password;
    public static int AccessLevel;

    public static void main(String[] args) {
        String ConnectionURL = "jdbc:sqlserver://movierentalserver.database.windows.net:1433;database=movieRentalDatabase;user=jc210762@movierentalserver;password={Cooper27};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        ResultSet rs;

        // Open a connection
        try(Connection conn = DriverManager.getConnection(ConnectionURL);
            Statement stmt = conn.createStatement();
        ) {
            rs = stmt.executeQuery("SELECT Username, Password, AccessLevel FROM [dbo].[tblEmployees]");

            while (rs.next()) {
                Username = rs.getString(1);
                Password = rs.getString(2);
                AccessLevel = rs.getInt(3);
            }

            rs = stmt.executeQuery("SELECT * FROM [dbo].[Rentals]");
            while (rs.next()) {
                Rentals rental = new Rentals(0, 0, 0, null, null);
                rental.setRentalID(rs.getInt(1));
                rental.setCustomerID(rs.getInt(2));
                rental.setMovieID(rs.getInt(3));
                rental.setDateRented(rs.getDate(4));
                System.out.println("Rented: "+(rs.getDate(4)).toString());
                rental.setDateDue(rs.getDate(5));
                System.out.println("Due: "+rs.getDate(5)+"\n");
                RentalList.add(rental);
            }
            
            rs = stmt.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = N'Rentals'");
            while (rs.next()) {
                Columns.add(rs.getString(1));
            }

//            GENERATES RECORDS FOR RENTAL TABLE IN DATABASE
//======================================================================================================================================================================================
//            int RentalID = 999;
//            int CustomerID, MovieID;
//            int Yrented, Mrented, Drented, Ydue, Mdue, Ddue;
//            for (int i = 1; i < 51; i++) {
//                Yrented = random.nextInt(2)+2022;
//                Mrented = random.nextInt(12-1)+1;
//                Drented = random.nextInt(28-1)+1;
//                Ydue = random.nextInt(2)+2022;
//                Mdue = random.nextInt(12-1)+1;
//                Ddue = random.nextInt(28-1)+1;
//                while (Ydue < Yrented) {
//                    Yrented = random.nextInt(2023-2022)+2022;
//                    Ydue = random.nextInt(2023-2022)+2022;
//                }
//                while (Ydue == Yrented && Mdue < Mrented) {
//                    Mrented = random.nextInt(12-1)+1;
//                    Mdue = random.nextInt(12-1)+1;
//                }
//                while (Ydue == Yrented && Mdue == Mrented && Ddue < Drented) {
//                    Drented = random.nextInt(28-1)+1;
//                    Ddue = random.nextInt(28-1)+1;
//                }
//
//                RentalID++;
//                CustomerID = random.nextInt(1049-1000)+1000;
//                MovieID = random.nextInt(1049-1001)+1001;
//                stmt.execute("INSERT INTO [dbo].[Rentals] VALUES ("+RentalID+", "+CustomerID+", "+MovieID+", '"+Yrented+"-"+Mrented+"-"+Drented+"', '"+Ydue+"-"+Mdue+"-"+Ddue+"')");
//            }
//======================================================================================================================================================================================


//            GENERATES RECORDS FOR CUSTOMER TABLE IN DATABASE
//==========================================================================================================================================================
//            String PhoneNumber;
//            int CustomerID = 999;
//            String Name;
//            for (int i = 1; i < 51; i++) {
//                PhoneNumber = ""+Integer.toString(random.nextInt(99999 - 10000)+10000)+" "+Integer.toString(random.nextInt(999999 - 100000)+100000)+"";
//                CustomerID++;
//                Name = "CustomerName"+i+"".toString();
//                stmt.execute("INSERT INTO [dbo].[tblCustomers] VALUES ("+CustomerID+", '"+Name+"', '"+PhoneNumber+"')");
//            }
//==========================================================================================================================================================

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        model = new RentalsTableModel(RentalList);
        Display = new JTable(model);

        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setLayout(null);
        frame.setVisible(true);

        UIManager.put(Type + ".font", new FontUIResource("SANS_SERIF", Font.BOLD, 12));
        UIManager.put(Type + ".font", new FontUIResource("SANS_SERIF", Font.BOLD, 12));
        UIManager.put(Type + ".font", new FontUIResource("SANS_SERIF", Font.BOLD, 12));
        Display.setFont(new FontUIResource("SANS_SERIF", Font.PLAIN, 12));
        Display.setRowHeight(12);
        Display.getTableHeader().setFont(new Font("SANS_SERIF", Font.BOLD, 12));

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

    public void mainMenu() {
        panel.removeAll();

        JButton NewRental;
        JButton EditRentals;
        JButton DeleteRental;
        JButton FontEdit;

        searchBar = new JTextField(255);
        searchBar.setBounds(0, 50, width-184, 25);
        panel.add(searchBar);

        JButton Filter = new JButton("Search");
        Filter.setBounds(width-184, 50, 84, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        Filter.addActionListener(new Main());
        panel.add(Filter);

        JButton Clear = new JButton("Clear");
        Clear.setBounds(width-100, 50, 84, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        Clear.addActionListener(new Main());
        panel.add(Clear);

        JScrollPane scrollPane = new JScrollPane(Display);
        scrollPane.setBounds(0, 75, width-15, height-120); //(X-POS, Y-POS, WIDTH, HEIGHT)
        Display.setFillsViewportHeight(true);
        Display.setPreferredScrollableViewportSize(new Dimension(width, height-50));
        Display.setAutoCreateRowSorter(true);
        Display.setRowSorter(rowSorter);
        panel.add(scrollPane);

//
//        searchBar.getDocument().addDocumentListener(new DocumentListener(){
//
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                String text = searchBar.getText();
//
//                if (text.trim().length() == 0) {
//                    rowSorter.setRojc201wFilter(null);
//                } else {
//                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
//                }
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                String text = searchBar.getText();
//
//                if (text.trim().length() == 0) {
//                    rowSorter.setRowFilter(null);
//                } else {
//                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
//                }
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//
//        });

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
        FontEdit.setBounds((width/4)*3, 0, (width/4)-16, 50); //(X-POS, Y-POS, WIDTH, HEIGHT)
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

        JLabel tempRentalID;
        tempRentalID = new JLabel("Current RentalID: "+tempRental.getRentalID(), SwingConstants.CENTER);
        tempRentalID.setBounds(-280, (height/2) - 200, width, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(tempRentalID);

        JLabel tempCustomerID;
        tempCustomerID = new JLabel("Current CustomerID: "+tempRental.getCustomerID(), SwingConstants.CENTER);
        tempCustomerID.setBounds(-270, (height/2) - 175, width, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(tempCustomerID);

        JLabel tempMovieID;
        tempMovieID = new JLabel("Current MovieID: "+tempRental.getMovieID(), SwingConstants.CENTER);
        tempMovieID.setBounds(-280, (height/2) - 150, width, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(tempMovieID);

        JLabel tempDateRented;
        tempDateRented = new JLabel("Current Date Rented: "+tempRental.getDateRented(), SwingConstants.CENTER);
        tempDateRented.setBounds(-250, (height/2) - 125, width, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(tempDateRented);

        JLabel tempDateDue;
        tempDateDue = new JLabel("Current Date Due: "+tempRental.getDateDue(), SwingConstants.CENTER);
        tempDateDue.setBounds(-259, (height/2) - 100, width, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(tempDateDue);

        ReturnToMenu = new JButton("Return to Main Menu");
        ReturnToMenu.setBounds((width/2) - 100, (height/2), 200, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
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
        Labels.setBounds((width/2) - 300, (height/2) - 200, 150, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        Labels.addActionListener(new Main());
        panel.add(Labels);

        Buttons = new JButton("Buttons");
        Buttons.setBounds((width/2) - 150, (height/2) - 200, 150, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        Buttons.addActionListener(new Main());
        panel.add(Buttons);

        TextFields = new JButton("TextFields");
        TextFields.setBounds((width/2), (height/2) - 200, 150, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        TextFields.addActionListener(new Main());
        panel.add(TextFields);

        Tables = new JButton("Tables");
        Tables.setBounds((width/2) + 150, (height/2) - 200, 150, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        Tables.addActionListener(new Main());
        panel.add(Tables);

        JLabel SelectFont;
        SelectFont = new JLabel("Select Font:", SwingConstants.CENTER);
        SelectFont.setBounds(0, (height/2) - 150, width, 50); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(SelectFont);

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

        fontSize = new JSlider(JSlider.HORIZONTAL, 4, 30, 12);
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
                else if (Type == "Table") {
                    tables.setFontSize(fontSize.getValue());
                }
            }
        });

        JButton Update;
        Update = new JButton("Update");
        Update.setBounds((width/2) - 100, (height/2) + 75, 200, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        Update.addActionListener(new Main());
        panel.add(Update);

        JButton Reset;
        Reset = new JButton("Reset Selected");
        Reset.setBounds((width/2) - 100, (height/2) + 115, 200, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        Reset.addActionListener(new Main());
        panel.add(Reset);

        JButton RESET;
        RESET = new JButton("Reset All");
        RESET.setBounds((width/2) - 100, (height/2) + 145, 200, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        RESET.addActionListener(new Main());
        panel.add(RESET);

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
            tempRental.setRentalID((Integer) model.getValueAt(Display.getSelectedRow(), 0));
            tempRental.setCustomerID((Integer) model.getValueAt(Display.getSelectedRow(), 1));
            tempRental.setMovieID((Integer) model.getValueAt(Display.getSelectedRow(), 2));
            tempRental.setDateRented((java.util.Date) model.getValueAt(Display.getSelectedRow(), 3));
            tempRental.setDateDue((java.util.Date) model.getValueAt(Display.getSelectedRow(), 4));

            System.out.println("" + tempRental.getRentalID() + "\n" + tempRental.getCustomerID() + "\n" +  tempRental.getMovieID() + "\n" + tempRental.getDateRented() + "\n" + tempRental.getDateDue());
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
            Tables.setEnabled(true);
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
            Tables.setEnabled(true);
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
            Tables.setEnabled(true);
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

        else if ((actionEvent.toString()).contains("cmd=Tables")) {
            Type = "Table";
            Tables.setEnabled(false);
            TextFields.setEnabled(true);
            Labels.setEnabled(true);
            Buttons.setEnabled(true);
            SANS_SERIF.setEnabled(true);
            SERIF.setEnabled(true);
            MONOSPACED.setEnabled(true);
            fontSize.setEnabled(true);
            fontSize.setValue(tables.getFontSize());
            if (tables.getFont() == "SANS_SERIF") {
                SANS_SERIF.setEnabled((false));
            } else if (tables.getFont() == "SERIF") {
                SERIF.setEnabled(false);
            } else if (tables.getFont() == "MONOSPACED") {
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
            else if (Type == "Table") {
                tables.setFont("SANS_SERIF");
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
            else if (Type == "Table") {
                tables.setFont("SERIF");
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
            else if (Type == "Table") {
                tables.setFont("MONOSPACED");
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
            else if (Type == "Table") {
                Display.setFont(new FontUIResource(tables.getFont(), Font.PLAIN, tables.getFontSize()));
                Display.setRowHeight(tables.getFontSize());
                Display.getTableHeader().setFont(new Font(tables.getFont(), Font.BOLD, tables.getFontSize()));
            }
            FontEdit();
        }
        else if ((actionEvent.toString()).contains("cmd=Reset Selected")) {
            if (Type == "Label") {
                labels.setFont("SANS_SERIF");
                labels.setFontSize(12);
                UIManager.put(Type+".font", new FontUIResource(labels.getFont(), Font.BOLD, labels.fontSize));
            } else if (Type == "Button") {
                buttons.setFont("SANS_SERIF");
                buttons.setFontSize(12);
                UIManager.put(Type+".font", new FontUIResource(buttons.getFont(), Font.BOLD, buttons.fontSize));
            }
            else if (Type == "Textfield") {
                textfields.setFont("SANS_SERIF");
                textfields.setFontSize(12);
                UIManager.put(Type+".font", new FontUIResource(textfields.getFont(), Font.BOLD, textfields.fontSize));
            }
            else if (Type == "Table") {
                tables.setFont("SANS_SERIF");
                tables.setFontSize(12);
                Display.setFont(new FontUIResource(tables.getFont(), Font.PLAIN, tables.getFontSize()));
                Display.setRowHeight(tables.getFontSize());
                Display.getTableHeader().setFont(new Font(tables.getFont(), Font.BOLD, tables.getFontSize()));
            }
            FontEdit();
        }
        else if ((actionEvent.toString()).contains("cmd=Reset All")) {
            buttons.setFont("SANS_SERIF");
            textfields.setFont("SANS_SERIF");
            labels.setFont("SANS_SERIF");
            tables.setFont("SANS_SERIF");
            buttons.setFontSize(12);
            textfields.setFontSize(12);
            labels.setFontSize(12);
            tables.setFontSize(12);
            UIManager.put("Button.font", new FontUIResource(buttons.getFont(), Font.BOLD, buttons.fontSize));
            UIManager.put("Label.font", new FontUIResource(labels.getFont(), Font.BOLD, labels.fontSize));
            UIManager.put("TextField.font", new FontUIResource(textfields.getFont(), Font.BOLD, textfields.fontSize));
            Display.setFont(new FontUIResource(tables.getFont(), Font.PLAIN, tables.getFontSize()));
            Display.setRowHeight(tables.getFontSize());
            Display.getTableHeader().setFont(new Font(tables.getFont(), Font.BOLD, tables.getFontSize()));
            FontEdit();
        }
        else if ((actionEvent.toString()).contains("cmd=Search")) {
            ArrayList<Rentals> TempSortList = new ArrayList<Rentals>();
            RentalsTableModel tempModel;
            Display.setModel(model);
            Display.repaint();
            for(int i = 0; i < Display.getRowCount(); i++){//For each row
                for(int j = 0; j < Display.getColumnCount(); j++){//For each column in that row
                    if((Display.getValueAt(i, j)).toString().equals(searchBar.getText())){//Search the model
                        Rentals tempRental = new Rentals((Integer) Display.getValueAt(i, 0), (Integer) Display.getValueAt(i, 1), (Integer) Display.getValueAt(i, 2), (Date) Display.getValueAt(i, 3), (Date) Display.getValueAt(i, 4));
                        TempSortList.add(tempRental);
                    }
                }
            }
            tempModel = new RentalsTableModel(TempSortList);
            Display.setModel(tempModel);
            Display.repaint();
        }
        else if ((actionEvent.toString()).contains("cmd=Clear")) {
            Display.setModel(model);
            Display.repaint();
        }
    }
}