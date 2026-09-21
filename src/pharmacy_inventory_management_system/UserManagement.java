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
public class UserManagement extends JFrame {
    
private JTextField txtUsername;
private JPasswordField txtPassword;
private JComboBox<String> cmbRole;
private JTable tableCashiers;
private JTextField txtFullName;
private DefaultTableModel tableModel;
    
private JButton btnAdd,btnUpdate,btnDelete,btnClear;
private final Color PRIMARY_GREEN = new Color(39,174,96);
private final Color DARK_GREEN = new Color(30,123,73);
private final Color LIGHT_GREEN = new Color(232,245,233);
private final Color RED = new Color(192,57,43);
private final Color LIGHT_BACKGROUND = new Color(245,250,247);
    
public UserManagement(){
    setTitle("HealthFirst User Management System");

    setSize(750, 500);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout(10,10));
    getContentPane().setBackground(LIGHT_BACKGROUND);

    
    JPanel panelForm = new JPanel(new GridLayout(4,2,5,5));
    panelForm.setBackground(LIGHT_BACKGROUND);
    panelForm.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY_GREEN,2),"User Details"));
    
    panelForm.add(new JLabel("Full Name:"));
    txtFullName = new JTextField();
    
    txtFullName.setBorder(BorderFactory.createLineBorder(PRIMARY_GREEN));
    panelForm.add(txtFullName);
    
    panelForm.add(new JLabel("Username:"));
    txtUsername = new JTextField();
    txtUsername.setBorder(BorderFactory.createLineBorder(PRIMARY_GREEN));

    panelForm.add(txtUsername);
    
    panelForm.add(new JLabel("Password:"));
    txtPassword = new JPasswordField();
    txtPassword.setBorder(BorderFactory.createLineBorder(PRIMARY_GREEN));

    panelForm.add(txtPassword);
    
    panelForm.add(new JLabel("Role:"));
    cmbRole = new JComboBox<>(new String[]{"Admin","Cashier"});
    panelForm.add(cmbRole);
    
    
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

    
    
    JPanel panelNorth = new JPanel(new BorderLayout());
    panelNorth.setBackground(LIGHT_BACKGROUND);

    panelNorth.add(panelForm,BorderLayout.CENTER);
    panelNorth.add(panelButtons,BorderLayout.SOUTH);
    add(panelNorth, BorderLayout.NORTH);
    
    tableModel = new DefaultTableModel(new String []{"User ID","Full Name","Username","Password","Role"},0);
    tableCashiers = new JTable(tableModel);
    tableCashiers.setSelectionBackground(LIGHT_GREEN);
    tableCashiers.setSelectionForeground(Color.BLACK);
    tableCashiers.getTableHeader().setBackground(PRIMARY_GREEN);

    tableCashiers.getTableHeader().setForeground(Color.WHITE);

    tableCashiers.getTableHeader().setFont(new Font("Arial",Font.BOLD,13 )); 

    
    add(new JScrollPane(tableCashiers),BorderLayout.CENTER);
    
    
    
    
    btnAdd.addActionListener(new ActionListener(){
    @Override
    public void actionPerformed(ActionEvent e){
    String fullName = txtFullName.getText().trim();
    String user = txtUsername.getText().trim();
    String pass = new String(txtPassword.getPassword()).trim();
    String role = cmbRole.getSelectedItem().toString();
    
    if(user.isEmpty() || pass.isEmpty() || fullName.isEmpty()){
    JOptionPane.showMessageDialog(UserManagement.this,
            "Fields cannot be empty!","Error",
            JOptionPane.ERROR_MESSAGE);
    return;
    }
    String sql = "INSERT INTO users" +"(username,password,role,full_name)" + "VALUES(?,?,?,?)";
    
    try(java.sql.Connection conn = DBConnection.getConnection();
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql)){
        
        stmt.setString(1,user);
        stmt.setString(2,pass);
        stmt.setString(3,role);
        stmt.setString(4,fullName);

        stmt.executeUpdate();
        JOptionPane.showMessageDialog(UserManagement.this, "User added successfully");
        
        clearFields();
        loadUsers();
    
    }catch(java.sql.SQLException ex){
    JOptionPane.showMessageDialog(UserManagement.this, "Error adding user:" + ex.getMessage(),"Database Error", JOptionPane.ERROR_MESSAGE);
    }
    
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
    int userId = Integer.parseInt(tableModel.getValueAt(selectedrow, 0).toString());
    String fullName = txtFullName.getText().trim();
    String user = txtUsername.getText().trim();
    String pass = new String(txtPassword.getPassword()).trim();
    String role = cmbRole.getSelectedItem().toString();
     
    String sql = "UPDATE users SET "
                + "full_name = ?, "
                + "username = ?, "
                + "password = ?, "
                + "role = ? "
                + "WHERE user_id = ?";

        try (java.sql.Connection conn = DBConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, fullName);
            stmt.setString(2, user);
            stmt.setString(3, pass);
            stmt.setString(4, role);
            stmt.setInt(5, userId);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(
                    UserManagement.this,
                    "User updated successfully!"
            );

            clearFields();
            loadUsers();

        }catch(java.sql.SQLException ex) {

            JOptionPane.showMessageDialog(
                    UserManagement.this,
                    "Error updating user: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        
    }}});
    
    btnDelete.addActionListener(new ActionListener(){
    @Override
    public void actionPerformed(ActionEvent e){
       int selectedrow = tableCashiers.getSelectedRow();
       if(selectedrow == -1){
       JOptionPane.showMessageDialog(UserManagement.this,
               "Select a row to Update!",
               "Warnning",
               JOptionPane.WARNING_MESSAGE);
    return;
       }
        int userId = Integer.parseInt(tableModel.getValueAt(selectedrow, 0).toString());

        String sql = "DELETE FROM users WHERE user_id = ?";

        try (java.sql.Connection conn = DBConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(
                    UserManagement.this,
                    "User deleted successfully!"
            );

            clearFields();
            loadUsers();

        } catch (java.sql.SQLException ex) {

            JOptionPane.showMessageDialog(
                    UserManagement.this,
                    "Error deleting user: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
       
       
}
    } });

btnClear.addActionListener(e-> clearFields());

tableCashiers.addMouseListener(new MouseAdapter(){
 @Override
public void mouseClicked(MouseEvent e){
int selectedrow =tableCashiers.getSelectedRow();
if(selectedrow !=-1){
    
txtFullName.setText(tableModel.getValueAt(selectedrow, 1).toString());
    
txtUsername.setText(tableModel.getValueAt(
        selectedrow, 2).toString());
txtPassword.setText(tableModel.getValueAt(
        selectedrow, 3).toString());

}
cmbRole.setSelectedItem(tableModel.getValueAt(selectedrow, 4).toString());
}
    
});
loadUsers();

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
private void loadUsers() {

    tableModel.setRowCount(0);

    String sql = "SELECT user_id, full_name, username, password, role "
            + "FROM users";

    try (java.sql.Connection conn = DBConnection.getConnection();
         java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
         java.sql.ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {

            tableModel.addRow(new Object[]{
                rs.getInt("user_id"),
                rs.getString("full_name"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("role")
            });
        }

    } catch (java.sql.SQLException ex) {

        JOptionPane.showMessageDialog(
                this,
                "Error loading users: " + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
private void clearFields(){
txtFullName.setText("");
txtUsername.setText("");
txtPassword.setText("");
cmbRole.setSelectedIndex(0);
tableCashiers.clearSelection();

}
}