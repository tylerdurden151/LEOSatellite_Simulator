package SatelliteSim;

public abstract class SatelliteError extends Exception {
    private final String errorDetails;

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

