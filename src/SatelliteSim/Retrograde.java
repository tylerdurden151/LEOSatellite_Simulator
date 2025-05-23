/* Project name: CMSC495
 * File name: Retrograde.java
 * Authors: Timothy Eckart, Tyler Blumenshine, Ricardo Gordon, Mitch Mclaughlin, Siddharth Patel
 * Date: 5 May 2025
 * Purpose: Computes time to satellite re-entry and formats the timeframe for display.
 */


package SatelliteSim;

public class Retrograde {
    private static final double REENTRY_ALTITUDE = 100_000; // 100 km
    private final Satellite satellite;

    public Retrograde(Satellite satellite) {
        this.satellite = satellite;
    }
    
    //calculates re-entry in seconds
    public double calculateTimeToReentry() {
        double currentAltitude = satellite.getAltitude();
        if (currentAltitude <= REENTRY_ALTITUDE) {
            return 0;
        }
        double decayPerOrbit = AtmosphericDrag.calculateDecayPerOrbit(satellite);
        double period = OrbitalPeriod.calculatePeriod(satellite);
        double orbitsToReentry = (currentAltitude - REENTRY_ALTITUDE) / decayPerOrbit;
        return orbitsToReentry * period;
    }

    // Returns re-entry time frame in years, months, and days.
    public String getReentryTimeframe() {
        double timeSeconds = calculateTimeToReentry();
        double years = timeSeconds / (365.25 * 24 * 60 * 60);
        int wholeYears = (int) years;
        double remainingDays = (years - wholeYears) * 365.25;
        int months = (int) (remainingDays / 30.4375);
        int days = (int) (remainingDays - months * 30.4375);
        return String.format("%d years, %d months, %d days", wholeYears, months, days);
    }

}
