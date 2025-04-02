package SatelliteSim;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Earth {
    private static final Sphere sphere = prepareEarth();
    private static final ImageView imageView = prepareImageView();

    private static Sphere prepareEarth() {
        Sphere sphere = new Sphere(150);
        PhongMaterial earthMaterial = new PhongMaterial();
        try {
            earthMaterial.setDiffuseMap(new Image(Earth.class.getResourceAsStream("/resources/earth/earth-d.jpg")));
            earthMaterial.setSelfIlluminationMap(new Image(Earth.class.getResourceAsStream("/resources/earth/earth-l.jpg")));
            earthMaterial.setSpecularMap(new Image(Earth.class.getResourceAsStream("/resources/earth/earth-s.jpg")));
            earthMaterial.setBumpMap(new Image(Earth.class.getResourceAsStream("/resources/earth/earth-n.jpg")));
        } catch (Exception e) {
            System.err.println("Error loading Earth texture: " + e.getMessage());
            earthMaterial.setDiffuseColor(Color.BLUE); // Fallback color
        }

        sphere.setRotationAxis(Rotate.Y_AXIS);
        sphere.setMaterial(earthMaterial);
        return sphere;
    }

    //Circle FOR EQUATOR ORBIT
    private Circle prepareOrbitCircle(double heightAboveSurface) {
        // Radius of the orbit is sphere radius + height above surface
        double orbitRadius = sphere.getRadius() + heightAboveSurface;
        
        // Create a 2D circle
        Circle circle = new Circle(orbitRadius);
        circle.setFill(null); // Transparent fill
        circle.setStroke(Color.RED); // RED outline
        circle.setStrokeWidth(2.0); // Thickness of the line

        // Rotate 90 degrees around X-axis to align with equator
        circle.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
        
        // Center it with the sphere
        circle.setTranslateX(0);
        circle.setTranslateY(0);
        circle.setTranslateZ(0);

        return circle;
    }

    private static ImageView prepareImageView() {
        ImageView imageView = new ImageView();
        try {
            // Use file: protocol for absolute paths
            String galaxyPath = "/resources/galaxy/galaxy.jpg";
            Image image = new Image(galaxyPath);
            imageView.setImage(image);
            imageView.setPreserveRatio(true);
            imageView.getTransforms().add(new Translate(-image.getWidth() / 2, -image.getHeight() / 2, 800));
        } catch (Exception e) {
            System.err.println("Error loading galaxy image: " + e.getMessage());
        }
        return imageView;
    }
    public static Sphere getSphere() {
        return sphere;
    }

    public static ImageView getImageView() {
        return imageView;
    }
}


