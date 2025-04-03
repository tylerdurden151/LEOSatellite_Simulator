import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Earth {
    private final Sphere sphere;
    private final ImageView imageView;
    private final Circle orbitCircle;

    // Constructor takes a Satellite object to get altitude
    public Earth(Satellite satellite) {
        this.sphere = prepareEarth();
        this.imageView = prepareImageView();
        this.orbitCircle = prepareOrbitCircle(satellite);
    }

    private Sphere prepareEarth() {
        Sphere sphere = new Sphere(150);
        PhongMaterial earthMaterial = new PhongMaterial();
        try {
            String earthPath = "file:C:/Computer Science Major/ProjectOrion/ProjectOrion/src/Images/earth-d.jpg";
            earthMaterial.setDiffuseMap(new Image(earthPath));
        } catch (Exception e) {
            System.err.println("Error loading Earth texture: " + e.getMessage());
            earthMaterial.setDiffuseColor(Color.BLUE);
        }

        sphere.setRotationAxis(Rotate.Y_AXIS);
        sphere.setMaterial(earthMaterial);
        return sphere;
    }

    private Circle prepareOrbitCircle(Satellite satellite) {
        // Scale altitude: e.g., 400 km real -> 50 units visual
        double realAltitude = satellite.getAltitude(); // in meters
        double heightAboveSurface = (realAltitude / 400_000) * 50; // Proportional scaling
        double orbitRadius = sphere.getRadius() + heightAboveSurface;

        // Create a 2D circle
        Circle circle = new Circle(orbitRadius);
        circle.setFill(null); // Transparent fill
        circle.setStroke(Color.RED); // Red outline
        circle.setStrokeWidth(2.0); // Thickness of the line

        // Rotate 90 degrees around X-axis to align with equator
        circle.getTransforms().add(new Rotate(90, Rotate.X_AXIS));

        // Center it with the sphere
        circle.setTranslateX(0);
        circle.setTranslateY(0);
        circle.setTranslateZ(0);

        return circle;
    }

    private ImageView prepareImageView() {
        ImageView imageView = new ImageView();
        try {
            // Adjusted path for resource loading (relative to classpath)
            String galaxyPath = "file:C:/Computer Science Major/ProjectOrion/ProjectOrion/src/Images/galaxy.jpg";
            Image image = new Image(galaxyPath);
            imageView.setImage(image);
            imageView.setPreserveRatio(true);
            imageView.getTransforms().add(new Translate(-image.getWidth() / 2, -image.getHeight() / 2, 800));
        } catch (Exception e) {
            System.err.println("Error loading galaxy image: " + e.getMessage());
        }
        return imageView;
    }

    public Sphere getSphere() {
        return sphere;
    }

    public Circle getOrbitCircle() {
        return orbitCircle;
    }

    public ImageView getImageView() {
        return imageView;
    }
}
