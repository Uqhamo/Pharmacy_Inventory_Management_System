/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pharmacy_inventory_management_system;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import javax.swing.table.TableRowSorter;
import java.awt.event.KeyEvent;
import java.sql.*;
/**
 *
 * @author uqham
 */
public class StockCheck extends JFrame{
    
    private JTextField searchField;
    private JTable medicineTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> rowSorter;
    private Connection conn;
    
    private final Color PRIMARY_GREEN = new Color(39,174,96);
    private final Color DARK_GREEN = new Color(30,123,73);
    private final Color LIGHT_GREEN = new Color(232,245,233);
    private final Color RED = new Color(192,57,43);
    private final Color LIGHT_BACKGROUND = new Color(245,250,247);

    public StockCheck(){
    setTitle("HealthFirst Stock Check");
    setSize(700, 450);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout(10,10));
             getContentPane().setBackground(LIGHT_BACKGROUND);

    
    JPanel searchPanel = new JPanel(new BorderLayout(10,10));
    searchPanel.setBackground(LIGHT_BACKGROUND);
    searchPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
    
    JLabel searchLabel = new JLabel("Quick Search (Name/ID):");
    searchLabel.setFont(new Font("Arial",Font.BOLD, 14));
    searchLabel.setForeground(DARK_GREEN);
    searchField = new JTextField();
    searchField.setFont(new Font("Arial", Font.PLAIN, 14));
    searchField.setBorder(
    BorderFactory.createLineBorder(PRIMARY_GREEN,2));

    searchPanel.add(searchLabel,BorderLayout.WEST);
    searchPanel.add(searchField, BorderLayout.CENTER);
    add(searchPanel,BorderLayout.NORTH);
    
    String [] columnNames = {"Medicine ID"
            ,"Medicine Name",
            "Company",
            "Price (R)", 
            "Available Stock", 
            "Reorder Level",
    "Expiry Date"};
   
    tableModel = new DefaultTableModel(columnNames,0){
    @Override
    public boolean isCellEditable(int row, int column){
    return false;
    }
    };
    
    medicineTable = new JTable(tableModel);
    medicineTable.setFont(new Font("Arial",Font.PLAIN,13));
    medicineTable.setRowHeight(28);
    medicineTable.setSelectionBackground(LIGHT_GREEN);
    medicineTable.setSelectionForeground(Color.BLACK);

    medicineTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    rowSorter = new TableRowSorter<>(tableModel);
    medicineTable.setRowSorter(rowSorter);
    
    JScrollPane scrollPane = new JScrollPane(medicineTable);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
    add(scrollPane, BorderLayout.CENTER);
    
    searchField.addKeyListener(new KeyAdapter(){
    @Override
    public void keyReleased(KeyEvent e){
        
    String text = searchField.getText().trim();
         if (text.length() == 0) {
            rowSorter.setRowFilter(null);
         }else{
             try {
                rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
             } catch (java.util.regex.PatternSyntaxException ex) {
                rowSorter.setRowFilter(null);
                    }
                }}
        });

        connectDatabase();
        loadStock();
    }
      private void connectDatabase() {

        try {

            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/healthfirstdb","root","Mekana@12345");

        }catch(SQLException ex) {

            JOptionPane.showMessageDialog(
                    this, "Database Error: " + ex.getMessage(),"Database Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadStock() {

        tableModel.setRowCount(0);

        String sql = "SELECT medicine_id, name, company, price, "
                + "quantity_in_stock, reorder_level, expiry_date "
                + "FROM medicines "
                + "ORDER BY name";

        try (
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()
        ) {

            while (rs.next()) {

                int quantity = rs.getInt("quantity_in_stock");
                int reorderLevel = rs.getInt("reorder_level");

                String stockStatus = String.valueOf(quantity);

                if (quantity == 0) {
                    stockStatus = quantity + " (OUT OF STOCK)";
                } else if (quantity <= reorderLevel) {
                    stockStatus = quantity + " (LOW STOCK)";
                }

                tableModel.addRow(new Object[]{
                    rs.getInt("medicine_id"),
                    rs.getString("name"),
                    rs.getString("company"),
                    rs.getDouble("price"),
                    stockStatus,
                    reorderLevel,
                    rs.getDate("expiry_date")
                });
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading stock: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
    
   
    

