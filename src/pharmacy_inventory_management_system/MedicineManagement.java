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
    
    add(panelForm, BorderLayout.NORTH);
    
    tableModel = new DefaultTableModel(new String[]{"ID","Name","Price","Quantity"},0);
    table = new JTable(tableModel);
    table.addMouseListener(new MouseAdapter(){
    public void mouseClicked(MouseEvent e){
    int row  = table.getSelectedRow();
    txtId.setText(tableModel.getValueAt(row, 0).toString());
    txtName.setText(tableModel.getValueAt(row, 1).toString());
    txtPrice.setText(tableModel.getValueAt(row, 2).toString());
    txtQuantity.setText(tableModel.getValueAt(row, 3).toString());

    }
    });
    add(new JScrollPane(table),BorderLayout.CENTER);
    
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
    
    add(panelButtons,BorderLayout.SOUTH);
    btnCreate.addActionListener(e -> createMedicine());
    btnRead.addActionListener(e -> loadTableData());
    btnUpdate.addActionListener(e -> updateMedicine());
    btnDelete.addActionListener(e -> deleteMedicine());
    btnClear.addActionListener(e -> clearFields());
 
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
             rs.getInt("id"),
             rs.getString("name"),
             rs.getDouble("price"),
             rs.getInt("quantity")
             
         
         });
         }
    }catch(SQLException ex ){
    ex.printStackTrace();
    }
    }
    
    private void createMedicine(){
     try{
         String sql = "INSERT INTO medicines(name,price,quantity) VALUES(?,?,?)";
         PreparedStatement pst = conn.prepareStatement(sql);
         pst.setString(1, txtName.getText());
         pst.setDouble(2, Double.parseDouble(txtPrice.getText()));
         pst.setInt(3, Integer.parseInt(txtQuantity.getText()));
         pst.executeUpdate();
         loadTableData();
         clearFields();
         JOptionPane.showMessageDialog(this, "Medicine Added Successfully");
     
     }catch(Exception ex){
       JOptionPane.showMessageDialog(this, "Error:Check your input values");

     }
    }
    private void updateMedicine(){
    try{
         String sql = "UPDATE medicines SET name=? , price=?,quantity=? WHERE id=?";
         PreparedStatement pst = conn.prepareStatement(sql);
         pst.setString(1, txtName.getText());
         pst.setDouble(2, Double.parseDouble(txtPrice.getText()));
         pst.setInt(3, Integer.parseInt(txtQuantity.getText()));
         pst.setInt(4, Integer.parseInt(txtId.getText()));

         pst.executeUpdate();
         loadTableData();
         clearFields();
         JOptionPane.showMessageDialog(this, "Medicine Updated Successfully");
     
     }catch(Exception ex){
       JOptionPane.showMessageDialog(this, "Error:Select a row or enter valid ID");
       

     }
    }
    
    private void deleteMedicine(){
        try{
         String sql = "DELETE FROM medicines WHERE id=?";
         PreparedStatement pst = conn.prepareStatement(sql);
        
         pst.setInt(1, Integer.parseInt(txtId.getText()));

         pst.executeUpdate();
         loadTableData();
         clearFields();
         JOptionPane.showMessageDialog(this, "Medicine Deleted Successfully");
     
     }catch(Exception ex){
       JOptionPane.showMessageDialog(this, "Error:Select or enter enter valid ID to delete");
       

     }
        
    }
    
    private void clearFields(){
    txtId.setText("");
    txtName.setText("");
    txtPrice.setText("");
    txtQuantity.setText("");
    
    }
    
}
