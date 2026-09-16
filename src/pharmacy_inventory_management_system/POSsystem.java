/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pharmacy_inventory_management_system;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

/**
 *
 * @author uqham
 */
public class POSsystem extends JFrame {
    private JTable carTable;
    private DefaultTableModel tableModel;
    private JLabel lblSubtotal;
    private JLabel lblTax;
    private JLabel lblTotal;
    
    private final double TAX_RATE = 0.05;
    private double subtotal = 0.00;
    
    private HashMap<String, Integer> cartItems = new HashMap<>();
    private NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
    
    public POSsystem(){
    setTitle("HealthFirstPOS");
    setSize(1024,700);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout(10,10));
    
    JPanel headerPanel = new JPanel(new BorderLayout());
    headerPanel.setBackground(new Color(44,62,80));
    headerPanel.setBorder(BorderFactory.createEmptyBorder(15,20,15,20));
    
    }
    
}
