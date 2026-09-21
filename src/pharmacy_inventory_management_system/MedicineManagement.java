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
    private JTextField txtMedicineId, txtName,txtCompany,txtMedicineType, 
            txtPrice, txtQuantity,txtReorderLevel,txtExpiryDate;
    private JComboBox<String> cmbSupplier;
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
    
    JPanel panelForm = new JPanel(new GridLayout(5,4,5,5));
    panelForm.setBorder(BorderFactory.createTitledBorder("Medicine Details"));
    
    panelForm.add(new JLabel("Medicine ID:"));
    txtMedicineId = new JTextField();
    panelForm.add(txtMedicineId);
    
    panelForm.add(new JLabel("Medicine Name:"));
    txtName = new JTextField();
    panelForm.add(txtName);
    
     panelForm.add(new JLabel("Company:"));
        txtCompany = new JTextField();
        panelForm.add(txtCompany);

        
        panelForm.add(new JLabel("Medicine Type:"));
        txtMedicineType = new JTextField();
        panelForm.add(txtMedicineType);
        
    panelForm.add(new JLabel("Price (R):"));
    txtPrice = new JTextField();
    panelForm.add(txtPrice);
    
    panelForm.add(new JLabel("Quantity in Stock:"));
    txtQuantity = new JTextField();
    panelForm.add(txtQuantity);
    
     panelForm.add(new JLabel("Reorder Level:"));
        txtReorderLevel = new JTextField();
        panelForm.add(txtReorderLevel);

        
        panelForm.add(new JLabel("Expiry Date (YYYY-MM-DD):"));
        txtExpiryDate = new JTextField();
        panelForm.add(txtExpiryDate);

        
        panelForm.add(new JLabel("Supplier:"));
        cmbSupplier = new JComboBox<>();
        panelForm.add(cmbSupplier);
    
    add(panelForm, BorderLayout.NORTH);
    
    //tableModel = new DefaultTableModel(new String[]{"ID","Name","Price","Quantity"},0);
      tableModel = new DefaultTableModel(new String[]{
                    "ID","Name","Company","Type","Price","Quantity","Reorder Level","Expiry Date","Supplier ID"},0);
    table = new JTable(tableModel);
    table.addMouseListener(new MouseAdapter(){
    public void mouseClicked(MouseEvent e){
    int row  = table.getSelectedRow();
     if (row != -1) {

                    txtMedicineId.setText(
                            tableModel.getValueAt(row, 0).toString()
                    );

                    txtName.setText(
                            tableModel.getValueAt(row, 1).toString()
                    );

                    txtCompany.setText(
                            tableModel.getValueAt(row, 2).toString()
                    );

                    txtMedicineType.setText(
                            tableModel.getValueAt(row, 3).toString()
                    );

                    txtPrice.setText(
                            tableModel.getValueAt(row, 4).toString()
                    );

                    txtQuantity.setText(
                            tableModel.getValueAt(row, 5).toString()
                    );

                    txtReorderLevel.setText(
                            tableModel.getValueAt(row, 6).toString()
                    );

                    txtExpiryDate.setText(
                            tableModel.getValueAt(row, 7).toString()
                    );

                    if (tableModel.getValueAt(row, 8) != null) {
                        cmbSupplier.setSelectedItem(
                                tableModel.getValueAt(row, 8).toString()
                        );
    /*
    txtMedicineId.setText(tableModel.getValueAt(row, 0).toString());
    txtName.setText(tableModel.getValueAt(row, 1).toString());
    txtPrice.setText(tableModel.getValueAt(row, 2).toString());
    txtQuantity.setText(tableModel.getValueAt(row, 3).toString());
*/
    }
    }}});
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
    loadSuppliers();

    }
    private void connectDatabase(){
    /*
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthfirstdb,root,Mekana@12345");
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS medicines("+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"+ "name TEXT, price REAL, quantity INTEGER)");
        
        }catch(Exception ex){
        JOptionPane.showMessageDialog(this, "Database Error:"+ ex.getMessage());
        }*/
    
    try {

            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/healthfirstdb",
                    "root",
                    "Mekana@12345"
            );

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
     private void loadSuppliers() {

        cmbSupplier.removeAllItems();

        String sql = "SELECT supplier_id FROM suppliers ORDER BY supplier_id";

        try (
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()
        ) {

            while (rs.next()) {

                cmbSupplier.addItem(
                        String.valueOf(rs.getInt("supplier_id"))
                );
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading suppliers: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    private void loadTableData(){
    tableModel.setRowCount(0);
    /*
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
    }*/
     String sql = "SELECT medicine_id, name, company, medicine_type, "
                + "price, quantity_in_stock, reorder_level, expiry_date, supplier_id "
                + "FROM medicines";

        try (
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()
        ) {

            while (rs.next()) {

                tableModel.addRow(new Object[]{
                    rs.getInt("medicine_id"),
                    rs.getString("name"),
                    rs.getString("company"),
                    rs.getString("medicine_type"),
                    rs.getDouble("price"),
                    rs.getInt("quantity_in_stock"),
                    rs.getInt("reorder_level"),
                    rs.getDate("expiry_date"),
                    rs.getObject("supplier_id")
                });
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading medicines: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private void createMedicine(){
        /*
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

     }*/
           String name = txtName.getText().trim();
        String company = txtCompany.getText().trim();
        String medicineType = txtMedicineType.getText().trim();
        String priceText = txtPrice.getText().trim();
        String quantityText = txtQuantity.getText().trim();
        String reorderText = txtReorderLevel.getText().trim();
        String expiryDate = txtExpiryDate.getText().trim();

        if (name.isEmpty()
                || company.isEmpty()
                || medicineType.isEmpty()
                || priceText.isEmpty()
                || quantityText.isEmpty()
                || reorderText.isEmpty()
                || expiryDate.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all medicine details.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        try {

            double price = Double.parseDouble(priceText);
            int quantity = Integer.parseInt(quantityText);
            int reorderLevel = Integer.parseInt(reorderText);

            int supplierId = Integer.parseInt(
                    cmbSupplier.getSelectedItem().toString()
            );

            String sql = "INSERT INTO medicines "
                    + "(name, company, medicine_type, price, "
                    + "quantity_in_stock, reorder_level, expiry_date, supplier_id) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, name);
            pst.setString(2, company);
            pst.setString(3, medicineType);
            pst.setDouble(4, price);
            pst.setInt(5, quantity);
            pst.setInt(6, reorderLevel);
            pst.setDate(7, Date.valueOf(expiryDate));
            pst.setInt(8, supplierId);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Medicine Added Successfully"
            );

            loadTableData();
            clearFields();

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Price, quantity and reorder level must contain valid numbers.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Expiry date must be in YYYY-MM-DD format.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error adding medicine: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    private void updateMedicine(){
        /*
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
       

     }*/
        
            if (txtMedicineId.getText().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select a medicine to update.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            int medicineId = Integer.parseInt(
                    txtMedicineId.getText()
            );

            double price = Double.parseDouble(
                    txtPrice.getText()
            );

            int quantity = Integer.parseInt(
                    txtQuantity.getText()
            );

            int reorderLevel = Integer.parseInt(
                    txtReorderLevel.getText()
            );

            int supplierId = Integer.parseInt(
                    cmbSupplier.getSelectedItem().toString()
            );

            String sql = "UPDATE medicines SET "
                    + "name=?, "
                    + "company=?, "
                    + "medicine_type=?, "
                    + "price=?, "
                    + "quantity_in_stock=?, "
                    + "reorder_level=?, "
                    + "expiry_date=?, "
                    + "supplier_id=? "
                    + "WHERE medicine_id=?";

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, txtName.getText());
            pst.setString(2, txtCompany.getText());
            pst.setString(3, txtMedicineType.getText());
            pst.setDouble(4, price);
            pst.setInt(5, quantity);
            pst.setInt(6, reorderLevel);
            pst.setDate(
                    7,
                    Date.valueOf(txtExpiryDate.getText())
            );
            pst.setInt(8, supplierId);
            pst.setInt(9, medicineId);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Medicine Updated Successfully"
            );

            loadTableData();
            clearFields();

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numbers.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Expiry date must be in YYYY-MM-DD format.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error updating medicine: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private void deleteMedicine(){
        /*
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
       

     }*/
        if (txtMedicineId.getText().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select a medicine to delete.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            int medicineId = Integer.parseInt(
                    txtMedicineId.getText()
            );

            String sql =
                    "DELETE FROM medicines WHERE medicine_id=?";

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setInt(1, medicineId);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Medicine Deleted Successfully"
            );

            loadTableData();
            clearFields();

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error deleting medicine: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private void clearFields(){
        /*
    txtId.setText("");
    txtName.setText("");
    txtPrice.setText("");
    txtQuantity.setText("");
    */
          txtMedicineId.setText("");
        txtName.setText("");
        txtCompany.setText("");
        txtMedicineType.setText("");
        txtPrice.setText("");
        txtQuantity.setText("");
        txtReorderLevel.setText("");
        txtExpiryDate.setText("");

        if (cmbSupplier.getItemCount() > 0) {
            cmbSupplier.setSelectedIndex(0);
        }

        table.clearSelection();
    }
    
}
