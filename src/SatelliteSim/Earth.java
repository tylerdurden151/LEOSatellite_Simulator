/* Project name: CMSC495
 * File name: Earth.java
 * Authors: Timothy Eckart, Tyler Blumenshine, Ricardo Gordon, Mitch Mclaughlin, Siddharth Patel
 * Date: 5 May 2025
 * Purpose: Create Earth simulation.
 */

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
    //private final Circle orbitCircle;
    // Constructor takes a Satellite object to get altitude

    private static Sphere prepareEarth() {
        Sphere sphere = new Sphere(200);
        PhongMaterial earthMaterial = new PhongMaterial();
        try {
            String earthPath = "/resources/earth/earth-d.jpg";
            earthMaterial.setDiffuseMap(new Image(earthPath));
        } catch (Exception e) {
            System.err.println("Error loading Earth texture: " + e.getMessage());
            earthMaterial.setDiffuseColor(Color.BLUE); // Fallback color
        }

        sphere.setRotationAxis(Rotate.Y_AXIS);
        sphere.setMaterial(earthMaterial);
        return sphere;
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
