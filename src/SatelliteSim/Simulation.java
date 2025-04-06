package SatelliteSim;

import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;
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
    private final Sphere sphere = Earth.getSphere();
    private Satellite satellite;
    private Group world;

   public Simulation(Satellite satellite) {
       this.satellite = satellite;
   }
/*    public void start(Stage primaryStage) {
        Camera camera = new PerspectiveCamera(true);
        camera.setNearClip(1);
        camera.setFarClip(10000);
        camera.translateZProperty().set(-1000);

        Group world = new Group();
        world.getChildren().add(sphere);

       // earth = new Earth(satellite);
        world.getChildren().add(satellite.getBody());

        Group root = new Group();
        root.getChildren().add(world);
        root.getChildren().add(Earth.getImageView());

        Scene scene = new Scene(root, WIDTH, HEIGHT, true);
        scene.setFill(Color.SILVER);
        scene.setCamera(camera);

        initMouseControl(world, scene, primaryStage);

        primaryStage.setTitle("SatelliteSimulator");
        primaryStage.setScene(scene);
        primaryStage.show();

        prepareAnimation();
    }
   */
    public SubScene getSubScene() {
        world = new Group();
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-1000);
        camera.setNearClip(1);
        camera.setFarClip(10000.0);

        world.getChildren().add(sphere);

        // earth = new Earth(satellite);
        world.getChildren().add(Earth.getImageView());
        world.getChildren().add(satellite.getBody());

        SubScene subScene = new SubScene(world, 800, 600, true, SceneAntialiasing.BALANCED);
      //  initMouseControl(world, scene, subScene);

        subScene.setCamera(camera);
        prepareAnimation();

        return subScene;
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
                sphere.rotateProperty().set(sphere.getRotate() + 0.2);
                double x = satellite.getOrbitRadius() * Math.cos(Math.toRadians(satellite.getAngle()));
                double z = satellite.getOrbitRadius() * Math.sin(Math.toRadians(satellite.getAngle()));
                satellite.getBody().setTranslateX(x);
                satellite.getBody().setTranslateZ(z);

            }
        };
        timer.start();
    }
}
