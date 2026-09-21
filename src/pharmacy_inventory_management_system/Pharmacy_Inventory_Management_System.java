 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pharmacy_inventory_management_system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.io.FileWriter; 
/*

public class LoginSystem extends JFrame{
private static final Map<String,UserData> userDatabase = new HashMap<>();
 static{
     userDatabase.put("admin1",new UserData("admin123".hashCode(),"Admin"));
     userDatabase.put("cashier1",new UserData("cashier123".hashCode(),"Cashier"));

 }
 private JTextField usernameField;
 private JPasswordField passwordField;
 private JLabel messageLabel;
 
 public LoginSystem(){
 
     setTitle("HealthFirst");
     setSize(400, 250);
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     setLocationRelativeTo(null);
     setLayout(new BorderLayout(10,10));
     
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
     });
 }
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
      openAdminMenu();
      }else if(user.role.equals("Cashier")){
      openCashierMenu();
      }
      }else{
          messageLabel.setText("Invaild username or password");
      }
      }else{
          messageLabel.setText("Invalid username or password");
      }
     
 }
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
 }
 private static class UserData{
 int passwordHash;
 String role;
 
 UserData(int passwordHash, String role){
 this.passwordHash = passwordHash;
 this.role = role;
 }
 }
}
*/
/**
 *
 * @author uqham
 */
public class Pharmacy_Inventory_Management_System {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        SwingUtilities.invokeLater(()-> {
        new HomePage().setVisible(true);
    
    });
        /*
        
        SwingUtilities.invokeLater(() -> {
        new BillingSystem().setVisible(true);
        });
        
         SwingUtilities.invokeLater(() -> {
        new POSsystem().setVisible(true);
        });
         
         SwingUtilities.invokeLater(() -> {
        new StockCheck().setVisible(true);
        });
         
         SwingUtilities.invokeLater(() -> {
        new SupplierManagement().setVisible(true);
        });
         
         SwingUtilities.invokeLater(() -> {
        new UserManagement().setVisible(true);
        });
*/
    }
    
}
