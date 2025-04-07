package application;
	
//last edit done by MITCH 9PM EST 4/6/25

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public void start(Stage primaryStage) {
        UserInterface ui = new UserInterface();
        ui.build(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
