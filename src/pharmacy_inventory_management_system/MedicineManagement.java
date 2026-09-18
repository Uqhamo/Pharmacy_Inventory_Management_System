/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pharmacy_inventory_management_system;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**
 *
 * @author uqham
 */
public class MedicineManagement extends JFrame{
    private JTextField txtId, txtName, txtPrice, txtQuantity;
    private JTable table;
    private DefaultTableModel tableModel;
    private Connection conn;
    
    public MedicineManagement(){
    setTitle("HealthFirst Medicine Management System");
    setSize(700, 450);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout(10,10));
    
    connectDatabase();
    
    JPanel panelForm = new JPanel(new GridLayout(4,2,5,5));
    panelForm.setBorder(BorderFactory.createTitledBorder("Medicine Details"));
    
    panelForm.add(new JLabel("Medicine ID (for Update/Delete):"));
    txtId = new JTextField();
    panelForm.add(txtId);
    
    panelForm.add(new JLabel("Medicine Name:"));
    txtName = new JTextField();
    panelForm.add(txtName);
    
    
    panelForm.add(new JLabel("Price (R):"));
    txtQuantity = new JTextField();
    panelForm.add(txtQuantity);
    
    add(panelForm, NORTH);
    
    tableModel = new DefaultTableModel(new String[]{"ID","Name","Price","Quantity"},0);
    table = new JTable(tableModel);
    
    add(new JScrollPane(table), CENTER);
    
    JPanel panelButtons = new JPanel();
    JButton btnCreate = new JButton("Add (Create)");
    JButton btnRead = new JButton("Refresh (Read)");
    JButton btnUpdate = new JButton("Update");
    JButton btnDelete = new JButton("Delete");
    JButton btnClear = new JButton("Clear");

    
    panelButtons.add(btnCreate);
    panelButtons.add(btnRead);
    panelButtons.add(btnUpdate);
    panelButtons.add(btnDelete);
    panelButtons.add(btnClear);
    
    add(panelButtons, SOUTH);
    btnCreate.addActionListener(e -> createMedicine());
    btnRead.addActionListener(e -> loadTableData());
    btnUpdate.addActionListener(e -> updateMedicine());
    btnDelete.addActionListener(e -> deleteMedicine());
    btnClear.addActionListener(e -> createFields());
 
    loadTableData();

    }
    private void connectDatabase(){
    
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306//,root,Mekana@12345");
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS medicines("+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"+ "name TEXT, price REAL, quantity INTEGER)");
        
        }catch(Exception ex){
        JOptionPane.showMessageDialog(this, "Database Error:"+ ex.getMessage());
        }
    }
    
    private void loadTableData(){
    tableModel.setRowCount(0);
    try{
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT * FROM medicines");
         while(rs.next()){
         tableModel.addRow(new Object[]{
         
         });
         }
    }catch(SQLException ex ){
    
    }
    }
    
}
