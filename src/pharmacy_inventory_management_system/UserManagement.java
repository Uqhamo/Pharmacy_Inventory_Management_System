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
public class UserManagement extends JFrame {
    
private JTextField txtUsername;
private JPasswordField txtPassword;
private JTable tableCashiers;
private DefaultTableModel tableModel;
    
private JButton btnAdd,btnUpdate,btnDelete,btnClear;

    
public UserManagement(){
setTitle("HealthFirst User Management System");
    setSize(650, 450);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout(10,10));
    
    JPanel panelForm = new JPanel(new GridLayout(2,2,5,5));
    panelForm.setBorder(BorderFactory.createTitledBorder("Cashier Details"));
    
    panelForm.add(new JLabel("Username:"));
    txtUsername = new JTextField();
    panelForm.add(txtUsername);
    
    panelForm.add(new JLabel("Password:"));
    txtPassword = new JPasswordField();
    panelForm.add(txtPassword);
    
    
    JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
    JButton btnAdd = new JButton("Add");
    JButton btnUpdate = new JButton("Update");
    JButton btnDelete = new JButton("Delete");
    JButton btnClear = new JButton("Clear");

    
    panelButtons.add(btnAdd);
    panelButtons.add(btnUpdate);
    panelButtons.add(btnDelete);
    panelButtons.add(btnClear);
    
    
    JPanel panelNorth = new JPanel(new BorderLayout());
     
    panelNorth.add(panelForm,BorderLayout.CENTER);
    panelNorth.add(panelButtons,BorderLayout.SOUTH);
    add(panelNorth, BorderLayout.NORTH);
    
    tableModel = new DefaultTableModel(new String []{"Username","Password"},0);
    tableCashiers = new JTable(tableModel);
    add(new JScrollPane(tableCashiers),BorderLayout.CENTER);
    
    tableModel.addRow(new Object [] {"cashier1","pass123"});
    tableModel.addRow(new Object [] {"alice_c","secret456"});
    
    
    btnAdd.addActionListener(new ActionListener(){
    @Override
    public void actionPerformed(ActionEvent e){
    String user = txtUsername.getText().trim();
    String pass = new String(txtPassword.getPassword().trim());
    
    if(user.isEmpty() || pass.isEmpty()){
    JOptionPane.showMessageDialog(UserManagement.this, "Fields cannot be empty!","Error",JOptionPane.ERROR_MESSAGE);
    return;
    }
    tableModel.addRow(new Object []{user,pass});
    clearFields();
    }
    });

    btnUpdate.addActionListener(new ActionListener(){
    @Override
    public void actionPerformed(ActionEvent e){
       int selectedrow = tableCashiers.getSelectedRow();
       if(selectedrow == -1){
       JOptionPane.showMessageDialog(UserManagement.this,
               "Select a row to Update!","Warnning",
               JOptionPane.WARNING_MESSAGE);
    return;
       }
       String user = txtUsername.getText().trim();
       String pass = new String(txtPassword.getPassword()).trim();
       
       tableModel.setValueAt(user, selectedrow, 0);
       tableModel.setValueAt(pass, selectedrow, 1);
       clearFields();

        
    }});
    btnDelete.addActionListener(new ActionListener(){
    @Override
    public void actionPerformed(ActionEvent e){
       int selectedrow = tableCashiers.getSelectedRow();
       if(selectedrow == -1){
       JOptionPane.showMessageDialog(UserManagement.this,
               "Select a row to Update!","Warnning",
               JOptionPane.WARNING_MESSAGE);
    return;
       }
       
       tableModel.removeRow(selectedrow);
       clearFields();
}
    });

btnClear.addActionListener(e-> clearFields());

tableCashiers.addMouseListener(new MouseAdapter(){
 @Override
 public void mouseClicked(MouseEvent e){
int selectedrow =tableCashiers.getSelectedRow();
if(selectedrow !=-1){
txtUsername.setText(tableModel.getValueAt(selectedrow, 0).toString());
txtPassword.setText(tableModel.getValueAt(selectedrow, 0).toString());

}
}
    
});

}
private void clearFields(){
txtUsername.setText("");
txtPassword.setText("");
tableCashiers.clearSelection();

}
}