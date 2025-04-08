/* Project name: CMSC495
 * File name: AtmosphericDrag.java
 * Authors: Timothy Eckart, Tyler Blumenshine, Ricardo Gordon, Mitch Mclaughlin, Siddharth Patel
 * Date: 7 Apr 2025
 * Purpose: Calculates atmospheric density and orbital decay based on altitude and satellite properties.
 */

package SatelliteSim;


public class AtmosphericDrag {
    private static final double MU_EARTH = 3.986004418e14;
    private static final double EARTH_RADIUS = 6.371e6;
    private static final double RHO_100KM = 3.0e-7;
    private static final double SCALE_HEIGHT = 50_000;

    public static double calculateDensity(double altitude) {
        return RHO_100KM * Math.exp(-(altitude - 100_000) / SCALE_HEIGHT);
    }

    //calculates orbital decay in meters per 1 orbit
    public static double calculateDecayPerOrbit(Satellite satellite) {
        double altitude = satellite.getAltitude();
        double radius = EARTH_RADIUS + altitude;

        double speed = (satellite.getSpeed() > 0) ? satellite.getSpeed() : OrbitalPeriod.calculateCircularSpeed(satellite);
        double period = OrbitalPeriod.calculatePeriod(satellite);

        double density = calculateDensity(altitude);
        double bc = Ballistic.calculateBallisticCoefficient(satellite);

        double dragAccel = 0.5 * density * Math.pow(speed, 2) / bc;
        return radius * (dragAccel * period) / speed; // Corrected formula
    }
    /*
    //function test for tyler B------------------------------------------------
    public static void main(String[] args) {
        Satellite testSat = new Satellite(500, 400_000, 0, 4);
        double decay = calculateDecayPerOrbit(testSat);
        System.out.printf("Decay per orbit: %.4f meters%n", decay);
    }
    */
}

