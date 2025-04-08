/* Project name: CMSC495
 * File name: NumOrbitsLifecycle.java
 * Authors: Timothy Eckart, Tyler Blumenshine, Ricardo Gordon, Mitch Mclaughlin, Siddharth Patel
 * Date: 8 Apr 2025
 * Purpose: Calculates the number of satellite orbits during its lifecycle.
 */


package SatelliteSim;

public class NumOrbitsLifecycle {
    private static final double DEFAULT_LIFECYCLE_SECONDS = 5 * 365.25 * 24 * 60 * 60; // 5 years
    private final Satellite satellite;
    private final double lifecycleSeconds;

    public NumOrbitsLifecycle(Satellite satellite) {
        this.satellite = satellite;
        this.lifecycleSeconds = DEFAULT_LIFECYCLE_SECONDS;
    }

    public NumOrbitsLifecycle(Satellite satellite, double lifecycleYears) {
        if (lifecycleYears <= 0) {
            throw new IllegalArgumentException("Lifecycle duration must be positive.");
        }
        this.satellite = satellite;
        this.lifecycleSeconds = lifecycleYears * 365.25 * 24 * 60 * 60;
    }


    public long calculateNumberOfOrbits() {
        double period = OrbitalPeriod.calculatePeriod(satellite);
        return (long) (lifecycleSeconds / period);
    }

    public double getLifecycleYears() {
        return lifecycleSeconds / (365.25 * 24 * 60 * 60);
    }
    /*
    public static double calculateNumberOfOrbits(Satellite satellite) {
        return new NumOrbitsLifecycle(satellite).calculateNumberOfOrbits();
    }
*/

    //function test for tyler B---------------------------------------------
    /*
    public static void main(String[] args) {
        Satellite testSat = new Satellite(500, 400_000, 0, 4);
        System.out.println();
    }
*/
}