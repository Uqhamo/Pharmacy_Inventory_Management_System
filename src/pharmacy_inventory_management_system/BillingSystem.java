/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pharmacy_inventory_management_system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.border.EmptyBorder;
/**
 *
 * @author uqham
 */
public class BillingSystem extends JFrame{
    
private JTextField txtCustomerName, txtItemName,txtQuantity, txtPrice;
private JTextArea txtReceipt;
private JButton btnGenerate, btnSave, btnPrint, btnClear;

private double totalAmount =0.0;

private final Color PRIMARY_GREEN = new Color(39,174,96);
private final Color DARK_GREEN = new Color(30,123,73);
private final Color LIGHT_GREEN = new Color(232,245,233);
private final Color RED = new Color(192,57,43);

private final Color LIGHT_BACKGROUND = new Color(245,250,247);



public BillingSystem(){
    setTitle(" HealthFirst Customer Billing System");
    setSize(950,600);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);
    getContentPane().setBackground(LIGHT_BACKGROUND);
    setLayout(new BorderLayout());

    JPanel headerPanel = new JPanel(new BorderLayout());

    headerPanel.setBackground(PRIMARY_GREEN);
    headerPanel.setBorder(new EmptyBorder(20,25,20,25));

    JLabel lblTitle = new JLabel("HealthFirst Pharmacy");
    lblTitle.setFont(new Font("Arial",Font.BOLD,24));
    lblTitle.setForeground(Color.WHITE);

    JLabel lblSystem = new JLabel("HealthFirst Billing System",JLabel.CENTER);
    lblSystem.setFont(new Font("Arial",Font.BOLD,24));
    lblSystem.setForeground(Color.WHITE);

    headerPanel.add(lblSystem,BorderLayout.EAST);

    add( headerPanel, BorderLayout.NORTH);
    JPanel mainPanel =new JPanel(new GridLayout(1, 2, 20, 0));

    mainPanel.setBackground(LIGHT_BACKGROUND);
    mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20) );

    JPanel leftPanel = new JPanel(new BorderLayout (10,10));
    leftPanel.setBackground(Color.WHITE);

    leftPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)),
        new EmptyBorder(20, 20, 20, 20))
);
    JLabel lblDetails =new JLabel("Customer & Item Details");
    lblDetails.setFont(new Font( "Arial", Font.BOLD, 20));
    lblDetails.setForeground(DARK_GREEN);
    leftPanel.add(lblDetails,BorderLayout.NORTH);


    JPanel panelInput = new JPanel(new GridLayout(8,1,5,8));
    panelInput.setBackground(Color.WHITE);

    panelInput.setBorder(BorderFactory.createTitledBorder("Customer & Item Details"));

    panelInput.add(new JLabel("Customer Name:"));
    txtCustomerName = new JTextField();
    panelInput.add(txtCustomerName);

    panelInput.add(new JLabel("Item Name:"));
    txtItemName = new JTextField();
    panelInput.add(txtItemName);

    panelInput.add(new JLabel("Quantity:"));
    txtQuantity = new JTextField();
    panelInput.add(txtQuantity);

    panelInput.add(new JLabel("Price Per Unit:"));
    txtPrice = new JTextField();
    panelInput.add(txtPrice);
    leftPanel.add(panelInput,BorderLayout.CENTER);

    JPanel inputButtons = new JPanel(new GridLayout(1, 2, 10, 0));

    inputButtons.setBackground(Color.WHITE);

    btnGenerate = new JButton("Generate Bill");

    styleButton(btnGenerate,DARK_GREEN);

    btnClear = new JButton("Clear");

    styleButton(btnClear,RED);

    inputButtons.add(btnGenerate);
    inputButtons.add(btnClear);

    leftPanel.add(inputButtons,BorderLayout.SOUTH);

    JPanel panelReceipt = new JPanel(new BorderLayout(10,10));
 
    panelReceipt.setBackground(Color.WHITE);

    panelReceipt.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(220, 220, 22)),
            new EmptyBorder(20, 20, 20, 20)));

        JLabel lblReceipt = new JLabel("Invoice Receipt");

        lblReceipt.setFont(new Font("Arial",Font.BOLD,20));

        lblReceipt.setForeground(DARK_GREEN);

        panelReceipt.add(lblReceipt,BorderLayout.NORTH);


    txtReceipt = new JTextArea();
    txtReceipt.setEditable(false);
    txtReceipt.setFont(new Font("Monospaced",Font.PLAIN,12));

    txtReceipt.setBackground(new Color(250, 250, 250));

    txtReceipt.setBorder(new EmptyBorder(15, 15, 15, 15));
    JScrollPane scrollPane = new JScrollPane(txtReceipt);
    scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

    panelReceipt.add(scrollPane,BorderLayout.CENTER);


    JPanel panelReceiptActions = new JPanel(new GridLayout(1,2,10,0));
    panelReceiptActions.setBackground(Color.WHITE);
    btnSave = new JButton("Save to File");
    styleButton(btnSave,PRIMARY_GREEN);
    
    btnPrint = new JButton("Print Bill");
 
    styleButton(btnPrint,DARK_GREEN );
    panelReceiptActions.add(btnSave);
    panelReceiptActions.add(btnPrint);
    panelReceipt.add(panelReceiptActions, BorderLayout.SOUTH);


    mainPanel.add(leftPanel);
    mainPanel.add(panelReceipt);

    add(mainPanel,BorderLayout.CENTER);
        
    btnGenerate.addActionListener(new ActionListener(){
    @Override
    public void actionPerformed(ActionEvent e){
    generateBillLogic();
    }
    });

    btnSave.addActionListener(new ActionListener(){
    @Override
    public void actionPerformed(ActionEvent e){
    saveBillToFile();
    }
    });

    btnPrint.addActionListener(new ActionListener(){
    @Override
    public void actionPerformed(ActionEvent e){
    printBillReceipt();
    }
    });
    
    btnClear.addActionListener(new ActionListener(){
    @Override
    public void actionPerformed(ActionEvent e){
    clearFields();
   }
});








}
private void styleButton(
            JButton button,
            Color backgroundColor) {

        button.setBackground(
                backgroundColor
        );

        button.setForeground(
                Color.WHITE
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        button.setFocusPainted(false);

        button.setPreferredSize(
                new Dimension(
                        150, 42
                )
        );
    }
private void generateBillLogic(){
try{
    String customerName = txtCustomerName.getText().trim();
    String itemName = txtItemName.getText().trim();
    
    if(customerName.isEmpty() || itemName.isEmpty()){
    JOptionPane.showMessageDialog(this, "Please fill in Customer and Item Names");
    return;
    }
    
    int qty = Integer.parseInt(txtQuantity.getText().trim());
    double price = Double.parseDouble(txtPrice.getText().trim());
    
    totalAmount = qty*price;
     StringBuilder receipt = new StringBuilder();
     
     receipt.append("==============================\n");
     receipt.append(" HealthFirst INVOICE\n");
     receipt.append("===============================\n");
     receipt.append(String.format("Customer Name: %s\n", customerName));
     receipt.append("==================================\n");
     receipt.append(String.format("%-20s %-10s %-10s\n","Item Name", "QTY","Price"));
     receipt.append("====================================\n");
     receipt.append(String.format("%-20s %10d R%-9.2f\n",itemName,qty,price));
     receipt.append("=====================================\n");
     receipt.append(String.format("Total Amount Due: R%-9.2f\n", totalAmount));
     receipt.append("=======================================\n");
     receipt.append(" Thank You For Your Business!\n");
     
     txtReceipt.setText(receipt.toString());



     
     

}catch(NumberFormatException ex){
JOptionPane.showMessageDialog(this, "Quantity must be an Interger and Price must be a Decimal.", "Data Error",JOptionPane.ERROR_MESSAGE);

}
}

 private void saveBillToFile(){
 String receiptContent = txtReceipt.getText();
 if(receiptContent.isEmpty()){
 JOptionPane.showMessageDialog(this,"No bill generated to save!","Warning",JOptionPane.WARNING_MESSAGE);
 return;
 }
 JFileChooser fileChooser = new JFileChooser();
 fileChooser.setDialogTitle("Specify a file to save the receipt");
 
 int userSelection = fileChooser.showSaveDialog(this);
 
 if(userSelection == JFileChooser.APPROVE_OPTION){
 java.io.File fileToSave = fileChooser.getSelectedFile();
 String filePath = fileToSave.getAbsolutePath();
 if(!filePath.endsWith(".txt")){
 filePath +=".txt";
 }
 try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath) )){
 writer.write(receiptContent);
 JOptionPane.showMessageDialog(this, "Bill saved successfully to:\n"+filePath,"Success",JOptionPane.INFORMATION_MESSAGE);
 }catch(IOException ex){
 JOptionPane.showMessageDialog(this, "Error saving file:"+ ex.getMessage(),"File Error",JOptionPane.ERROR_MESSAGE);
 
 }
 }
 }
 private void printBillReceipt(){
  if(txtReceipt.getText().isEmpty()){
  JOptionPane.showMessageDialog(this, "No bill generated to print!","Warning", JOptionPane.WARNING_MESSAGE);
  return;
  }
  try{
      boolean done = txtReceipt.print();
      if(done){
      JOptionPane.showMessageDialog(this, "Printing Completed Sucessfully","Printer Job",JOptionPane.INFORMATION_MESSAGE);
      
      }else{
      JOptionPane.showMessageDialog(this, "Printing Cancelled","Printer Job",JOptionPane.WARNING_MESSAGE);
      }
  }catch(Exception ex){
  JOptionPane.showMessageDialog(this, "Printing Error:"+ ex.getMessage(),"Printer Error",JOptionPane.ERROR_MESSAGE);
  
  }
 }
 private void clearFields(){
 txtCustomerName.setText("");
 txtItemName.setText("");
 txtQuantity.setText("");
 txtPrice.setText("");
 txtReceipt.setText("");
 totalAmount =0.0;
   txtCustomerName.requestFocus();
 }
}
 
 
 

    

