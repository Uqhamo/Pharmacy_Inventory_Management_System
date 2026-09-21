/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pharmacy_inventory_management_system;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

/**
 *
 * @author uqham
 */
public class RegistrationSystem extends JFrame {
   
    private JTextField txtFullName;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JComboBox<String> cmbRole;
    private JButton registerButton;
    private JButton btnBack;
    private final Color PRIMARY_GREEN = new Color(39,174,96);
    private final Color DARK_GREEN = new Color(30,123,73);
    private final Color LIGHT_GREEN = new Color(232,245,233);
    private final Color RED = new Color(192,57,43);
    private final Color LIGHT_BACKGROUND = new Color(245,250,247);
    
    public RegistrationSystem(){
    setTitle("HealthFirst -User Registration");
    setSize(450, 420);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout(10,10));
    getContentPane().setBackground(LIGHT_BACKGROUND);

    
    JPanel headerPanel = new JPanel();
    headerPanel.setBackground(PRIMARY_GREEN);
    headerPanel.setBorder( BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
    JLabel lblTitle = new JLabel("HealthFirst Registration");

    lblTitle.setForeground(Color.WHITE);

    lblTitle.setFont(new Font("Arial",Font.BOLD,20));

    headerPanel.add(lblTitle);

    add(headerPanel, BorderLayout.NORTH);

       

    JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 15) );
    formPanel.setBackground(LIGHT_BACKGROUND);

    formPanel.setBorder( BorderFactory.createEmptyBorder(30, 30, 30, 30));

       
    formPanel.add(new JLabel("Full Name:") );

    txtFullName = new JTextField();
    txtFullName.setBorder( BorderFactory.createLineBorder(PRIMARY_GREEN));
    formPanel.add(txtFullName);

        
    formPanel.add(new JLabel("Username:"));

    txtUsername = new JTextField();
    txtUsername.setBorder(BorderFactory.createLineBorder( PRIMARY_GREEN) );
    formPanel.add(txtUsername);

        
    formPanel.add(new JLabel("Password:"));

    txtPassword =new JPasswordField();
    txtPassword.setBorder(BorderFactory.createLineBorder( PRIMARY_GREEN));
    formPanel.add(txtPassword);

        
    formPanel.add(new JLabel("Confirm Password:"));

    txtConfirmPassword =new JPasswordField();
        
    txtConfirmPassword.setBorder(BorderFactory.createLineBorder(PRIMARY_GREEN,1));
    formPanel.add(txtConfirmPassword);

        
    formPanel.add(new JLabel("Role:"));

    cmbRole = new JComboBox<>(new String [] {"Admin","Cashier"});
       
    formPanel.add(cmbRole);

    add(formPanel,BorderLayout.CENTER);
        

        

        JPanel buttonPanel =new JPanel( new FlowLayout(FlowLayout.CENTER, 10,15));
        
        buttonPanel.setBackground(LIGHT_BACKGROUND);
        registerButton = new JButton("Register");
        registerButton.setBackground(PRIMARY_GREEN);
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(false);
        registerButton.setPreferredSize(new Dimension(130, 40));

        btnBack =new JButton("Back");
        btnBack.setBackground(DARK_GREEN);
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(new Font("Arial", Font.BOLD, 14));
        btnBack.setFocusPainted(false);
        btnBack.setBorderPainted(false);
        btnBack.setPreferredSize(new Dimension(130, 40));
        buttonPanel.add(registerButton);
        buttonPanel.add(btnBack);

        add(buttonPanel,BorderLayout.SOUTH);

       

        registerButton.addActionListener(
                e -> registerUser()
        );

       

        btnBack.addActionListener(e -> {

            dispose();

        });
    }

    

    private void registerUser() {

        String fullName =txtFullName.getText().trim();

        String username =txtUsername.getText().trim();

        String password =new String(txtPassword.getPassword());

        String confirmPassword =new String(txtConfirmPassword.getPassword());

        
        String role = "Cashier";

        
        if (fullName.isEmpty() 
                || username.isEmpty()
                || password.isEmpty()
                || confirmPassword.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields.",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        
        if (!password.equals(confirmPassword)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Passwords do not match.",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        
        if (password.length() < 5) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password must be at least 5 characters.",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        
        String checkSql =
                "SELECT user_id FROM users "
                + "WHERE username = ?";

        try (
            Connection conn = DBConnection.getConnection();

            PreparedStatement checkStmt =conn.prepareStatement(checkSql)
        ) {

            checkStmt.setString(1,username);

            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Username already exists. "
                        + "Please choose another username.",
                        "Registration Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

           
            String insertSql =
                    "INSERT INTO users "
                    + "(username, password, role, full_name) "
                    + "VALUES (?, ?, ?, ?)";

            try (
                PreparedStatement insertStmt = conn.prepareStatement(insertSql)
            ) {

                insertStmt.setString(1,username);

                insertStmt.setString(2,password);

                insertStmt.setString(3,role);

                insertStmt.setString(4,fullName);

                insertStmt.executeUpdate();

                JOptionPane.showMessageDialog(
                        this,
                        "Registration successful!\n"
                        + "You can now log in as a Cashier.",
                        "Registration Successful",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearFields();

              
                dispose();
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error registering user: "
                    + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    
    private void clearFields() {

        txtFullName.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        txtConfirmPassword.setText("");
    }
}
    
    

