package application;

import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class Simulation {
    private static final float WIDTH = 1400;
    private static final float HEIGHT = 1000;
    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);
    private Earth earth; // Instance of Earth instead of static access
    private Satellite satellite; // To pass to Earth

    public void start(Stage primaryStage) {
        // Create a sample Satellite (replace with user input if desired)
        satellite = new Satellite(500, 400_000, 0, 4); // 500 kg, 400 km, circular, 4 m^2
        earth = new Earth(satellite); // Initialize Earth with Satellite

        Camera camera = new PerspectiveCamera(true);
        camera.setNearClip(1);
        camera.setFarClip(10000);
        camera.translateZProperty().set(-1000);

        Group world = new Group();
        world.getChildren().add(earth.getSphere()); // Add Earth sphere
        world.getChildren().add(earth.getOrbitCircle()); // Add orbit circle

        Group root = new Group();
        root.getChildren().add(world);
        root.getChildren().add(earth.getImageView()); // Use Earth's ImageView

        Scene scene = new Scene(root, WIDTH, HEIGHT, true);
        scene.setFill(Color.SILVER);
        scene.setCamera(camera);

        initMouseControl(world, scene, primaryStage);

        primaryStage.setTitle("SatelliteSimulator");
        primaryStage.setScene(scene);
        primaryStage.show();

        prepareAnimation();
    }

    private void initMouseControl(Group world, Scene scene, Stage primaryStage) {
        Rotate xRotate;
        Rotate yRotate;
        world.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS)
        );
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        scene.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        scene.setOnMouseDragged(event -> {
            angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
            angleY.set(anchorAngleY + anchorX - event.getSceneX());
        });
    }

    private void prepareAnimation() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                earth.getSphere().rotateProperty().set(earth.getSphere().getRotate() + 0.2);
                // Orbit circle remains fixed (no rotation)
            }
        };
        timer.start();
    }
}
