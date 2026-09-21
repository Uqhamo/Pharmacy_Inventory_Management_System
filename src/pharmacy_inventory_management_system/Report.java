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
    private final Color PRIMARY_GREEN = new Color(39,174,96);
private final Color DARK_GREEN = new Color(30,123,73);
private final Color LIGHT_GREEN = new Color(232,245,233);
private final Color RED = new Color(192,57,43);
private final Color LIGHT_BACKGROUND = new Color(245,250,247);
    public Report(){
    setTitle("HealthFirst Reports");
    setSize(500, 450);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout(10,10));
    getContentPane().setBackground(LIGHT_BACKGROUND);

    connectDatabase();
    JPanel headerPanel = new JPanel();
    headerPanel.setBackground(PRIMARY_GREEN);
    headerPanel.setBorder( BorderFactory.createEmptyBorder(15, 20, 15, 20));
    JLabel titleLabel = new JLabel("HealthFirst Reports");

    titleLabel.setForeground(Color.WHITE);

    titleLabel.setFont(new Font("Arial", Font.BOLD,22));

    headerPanel.add(titleLabel);
    
    
    JPanel topPanel  = new JPanel(new GridLayout(3,1,10,10));
    topPanel.setBackground(LIGHT_BACKGROUND);
    topPanel.setBorder(BorderFactory.createTitledBorder("Select Report"));

    JLabel lblReport = new JLabel("Report Type:");

    lblReport.setFont(new Font("Arial",Font.BOLD,14));

    lblReport.setForeground(DARK_GREEN);
      reportSelector = new JComboBox<>(new String[]{
      "Stock Levels Report",
       "Low Stock /Out of Stock Report",
       "Inventory Value Report",
       "Sales Summary "
      });
      
    JButton btnGenerate = new JButton("Generate Report");
     
    btnGenerate.setBackground(PRIMARY_GREEN);
    btnGenerate.setForeground(Color.WHITE);

    btnGenerate.setFont(new Font("Arial",Font.BOLD,14));

    btnGenerate.setFocusPainted(false);
    btnGenerate.setBorderPainted(false);

      
    btnGenerate.addActionListener(e -> generateReport());
      
    topPanel.add(new JLabel ("Report Type:"));
    topPanel.add(reportSelector);
    topPanel.add(btnGenerate);
    add(topPanel, BorderLayout.NORTH);
      
    JPanel topContainer = new JPanel(new BorderLayout(10, 10));

    topContainer.setBackground(LIGHT_BACKGROUND);

    topContainer.add(headerPanel,BorderLayout.NORTH);

    topContainer.add(topPanel,BorderLayout.CENTER);

    add(topContainer,BorderLayout.NORTH);
    
    tableModel = new DefaultTableModel();
    reportTable = new JTable(tableModel);
      
    reportTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    reportTable.setFont(new Font("Arial",Font.PLAIN,13));

    reportTable.setRowHeight(28);

    reportTable.setSelectionBackground(
            LIGHT_GREEN);

    reportTable.setSelectionForeground(
            Color.BLACK
    );

    
    reportTable
            .getTableHeader()
            .setBackground(PRIMARY_GREEN);

    reportTable
            .getTableHeader()
            .setForeground(Color.WHITE);

    reportTable.getTableHeader().setFont(new Font( "Arial", Font.BOLD, 13));
    JScrollPane scrollPane =
            new JScrollPane(reportTable);

    scrollPane.setBorder(
            BorderFactory.createEmptyBorder(
                    10, 10, 10, 10
            )
    );

    add(scrollPane,BorderLayout.CENTER);
       
      add(new JScrollPane(reportTable), BorderLayout.CENTER);
      
     lblSummary = new JLabel(" ", SwingConstants.LEFT);
     lblSummary.setFont(new Font ("Arial", Font.BOLD,14));
      
     lblSummary.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
     lblSummary.setForeground(DARK_GREEN);
    lblSummary.setOpaque(true);
    lblSummary.setBackground(LIGHT_GREEN);

    lblSummary.setBorder(
            BorderFactory.createEmptyBorder(
                    15, 15, 15, 15
            )
    );
      add(lblSummary, BorderLayout.SOUTH);
      
      generateReport();
    }
    
    private void connectDatabase(){
       try {

            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/healthfirstdb",
                    "root",
                    "Mekana@12345"
            );

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: "
                    + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
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
    
    private void stockLevelsReport(){
    tableModel.setDataVector(
                new Object[0][0],
                new String[]{
                    "ID",
                    "Name",
                    "Company",
                    "Price (R)",
                    "Quantity",
                    "Reorder Level"
                }
        );    
int totalItems = 0;

  int totalQuantity = 0;

        String sql =
                "SELECT medicine_id, name, company, price, "
                + "quantity_in_stock, reorder_level "
                + "FROM medicines "
                + "ORDER BY name";

        try (
                PreparedStatement pst =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        pst.executeQuery()
        ) {

            while (rs.next()) {

                int quantity =
                        rs.getInt("quantity_in_stock");

                tableModel.addRow(
                        new Object[]{
                            rs.getInt("medicine_id"),
                            rs.getString("name"),
                            rs.getString("company"),
                            rs.getDouble("price"),
                            quantity,
                            rs.getInt("reorder_level")
                        }
                );

                totalItems++;
                totalQuantity += quantity;
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading report: "
                    + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        lblSummary.setText(
                "Total different medicines: "
                + totalItems
                + " | Total units in stock: "
                + totalQuantity
        );
    
    }
    
    private void lowStockReport(){
        
      tableModel.setDataVector(
                new Object[0][0],
                new String[]{
                    "ID",
                    "Name",
                    "Company",
                    "Price (R)",
                    "Quantity",
                    "Reorder Level",
                    "Status"
                }
        );   
    int lowCount = 0;
    
    
    String sql =
                "SELECT medicine_id, name, company, price, "
                + "quantity_in_stock, reorder_level "
                + "FROM medicines "
                + "WHERE quantity_in_stock <= reorder_level "
                + "ORDER BY quantity_in_stock ASC";

        try (
            PreparedStatement pst = conn.prepareStatement(sql);

            ResultSet rs = pst.executeQuery()
        ) {

            while (rs.next()) {

                int quantity = rs.getInt("quantity_in_stock");

                String status;

                if (quantity == 0) {
                    status = "OUT OF STOCK";
                } else {
                    status = "LOW STOCK";
                }

                tableModel.addRow(
                        new Object[]{
                            rs.getInt("medicine_id"),
                            rs.getString("name"),
                            rs.getString("company"),
                            rs.getDouble("price"),
                            quantity,
                            rs.getInt("reorder_level"),
                            status
                        }
                );

                lowCount++;
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading low stock report: "
                    + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        lblSummary.setText(
                "Medicines at or below their reorder level: "
                + lowCount
        );
    }

    
    private void inventoryValueReport(){
       
         tableModel.setDataVector(
                new Object[0][0],
                new String[]{
                    "ID",
                    "Name",
                    "Price (R)",
                    "Quantity",
                    "Total Value (R)"
                }
        );
    double totalValue =0.00;

    String sql =
                "SELECT medicine_id, name, price, "
                + "quantity_in_stock "
                + "FROM medicines "
                + "ORDER BY name";

        try (
                PreparedStatement pst =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        pst.executeQuery()
        ) {

            while (rs.next()) {

            double price =rs.getDouble("price");

            int quantity =rs.getInt("quantity_in_stock");

            double lineValue = price * quantity;

            totalValue += lineValue;

            tableModel.addRow(
                        new Object[]{
                            rs.getInt("medicine_id"),
                            rs.getString("name"),
                            price,
                            quantity,
                            lineValue
                        }
                );
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading inventory report: "
                    + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        lblSummary.setText(String.format("Total inventory value: R%.2f",totalValue
                )
        );
    }


    private void salesSummaryReport(){
        
        
        tableModel.setDataVector(
                new Object[0][0],
                new String[]{
                    "Medicine",
                    "Units Sold",
                    "Revenue (R)"
                }
        );
double totalRevenue =0.00;

   String sql =
                "SELECT m.name AS medicine_name, "
                + "SUM(si.quantity_sold) AS units, "
                + "SUM(si.quantity_sold * si.price_at_sale) "
                + "AS revenue "
                + "FROM sale_items si "
                + "INNER JOIN medicines m "
                + "ON si.medicine_id = m.medicine_id "
                + "GROUP BY m.medicine_id, m.name "
                + "ORDER BY revenue DESC";

        try (
                PreparedStatement pst =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        pst.executeQuery()
        ) {

            while (rs.next()) {

                double revenue =
                        rs.getDouble("revenue");

                tableModel.addRow(
                        new Object[]{
                            rs.getString("medicine_name"),
                            rs.getInt("units"),
                            revenue
                        }
                );

                totalRevenue += revenue;
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading sales report: "
                    + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        lblSummary.setText(
                String.format(
                        "Total revenue from all sales: R%.2f",
                        totalRevenue
                )
        );
    }
    
    
}
