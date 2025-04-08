package SatelliteSim;

import java.sql.*;


public class SatelliteDataBaseManager {
        private static final String URL = "jdbc:postgresql://localhost:5432/SatelliteSimulator";
        private static final String USER = "satellite_user";
        private static final String PASSWORD = "123456789";

        // Method to get a Satellite by ID
        public Satellite getSatelliteById(int id) throws DatabaseError {
            Satellite satellite = null;
            String sql = "SELECT * FROM \"Satellite\" WHERE \"Satellite_ID\" = ?";
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    satellite = new Satellite(rs.getInt("User_ID"), rs.getString("SatelliteName"),rs.getDouble("Mass"),rs.getDouble("Area"), rs.getDouble("Altitude"));
                    satellite.setSatellite_ID(rs.getInt("Satellite_id"));

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

            String sql = "INSERT INTO \"Satellite\" (\"SatelliteName\", \"User_ID\", \"Mass\", \"Altitude\", \"Area\") " +
                    "VALUES (?, ?, ?, ?, ?)";
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, satellite.getId());
                pstmt.setInt(2, satellite.getUser_Id());
                pstmt.setDouble(3, satellite.getMass());
                pstmt.setDouble(4, satellite.getAltitude());
                pstmt.setDouble(5, satellite.getArea());
                pstmt.executeUpdate();
                satellite.setSatellite_ID(getSatelliteIdByName(satellite.getId()));
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
                    "\"Area\" = ? WHERE \"Satellite_ID\" = ?";
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, satellite.getId());
                pstmt.setLong(2, satellite.getUser_Id());
                pstmt.setDouble(3, satellite.getMass());
                pstmt.setDouble(4, satellite.getAltitude());
                pstmt.setDouble(5, satellite.getSpeed());
                pstmt.setDouble(6, satellite.getArea());
                pstmt.setInt(7, satellite.getSatellite_ID());
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new DatabaseError("Satellite update failed", "No satellite found with ID: " + satellite.getSatellite_ID());
                }
            } catch (SQLException e) {
                throw new DatabaseError("Database connection failed", e.getMessage());
            }
        }
        public int getSatelliteIdByName(String satelliteName) throws DatabaseError {
            String sql = "SELECT \"Satellite_ID\" FROM \"Satellite\" WHERE \"SatelliteName\" = ?";
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, satelliteName);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("Satellite_ID");
                } else {
                    throw new DatabaseError("Satellite not found", "Name: " + satelliteName);
                }
            } catch (SQLException e) {
                throw new DatabaseError("Database connection failed", e.getMessage());
            }
        }
        public String[] getSatelliteNamesAndIds() throws DatabaseError {
            String sql = "SELECT \"Satellite_ID\", \"SatelliteName\" FROM \"Satellite\"";
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                // Determine the largest Satellite_ID for array size
                int maxId = 0;
                while (rs.next()) {
                    maxId = Math.max(maxId, rs.getInt("Satellite_ID"));
                }

                // Create an array of satellite names indexed by Satellite_ID
                String[] satelliteNames = new String[maxId + 1];
                rs.beforeFirst(); // Reset the cursor to process rows again
                while (rs.next()) {
                    int id = rs.getInt("Satellite_ID");
                    satelliteNames[id] = rs.getString("SatelliteName");
                }
                return satelliteNames;

            } catch (SQLException e) {
                throw new DatabaseError("Database connection failed", e.getMessage());
            }
        }

    }



