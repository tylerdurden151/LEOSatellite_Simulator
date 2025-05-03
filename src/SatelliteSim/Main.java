/* Project name: CMSC495
 * File name: Main.java
 * Author: Timothy Eckart
 * source: afsalashyana
 * Date: 5 May 2025
 * Purpose:
 *
 */

//last edit done by Tim Eckart on 2 May

package SatelliteSim;
	

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // First UI (Login UI)
        // On successful login, switch to the dropdown UI

        SatelliteSimulatorLogin loginUI = new SatelliteSimulatorLogin();
        loginUI.start(primaryStage);

     loginUI.setOnLoginSuccess(() -> {
        UserInterface ui = new UserInterface();
        ui.build(primaryStage);
    });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
