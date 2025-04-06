package SatelliteSim;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;


public class UserInterface {

    private static final double WIDTH = 1400;
    private static final double HEIGHT = 1000;
    private Satellite satellite;
    private ObservableList<VBox> satelliteDataList = FXCollections.observableArrayList();

    // For Testing purposes variables
    String testIntString = "10";
    String testString = "string";





    public void build(Stage stage) {
        // Labels
        Label idLabel = new Label("Satellite Id");
        Label massLabel = new Label("Mass");
        Label areaLabel = new Label("Area");
        Label altitudeLabel = new Label("Altitude");

        // Text fields
        TextField idTextField = new TextField(testString);
        idTextField.setPromptText("Satellite Id");

        TextField massTextField = new TextField(testIntString);
        massTextField.setPromptText("mass in Kg");

        TextField areaTextField = new TextField(testIntString);
        areaTextField.setPromptText("area in square Meters");

        TextField altitudeTextField = new TextField(testIntString);
        altitudeTextField.setPromptText("altitude in meters");

        // HBoxes
        HBox idBox = new HBox(10);
        idBox.setAlignment(Pos.CENTER);
        idBox.getChildren().addAll(idLabel, idTextField);

        HBox massBox = new HBox(10);
        massBox.setAlignment(Pos.CENTER);
        massBox.getChildren().addAll(massLabel, massTextField);

        HBox areaBox = new HBox(10);
        areaBox.setAlignment(Pos.CENTER);
        areaBox.getChildren().addAll(areaLabel, areaTextField);

        HBox altitudeBox = new HBox(10);
        altitudeBox.setAlignment(Pos.CENTER);
        altitudeBox.getChildren().addAll(altitudeLabel, altitudeTextField);

        VBox inputBox = new VBox(15, idBox, massBox, areaBox, altitudeBox);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Buttons
        Button calcButton = new Button("Calculate");
        calcButton.setOnAction(e -> {
            showAnimation(idTextField, massTextField, areaTextField, altitudeTextField, stage);
        });

        // Pane Setup
        VBox buttonBox = new VBox(10, calcButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setStyle("-fx-alignment: center;");

        BorderPane layout = new BorderPane();
        layout.setCenter(inputBox);
        layout.setBottom(buttonBox);

        Scene scene = new Scene(layout, WIDTH, HEIGHT);
        scene.setFill(Color.SILVER);

        stage.setTitle("User Interface");
        stage.setScene(scene);
        stage.show();

    }



    private void handleCalculation(TextField idField, TextField massField, TextField areaField, TextField altitudeField, Stage stage) {
        try {
            String id = idField.getText();
            int mass = Integer.parseInt(massField.getText());
            int area = Integer.parseInt(areaField.getText());
            int altitude = Integer.parseInt(altitudeField.getText());
            Satellite satellite = new Satellite(id, mass, area, altitude);
            Simulation simulation = new Simulation();
            simulation.start(stage);
        } catch (NumberFormatException _) {

        }
    }



    private void showAnimation(TextField idField, TextField massField, TextField areaField, TextField altitudeField, Stage stage) {

        addNewSatellite(idField, massField, areaField, altitudeField);

        TabPane layout = new TabPane();
        Tab simTab = new Tab("Simulation");

        //UI Tab__________________________________________________________________________
        ListView<VBox> listView = new ListView<>(satelliteDataList);
        listView.setPrefSize(300, 400);

        VBox addSatellite = createInput();
        ScrollPane scrollPane = new ScrollPane(listView);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);


        BorderPane uiPane = new BorderPane();
        uiPane.setLeft(scrollPane);
        uiPane.setCenter(addSatellite);

        Tab uiTab = new Tab("UI Tab", uiPane);
        // UI Tab_________________________________________________________________________________


        layout.getTabs().addAll(simTab, uiTab);

        Scene resultScene = new Scene(layout, WIDTH, HEIGHT);
        resultScene.setFill(Color.LIGHTGRAY);

        stage.setScene(resultScene);
    }





    public void addNewSatellite (TextField idField, TextField massField, TextField areaField, TextField altitudeField){
        String id = idField.getText();
        int mass = Integer.parseInt(massField.getText());
        int area = Integer.parseInt(areaField.getText());
        int altitude = Integer.parseInt(altitudeField.getText());

        VBox satelliteBox = new VBox(new Label("ID: " + id), new Label("Mass: " + mass + " kg"), new Label("Area: " + area + " m²"), new Label("Altitude: " + altitude + " m"));
        satelliteBox.setAlignment(Pos.CENTER_LEFT);
        satelliteBox.setStyle("-fx-padding: 10; -fx-border-color: gray; -fx-border-width: 1;");

        satelliteDataList.add(satelliteBox);


    }

    public static Satellite createNewSatellite (String id, int mass, int area, int altitude){
        return new Satellite (id, mass, area, altitude);
    }

    public static void showSatelliteAddMenu(){

    }

    public static VBox showSatelliteData (Satellite satellite){
        String idString = "ID: " + Satellite.getName();
        String massString = "Mass: " + Satellite.getMass();
        String areaString = "area; " + Satellite.getArea();
        String altitudeString = "altitude: " + Satellite.getAltitude();

        Label idLabel = new Label(idString);
        Label massLabel = new Label(massString);
        Label areaLabel = new Label(areaString);
        Label altitudeLabel = new Label(altitudeString);

        VBox dataVbox = new VBox(idLabel, massLabel, areaLabel, altitudeLabel);
        dataVbox.setAlignment(Pos.CENTER);
        dataVbox.setStyle("-fx-padding: 10; -fx-border-color: gray; -fx-border-width: 1;");


        return dataVbox;
    }

    public VBox createInput (){

        Label idLabel = new Label("Satellite Id");
        Label massLabel = new Label("Mass");
        Label areaLabel = new Label("Area");
        Label altitudeLabel = new Label("Altitude");

        // Text fields
        TextField idTextField = new TextField(testString);
        idTextField.setPromptText("Satellite Id");

        TextField massTextField = new TextField(testIntString);
        massTextField.setPromptText("mass in Kg");

        TextField areaTextField = new TextField(testIntString);
        areaTextField.setPromptText("area in square Meters");

        TextField altitudeTextField = new TextField(testIntString);
        altitudeTextField.setPromptText("altitude in meters");

        // HBoxes
        HBox idBox = new HBox(10);
        idBox.setAlignment(Pos.CENTER);
        idBox.getChildren().addAll(idLabel, idTextField);

        HBox massBox = new HBox(10);
        massBox.setAlignment(Pos.CENTER);
        massBox.getChildren().addAll(massLabel, massTextField);

        HBox areaBox = new HBox(10);
        areaBox.setAlignment(Pos.CENTER);
        areaBox.getChildren().addAll(areaLabel, areaTextField);

        HBox altitudeBox = new HBox(10);
        altitudeBox.setAlignment(Pos.CENTER);
        altitudeBox.getChildren().addAll(altitudeLabel, altitudeTextField);

        Button addSatellite = new Button("Add Satellite");
        addSatellite.setOnAction(e ->{
            addNewSatellite(idTextField, massTextField, areaTextField, altitudeTextField);
        });

        VBox inputBox = new VBox(20, idBox, massBox, areaBox, altitudeBox, addSatellite);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setStyle("-fx-padding: 20; -fx-alignment: center;");



        return inputBox;
    }

    private String formatString(String id,int mass,int area, int altitude){
        return String.format("ID: %s, Mass: %d kg, Area: %d m², Altitude: %d m", id, mass, area, altitude);
    }











}