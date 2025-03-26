/* Project name: CMSC335Project3
 * File name: Main.java
 * Author: Timothy Eckart
 * Date: 10 dec 2024
 * Purpose: Script is to allow the user to start a traffic simulator and
 * record time that it takes to travel from one intersection to the next.
 * Additionally, it will be able to add additional vehicles on the road.
 *
 */


package SatelliteSim;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * This is the Main class for the main method and prompts user
 * homepage page FXML
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SatelliteScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("SatelliteScene");
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}