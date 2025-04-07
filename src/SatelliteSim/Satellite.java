package application;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;


public class Satellite {
    private final String id;
    private final Box body;
    private final double mass;      // in kg
    private final double altitude;  // in meters
    private final double speed = 0;     // in m/s (optional for now)
    private final double area;
    private double angle = 0;
    private final double orbitRadius = 250;
    private final double orbitSpeed = 0.5;
    // private final Sphere sphere = Earth.getSphere();

    public Satellite(String id, double mass, double area, double altitude) {
        if (mass <= 0 || area <= 0 || altitude < 0) {
            throw new IllegalArgumentException("Mass and area must be positive, altitude and speed non-negative.");
        }
        this.id = id;
        this.mass = mass;
        this.altitude = altitude;
        //this.speed = speed;
        this.area = area;

        // Build visual representation
        this.body = createVisual();
    }

    private Box createVisual() {
        // Use area to estimate width/height visually (simplified to square face)
        double size = Math.sqrt(area) * 5; // Scaling factor for visibility
        Box visual = new Box(size, size, size / 2);

        PhongMaterial material = new PhongMaterial();
        try {
            // Use relative resource path starting with "/"
            String texturePath = "file:C:/Computer Science Major/ProjectOrion/ProjectOrion/src/Images/satellite_texture.jpg";
            Image texture = new Image(texturePath);
            material.setDiffuseMap(texture);
        } catch (Exception e) {
            System.err.println("Error loading satellite texture: " + e.getMessage());
            // Optionally set a default color if the texture fails to load
            material.setDiffuseColor(javafx.scene.paint.Color.GRAY);
        }
        visual.setMaterial(material);

        return visual;
    }

    // Accessors
    public String getId() {
        return id;
    }

    public double getMass() {
        return mass;
    }

    public double getAltitude() {
        return altitude;
    }

    public double getSpeed() {
        return speed;
    }

    public double getArea() {
        return area;
    }

    public double getOrbitRadius() {
        return orbitRadius;
    }

    public double getOrbitSpeed() {
        return orbitSpeed;
    }

    public double getAngle() {
        return angle;
    }

    public Box getBody() {
        return body;
    }

    public void updatePosition() {
        angle += orbitSpeed;
        double radians = Math.toRadians(angle);
        double x = orbitRadius * Math.cos(radians);
        double z = orbitRadius * Math.sin(radians);
        body.setTranslateX(x);
        body.setTranslateZ(z);
    }
}
