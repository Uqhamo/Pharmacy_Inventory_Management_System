/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pharmacy_inventory_management_system;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author uqham
 */
public class HomePage extends JFrame {
private final Color PRIMARY_GREEN = new Color(39,174,96);
private final Color DARK_GREEN = new Color(30,123,73);
private final Color LIGHT_GREEN = new Color(232,245,233);
private final Color RED = new Color(192,57,43);
private final Color LIGHT_BACKGROUND = new Color(245,250,247);

    public HomePage(){
    setTitle("HealthFirst Pharmacy Inventory Management System");
    setSize(600, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout(20,20));
    getContentPane().setBackground(LIGHT_BACKGROUND);

     
    JPanel headerPanel = new JPanel();
    headerPanel.setBackground(PRIMARY_GREEN);
    headerPanel.setBorder(BorderFactory.createEmptyBorder(30,20,30,20));
     
    JLabel titleLabel = new JLabel("HealthFirst Pharmacy");
    titleLabel.setFont(new Font("Arial",Font.BOLD,28));
    titleLabel.setForeground(Color.WHITE);
    headerPanel.add(titleLabel);
    add(headerPanel, BorderLayout.NORTH);
     
    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new BoxLayout(centerPanel,BoxLayout.Y_AXIS));
    centerPanel.setBackground(LIGHT_BACKGROUND);
    centerPanel.setBorder(BorderFactory.createEmptyBorder(40,80,40,80));
     
    JLabel subtitleLabel = new JLabel("Inventory Management System");
    subtitleLabel.setFont(new Font ("Arial",Font.PLAIN,16));
    subtitleLabel.setForeground(DARK_GREEN);
    subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
    JButton btnLogin = new JButton("Login");
    btnLogin.setFont(new Font("Arial",Font.BOLD,16));
    btnLogin.setBackground(PRIMARY_GREEN);
    btnLogin.setForeground(Color.WHITE);
    btnLogin.setFocusPainted(false);
    btnLogin.setBorderPainted(false);
        
    btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
    btnLogin.setMaximumSize(new Dimension(200,45));
    btnLogin.addActionListener(e ->{this.dispose();
    new LoginSystem().setVisible(true);
    });
    
    JButton btnRegister = new JButton("Register");

        btnRegister.setFont(
                new Font("Arial", Font.BOLD, 16)
        );
    btnRegister.setBackground(DARK_GREEN);
    btnRegister.setForeground(Color.WHITE);
    btnRegister.setFocusPainted(false);
    btnRegister.setBorderPainted(false);
        
    btnRegister.setAlignmentX(Component.CENTER_ALIGNMENT);

    btnRegister.setMaximumSize(new Dimension(200, 45));

    btnRegister.addActionListener(e -> {new RegistrationSystem().setVisible(true);
    });
        
    
   centerPanel.add(subtitleLabel);
   centerPanel.add(Box.createRigidArea(new Dimension(0,30)));
    
   centerPanel.add(btnLogin);
  
   centerPanel.add(Box.createRigidArea(new Dimension(0, 15) ));
   centerPanel.add(btnRegister);
   add(centerPanel, BorderLayout.CENTER);
    
    
    
}
}