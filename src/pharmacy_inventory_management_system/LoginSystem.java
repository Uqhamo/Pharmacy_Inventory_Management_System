/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pharmacy_inventory_management_system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;


public class LoginSystem extends JFrame{
    /*
private static final Map<String,UserData> userDatabase = new HashMap<>();
 static{
     userDatabase.put("Uqhamo",new UserData("U123".hashCode(),"Admin"));
     userDatabase.put("James",new UserData("James123".hashCode(),"Cashier"));

 }
 */
 private JTextField txtusername;
 /*
  private JTextField RoleField;
    private JTextField FullNameField;

*/
 private JPasswordField txtpassword;
 //private JLabel messageLabel;
 
 public LoginSystem(){
 
     setTitle("HealthFirst Pharmacy - Login");
     setSize(600, 450);
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     setLocationRelativeTo(null);
     setLayout(new BorderLayout(10,10));

     
     JPanel headerPanel = new JPanel();
     headerPanel.setBackground(new Color(41,128,185));
     
  JLabel titleLabel = new JLabel("HeathFirst Login");
  titleLabel.setFont(new Font("Arial",Font.BOLD,22));
  
  titleLabel.setForeground(Color.WHITE);
  headerPanel.add(titleLabel);
  add(headerPanel, BorderLayout.NORTH);
  JPanel formPanel = new JPanel(new GridLayout(2,2,
          10,15));
  formPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
    
    
  formPanel.add(new JLabel("Username:"));
  txtusername = new JTextField();
  formPanel.add(txtusername);
  
  formPanel.add(new JLabel("Password:"));
    
    //txtusername = new JTextField();
    txtpassword = new JPasswordField();
    formPanel.add(txtpassword);
    
    add(formPanel, BorderLayout.CENTER);
    
    JPanel buttonPanel = new JPanel();
    JButton btnLogin = new JButton(""
            + "Login");
    btnLogin.setPreferredSize(
                new Dimension(150, 40)
        );

    //btnLogin.addActionListener(e-> 
      //      login());
      
      buttonPanel.add(btnLogin);
      add(buttonPanel, BorderLayout.SOUTH);
   btnLogin.addActionListener(e-> 
           login());
 
/*
    setLayout(new GridLayout(3,2,10,10));
    
    //add(lblUsername);
    add(txtusername);
    //add(lblPassword);
    add(txtpassword);
    
    add(new JLabel());
    add(btnLogin);
  */
    //setLayout(new BorderLayout(10,10));
     
     /*
     JPanel headerPanel = new JPanel();
     headerPanel.setBackground(new Color(41, 128, 185));
     JLabel headerLabel = new JLabel("User Authentication");
     headerLabel.setForeground(Color.WHITE);
     headerLabel.setFont(new Font("Arial",Font.BOLD, 18));
     headerPanel.add(headerLabel);
     add(headerPanel,BorderLayout.NORTH);
     
     JPanel formPanel = new JPanel(new GridBagLayout());
     GridBagConstraints gbc = new GridBagConstraints();
     gbc.insets = new Insets(5,5,5,5);
     gbc.fill = GridBagConstraints.HORIZONTAL;
     
     gbc.gridx = 0; gbc.gridy=0;
     formPanel.add(new JLabel("Username:"),gbc);
     gbc.gridx = 1;
     usernameField = new JTextField(15);
     formPanel.add(usernameField, gbc);
     
     gbc.gridx =0; gbc.gridy =1;
     formPanel.add(new JLabel("Password:"),gbc);
     gbc.gridx =1;
     passwordField =new JPasswordField(15);
     formPanel.add(passwordField,gbc);
     add(formPanel, BorderLayout.CENTER);
     /*
     gbc.gridx = 0; gbc.gridy=2;
     formPanel.add(new JLabel("Role:"),gbc);
     gbc.gridx = 1;
     RoleField = new JTextField(15);
     formPanel.add(RoleField, gbc);
     
     gbc.gridx = 0; gbc.gridy=3;
     formPanel.add(new JLabel("Full Name:"),gbc);
     gbc.gridx = 1;
     FullNameField = new JTextField(15);
     formPanel.add(FullNameField, gbc);
     */
     /*
     
     JPanel bottomPanel = new JPanel(new GridLayout(2,1,5,5));
     
     JButton loginButton = new JButton("Login");
     loginButton.setFont(new Font("Arial", Font.BOLD, 14));
     
     messageLabel = new JLabel("", SwingConstants.CENTER);
     messageLabel.setForeground(Color.red);
     
     bottomPanel.add(loginButton);
     bottomPanel.add(messageLabel);
     add(bottomPanel,BorderLayout.SOUTH);
     
     loginButton.addActionListener(new ActionListener(){
     @Override
     public void actionPerformed(ActionEvent e){
     handleLogin();
     }
     });*/
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
 /*
 private void handleLogin(){
     String username = usernameField.getText().trim();
     char [] passwordChars = passwordField.getPassword();
     String password = new String(passwordChars);
     
     java.util.Arrays.fill(passwordChars, ' ');
      if(username.isEmpty() || password.isEmpty()){
      messageLabel.setText("Please fill in all fields.");
      return;
      }
      
      if(userDatabase.containsKey(username)){
      UserData user = userDatabase.get(username);
      int inputHash = password.hashCode();
      
      if(inputHash == user.passwordHash){
      this.dispose();
      
      if(user.role.equals("Admin")){
      new AdminDashboard().setVisible(true);
      }else if(user.role.equals("Cashier")){
      new CashierDashboard().setVisible(true);
      }
      }else{
          messageLabel.setText("Invaild username or password");
      }
      
     
 }}
 */
 /*
 private void openAdminMenu(){
 JFrame adminFrame = new JFrame("Admin Dashboard");
 adminFrame.setSize(500, 400);
 adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 adminFrame.setLocationRelativeTo(null);
 
 JLabel label = new JLabel("Welcome to the Admin Menu",SwingConstants.CENTER);
 label.setFont(new Font("Arial",Font.BOLD,20));
 adminFrame.add(label);
 
 adminFrame.setVisible(true);
 }
 private void openCashierMenu(){
 JFrame cashierFrame = new JFrame("Cashier Dashboard");
 cashierFrame.setSize(500,400);
 cashierFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 cashierFrame.setLocationRelativeTo(null);
 
 JLabel label = new JLabel("Welcome to the Cashier Menu",SwingConstants.CENTER);
 label.setFont(new Font("Arial",Font.BOLD,20));
 cashierFrame.add(label);
 
 cashierFrame.setVisible(true);
 }*/
/*
 private static class UserData{
 int passwordHash;
 String role;
 
 UserData(int passwordHash, String role){
 this.passwordHash = passwordHash;
 this.role = role;
 }
 }
 
}*/
