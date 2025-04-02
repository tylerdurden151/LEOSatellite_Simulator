/* Project name: CMSC495
 * File name: Main.java
 * Author: Timothy Eckart
 * source: afsalashyana
 * Date: 2 Apr 2025
 * Purpose:
 *
 */

//last edit done by Tim E 07:40 28 Mar 2025

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
