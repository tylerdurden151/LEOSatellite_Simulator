/* Project name: CMSC495
 * File name: ValidationError.java
 * Authors: Timothy Eckart, Tyler Blumenshine, Ricardo Gordon, Mitch Mclaughlin, Siddharth Patel
 * Date: 8 Apr 2025
 * Purpose: Specific error class for handling data validation issues within the simulation and database operations.
 */

package SatelliteSim;

public class ValidationError extends SatelliteError {
    public ValidationError(String message, String errorDetails) {
        super(message, errorDetails);
    }
}

