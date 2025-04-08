/* Project name: CMSC495
 * File name: Ballistic.java
 * Authors: Timothy Eckart, Tyler Blumenshine, Ricardo Gordon, Mitch Mclaughlin, Siddharth Patel
 * Date: 8 Apr 2025
 * Purpose: Calculates the ballistic coefficient used in drag and re-entry computations.
 */


package SatelliteSim    ;

public class Ballistic {
    private static final double DRAG_COEFFICIENT = 2.2; // Typical for satellites

    // return Ballistic coefficient in kg/m^2
    public static double calculateBallisticCoefficient(Satellite satellite) {
        return satellite.getMass() / (DRAG_COEFFICIENT * satellite.getArea());
    }
/*
    //function test for tyler B----------------------
    public static void main(String[] args) {
        Satellite testSat = new Satellite(500, 400_000, 0, 4);
        System.out.println();
    }
*/
}

