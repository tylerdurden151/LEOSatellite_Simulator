package SatelliteSim;


import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class UserInterface {

    private static final float WIDTH = 1400;
    private static final float HEIGHT = 1000;


    public void build(Stage stage) {
        Button calcButton = new Button("Calculate");
        calcButton.setOnAction(e -> {
            Simulation simulation = new Simulation();
            simulation.start(stage);
        });

        StackPane layout = new StackPane(calcButton);
        Scene scene = new Scene(layout, WIDTH, HEIGHT);
        scene.setFill(Color.SILVER);

        stage.setTitle("User Interface");
        stage.setScene(scene);
        stage.show();
    }
}
