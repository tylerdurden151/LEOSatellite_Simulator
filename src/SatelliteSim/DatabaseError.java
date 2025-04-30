/* Project name: CMSC495
 * File name: DatabaseError.java
 * Authors: Timothy Eckart, Tyler Blumenshine, Ricardo Gordon, Mitch Mclaughlin, Siddharth Patel
 * Date: 5 May 2025
 * Purpose: Custom error class for database-specific exceptions.
 */


package SatelliteSim;

public class DatabaseError extends SatelliteError{
    public DatabaseError(String message, String errorDetails) {
        super(message, errorDetails);
    }
}