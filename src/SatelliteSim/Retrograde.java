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

    /*
    public static double calculateTimeToReentry(Satellite satellite) {
    return new Retrograde(satellite).calculateTimeToReentry();
}
*/


    /*
    //function test for tyler B--------------------------------------
    public static void main(String[] args) {
        Satellite testSat = new Satellite(500, 400_000, 0, 4);
        System.out.println();
    }

     */
}
