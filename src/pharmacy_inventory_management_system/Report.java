/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pharmacy_inventory_management_system;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
/**
 *
 * @author uqham
 */
public class Report extends JFrame{
    
    private JComboBox<String> reportSelector;
    private JTable reportTable;
    private DefaultTableModel tableModel;
    private JLabel lblSummary;
    private Connection conn;
    
    public Report(){
    setTitle("HealthFirst");
    setSize(500, 450);
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     setLocationRelativeTo(null);
     setLayout(new BorderLayout(10,10));
     
     connectDatabase();
      JPanel topPanel  = new JPanel(new GridLayout(4,1,10,10));
      topPanel.setBorder(BorderFactory.createTitledBorder("Select Report"));
      
      reportSelector = new JComboBox<>(new String[]{
      "Stock Levels Report",
       "Low Stock /Out of Stock Report",
       "Inventory Value Report",
       "Sales Summary "
      });
      
      JButton btnGenerate = new JButton("Generate Report");
      btnGenerate.addActionListener(e -> generateReport());
      
      topPanel.add(new JLabel ("Report Type:"));
      topPanel.add(reportSelector);
      topPanel.add(btnGenerate);
      add(topPanel, BorderLayout.NORTH);
      
      
      tableModel = new DefaultTableModel();
      reportTable = new JTable(tableModel);
      add(new JScrollPane(reportTable), BorderLayout.CENTER);
      
      lblSummary = new JLabel(" ", SwingConstants.LEFT);
      lblSummary.setFont(new Font ("Segoe UI", Font.BOLD,14));
      lblSummary.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
      add(lblSummary, BorderLayout.SOUTH);
      
      generateReport();
    }
    
    private void connectDatabase(){
    try{
    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthfirstdb","root","Mekana@12345");
    
    }catch(Exception ex){
    JOptionPane.showMessageDialog(this, "Database Error:"+ex.getMessage());
    }
    }
    
    private void generateReport(){
    String selected = (String) reportSelector.getSelectedItem();
    if(selected == null) return;
    
    switch(selected){
        case "Stock Levels Report" ->stockLevelsReport();
        case "Low Stock /Out of Stock Report"-> lowStockReport();
        case "Inventory Value Report"-> inventoryValueReport();
        case "Sales Summary " -> salesSummaryReport();
    }
    }
    
    private void stockLevelReport(){
    tableModel.setDataVector(new Object[0][0], new String[]{"ID","Name","Price (R)","Quantity","Line Value(R)"});
    int totalItems = 0;
    
    try{
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT *FROM medicines ORDER BY name");
    while(rs.next()){
    tableModel.addRow(new Object[]{
    rs.getInt("id"),rs.getString("name"),
     rs.getDouble("price"), rs.getInt("quantity")
    });
    totalItems++;
    }
    
    }catch(SQLException ex){
    JOptionPane.showMessageDialog(this, "Error loading report: "+ ex.getMessage());
    
    }
    lblSummary.setText(String.format("Total inventory value: R%.2f", totalValue));
    

    }
    private void lowStockReport(){
    tableModel.setDataVector(new Object [0][0], new String []{"ID","Name","Price (R)","Quantity"});
    int lowCount = 0;
    final int LOW_THRESHOLD = 10;
    
    try{
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT *FROM medicnes WHERE quantity <="+LOW_THRESHOLD +"ORDER BY quantity ASC");
    while(rs.next()){
    tableModel.addRow(new Object[]{
    rs.getInt("id"),rs.getString("name"),rs.getDouble("price"),rs.getInt("quantity")
    });
    lowCount++;
    }
    }catch(SQLException ex){
    JOptionPane.showMessageDialog(this, "Error loading report: "+ ex.getMessage());
    }
    lblSummary.setText("Medicines at or below" + LOW_THRESHOLD + "units:" + lowCount);
    }
    
    private void inventoryValueReport(){
    tableModel.setDataVector(new Object [0][0], new String []{"ID","Name","Price (R)","Quantity","Line Value (R)"});
    double totalValue =0;
    
    try{
    Statement stmt = conn.createStatement();
    
    ResultSet rs = stmt.executeQuery("SELECT *FROM medicines ORDER BY name" );
    
    while(rs.next()){
    double price = rs.getDouble("price");
    int qty = rs.getInt("quantity");
    double lineValue = price * qty;
    totalValue += lineValue;
    tableModel.addRow(new Object[] {rs.getInt("id"), rs.getString("name"),price,qty,lineValue});
        
    }
    }catch(SQLException ex){
        JOptionPane.showMessageDialog(this, "Error loading report:"+ ex.getMessage());
    }
    lblSummary.setText(String.format("Total inventory value: R%.2f", totalValue));
    
    }
    
    private void salesSummaryReport(){
    tableModel.setDataVector(new Object[0][0], new String[]{"Item","Unit Sold","Revenue (R)"});
    double totalRevenue =0;
    
    try{
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT item_name, SUM(quantity) AS units, SUM(line_total) AS revenue "+ "FROM sales GROUP BY item_name ORDER BY revenue DESC");
            
    while(rs.next()){
        double revenue = rs.getDouble("revenue");
        tableModel.addRow(new Object []{
        rs.getString("item_name"), rs.getInt("units"),revenue
        });
        totalRevenue += revenue;
    }
    }catch(SQLException ex){
        JOptionPane.showMessageDialog(this, "Error loading sales report: "+ ex.getMessage());
    }
    lblSummary.setText(String.format("Total revenue from all sales: R%.2f", totalRevenue));
    }
    
    
    
}
