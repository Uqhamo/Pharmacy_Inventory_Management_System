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
/**
 *
 * @author uqham
 */
public class StockCheck extends JFrame{
    
    private JTextField searchField;
    private JTable medicineTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> rowSorter;
    
    public StockCheck(){
    setTitle("HealthFirst Stock Check");
    setSize(700, 450);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout(10,10));
    
    
    JPanel searchPanel = new JPanel(new BorderLayout(5,5));
    searchPanel.setBorder(BorderBorderFactory(10));
    
    JLabel searchLabel = new JLabel("Quick Search (Name/ID):");
    searchLabel.setFont(new Font("Arial",Font.BOLD, 14));
    
    searchField = new JTextField();
    searchField.setFont(new Font("Arial", Font.PLAIN, 14));
    
    searchPanel.add(searchLabel,BorderLayout.WEST);
    searchPanel.add(searchField, BorderLayout.CENTER);
    add(searchPanel,BorderLayout.NORTH);
    
    String [] columnNames = {"Medicine ID","Medicine Name","Price (R)", "Available Stock", "Shelf Location"};
    
    Object [][] data = {
        {"M001","Amoxicillin 500mg","40", "45 boxes","Aisle 3-Shelf B"},
        {"M002","Ibuprofen 400mg","35", "120 bottles","Aisle 3-Shelf A"},
        {"M003","Metformin  850mg","70", "0 (Out of Stock)","Aisle 4-Shelf C"},
        {"M004","Atorvastatin 20mg","140", "30 boxes","Aisle 2-Shelf D"},
        {"M005","Paracetmol 500mg","25", "200 packs","Aisle 1-Shelf A"},
        {"M006","Omeprazole 500mg","85", "10 boxes","Aisle 3-Shelf A"},

    };
    
    tableModel = new DefaultTableModel(data, columnNames){
    @Override
    public boolean isCellEditable(int row, int column){
    return false;
    }
    };
    
    medicineTable = new JTable(tableModel);
    medicineTable.setFont(new Font("Arial",Font.PLAIN,13));
    medicineTable.setRowHeight(25);
    medicineTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    rowSorter = new TableRowSorter<>(tableModel);
    medicineTable.setRowSorter(rowSorter);
    
    
    }
}
