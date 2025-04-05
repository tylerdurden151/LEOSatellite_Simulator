package SatelliteSim;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


    public class SatelliteDataBaseManager {
        private static final String URL = "jdbc:postgresql://localhost:5432/SatelliteSimulator";
        private static final String USER = "postgres";
        private static final String PASSWORD = "your_password";

        // Method to get a Satellite by ID
        public Satellite getSatelliteById(int id) throws DatabaseError {
            Satellite satellite = null;
            String sql = "SELECT * FROM \"Satellite\" WHERE \"Satellite_ID\" = ?";
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    satellite = new Satellite();
                    satellite.setSatelliteID(rs.getInt("Satellite_ID"));
                    satellite.setSatelliteName(rs.getString("SatelliteName"));
                    satellite.setUserID(rs.getLong("User_ID"));
                    satellite.setMass(rs.getDouble("Mass"));
                    satellite.setAltitude(rs.getDouble("Altitude"));
                    satellite.setSpeed(rs.getDouble("Speed"));
                    satellite.setArea(rs.getDouble("Area"));
                } else {
                    throw new DatabaseError("Satellite not found", "ID: " + id);
                }
            } catch (SQLException e) {
                throw new DatabaseError("Database connection failed", e.getMessage());
            }
            return satellite;
        }

        // Method to insert a new Satellite into the database
        public void addSatellite(Satellite satellite) throws ValidationError, DatabaseError {
            if (satellite.getMass() <= 0) {
                throw new ValidationError("Invalid mass", "Mass must be positive. Given: " + satellite.getMass());
            }

            String sql = "INSERT INTO \"Satellite\" (\"SatelliteName\", \"User_ID\", \"Mass\", \"Altitude\", \"Speed\", \"Area\") " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, satellite.getSatelliteName());
                pstmt.setLong(2, satellite.getUserID());
                pstmt.setDouble(3, satellite.getMass());
                pstmt.setDouble(4, satellite.getAltitude());
                pstmt.setDouble(5, satellite.getSpeed());
                pstmt.setDouble(6, satellite.getArea());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new DatabaseError("Failed to insert satellite", e.getMessage());
            }
        }

        // New Method: Delete a Satellite by ID
        public void deleteSatelliteById(int id) throws DatabaseError {
            String sql = "DELETE FROM \"Satellite\" WHERE \"Satellite_ID\" = ?";
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new DatabaseError("Satellite deletion failed", "No satellite found with ID: " + id);
                }
            } catch (SQLException e) {
                throw new DatabaseError("Database connection failed", e.getMessage());
            }
        }

        // New Method: Update a Satellite's Information
        public void updateSatellite(Satellite satellite) throws ValidationError, DatabaseError {
            if (satellite.getMass() <= 0) {
                throw new ValidationError("Invalid mass", "Mass must be positive. Given: " + satellite.getMass());
            }

            String sql = "UPDATE \"Satellite\" SET \"SatelliteName\" = ?, \"User_ID\" = ?, \"Mass\" = ?, \"Altitude\" = ?, " +
                    "\"Speed\" = ?, \"Area\" = ? WHERE \"Satellite_ID\" = ?";
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, satellite.getSatelliteName());
                pstmt.setLong(2, satellite.getUserID());
                pstmt.setDouble(3, satellite.getMass());
                pstmt.setDouble(4, satellite.getAltitude());
                pstmt.setDouble(5, satellite.getSpeed());
                pstmt.setDouble(6, satellite.getArea());
                pstmt.setInt(7, satellite.getSatelliteID());
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new DatabaseError("Satellite update failed", "No satellite found with ID: " + satellite.getSatelliteID());
                }
            } catch (SQLException e) {
                throw new DatabaseError("Database connection failed", e.getMessage());
            }
        }
    }



