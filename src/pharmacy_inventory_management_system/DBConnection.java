/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pharmacy_inventory_management_system;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author uqham
 */
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/healthfirstdb";
    private static final String USER = "root";
    private static final String PASSWORD = "Mekana@12345";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
