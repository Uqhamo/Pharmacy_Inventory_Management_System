/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pharmacy_inventory_management_system;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.sql.*;

/**
 *
 * @author uqham
 */
public class POSsystem extends JFrame {
    private JTable cartTable;
    private DefaultTableModel tableModel;
    private JLabel lblSubtotal;
    private JLabel lblTax;
    private JLabel lblTotal;
    private Connection conn;
    
    private final double TAX_RATE = 0.05;
    private double subtotal = 0.00;
    
    private HashMap<String, Integer> cartItems = new HashMap<>();
    private NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
    
    public POSsystem(){
    setTitle("HealthFirstPOS");
    setSize(1024,700);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout(10,10));
    connectDatabase();
    
    JPanel headerPanel = new JPanel(new BorderLayout());
    headerPanel.setBackground(new Color(44,62,80));
    headerPanel.setBorder(BorderFactory.createEmptyBorder(15,20,15,20));
    
    JLabel titleLabel = new JLabel("HeallthFirst POS");
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
    titleLabel.setForeground(Color.WHITE);
    headerPanel.add(titleLabel, BorderLayout.WEST);
    
    add(headerPanel, BorderLayout.NORTH);
    
    JPanel productPanel = new JPanel(new BorderLayout());
    productPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Quick Catalog",0,0,new Font("Sagoe UI",Font.BOLD, 14)));
    
    JPanel gridPanel = new JPanel(new GridLayout(0,3,10,10));
    
    gridPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    
    String [][] medicine ={{"Tablet","40"},{"Capsule","30"},{"Syrup","80"},{"Injection","40"},{"Cream","140"}};
 
for(String[] med:medicine){
String name = med[0];
double price = Double.parseDouble(med[1]);
JButton medButton = new JButton("<html><center><b>"+name+"</b><br>"+ currencyFormatter.format(price)+ "</center></html>");
medButton.setFont(new Font("Segoe UI", Font.PLAIN,14));
medButton.setFocusPainted(false);
medButton.setBackground(new Color(236,240,241));
medButton.addActionListener(e -> addToCart(name,price));
gridPanel.add(medButton);

}    
JScrollPane itemScroll = new JScrollPane(gridPanel);
productPanel.add(itemScroll, BorderLayout.CENTER);
add(productPanel, BorderLayout.CENTER);

JPanel rightPanel = new JPanel(new BorderLayout(10,10));
rightPanel.setPreferredSize(new Dimension(420,0));
rightPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY),"Current Order",0,0,new Font("Segoe UI",Font.BOLD, 14)));

String [] columns ={"Item","Qty","Price","Total"};
tableModel = new DefaultTableModel(columns,0){
    @Override
    public boolean isCellEditable(int row, int column){return false;}
    
};
cartTable = new JTable(tableModel);
cartTable.setFont(new Font("Segoe UI",Font.PLAIN,13));
cartTable.setRowHeight(24);
rightPanel.add(new JScrollPane(cartTable),BorderLayout.CENTER);

JPanel summaryPanel = new JPanel(new GridLayout(4,2,5,5));
summaryPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
summaryPanel.add(new JLabel("Subtotal"));
lblSubtotal = new JLabel(currencyFormatter.format(0),SwingConstants.RIGHT);
summaryPanel.add(lblSubtotal);

summaryPanel.add(new JLabel("TAX (5%):"));
lblTax = new JLabel(currencyFormatter.format(0),SwingConstants.RIGHT);
summaryPanel.add(lblTax);

JLabel lblTotalTag = new JLabel("Total Due:");
lblTotalTag.setFont(new Font("Segoe UI",Font.BOLD, 16));
summaryPanel.add(lblTotalTag);

lblTotal = new JLabel(currencyFormatter.format(0),SwingConstants.RIGHT);
lblTotal.setFont(new Font("Segoe UI",Font.BOLD,16));
lblTotal.setForeground(new Color(192,57,43));
summaryPanel.add(lblTotal);

JButton btnClear = new JButton("Clear Order");
btnClear.setBackground(new Color(231,76,60));
btnClear.setForeground(Color.WHITE);
btnClear.setFocusPainted(false);
btnClear.addActionListener(e -> clearCart());
summaryPanel.add(btnClear);

JButton btnPay = new JButton("Pay");
btnPay.setBackground(new Color(46,204,113));
btnPay.setForeground(Color.WHITE);
btnPay.setFocusPainted(false);
btnPay.setFont(new Font("Segoe UI",Font.BOLD,14));
btnPay.addActionListener(e -> clearCart());
summaryPanel.add(btnPay);

rightPanel.add(summaryPanel, BorderLayout.SOUTH);
add(rightPanel, BorderLayout.EAST);
    }

private void addToCart(String name, double price){
if(cartItems.containsKey(name)){
int rowIndex = cartItems.get(name);
int currentQty = (int) tableModel.getValueAt(rowIndex, 1);
int newQty = currentQty +1;
double newTotal = price * newQty;
tableModel.setValueAt(newQty, rowIndex, 1);
tableModel.setValueAt(currencyFormatter.format(newTotal), rowIndex, 3);
}else{
int newRowIndex = tableModel.getRowCount();
tableModel.addRow(new Object[]{name,1,currencyFormatter.format(price),currencyFormatter.format(price)});
cartItems.put(name, newRowIndex);
}
subtotal +=price;
updateTotals();
}
private void connectDatabase(){
try{
conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthfirstdb","root","Mekana@12345");
Statement stmt = conn.createStatement();

stmt.execute("CREATE TABLE IF NOT EXISTS sales("+"sale_id INT PRIMARY KEY AUTO_INCREMENT," +"sale_date: TIMESTAMP, DEFAULT CURRENT_TIMESTAMP "+"total_amount: DECIMAL(10,2)"+"user_id: INT, FK )");
}catch(Exception ex ){
JOptionPane.showMessageDialog(this, "Database Error:" + ex.getMessage());
}
}
    private void updateTotals(){
    double tax = subtotal * TAX_RATE;
    double total = subtotal + tax;
    
    lblSubtotal.setText(currencyFormatter.format(subtotal));
    lblTax.setText(currencyFormatter.format(tax));
    lblTotal.setText(currencyFormatter.format(total));

    
    }
    private void clearCart(){
    tableModel.setRowCount(0);
    cartItems.clear();
    subtotal =0.00;
    updateTotals();
    }
    
    private void processPayment(){
    if(tableModel.getRowCount()==0){
    JOptionPane.showMessageDialog(this, 
            "Your cart is empty!","Error",
            JOptionPane.WARNING_MESSAGE);
    return;
    }
    double tax = subtotal * TAX_RATE;
    double total = subtotal + tax;
    saveSaleToDatabase();
    
    String receiptMessage = String.format("Transaction Complete!\n\nTotal Paid: %s\n Thank you for your business!",currencyFormatter.format(total));
    JOptionPane.showMessageDialog(this, receiptMessage,"Success",JOptionPane.INFORMATION_MESSAGE);
    clearCart();
    }
    
    private void saveSaleToDatabase(){
    try{
        Statement idStmt = conn.createStatement();
        ResultSet idRs = idStmt.executeQuery("SELECT COALESCE(MAX(transaction_id),0) +1 AS sales_id FROM sales");
        int transactionId = 1;
        if(idRs.next()){
        transactionId =  idRs.getInt("sales_id");
        }
        String sql = "INSERT INTO sales(sale_id,sale_date,total_amount,user_id)";
        PreparedStatement pst = conn.prepareStatement(sql);
        for(int row = 0; row<tableModel.getRowCount(); row++){
        String itemName = tableModel.getValueAt(row, 0).toString();
            int qty = (int) tableModel.getValueAt(row, 1);
            String priceStr = tableModel.getValueAt(row, 2).toString().replaceAll("[^0-9.]", "");
            String totalStr = tableModel.getValueAt(row, 3).toString().replaceAll("[^0-9.]", "");
            double unitPrice = Double.parseDouble(priceStr);
            double lineTotal = Double.parseDouble(totalStr);

            pst.setInt(1, transactionId);
            pst.setString(2, itemName);
            pst.setInt(3, qty);
            pst.setDouble(4, unitPrice);
            pst.setDouble(5, lineTotal);
            pst.executeUpdate();
        }
    
    }catch(Exception ex){
    JOptionPane.showMessageDialog(this,"Error saving sale:" + ex.getMessage());
    }
    }
}
