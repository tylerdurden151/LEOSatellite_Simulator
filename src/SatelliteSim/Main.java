/* Project name: CMSC495
 * File name: Main.java
 * Author: Timothy Eckart
 * source: afsalashyana
 * Date: 5 Apr 2025
 * Purpose:
 *
 */

//last edit done by Tim E 13:30 6 Apr 2025

package SatelliteSim;
	

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        UserInterface ui = new UserInterface();
        ui.build(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
