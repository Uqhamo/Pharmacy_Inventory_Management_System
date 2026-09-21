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
    private final Color PRIMARY_GREEN = new Color(39,174,96);
    private final Color DARK_GREEN = new Color(30,123,73);
    private final Color LIGHT_GREEN = new Color(232,245,233);
    private final Color RED = new Color(192,57,43);
    private final Color LIGHT_BACKGROUND = new Color(245,250,247);
    
    public MedicineManagement(){
    setTitle("HealthFirst Medicine Management System");
    setSize(700, 450);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout(10,10));
    getContentPane().setBackground(LIGHT_BACKGROUND);

    connectDatabase();
    
    JPanel panelForm = new JPanel(new GridLayout(5,4,10,10));
    panelForm.setBackground(LIGHT_BACKGROUND);
 
    panelForm.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY_GREEN,2),"Medicine Details"));
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
    
  
    tableModel = new DefaultTableModel(new String[]{
                    "ID","Name","Company","Type","Price","Quantity","Reorder Level","Expiry Date","Supplier ID"},0);
    table = new JTable(tableModel);
    table.setFont(
        new Font("Arial", Font.PLAIN, 13)
);

    table.setRowHeight(28);

    table.setSelectionBackground(LIGHT_GREEN);
    table.setSelectionForeground(Color.BLACK);


    table.getTableHeader().setBackground(PRIMARY_GREEN);
    table.getTableHeader().setForeground(Color.WHITE);

    table.getTableHeader().setFont(
        new Font("Arial", Font.BOLD, 13)
);
    table.addMouseListener(new MouseAdapter(){
    public void mouseClicked(MouseEvent e){
    int row  = table.getSelectedRow();
    if (row != -1) {

    txtMedicineId.setText(tableModel.getValueAt(row, 0).toString());

    txtName.setText(tableModel.getValueAt(row, 1).toString());

    txtCompany.setText(tableModel.getValueAt(row, 2).toString());

    txtMedicineType.setText(tableModel.getValueAt(row, 3).toString());

    txtPrice.setText(tableModel.getValueAt(row, 4).toString());

    txtQuantity.setText(tableModel.getValueAt(row, 5).toString());

    txtReorderLevel.setText(tableModel.getValueAt(row, 6).toString());

    txtExpiryDate.setText(tableModel.getValueAt(row, 7).toString());

    if (tableModel.getValueAt(row, 8) != null) {cmbSupplier.setSelectedItem(
            tableModel.getValueAt(row, 8).toString());
    
    }
    }}});
    add(new JScrollPane(table),BorderLayout.CENTER);
    
    JPanel panelButtons = new JPanel();
    JButton btnCreate = new JButton("Add (Create)");
    JButton btnRead = new JButton("Refresh (Read)");
    JButton btnUpdate = new JButton("Update");
    JButton btnDelete = new JButton("Delete");
    JButton btnClear = new JButton("Clear");
    panelButtons.setBackground(LIGHT_BACKGROUND);

    styleButton(btnCreate, PRIMARY_GREEN);
    styleButton(btnRead, DARK_GREEN);
    styleButton(btnUpdate, PRIMARY_GREEN);
    styleButton(btnDelete, RED);
    styleButton(btnClear, DARK_GREEN);
    
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
    private void styleButton(
        JButton button,
        Color color) {

    button.setBackground(color);
    button.setForeground(Color.WHITE);

    button.setFont(new Font("Arial",Font.BOLD,13));

    button.setFocusPainted(false);
    button.setBorderPainted(false);

    button.setPreferredSize( new Dimension(100, 35));
}
    
private void connectDatabase(){
    
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

        }catch(NumberFormatException ex) {

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

            int medicineId = Integer.parseInt(txtMedicineId.getText());

            double price = Double.parseDouble(txtPrice.getText());

            int quantity = Integer.parseInt( txtQuantity.getText());

            int reorderLevel = Integer.parseInt(txtReorderLevel.getText());

            int supplierId = Integer.parseInt(cmbSupplier.getSelectedItem().toString());

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

            int medicineId = Integer.parseInt(txtMedicineId.getText());

            String sql ="DELETE FROM medicines WHERE medicine_id=?";

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setInt(1, medicineId);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this,"Medicine Deleted Successfully");

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
