/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pharmacy_inventory_management_system;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author uqham
 */
public class SupplierManagement extends JFrame {
    
    private JTextField txtId,txtName,txtPhone,txtEmail,txtContactPerson;
    private JTextArea txtAddress;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd,btnUpdate,btnDelete,btnClear;

    private final Color PRIMARY_GREEN = new Color(39,174,96);
    private final Color DARK_GREEN = new Color(30,123,73);
    private final Color LIGHT_GREEN = new Color(232,245,233);
    private final Color RED = new Color(192,57,43);
    private final Color LIGHT_BACKGROUND = new Color(245,250,247);

    public SupplierManagement(){
    setTitle("HealthFirst Supplier Management System");
    setSize(700, 450);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout(10,10));
    getContentPane().setBackground(LIGHT_BACKGROUND);

    
    JPanel panelForm = new JPanel(new GridLayout(5,2,5,5));
    panelForm.setBackground(LIGHT_BACKGROUND);
    panelForm.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(PRIMARY_GREEN,2),
            "Supplier Details"));
    
    txtId = new JTextField();
    txtName = new JTextField();
    txtContactPerson = new JTextField();
    txtPhone = new JTextField();
    txtEmail = new JTextField();
    txtAddress = new JTextArea();
    
    
    panelForm.add(new JLabel("Name:"));
    panelForm.add(txtName);
    panelForm.add(new JLabel("Contact Person:"));
    panelForm.add(txtContactPerson);
    panelForm.add(new JLabel("Phone:"));
    panelForm.add(txtPhone);
    panelForm.add(new JLabel("Email:"));
    panelForm.add(txtEmail);
    
    panelForm.add(new JLabel("Address:"));
    panelForm.add(new JScrollPane(txtAddress));
 
    add(panelForm, BorderLayout.NORTH);
    
    tableModel = new DefaultTableModel(
    new String[]{"Supplier ID","Name","Contact Person","Phone","Email", "Address"}, 0);
    table = new JTable(tableModel);
    table.setRowHeight(28);
    table.setSelectionBackground(LIGHT_GREEN);
    table.setSelectionForeground(Color.BLACK);

    table.getTableHeader().setBackground(PRIMARY_GREEN);
    table.getTableHeader().setForeground(Color.WHITE);

    table.getTableHeader().setFont(new Font("Arial",Font.BOLD,13));

    JScrollPane tableScrollPane = new JScrollPane(table);
    add(tableScrollPane,BorderLayout.CENTER);
    
    JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
    panelButtons.setBackground(LIGHT_BACKGROUND);

    btnAdd = new JButton("Add");
    btnUpdate = new JButton("Update");
    btnDelete = new JButton("Delete");
    btnClear = new JButton("Clear");

    styleButton(btnAdd, PRIMARY_GREEN);
    styleButton(btnUpdate, DARK_GREEN);
    styleButton(btnDelete, RED);
    styleButton(btnClear, DARK_GREEN);
    
    panelButtons.add(btnAdd);
    panelButtons.add(btnUpdate);
    panelButtons.add(btnDelete);
    panelButtons.add(btnClear);
    
    add(panelButtons, BorderLayout.SOUTH);
    btnAdd.addActionListener(e -> addSupplier());
    btnUpdate.addActionListener(e ->updateSupplier()) ;
    btnDelete.addActionListener(e ->deleteSupplier()) ;
    btnClear.addActionListener(e ->clearForm()) ;
   
    table.addMouseListener(new MouseAdapter(){
        
    @Override
    public void mouseClicked(MouseEvent e){
    int selectedrow  = table.getSelectedRow();
    
      if (selectedrow != -1) {

            txtId.setText(tableModel.getValueAt(selectedrow, 0).toString());

            txtName.setText(tableModel.getValueAt(selectedrow, 1).toString());

            txtContactPerson.setText(tableModel.getValueAt(selectedrow, 2).toString());

            txtPhone.setText(tableModel.getValueAt(selectedrow, 3).toString());

            txtEmail.setText(tableModel.getValueAt(selectedrow, 4).toString());

            txtAddress.setText(tableModel.getValueAt(selectedrow, 5).toString());
            
            txtId.setEnabled(false);
    }
    }
    });
    loadSuppliers();
    }
    private void styleButton(
        JButton button,Color color) {

    button.setBackground(color);
    button.setForeground(Color.WHITE);

    button.setFont(new Font("Arial",Font.BOLD,13));

    button.setFocusPainted(false);
    button.setBorderPainted(false);

    button.setPreferredSize(
            new Dimension(100, 35)
    );
}
    
 private void addSupplier(){
     String name = txtName.getText().trim();
    String contactPerson = txtContactPerson.getText().trim();
    String phone = txtPhone.getText().trim();
    String email = txtEmail.getText().trim();
    String address = txtAddress.getText().trim();
     
    if(name.isEmpty()){
    JOptionPane.showMessageDialog(this, 
         "Supplier name cannot be empty!",
         "Error",JOptionPane.ERROR_MESSAGE);
    return;
  }
    String sql = "INSERT INTO suppliers "
            + "(name, contact_person, phone, email, address) "
            + "VALUES (?, ?, ?, ?, ?)";

    try (java.sql.Connection conn = DBConnection.getConnection();
         java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, name);
        stmt.setString(2, contactPerson);
        stmt.setString(3, phone);
        stmt.setString(4, email);
        stmt.setString(5, address);

        stmt.executeUpdate();

        JOptionPane.showMessageDialog(
                this,
                "Supplier added successfully!"
        );

        clearForm();
        loadSuppliers();

    } catch (java.sql.SQLException ex) {

        JOptionPane.showMessageDialog(
                this,
                "Error adding supplier: " + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
 
 }
 }
 
 private void updateSupplier(){
     int selectedrow  = table.getSelectedRow();
    if(selectedrow ==-1){
    
    
    JOptionPane.showMessageDialog(
                this,
                "Select a supplier to update",
                "Warning",
                JOptionPane.WARNING_MESSAGE
        );

    return;
    }

    int supplierId = Integer.parseInt(
            tableModel.getValueAt(selectedrow, 0).toString()
    );

    String name = txtName.getText().trim();
    String contactPerson = txtContactPerson.getText().trim();
    String phone = txtPhone.getText().trim();
    String email = txtEmail.getText().trim();
    String address = txtAddress.getText().trim();

    String sql = "UPDATE suppliers SET "
            + "name = ?, "
            + "contact_person = ?, "
            + "phone = ?, "
            + "email = ?, "
            + "address = ? "
            + "WHERE supplier_id = ?";

    try (java.sql.Connection conn = DBConnection.getConnection();
         java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, name);
        stmt.setString(2, contactPerson);
        stmt.setString(3, phone);
        stmt.setString(4, email);
        stmt.setString(5, address);
        stmt.setInt(6, supplierId);

        stmt.executeUpdate();

        JOptionPane.showMessageDialog(
                this,
                "Supplier updated successfully!"
        );

        clearForm();
        loadSuppliers();

    } catch (java.sql.SQLException ex) {

        JOptionPane.showMessageDialog(
                this,
                "Error updating supplier: " + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
 
    }}
 private void deleteSupplier(){
int selectedrow  = table.getSelectedRow();
    if(selectedrow ==-1){
     JOptionPane.showMessageDialog(
                this,
                "Select a supplier to delete",
                "Warning",
                JOptionPane.WARNING_MESSAGE
        );

    return;
    }

    int supplierId = Integer.parseInt(
            tableModel.getValueAt(selectedrow, 0).toString()
    );

    String sql = "DELETE FROM suppliers WHERE supplier_id = ?";

    try (java.sql.Connection conn = DBConnection.getConnection();
         java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, supplierId);

        stmt.executeUpdate();

        JOptionPane.showMessageDialog(
                this,
                "Supplier deleted successfully!"
        );

        clearForm();
        loadSuppliers();

    } catch (java.sql.SQLException ex) {

        JOptionPane.showMessageDialog(
                this,
                "Error deleting supplier: " + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
     }}
 private void loadSuppliers() {

    tableModel.setRowCount(0);

    String sql = "SELECT supplier_id, name, contact_person, "
            + "phone, email, address "
            + "FROM suppliers";

    try (java.sql.Connection conn = DBConnection.getConnection();
         java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
         java.sql.ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {

            tableModel.addRow(new Object[]{
                rs.getInt("supplier_id"),
                rs.getString("name"),
                rs.getString("contact_person"),
                rs.getString("phone"),
                rs.getString("email"),
                rs.getString("address")
            });
        }

    } catch (java.sql.SQLException ex) {

        JOptionPane.showMessageDialog(
                this,
                "Error loading suppliers: " + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
  private void clearForm(){
    txtId.setText("");
    txtName.setText("");
    txtContactPerson.setText("");
    txtPhone.setText("");
    txtEmail.setText("");
    txtAddress.setText("");

    txtId.setEnabled(true);

    table.clearSelection();
    
    }
}
