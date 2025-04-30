/* Project name: CMSC495
 * File name: DatabaseConnection.java
 * Authors: Timothy Eckart, Tyler Blumenshine, Ricardo Gordon, Mitch Mclaughlin, Siddharth Patel
 * Date: 5 May 2025
 * Purpose: Connection database with Postgre SQL
 */

package SatelliteSim;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/SatelliteSimulator"; // Replace with your database URL
    private static final String USER = "satellite_user"; // Replace with your username
    private static final String PASSWORD = "123456789"; // Replace with your password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

