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
    
    public CashierDashboard(){
    setTitle("HealthFirst");
    setSize(500, 450);
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     setLocationRelativeTo(null);
     setLayout(new BorderLayout(10,10));
     
     JLabel label = new JLabel("Welcome to the Cashier Dashboard",SwingConstants.CENTER);
 label.setFont(new Font("Arial",Font.BOLD,20));
 add(label,BorderLayout.NORTH);
 
 JPanel panelButtons  = new JPanel(new GridLayout(3,1,10,10));
 panelButtons.setBorder(BorderFactory.createEmptyBorder(20,60,20,60));
 
 
 JButton btnPOS = new JButton("Point of Sale");
 btnPOS.addActionListener(e-> new POSsystem().setVisible(true));
 
 
 JButton btnBilling = new JButton("Billing");
 btnBilling.addActionListener(e-> new BillingSystem().setVisible(true));
 
 JButton btnStock = new JButton("Stock Check");
 btnStock.addActionListener(e-> new MedicineManagement().setVisible(true));
    
 panelButtons.add(btnPOS);
  panelButtons.add(btnBilling);
 panelButtons.add(btnStock);
 
 add(panelButtons, BorderLayout.CENTER);
    
    }
}
