package com.company;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.*;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
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
    public static JButton AddRental;
    public static JButton DeleteRental;
    public static JSlider fontSize;
    public static ArrayList<Rentals> RentalList = new ArrayList<Rentals>();
    public static ArrayList<String> Columns = new ArrayList<String>();
    public static RentalsTableModel model;
    public static JTable table;
    public static JTextField newRentalRentalIDinput;
    public static JTextField newRentalMovieIDinput;
    public static JTextField newRentalCustomerIDinput;
    public static JTextField newRentalDateRentedYearinput;
    public static JTextField newRentalDateRentedMonthinput;
    public static JTextField newRentalDateRentedDayinput;
    public static JTextField newRentalDateDueYearinput;
    public static JTextField newRentalDateDueMonthinput;
    public static JTextField newRentalDateDueDayinput;
    public static JTextField rentalIDinput;
    private TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table.getModel());

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
    public static String ConnectionURL = "jdbc:sqlserver://movierentalserver.database.windows.net:1433;database=movieRentalDatabase;user=jc210762@movierentalserver;password={Cooper27};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
    public static String databasePasswordHashed;

    //////////////////////////////////////////////////////////////////
    // HASHING ALGORITHM AND COMPARISON COURTESY OF https://howtodoinjava.com/java/java-security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
    //////////////////////////////////////////////////////////////////
    private static String generateStrongPasswordHash(String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    private static boolean validatePassword(String originalPassword, String storedPassword)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);

        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(),
                salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }
    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i < bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    private static String toHex(byte[] array) throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);

        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }
    //////////////////////////////////////////////////////////////////

    public static void cloudDatabaseDownload() {
        ResultSet rs;
        RentalList.clear();

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
                rental.setDateDue(rs.getDate(5));
                RentalList.add(rental);
            }

            rs = stmt.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = N'Rentals'");
            while (rs.next()) {
                Columns.add(rs.getString(1));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        table = new JTable(model = new RentalsTableModel(RentalList));
    }

    public static void localDatabaseUpload() {
        // Open a connection
        try(Connection conn = DriverManager.getConnection(ConnectionURL);
            Statement stmt = conn.createStatement();
        ) {
            stmt.executeUpdate("TRUNCATE TABLE [dbo].[Rentals]");

            for (int i = 0; i < RentalList.size(); i++) {
                int a = RentalList.get(i).getRentalID();
                int b = RentalList.get(i).getCustomerID();
                int c = RentalList.get(i).getMovieID();
                java.sql.Date d = RentalList.get(i).getDateRented();
                java.sql.Date e = RentalList.get(i).getDateDue();

                stmt.executeUpdate("INSERT INTO [dbo].[Rentals](RentalID, CustomerID, MovieID, DateRented, DateDue) VALUES("+RentalList.get(i).getRentalID()+", "+RentalList.get(i).getCustomerID()+", "+RentalList.get(i).getMovieID()+", \'"+RentalList.get(i).getDateRented()+"\', \'"+RentalList.get(i).getDateDue()+"\')");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static TableCellRenderer tableCellRenderer = new DefaultTableCellRenderer() {
        SimpleDateFormat f = new SimpleDateFormat("YYYY-MM-dd");
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof Date) {
                value = f.format(value);
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    };

    public static void main(String[] args) {
        cloudDatabaseDownload();

        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setLayout(null);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                localDatabaseUpload();
                System.exit(0);
            }
        });

        UIManager.put(Type + ".font", new FontUIResource("SANS_SERIF", Font.BOLD, 12));
        UIManager.put(Type + ".font", new FontUIResource("SANS_SERIF", Font.BOLD, 12));
        UIManager.put(Type + ".font", new FontUIResource("SANS_SERIF", Font.BOLD, 12));
        table.setFont(new FontUIResource("SANS_SERIF", Font.PLAIN, 12));
        table.setRowHeight(12);
        table.getTableHeader().setFont(new Font("SANS_SERIF", Font.BOLD, 12));
        table.getColumnModel().getColumn(3).setCellRenderer(tableCellRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(tableCellRenderer);

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

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 75, width-15, height-120); //(X-POS, Y-POS, WIDTH, HEIGHT)
        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(width, height-50));
        table.setAutoCreateRowSorter(true);
        table.setRowSorter(rowSorter);
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

        NewRental = new JButton("Create New Rental");
        NewRental.setBounds(0, 0, width/4, 50); //(X-POS, Y-POS, WIDTH, HEIGHT)
        NewRental.addActionListener(new Main());
        panel.add(NewRental);

        EditRentals = new JButton("Edit Selected Rental");
        EditRentals.setBounds(width/4, 0, width/4, 50); //(X-POS, Y-POS, WIDTH, HEIGHT)
        EditRentals.addActionListener(new Main());
        panel.add(EditRentals);

        DeleteRental = new JButton("Delete Selected Rental");
        DeleteRental.setBounds(width/2, 0, width/4, 50); //(X-POS, Y-POS, WIDTH, HEIGHT)
        DeleteRental.addActionListener(new Main());
        panel.add(DeleteRental);

        FontEdit = new JButton("Settings");
        FontEdit.setBounds((width/4)*3, 0, (width/4)-16, 50); //(X-POS, Y-POS, WIDTH, HEIGHT)
        FontEdit.addActionListener(new Main());
        panel.add(FontEdit);

        panel.update(panel.getGraphics());
    }

    public static void newRental() {
        panel.removeAll();

        JLabel rentalID;
        rentalID = new JLabel("RentalID:", SwingConstants.CENTER);
        rentalID.setBounds((width/2) - 82, (height/2) - 200, 165, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(rentalID);

        JLabel movieID;
        movieID = new JLabel("MovieID:", SwingConstants.CENTER);
        movieID.setBounds((width/2) - 82, (height/2) - 100, 165, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(movieID);

        JLabel customerID;
        customerID = new JLabel("CustomerID:", SwingConstants.CENTER);
        customerID.setBounds((width/2) - 82, (height/2) - 150, 165, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(customerID);

        JLabel dateRented;
        dateRented = new JLabel("(YYYY-MM-DD) Date Rented:", SwingConstants.CENTER);
        dateRented.setBounds((width/2) - 82, (height/2) -50, 165, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(dateRented);

        JLabel dateDue;
        dateDue = new JLabel("(YYYY-MM-DD) Date Due:", SwingConstants.CENTER);
        dateDue.setBounds((width/2) - 82, (height/2), 165, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(dateDue);

        newRentalRentalIDinput = new JTextField(20);
        newRentalRentalIDinput.setBounds((width/2) - 82, (height/2) - 175, 165, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        newRentalRentalIDinput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if(newRentalRentalIDinput.getText().length()>=4&&!(evt.getKeyChar()== KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
                    evt.consume();
                }
            }
        });
        panel.add(newRentalRentalIDinput);

        newRentalMovieIDinput = new JTextField(20);
        newRentalMovieIDinput.setBounds((width/2) - 82, (height/2) - 125, 165, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        newRentalMovieIDinput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if(newRentalMovieIDinput.getText().length()>=4&&!(evt.getKeyChar()== KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
                    evt.consume();
                }
            }
        });
        panel.add(newRentalMovieIDinput);

        newRentalCustomerIDinput = new JTextField(20);
        newRentalCustomerIDinput.setBounds((width/2) - 82, (height/2)-75, 165, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        newRentalCustomerIDinput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if(newRentalCustomerIDinput.getText().length()>=4&&!(evt.getKeyChar()== KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
                    evt.consume();
                }
            }
        });
        panel.add(newRentalCustomerIDinput);

        newRentalDateRentedYearinput = new JTextField(4);
        newRentalDateRentedYearinput.setBounds((width/2) - 82, (height/2) -25, 55, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        newRentalDateRentedYearinput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if(newRentalDateRentedYearinput.getText().length()>=4&&!(evt.getKeyChar()== KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
                    evt.consume();
                }
            }
        });
        panel.add(newRentalDateRentedYearinput);

        newRentalDateRentedMonthinput = new JTextField(2);
        newRentalDateRentedMonthinput.setBounds((width/2) - 27, (height/2)-25, 55, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        newRentalDateRentedMonthinput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if(newRentalDateRentedMonthinput.getText().length()>=2&&!(evt.getKeyChar()== KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
                    evt.consume();
                }
            }
        });
        panel.add(newRentalDateRentedMonthinput);

        newRentalDateRentedDayinput = new JTextField(2);
        newRentalDateRentedDayinput.setBounds((width/2) + 28, (height/2)-25, 55, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        newRentalDateRentedDayinput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if(newRentalDateRentedDayinput.getText().length()>=2&&!(evt.getKeyChar()== KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
                    evt.consume();
                }
            }
        });
        panel.add(newRentalDateRentedDayinput);

        newRentalDateDueYearinput = new JTextField(4);
        newRentalDateDueYearinput.setBounds((width/2) - 82, (height/2) + 25, 55, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        newRentalDateDueYearinput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if(newRentalDateDueYearinput.getText().length()>=4&&!(evt.getKeyChar()== KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
                    evt.consume();
                }
            }
        });
        panel.add(newRentalDateDueYearinput);

        newRentalDateDueMonthinput = new JTextField(2);
        newRentalDateDueMonthinput.setBounds((width/2) - 27, (height/2) + 25, 55, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        newRentalDateDueMonthinput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if(newRentalDateDueMonthinput.getText().length()>=2&&!(evt.getKeyChar()== KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
                    evt.consume();
                }
            }
        });
        panel.add(newRentalDateDueMonthinput);

        newRentalDateDueDayinput = new JTextField(2);
        newRentalDateDueDayinput.setBounds((width/2) + 28, (height/2) + 25, 55, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        newRentalDateDueDayinput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if(newRentalDateDueDayinput.getText().length()>=2&&!(evt.getKeyChar()== KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
                    evt.consume();
                }
            }
        });
        panel.add(newRentalDateDueDayinput);

        AddRental = new JButton("Add Rental");
        AddRental.setBounds((width/2) - 100, (height/2) + 125, 200, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        AddRental.addActionListener(new Main());
        panel.add(AddRental);

        ReturnToMenu = new JButton("Return to Main Menu");
        ReturnToMenu.setBounds((width/2) - 100, (height/2) + 150, 200, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        ReturnToMenu.addActionListener(new Main());
        panel.add(ReturnToMenu);

        panel.update(panel.getGraphics());
    }

    public static void editRentals() {
        panel.removeAll();

        JLabel tempRentalID;
        tempRentalID = new JLabel("Current RentalID: "+tempRental.getRentalID(), SwingConstants.CENTER);
        tempRentalID.setBounds(-320, (height/2) - 175, width, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(tempRentalID);

        JLabel tempCustomerID;
        tempCustomerID = new JLabel("Current CustomerID: "+tempRental.getCustomerID(), SwingConstants.CENTER);
        tempCustomerID.setBounds(-310, (height/2) - 125, width, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(tempCustomerID);

        JLabel tempMovieID;
        tempMovieID = new JLabel("Current MovieID: "+tempRental.getMovieID(), SwingConstants.CENTER);
        tempMovieID.setBounds(-320, (height/2) - 75, width, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(tempMovieID);

        JLabel tempDateRented;
        tempDateRented = new JLabel("(YYYY-MM-DD) Current Date Rented: "+tempRental.getDateRented(), SwingConstants.CENTER);
        tempDateRented.setBounds(-250, (height/2) - 25, width, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(tempDateRented);

        JLabel tempDateDue;
        tempDateDue = new JLabel("(YYYY-MM-DD) Current Date Due: "+tempRental.getDateDue(), SwingConstants.CENTER);
        tempDateDue.setBounds(-259, (height/2) + 25, width, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(tempDateDue);

        JLabel rentalID;
        rentalID = new JLabel("New RentalID:", SwingConstants.CENTER);
        rentalID.setBounds(0, (height/2) - 200, width, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(rentalID);

        JLabel movieID;
        movieID = new JLabel("New MovieID:", SwingConstants.CENTER);
        movieID.setBounds(0, (height/2) - 100, width, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(movieID);

        JLabel customerID;
        customerID = new JLabel("New CustomerID:", SwingConstants.CENTER);
        customerID.setBounds(0, (height/2) -150, width, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(customerID);

        JLabel dateRented;
        dateRented = new JLabel("(YYYY-MM-DD) New Date Rented:", SwingConstants.CENTER);
        dateRented.setBounds(0, (height/2) -50, width, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(dateRented);

        JLabel dateDue;
        dateDue = new JLabel("(YYYY-MM-DD) New Date Due:", SwingConstants.CENTER);
        dateDue.setBounds(0, (height/2), width, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(dateDue);

        newRentalRentalIDinput = new JTextField(20);
        newRentalRentalIDinput.setBounds((width/2) - 82, (height/2) - 175, 165, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        newRentalRentalIDinput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if(newRentalRentalIDinput.getText().length()>=4&&!(evt.getKeyChar()== KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
                    evt.consume();
                }
            }
        });
        panel.add(newRentalRentalIDinput);

        newRentalMovieIDinput = new JTextField(20);
        newRentalMovieIDinput.setBounds((width/2) - 82, (height/2) - 75, 165, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        newRentalMovieIDinput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if(newRentalMovieIDinput.getText().length()>=4&&!(evt.getKeyChar()== KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
                    evt.consume();
                }
            }
        });
        panel.add(newRentalMovieIDinput);

        newRentalCustomerIDinput = new JTextField(20);
        newRentalCustomerIDinput.setBounds((width/2) - 82, (height/2)-125, 165, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        newRentalCustomerIDinput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if(newRentalCustomerIDinput.getText().length()>=4&&!(evt.getKeyChar()== KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
                    evt.consume();
                }
            }
        });
        panel.add(newRentalCustomerIDinput);

        newRentalDateRentedYearinput = new JTextField(4);
        newRentalDateRentedYearinput.setBounds((width/2) - 82, (height/2) -25, 55, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        newRentalDateRentedYearinput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if(newRentalDateRentedYearinput.getText().length()>=4&&!(evt.getKeyChar()== KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
                    evt.consume();
                }
            }
        });
        panel.add(newRentalDateRentedYearinput);

        newRentalDateRentedDayinput = new JTextField(2);
        newRentalDateRentedDayinput.setBounds((width/2) + 28, (height/2)-25, 55, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        newRentalDateRentedDayinput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if(newRentalDateRentedDayinput.getText().length()>=2&&!(evt.getKeyChar()== KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
                    evt.consume();
                }
            }
        });
        panel.add(newRentalDateRentedDayinput);

        newRentalDateRentedMonthinput = new JTextField(2);
        newRentalDateRentedMonthinput.setBounds((width/2) - 27, (height/2)-25, 55, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        newRentalDateRentedMonthinput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if(newRentalDateRentedMonthinput.getText().length()>=2&&!(evt.getKeyChar()== KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
                    evt.consume();
                }
            }
        });
        panel.add(newRentalDateRentedMonthinput);

        newRentalDateDueYearinput = new JTextField(4);
        newRentalDateDueYearinput.setBounds((width/2) - 82, (height/2) + 25, 55, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        newRentalDateDueYearinput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if(newRentalDateDueYearinput.getText().length()>=4&&!(evt.getKeyChar()== KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
                    evt.consume();
                }
            }
        });
        panel.add(newRentalDateDueYearinput);

        newRentalDateDueDayinput = new JTextField(2);
        newRentalDateDueDayinput.setBounds((width/2) + 28, (height/2) + 25, 55, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        newRentalDateDueDayinput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if(newRentalDateDueDayinput.getText().length()>=2&&!(evt.getKeyChar()== KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
                    evt.consume();
                }
            }
        });
        panel.add(newRentalDateDueDayinput);

        newRentalDateDueMonthinput = new JTextField(2);
        newRentalDateDueMonthinput.setBounds((width/2) - 27, (height/2) + 25, 55, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        newRentalDateDueMonthinput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if(newRentalDateDueMonthinput.getText().length()>=2&&!(evt.getKeyChar()== KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)) {
                    evt.consume();
                }
            }
        });
        panel.add(newRentalDateDueMonthinput);

        AddRental = new JButton("Update Rental");
        AddRental.setBounds((width/2) - 100, (height/2) + 125, 200, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        AddRental.addActionListener(new Main());
        panel.add(AddRental);

        ReturnToMenu = new JButton("Return to Main Menu");
        ReturnToMenu.setBounds((width/2) - 100, (height/2) + 150, 200, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        ReturnToMenu.addActionListener(new Main());
        panel.add(ReturnToMenu);

        panel.update(panel.getGraphics());
    }

    public static void deleteRental() {
        panel.removeAll();

        JLabel tempRentalID;
        tempRentalID = new JLabel("RentalID: "+tempRental.getRentalID(), SwingConstants.CENTER);
        tempRentalID.setBounds(0, (height/2) - 175, width, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(tempRentalID);

        JLabel tempCustomerID;
        tempCustomerID = new JLabel("CustomerID: "+tempRental.getCustomerID(), SwingConstants.CENTER);
        tempCustomerID.setBounds(0, (height/2) - 125, width, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(tempCustomerID);

        JLabel tempMovieID;
        tempMovieID = new JLabel("MovieID: "+tempRental.getMovieID(), SwingConstants.CENTER);
        tempMovieID.setBounds(0, (height/2) - 75, width, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(tempMovieID);

        JLabel tempDateRented;
        tempDateRented = new JLabel("(YYYY-MM-DD) Date Rented: "+tempRental.getDateRented(), SwingConstants.CENTER);
        tempDateRented.setBounds(0, (height/2) - 25, width, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(tempDateRented);

        JLabel tempDateDue;
        tempDateDue = new JLabel("(YYYY-MM-DD) Date Due: "+tempRental.getDateDue(), SwingConstants.CENTER);
        tempDateDue.setBounds(0, (height/2) + 25, width, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        panel.add(tempDateDue);

        DeleteRental = new JButton("Delete Rental");
        DeleteRental.setBounds((width/2) - 100, (height/2) + 75, 200, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        DeleteRental.addActionListener(new Main());
        panel.add(DeleteRental);

        ReturnToMenu = new JButton("Return to Main Menu");
        ReturnToMenu.setBounds((width/2) - 100, (height/2) + 100, 200, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
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

        JButton Apply;
        Apply = new JButton("Apply");
        Apply.setBounds((width/2) - 100, (height/2) + 75, 200, 25); //(X-POS, Y-POS, WIDTH, HEIGHT)
        Apply.addActionListener(new Main());
        panel.add(Apply);

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
            try {
                databasePasswordHashed = generateStrongPasswordHash(Password);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
            try {
                if (username.equals(Username) && validatePassword(passwordText.getText(), databasePasswordHashed) == true) {
                    mainMenu();
                }
                else {
                    success.setText("Username or Password is incorrect.");
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }
        else if ((actionEvent.toString()).contains("cmd=Create New Rental")) {
            newRental();
        }
        else if ((actionEvent.toString()).contains("cmd=Edit Selected Rental")) {
            tempRental.setRentalID((Integer) model.getValueAt(table.getRowSorter().convertRowIndexToModel(table.getSelectedRow()), 0));
            tempRental.setCustomerID((Integer) model.getValueAt(table.getRowSorter().convertRowIndexToModel(table.getSelectedRow()), 1));
            tempRental.setMovieID((Integer) model.getValueAt(table.getRowSorter().convertRowIndexToModel(table.getSelectedRow()), 2));
            tempRental.setDateRented((java.sql.Date) model.getValueAt(table.getRowSorter().convertRowIndexToModel(table.getSelectedRow()), 3));
            tempRental.setDateDue((java.sql.Date) model.getValueAt(table.getRowSorter().convertRowIndexToModel(table.getSelectedRow()), 4));
            editRentals();
        }
        else if ((actionEvent.toString()).contains("cmd=Delete Selected Rental")) {
            tempRental.setRentalID((Integer) model.getValueAt(table.getRowSorter().convertRowIndexToModel(table.getSelectedRow()), 0));
            tempRental.setCustomerID((Integer) model.getValueAt(table.getRowSorter().convertRowIndexToModel(table.getSelectedRow()), 1));
            tempRental.setMovieID((Integer) model.getValueAt(table.getRowSorter().convertRowIndexToModel(table.getSelectedRow()), 2));
            tempRental.setDateRented((java.sql.Date) model.getValueAt(table.getRowSorter().convertRowIndexToModel(table.getSelectedRow()), 3));
            tempRental.setDateDue((java.sql.Date) model.getValueAt(table.getRowSorter().convertRowIndexToModel(table.getSelectedRow()), 4));
            deleteRental();
        }
        else if ((actionEvent.toString()).contains("cmd=Return to Main Menu")) {
            mainMenu();
        }
        else if ((actionEvent.toString()).contains("cmd=Quit")) {
            frame.dispose();
        }
        else if ((actionEvent.toString()).contains("cmd=Settings")) {
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
        else if ((actionEvent.toString()).contains("cmd=Update Rental")) {
            int input = JOptionPane.showConfirmDialog(null, "Do you want to update this rental with these values?", "Confirm",
                    JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
            if (input == 0) {
                localDatabaseUpload();

                java.sql.Date tempDateRented = java.sql.Date.valueOf("" + newRentalDateRentedYearinput.getText() + "-" + newRentalDateRentedMonthinput.getText() + "-" + newRentalDateRentedDayinput.getText() + "");
                java.sql.Date tempDateDue = java.sql.Date.valueOf("" + newRentalDateDueYearinput.getText() + "-" + newRentalDateDueMonthinput.getText() + "-" + newRentalDateDueDayinput.getText() + "");

                // Open a connection
                try (Connection conn = DriverManager.getConnection(ConnectionURL);
                     Statement stmt = conn.createStatement();
                ) {
                    stmt.executeUpdate("UPDATE [dbo].[Rentals] SET " +
                            "RentalID = " + newRentalRentalIDinput.getText() + ", " +
                            "CustomerID = " + newRentalCustomerIDinput.getText() + ", " +
                            "MovieID = " + newRentalMovieIDinput.getText() + ", " +
                            "DateRented = \'" + tempDateRented + "\', " +
                            "DateDue = \'" + tempDateDue + "\' " +
                            "WHERE RentalID = " + tempRental.getRentalID());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                cloudDatabaseDownload();
                table.getColumnModel().getColumn(3).setCellRenderer(tableCellRenderer);
                table.getColumnModel().getColumn(4).setCellRenderer(tableCellRenderer);
                editRentals();
            }
        }

        else if ((actionEvent.toString()).contains("cmd=Delete Rental")) {
            int input = JOptionPane.showConfirmDialog(null, "Do you want to delete this rental?", "Confirm",
                    JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
            if (input == 0) {
                localDatabaseUpload();

                // Open a connection
                try(Connection conn = DriverManager.getConnection(ConnectionURL);
                    Statement stmt = conn.createStatement();
                ) {
                    stmt.executeUpdate("DELETE FROM [dbo].[Rentals] WHERE RentalID="+rentalIDinput.getText());
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }

                cloudDatabaseDownload();
                table.getColumnModel().getColumn(3).setCellRenderer(tableCellRenderer);
                table.getColumnModel().getColumn(4).setCellRenderer(tableCellRenderer);
                deleteRental();
            }
        }

        else if ((actionEvent.toString()).contains("cmd=Apply")) {
            if (Type == "Label") {
                UIManager.put(Type+".font", new FontUIResource(labels.getFont(), Font.BOLD, labels.fontSize));
            } else if (Type == "Button") {
                UIManager.put(Type+".font", new FontUIResource(buttons.getFont(), Font.BOLD, buttons.fontSize));
            } else if (Type == "TextField") {
                UIManager.put(Type+".font", new FontUIResource(textfields.getFont(), Font.BOLD, textfields.fontSize));
            }
            else if (Type == "Table") {
                table.setFont(new FontUIResource(tables.getFont(), Font.PLAIN, tables.getFontSize()));
                table.setRowHeight(tables.getFontSize());
                table.getTableHeader().setFont(new Font(tables.getFont(), Font.BOLD, tables.getFontSize()));
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
                table.setFont(new FontUIResource(tables.getFont(), Font.PLAIN, tables.getFontSize()));
                table.setRowHeight(tables.getFontSize());
                table.getTableHeader().setFont(new Font(tables.getFont(), Font.BOLD, tables.getFontSize()));
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
            table.setFont(new FontUIResource(tables.getFont(), Font.PLAIN, tables.getFontSize()));
            table.setRowHeight(tables.getFontSize());
            table.getTableHeader().setFont(new Font(tables.getFont(), Font.BOLD, tables.getFontSize()));
            FontEdit();
        }
        else if ((actionEvent.toString()).contains("cmd=Search")) {
            ArrayList<Rentals> TempSortList = new ArrayList<Rentals>();
            RentalsTableModel tempModel;
            table.setModel(model);
            table.repaint();
            for(int i = 0; i < table.getRowCount(); i++){//For each row
                for(int j = 0; j < table.getColumnCount(); j++){//For each column in that row
                    if((table.getValueAt(i, j)).toString().equals(searchBar.getText())){//Search the model
                        Rentals tempRental = new Rentals((Integer) table.getValueAt(i, 0), (Integer) table.getValueAt(i, 1), (Integer) table.getValueAt(i, 2), (java.sql.Date) table.getValueAt(i, 3), (java.sql.Date) table.getValueAt(i, 4));
                        TempSortList.add(tempRental);
                    }
                }
            }
            tempModel = new RentalsTableModel(TempSortList);
            table.setModel(tempModel);
            table.repaint();
            table.getColumnModel().getColumn(3).setCellRenderer(tableCellRenderer);
            table.getColumnModel().getColumn(4).setCellRenderer(tableCellRenderer);
        }
        else if ((actionEvent.toString()).contains("cmd=Clear")) {
            searchBar.setText("");
            table.setModel(model);
            table.repaint();
            table.getColumnModel().getColumn(3).setCellRenderer(tableCellRenderer);
            table.getColumnModel().getColumn(4).setCellRenderer(tableCellRenderer);
        }
        else if ((actionEvent.toString()).contains("cmd=Add Rental")) {
            int input = JOptionPane.showConfirmDialog(null, "Do you want to add this rental?", "Confirm",
                    JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
            if (input == 0) {
                Rentals newRental = new Rentals(Integer.parseInt(newRentalRentalIDinput.getText()), Integer.parseInt(newRentalCustomerIDinput.getText()), Integer.parseInt(newRentalMovieIDinput.getText()), new Date(Integer.parseInt(newRentalDateRentedYearinput.getText()) - 1900, Integer.parseInt(newRentalDateRentedMonthinput.getText()) - 1, Integer.parseInt(newRentalDateRentedDayinput.getText())), new Date(Integer.parseInt(newRentalDateDueYearinput.getText()) - 1900, Integer.parseInt(newRentalDateDueMonthinput.getText()) - 1, Integer.parseInt(newRentalDateDueDayinput.getText())));
                RentalList.add(newRental);
                table = new JTable(model = new RentalsTableModel(RentalList));
                table.getColumnModel().getColumn(3).setCellRenderer(tableCellRenderer);
                table.getColumnModel().getColumn(4).setCellRenderer(tableCellRenderer);
                newRental();
            }
        }
    }
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