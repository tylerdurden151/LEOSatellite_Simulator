/* Project name: CMSC495
 * File name: NumOrbitsLifecycle.java
 * Authors: Timothy Eckart, Tyler Blumenshine, Ricardo Gordon, Mitch Mclaughlin, Siddharth Patel
 * Date: 5 May 2025
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


    public long calculateNumberOfOrbits() {
        double period = OrbitalPeriod.calculatePeriod(satellite);
        return (long) (lifecycleSeconds / period);
    }

}