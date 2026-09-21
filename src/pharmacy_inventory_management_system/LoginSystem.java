/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pharmacy_inventory_management_system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;


public class LoginSystem extends JFrame{
private final Color PRIMARY_GREEN = new Color(39,174,96);
private final Color DARK_GREEN = new Color(30,123,73);
private final Color LIGHT_GREEN = new Color(232,245,233);
private final Color RED = new Color(192,57,43);

private final Color LIGHT_BACKGROUND = new Color(245,250,247);
private JTextField txtusername;
 
private JPasswordField txtpassword;
 
public LoginSystem(){
 
    setTitle("HealthFirst Pharmacy - Login");
    setSize(600, 450);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout(10,10));
    getContentPane().setBackground(LIGHT_BACKGROUND);

     
    JPanel headerPanel = new JPanel();
    headerPanel.setBackground(PRIMARY_GREEN);
    JLabel titleLabel = new JLabel("HeathFirst Login");
    titleLabel.setFont(new Font("Arial",Font.BOLD,22));
  
    titleLabel.setForeground(Color.WHITE);
    headerPanel.add(titleLabel);
    add(headerPanel, BorderLayout.NORTH);
    JPanel formPanel = new JPanel(new GridLayout(2,2,10,15));
    formPanel.setBackground(LIGHT_BACKGROUND);

    formPanel.setBorder(
    BorderFactory.createEmptyBorder(50,50,50,50));
    
    
    formPanel.add(new JLabel("Username:"));
  
    txtusername = new JTextField();
  
    txtusername.setBorder(
        BorderFactory.createLineBorder(PRIMARY_GREEN,2));
  formPanel.add(txtusername);
  
  formPanel.add(new JLabel("Password:"));
    
    
    txtpassword = new JPasswordField();
    txtpassword.setBorder(
        BorderFactory.createLineBorder(
                PRIMARY_GREEN,2));
    formPanel.add(txtpassword);
    
    add(formPanel, BorderLayout.CENTER);
    
    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(LIGHT_BACKGROUND);

    JButton btnLogin = new JButton(""+ "Login");
    btnLogin.setPreferredSize(new Dimension(150, 40));

    
      
    btnLogin.setBackground(PRIMARY_GREEN);
    btnLogin.setForeground(Color.WHITE);
    btnLogin.setFont(new Font("Arial",Font.BOLD,14));

    btnLogin.setFocusPainted(false);
    buttonPanel.add(btnLogin);
    add(buttonPanel, BorderLayout.SOUTH);
    btnLogin.addActionListener(e-> 
           login());
 

 }
 
private void login(){
 String username = txtusername.getText().trim();
 String password = new String(
         txtpassword.getPassword());
 
 if(username.isEmpty() || password.isEmpty()){
 JOptionPane.showMessageDialog(this, 
         "Please enter username and password");
 return;
 }
 String sql = "SELECT user_id, username, password, role, full_name "
            + "FROM users "
            + "WHERE username = ? AND password = ?";
 
 try(Connection conn = 
         DBConnection.getConnection();
         PreparedStatement stmt = 
                 conn.prepareStatement(sql)){
     
     stmt.setString(1, username);
     stmt.setString(2, password);
     
     ResultSet rs = stmt.executeQuery();
     
     if(rs.next()){
     
         int userId = rs.getInt("user_id");
         String role = rs.getString("role");
         String fullName = rs.getString("full_name");
         
         JOptionPane.showMessageDialog(this, "Welcome " +  fullName);
         
         if(role.equalsIgnoreCase("Admin")){
         new AdminDashboard().setVisible(true);
         }else if(role.equalsIgnoreCase("Cashier")){
         new CashierDashboard(userId).setVisible(true);
         }
         dispose();
     }else{
         JOptionPane.showMessageDialog(this, "Invalid username or password","Login Failed",JOptionPane.ERROR_MESSAGE);
     
     }

 }catch(SQLException ex){
 JOptionPane.showMessageDialog(this, "Database error:"+ex.getMessage(),"Database Error",JOptionPane.ERROR_MESSAGE);
 }
 }     
 }
 