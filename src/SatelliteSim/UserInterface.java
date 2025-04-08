/* Project name: CMSC495
 * File name: UserInterface.java
 * Authors: Timothy Eckart, Tyler Blumenshine, Ricardo Gordon, Mitch Mclaughlin, Siddharth Patel
 * Date: 8 Apr 2025
 * Purpose: Builds the JavaFX GUI for input, output display, and control of the simulation and satellite data.
 */


package SatelliteSim;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class UserInterface {

    private static final double WIDTH = 1400;
    private static final double HEIGHT = 1000;
    private Satellite satellite;
    private ObservableList<VBox> satelliteDataList = FXCollections.observableArrayList();

    // For Testing purposes variables
    String testIntString = "10";
    String testString = "string";

//GUI UI to take user inputs
    public void build(Stage stage) {
        // Labels
        Label idLabel = new Label("Satellite Id");
        Label massLabel = new Label("Mass");
        Label areaLabel = new Label("Area");
        Label speedLabel = new Label("Speed (m/s)");
        Label altitudeLabel = new Label("Altitude");

        // Text fields
        TextField idTextField = new TextField(testString);
        idTextField.setPromptText("Satellite Id");

        TextField massTextField = new TextField(testIntString);
        massTextField.setPromptText("mass in Kg");

        TextField areaTextField = new TextField(testIntString);
        areaTextField.setPromptText("area in square Meters");

        // Phase II
        // TextField speedField = new TextField();
        //speedField.setPromptText("Speed (m/s)");

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

        //Phase II
        // HBox speedBox = new HBox(10);
        //speedBox.setAlignment(Pos.CENTER);
        // speedBox.getChildren().addAll(speedLabel, speedField);

        HBox altitudeBox = new HBox(10);
        altitudeBox.setAlignment(Pos.CENTER);
        altitudeBox.getChildren().addAll(altitudeLabel, altitudeTextField);

        VBox inputBox = new VBox(15, idBox, massBox, areaBox, altitudeBox);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setStyle("-fx-padding: 20; -fx-alignment: center;");


        // Buttons
        Button calcButton = new Button("Calculate");
        calcButton.setOnAction(e -> {
                    try {

                        showAnimation(idTextField, massTextField, areaTextField, altitudeTextField, stage);
                    } catch (DatabaseError dbError) {
                        // Handle the custom DatabaseError here
                        System.err.println("Database error occurred: " + dbError.getMessage());
                        // Optionally show an alert to the user
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Database Error");
                        alert.setHeaderText("An error occurred while accessing the database.");
                        alert.setContentText(dbError.getMessage());
                        alert.showAndWait();
                    } catch (Exception ex) {
                        // Catch other unexpected exceptions
                        System.err.println("An unexpected error occurred: " + ex.getMessage());
                    }

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

// Visual method to pass inputs into class calculation fuctions and generates Sim & UI Tab.
    private void showAnimation(TextField idField, TextField massField, TextField areaField, TextField altitudeField, Stage stage) throws DatabaseError, ValidationError {

        // addNewSatellite(idField, massField, areaField, altitudeField);

        TabPane layout = new TabPane();
        Tab simTab = new Tab("Simulation");
        //Simulation Tab_________________________________________________________________
        //Create Satellite from user inputs
        String id = idField.getText();
        double mass = Double.parseDouble(massField.getText());
        double area = Double.parseDouble(areaField.getText());
        double altitude = Double.parseDouble(altitudeField.getText());
        //int speed = Integer.parseInt(speedField.getText());
        //Satellite satellite = new Satellite(id, mass, area, altitude, speed);
        // instantiate a database manager
        SatelliteDataBaseManager dbManager = new SatelliteDataBaseManager();

        Satellite satellite = new Satellite(1,id, mass, area, altitude);
        dbManager.addSatellite(satellite);
        this.satellite = satellite;

        // Step 3: Perform calculations
        double period = OrbitalPeriod.calculatePeriod(satellite);
        double ballistic = Ballistic.calculateBallisticCoefficient(satellite);
        double totalOrbits = new NumOrbitsLifecycle(satellite).calculateNumberOfOrbits();
        String reentryFormattedTime = new Retrograde(satellite).getReentryTimeframe();


        Simulation simulation = new Simulation(satellite);
        SubScene simScene = simulation.getSubScene();
        simScene.setFill(Color.BLACK);

        VBox simLayout = new VBox(simScene);
        simTab.setContent(simLayout);

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
        showSatelliteData(satellite, period, ballistic, totalOrbits, reentryFormattedTime);

}

//Phase II
/*
    public void addNewSatellite(TextField idField, TextField massField, TextField areaField, TextField altitudeField) {
        String id = idField.getText();
        int mass = Integer.parseInt(massField.getText());
        int area = Integer.parseInt(areaField.getText());
        int altitude = Integer.parseInt(altitudeField.getText());

        VBox satelliteBox = new VBox(new Label("ID: " + id), new Label("Mass: " + mass + " kg"), new Label("Area: " + area + " m²"), new Label("Altitude: " + altitude + " m"));
        satelliteBox.setAlignment(Pos.CENTER_LEFT);
        satelliteBox.setStyle("-fx-padding: 10; -fx-border-color: gray; -fx-border-width: 1;");

        satelliteDataList.add(satelliteBox);


    }
*/
    // Phase II
    /*
    public static Satellite createNewSatellite(String id, int mass, int area, int altitude) {
        //return new Satellite(id, mass, area, altitude, speed);
        return new Satellite(id, mass, area, altitude);
    }
    */
    //Phase II
    /*
    public static void showSatelliteAddMenu(){

    }
*/
    //Method to display user input results on UI Tab
    public void showSatelliteData(Satellite satellite,
                                         double orbitalPeriod,
                                         double ballistic,
                                         double totalOrbits,
                                         String reentryFormattedTime) {

        String idString = "ID: " + satellite.getId();
        String massString = "Mass: " + satellite.getMass();
        String areaString = "Area: " + satellite.getArea();
        String altitudeString = "Altitude: " + satellite.getAltitude();
        String periodString = "Orbital Period: " + String.format("%.2f", orbitalPeriod) + " minutes";
        String ballisticString = "Ballistic Coefficient: " + String.format("%.2f", ballistic);
        String orbitsString = "Total Orbits Before Reentry: " + String.format("%.0f", totalOrbits);
        String reentryString = "Estimated Reentry Time: " + reentryFormattedTime;

        Label idLabel = new Label(idString);
        Label massLabel = new Label(massString);
        Label areaLabel = new Label(areaString);
        Label altitudeLabel = new Label(altitudeString);
        Label periodLabel = new Label(periodString);
        Label ballisticLabel = new Label(ballisticString);
        Label orbitsLabel = new Label(orbitsString);
        Label reentryLabel = new Label(reentryString);

        VBox satelliteBox = new VBox(
                idLabel, massLabel, areaLabel, altitudeLabel,
                periodLabel, ballisticLabel, orbitsLabel, reentryLabel
        );

        satelliteBox.setAlignment(Pos.CENTER_LEFT);
        satelliteBox.setSpacing(5);
        satelliteBox.setStyle("-fx-padding: 10; -fx-border-color: gray; -fx-border-width: 1;");

        // Add to the UI container
        satelliteDataList.add(satelliteBox);

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
        addSatellite.setOnAction(e -> {
                    try {

                        showAnimation(idTextField, massTextField, areaTextField, altitudeTextField, (Stage) addSatellite.getScene().getWindow());
                    } catch (DatabaseError dbError) {
                        // Handle the custom DatabaseError here
                        System.err.println("Database error occurred: " + dbError.getMessage());
                        // Optionally show an alert to the user
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Database Error");
                        alert.setHeaderText("An error occurred while accessing the database.");
                        alert.setContentText(dbError.getMessage());
                        alert.showAndWait();
                    } catch (Exception ex) {
                        // Catch other unexpected exceptions
                        System.err.println("An unexpected error occurred: " + ex.getMessage());
                    }

        });

        //  addNewSatellite(idTextField, massTextField, areaTextField, altitudeTextField);

        VBox inputBox = new VBox(20, idBox, massBox, areaBox, altitudeBox, addSatellite);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setStyle("-fx-padding: 20; -fx-alignment: center;");



        return inputBox;
    }

}