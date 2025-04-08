/* Project name: CMSC495
 * File name: OrbitalPeriod.java
 * Authors: Timothy Eckart, Tyler Blumenshine, Ricardo Gordon, Mitch Mclaughlin, Siddharth Patel
 * Date: 8 Apr 2025
 * Purpose: Calculates the orbital period and circular speed for a satellite.
 */


package SatelliteSim;

public class OrbitalPeriod {
    private static final double MU_EARTH = 3.986004418e14; // m^3/s^2
    private static final double EARTH_RADIUS = 6.371e6;    // m

    //Calculates period from altitude (circular orbit), returns Period in seconds
    public static double calculatePeriodFromAltitude(Satellite satellite) {
        double radius = EARTH_RADIUS + satellite.getAltitude();
        if (radius <= 0) {
            throw new IllegalArgumentException("Effective radius must be positive.");
        }
        return 2 * Math.PI * Math.sqrt(Math.pow(radius, 3) / MU_EARTH);
    }

    //Calculates period from speed (circular orbit), returns Period in seconds
    public static double calculatePeriodFromSpeed(Satellite satellite) {
        double speed = satellite.getSpeed();
        if (speed <= 0) {
            throw new IllegalArgumentException("Speed must be positive.");
        }
        return 2 * Math.PI * MU_EARTH / Math.pow(speed, 3);
    }

    //Default period calculation (speed if provided, else altitude).
    public static double calculatePeriod(Satellite satellite) {
        return (satellite.getSpeed() > 0) ? calculatePeriodFromSpeed(satellite) : calculatePeriodFromAltitude(satellite);
    }

    //Calculates circular orbit speed for a given altitude, Speed in m/s
    public static double calculateCircularSpeed(Satellite satellite) {
        double radius = EARTH_RADIUS + satellite.getAltitude();
        return Math.sqrt(MU_EARTH / radius);
    }

    /*
    //function test for tyler B----------------------------------------------
    public static void main(String[] args) {
        Satellite testSat = new Satellite(500, 400_000, 0, 4);
        System.out.println();
    }

     */
}
