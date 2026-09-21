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
/**
 *
 * @author uqham
 */
public class BillingSystem extends JFrame{
    
  private JTextField txtCustomerName, txtItemName,txtQuantity, txtPrice;
  private JTextArea txtReceipt;
private JButton btnGenerate, btnSave, btnPrint, btnClear;

private double totalAmount =0.0;

public BillingSystem(){
setTitle("Customer Billing System");
setSize(700,500);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setLocationRelativeTo(null);
setLayout(new BorderLayout(10,10));

JLabel lblTitle = new JLabel("HealthFirst Billing System",JLabel.CENTER);
lblTitle.setFont(new Font("Arial",Font.BOLD,22));
lblTitle.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
add( lblTitle, BorderLayout.NORTH);


JPanel panelInput = new JPanel(new GridLayout(5,2,10,15));
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

btnGenerate = new JButton("Generate Bill");
btnClear = new JButton("Clear");
panelInput.add(btnGenerate);
panelInput.add(btnClear);

add(panelInput, BorderLayout.WEST);

JPanel panelReceipt = new JPanel(new BorderLayout());
panelReceipt.setBorder(BorderFactory.createTitledBorder("Invoice Receipt"));

txtReceipt = new JTextArea();
txtReceipt.setEditable(false);
txtReceipt.setFont(new Font("Monospaced",Font.PLAIN,12));
JScrollPane scrollPane = new JScrollPane(txtReceipt);
panelReceipt.add(scrollPane, BorderLayout.CENTER);

JPanel panelReceiptActions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
btnSave = new JButton("Save to File");
btnPrint = new JButton("Print Bill");
panelReceiptActions.add(btnSave);
panelReceiptActions.add(btnPrint);
panelReceipt.add(panelReceiptActions, BorderLayout.SOUTH);

add(panelReceipt, BorderLayout.CENTER);

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
     
     receipt.append("---------------------\n");
     receipt.append(" HealthFirst INVOICE\n");
     receipt.append("----------------------\n");
     receipt.append(String.format("Customer Name: %s\n", customerName));
     receipt.append("----------------------\n");
     receipt.append(String.format("%-20s %-10s %-10s\n","Item Name", "QTY","Price"));
     receipt.append("----------------------\n");
     receipt.append(String.format("%-20s %10d R%-9.2f\n",itemName,qty,price));
     receipt.append("----------------------\n");
     receipt.append(String.format("Total Amount Due: R%-9.2f\n", totalAmount));
     receipt.append("----------------------\n");
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
 }
}
 
 
 

    

