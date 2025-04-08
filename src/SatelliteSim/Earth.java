/* Project name: CMSC495
 * File name: Earth.java
 * Authors: Timothy Eckart, Tyler Blumenshine, Ricardo Gordon, Mitch Mclaughlin, Siddharth Patel
 * Date: 7 Apr 2025
 * sampled source: https://github.com/afsalashyana/JavaFX-3D
 * Purpose: Constructs the 3D visual representation of Earth and background for the simulation.
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


    //Makes sphere from image resources
    private static Sphere prepareEarth() {
        Sphere sphere = new Sphere(150);
        PhongMaterial earthMaterial = new PhongMaterial();
        //Modified our original source to handle error coding
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

//Method makes Galaxy background
    private static ImageView prepareImageView() {
        ImageView imageView = new ImageView();
        //Modified our original source to handle error coding
        try {
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

    //getter for earth
    public static Sphere getSphere() {
        return sphere;
    }

    //getter for Galaxy
    public static ImageView getImageView() {
        return imageView;
    }
}
