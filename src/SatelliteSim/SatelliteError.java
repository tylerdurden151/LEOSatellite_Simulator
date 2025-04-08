/* Project name: CMSC495
 * File name: SatelliteError.java
 * Authors: Timothy Eckart, Tyler Blumenshine, Ricardo Gordon, Mitch Mclaughlin, Siddharth Patel
 * Date: 8 Apr 2025
 * Purpose: Base exception class for handling satellite-related errors throughout the project.
 */


package SatelliteSim;

public abstract class SatelliteError extends Exception {
    private final String errorDetails;

    //Throws error message for Database Error and ValidationError
    public SatelliteError(String message, String errorDetails) {
        super(message);
        this.errorDetails = errorDetails;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": " + getMessage() + " | Details: " + errorDetails;
    }
}

