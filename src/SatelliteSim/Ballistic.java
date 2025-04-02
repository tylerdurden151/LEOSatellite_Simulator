package application;

public class Ballistic {
    private static final double DRAG_COEFFICIENT = 2.2; // Typical for satellites

    // return Ballistic coefficient in kg/m^2
    public static double calculateBallisticCoefficient(Satellite satellite) {
        return satellite.getMass() / (DRAG_COEFFICIENT * satellite.getArea());
    }
    //function test for tyler B----------------------
    public static void main(String[] args) {
        Satellite testSat = new Satellite(500, 400_000, 0, 4);
        System.out.println();
    }
}
