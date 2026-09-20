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
    
    public HomePage(){
    setTitle("HealthFirst Pharmacy Inventory Management System");
    setSize(600, 400);
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     setLocationRelativeTo(null);
     setLayout(new BorderLayout(20,20));
     
     
     JPanel headerPanel = new JPanel();
     headerPanel.setBackground(new Color(41,128,185));
     headerPanel.setBorder(BorderFactory.createEmptyBorder(30,20,30,20));
     
     JLabel titleLabel = new JLabel("HealthFirst Pharmacy");
     titleLabel.setFont(new Font("Arial",Font.BOLD,28));
     titleLabel.setForeground(Color.WHITE);
     headerPanel.add(titleLabel);
     add(headerPanel, BorderLayout.NORTH);
     
     JPanel centerPanel = new JPanel();
     centerPanel.setLayout(new BoxLayout(centerPanel,BoxLayout.Y_AXIS));
     centerPanel.setBorder(BorderFactory.createEmptyBorder(40,80,40,80));
     
     JLabel subtitleLabel = new JLabel("Inventory Management System");
     subtitleLabel.setFont(new Font ("Arial",Font.PLAIN,16));
     subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
    JButton btnLogin = new JButton("Login");
    btnLogin.setFont(new Font("Arial",Font.BOLD,16));
    btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
    btnLogin.setMaximumSize(new Dimension(200,45));
    btnLogin.addActionListener(e ->{this.dispose();
    new LoginSystem().setVisible(true);
    });
    
    centerPanel.add(subtitleLabel);
    centerPanel.add(Box.createRigidArea(new Dimension(0,30)));
    centerPanel.add(btnLogin);
    
    add(centerPanel, BorderLayout.CENTER);
    
    
    }
}
