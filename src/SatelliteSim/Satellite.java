package SatelliteSim;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;


public class Satellite {
    private final Box body;
    private double angle = 0;
    private final double orbitRadius;
    private final double orbitSpeed;
    private final Sphere sphere = Earth.getSphere();

    public Satellite(double orbitRadius, double orbitSpeed) {
        double realAltitude = satellite.getAltitude(); // in meters
        double heightAboveSurface = (realAltitude / 400_000) * 50; // Proportional scaling
        this.orbitRadius =  sphere.getRadius() + heightAboveSurface;
        this.orbitSpeed = orbitSpeed;
        this.body = new Box(30, 30, 1); // flat like a card
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image(getClass().getResource("/resources/satellite/satellite_texture.png").toExternalForm()));
        body.setMaterial(material);
        updatePosition(); // initial position
    }

    public void updatePosition() {
        angle += orbitSpeed;
        double radians = Math.toRadians(angle);
        double x = orbitRadius * Math.cos(radians);
        double z = orbitRadius * Math.sin(radians);
        body.setTranslateX(x);
        body.setTranslateZ(z);
    }

    public Box getBody() {
        return body;
    }
}
