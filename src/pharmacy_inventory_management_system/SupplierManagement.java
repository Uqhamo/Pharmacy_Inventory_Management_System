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
    
    private JTextField txtId,txtName,txtPhone,txtEmail;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd,btnUpdate,btnDelete,btnClear;

    public SupplierManagement(){
    setTitle("HealthFirst Supplier Management System");
    setSize(700, 450);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout(10,10));
    
    JPanel panelForm = new JPanel(new GridLayout(4,2,5,5));
    panelForm.setBorder(BorderFactory.createTitledBorder("Supplier Details"));
    
    txtId = new JTextField();
    txtName = new JTextField();
    txtPhone = new JTextField();
    txtEmail = new JTextField();
    
    panelForm.add(new JLabel("Supplier ID:"));
    panelForm.add(txtId);
    panelForm.add(new JLabel("Name:"));
    panelForm.add(txtName);
    panelForm.add(new JLabel("Phone:"));
    panelForm.add(txtPhone);
    panelForm.add(new JLabel("Email:"));
    panelForm.add(txtEmail);
 
    add(panelForm, BorderLayout.NORTH);
    
    tableModel = new DefaultTableModel(new String[]{"ID","Name","Phone","Email"},0);
    table = new JTable(tableModel);
    add(new JScrollPane(table),BorderLayout.CENTER);
    
    JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
    JButton btnAdd = new JButton("Add");
    JButton btnUpdate = new JButton("Update");
    JButton btnDelete = new JButton("Delete");
    JButton btnClear = new JButton("Clear");

    
    panelButtons.add(btnAdd);
    panelButtons.add(btnUpdate);
    panelButtons.add(btnDelete);
    panelButtons.add(btnClear);
    
    add(panelButtons, BorderLayout.SOUTH);
    btnAdd.addActionListener(e -> addSupplier());
    btnUpdate.addActionListener(e ->updateSupplier()) ;
    btnDelete.addActionListener(e ->deleteSupplier()) ;
    btnClear.addActionListener(e ->clearForm()) ;
    
    /*
    btnAdd.e -> addSupplier());
    btnUpdate.e -> updateSupplier());
    btnDelete.e -> deleteSupplier());
    btnClear.e -> clearForm());
    
    */
    table.addMouseListener(new MouseAdapter(){
    public void mouseClicked(MouseEvent e){
    int selectedrow  = table.getSelectedRow();
    txtId.setText(tableModel.getValueAt(selectedrow, 0).toString());
    txtName.setText(tableModel.getValueAt(selectedrow, 1).toString());
    txtPhone.setText(tableModel.getValueAt(selectedrow, 2).toString());
    txtEmail.setText(tableModel.getValueAt(selectedrow, 3).toString());
    txtId.setEnabled(false);
    }
    });
    }
 private void addSupplier(){
 if(txtId.getText().isEmpty() || txtName.getText().isEmpty()){
 JOptionPane.showMessageDialog(this, "ID and Name cannot be empty!","Error",JOptionPane.ERROR_MESSAGE);
 return;
 }
 tableModel.addRow(new Object[]{txtId.getText(),txtName.getText(),txtPhone.getText(),txtEmail.getText()});
 clearForm();
 }
 
 private void updateSupplier(){
     int selectedrow  = table.getSelectedRow();
if(selectedrow >=0){
tableModel.setValueAt(txtName.getText(),selectedrow,1);
tableModel.setValueAt(txtPhone.getText(),selectedrow,2);
tableModel.setValueAt(txtEmail.getText(),selectedrow,3);
clearForm();
}else{
JOptionPane.showMessageDialog(this, "Select a supplier to update","Warning",JOptionPane.WARNING_MESSAGE);
}
 }
 
 private void deleteSupplier(){
  int selectedrow  = table.getSelectedRow();
if(selectedrow >=0){
tableModel.removeRow(selectedrow);
clearForm();
 }else{
JOptionPane.showMessageDialog(this, "Select a supplier to delete","Warning",JOptionPane.WARNING_MESSAGE);
}
 }
  private void clearForm(){
    txtId.setText("");
    txtName.setText("");
    txtPhone.setText("");
    txtEmail.setText("");
    txtId.setEnabled(true);
    table.clearSelection();
    
    }
}
