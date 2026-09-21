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
public class CashierDashboard extends JFrame {
    private int userId;
    private final Color PRIMARY_GREEN = new Color(39,174,96);
    private final Color DARK_GREEN = new Color(30,123,73);
    private final Color LIGHT_GREEN = new Color(232,245,233);
    private final Color RED = new Color(192,57,43);
    private final Color LIGHT_BACKGROUND = new Color(245,250,247);
    
    public CashierDashboard(int userId){
    this.userId = userId;
    setTitle("HealthFirst Cashier Dashboard");
    setSize(500, 450);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout(10,10));
    getContentPane().setBackground(LIGHT_BACKGROUND);

    JPanel headerPanel = new JPanel();
    headerPanel.setBackground(PRIMARY_GREEN);
    headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
    
     
    JLabel label = new JLabel("Welcome to the Cashier Dashboard",SwingConstants.CENTER);
 
  

    label.setFont(new Font("Arial",Font.BOLD,20));
    label.setForeground(Color.WHITE);
    headerPanel.add(label,BorderLayout.CENTER);
 
    add(headerPanel,BorderLayout.NORTH);
 
    JPanel panelButtons  = new JPanel(new GridLayout(3,1,10,10));
    panelButtons.setBackground(LIGHT_BACKGROUND);
 
    panelButtons.setBorder(BorderFactory.createEmptyBorder(40,80,40,80));
 
 
    JButton btnPOS = new JButton("Point of Sale");
    styleButton(btnPOS,PRIMARY_GREEN);
    btnPOS.addActionListener(e-> new POSsystem(userId).setVisible(true));
 
 
    JButton btnBilling = new JButton("Billing");
    styleButton(btnBilling,PRIMARY_GREEN);

    btnBilling.addActionListener(e-> new BillingSystem().setVisible(true));
 
    JButton btnStock = new JButton("Stock Check");
    styleButton(btnStock,PRIMARY_GREEN);

    btnStock.addActionListener(e-> new StockCheck().setVisible(true));
    
    panelButtons.add(btnPOS);
    panelButtons.add(btnBilling);
    panelButtons.add(btnStock);
 
     add(panelButtons, BorderLayout.CENTER);
    
 }
    
private void styleButton(
        
    JButton button,Color color) {
    
    button.setBackground(color);
    button.setForeground(Color.WHITE);

    button.setFont(new Font( "Arial",Font.BOLD,13));

    button.setFocusPainted(false);
    button.setBorderPainted(false);

    button.setPreferredSize(new Dimension(100, 35));
}
}
