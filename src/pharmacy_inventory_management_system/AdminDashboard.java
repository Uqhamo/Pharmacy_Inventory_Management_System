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
    private final Color PRIMARY_GREEN = new Color(39,174,96);
    private final Color DARK_GREEN = new Color(30,123,73);
    private final Color LIGHT_GREEN = new Color(232,245,233);
    private final Color RED = new Color(192,57,43);
    private final Color LIGHT_BACKGROUND = new Color(245,250,247);
    public AdminDashboard(){
    
    setTitle("HealthFirst Admin Dashboard");
    setSize(500, 450);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout(10,10));
    getContentPane().setBackground(LIGHT_BACKGROUND);
    JPanel headerPanel = new JPanel();
    headerPanel.setBackground(PRIMARY_GREEN);
    headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
    
    JLabel label = new JLabel("Welcome to the Admin Dashboard",SwingConstants.CENTER);
    label.setFont(new Font("Arial",Font.BOLD,20));
     label.setForeground(DARK_GREEN);

    add(label,BorderLayout.NORTH);
 
    JPanel panelButtons  = new JPanel(new GridLayout(4,1,15,15));
    panelButtons.setBackground(LIGHT_BACKGROUND);
 
    
    panelButtons.setBorder(BorderFactory.createEmptyBorder(35,70,35,70));
 
 
    JButton btnMedicine = new JButton("Medicine Management");
    styleButton(btnMedicine,PRIMARY_GREEN);
    btnMedicine.addActionListener(e-> new MedicineManagement().setVisible(true));
 
 
    JButton btnSupplier = new JButton("Supplier Management");
    styleButton(btnSupplier,PRIMARY_GREEN);

    btnSupplier.addActionListener(e-> new SupplierManagement().setVisible(true));
 
 
    JButton btnUsers = new JButton("User Management");
    styleButton(btnUsers,PRIMARY_GREEN);

    btnUsers.addActionListener(e-> new UserManagement().setVisible(true));
 
   JButton btnReports = new JButton("Reports");
   styleButton(btnReports,PRIMARY_GREEN);
 

    btnReports.addActionListener(e-> new Report().setVisible(true));
   
    panelButtons.add(btnMedicine);
    panelButtons.add(btnSupplier);
    panelButtons.add(btnUsers);
    panelButtons.add(btnReports);
 
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
