package SatelliteSim;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;


public class Satellite {
    private final Box body;
    private final double mass;      // in kg
    private final double altitude;  // in meters
    private final double speed;     // in m/s (optional for now)
    private final double area;
    private double angle = 0;
    private final double orbitRadius = 250;
    private final double orbitSpeed = 0.5;
    // private final Sphere sphere = Earth.getSphere();

    public Satellite(double mass, double altitude, double speed, double area) {
        if (mass <= 0 || altitude < 0 || speed < 0 || area <= 0) {
            throw new IllegalArgumentException("Mass and area must be positive, altitude and speed non-negative.");
        }
        this.mass = mass;
        this.altitude = altitude;
        this.speed = speed;
        this.area = area;

        // Build visual representation
        this.body = createVisual();
    }
    private Box createVisual() {
        // Use area to estimate width/height visually (simplified to square face)
        double size = Math.sqrt(area) * 5; // Scaling factor for visibility
        Box visual = new Box(size, size, size / 2);

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.GRAY); // Default color, or add texture
        visual.setMaterial(material);

        return visual;
    }

    // Accessors
    public double getMass() { return mass; }
    public double getAltitude() { return altitude; }
    public double getSpeed() { return speed; }
    public double getArea() { return area; }
    public double getOrbitRadius() { return orbitRadius; }
    public double getOrbitSpeed() { return orbitSpeed; }
    public Box getBody() { return body; }
}
  /*  public void updatePosition() {
        angle += orbitSpeed;
        double radians = Math.toRadians(angle);
        double x = orbitRadius * Math.cos(radians);
        double z = orbitRadius * Math.sin(radians);
        body.setTranslateX(x);
        body.setTranslateZ(z);
    }
*/
/*
    public class Satellite {
        private double mass;          // kg
        private double altitude;      // m (height above Earth's surface)
        private double speed;         // m/s (initial speed, optional)
        private double area;          // m^2 (directional/cross-sectional area)

        public Satellite(double mass, double altitude, double speed, double area) {
            if (mass <= 0 || altitude < 0 || speed < 0 || area <= 0) {
                throw new IllegalArgumentException("Mass and area must be positive, altitude and speed non-negative.");
            }
            this.mass = mass;
            this.altitude = altitude;
            this.speed = speed;
            this.area = area;
        }

        // Getters
        public double getMass() { return mass; }
        public double getAltitude() { return altitude; }
        public double getSpeed() { return speed; }
        public double getArea() { return area; }
    }

 */

