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
public class AdminDashboard extends JFrame {
    public AdminDashboard(){
    
    setTitle("HealthFirst");
    setSize(500, 450);
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     setLocationRelativeTo(null);
     setLayout(new BorderLayout(10,10));
     JLabel label = new JLabel("Welcome to the Admin Dashboard",SwingConstants.CENTER);
 label.setFont(new Font("Arial",Font.BOLD,20));
 add(label,BorderLayout.NORTH);
 
 JPanel panelButtons  = new JPanel(new GridLayout(4,1,10,10));
 panelButtons.setBorder(BorderFactory.createEmptyBorder(20,60,20,60));
 
 
 JButton btnMedicine = new JButton("Medicine Management");
 btnMedicine.addActionListener(e-> new MedicineManagement().setVisible(true));
 
 
 JButton btnSupplier = new JButton("Suppiler Management");
 btnSupplier.addActionListener(e-> new SupplierManagement().setVisible(true));
 
 JButton btnUsers = new JButton("User Management");
 btnUsers.addActionListener(e-> new UserManagement().setVisible(true));
 
 JButton btnReports = new JButton("Reports");
 btnReports.addActionListener(e-> new Report().setVisible(true));
   
 panelButtons.add(btnMedicine);
  panelButtons.add(btnSupplier);
 panelButtons.add(btnUsers);
 panelButtons.add(btnReports);
 
 add(panelButtons, BorderLayout.CENTER);

    
    }
}
