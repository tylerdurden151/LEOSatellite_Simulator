package SatelliteSim;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;


public class UserInterface {
    private static final String URL = "jdbc:postgresql://localhost:5432/SatelliteSimulator";
    private static final String USER = "satellite_user";
    private static final String PASSWORD = "123456789";
    private static final double WIDTH = 1400;
    private static final double HEIGHT = 1000;
    private Satellite satellite;
    private ObservableList<VBox> satelliteDataList = FXCollections.observableArrayList();
    private enum    VType {INT,DOUBLE};

    // For Testing purposes variables
    String testIntString = "10";
    String testString = "string";
    private ComboBox<String> satelliteDropdown = new ComboBox<>();


    public void build(Stage stage) {
        // Labels
        Label idLabel = new Label("Satellite Id");
        Label massLabel = new Label("Mass");
        Label areaLabel = new Label("Area");
        Label altitudeLabel = new Label("Altitude");


        satelliteDropdown.setPromptText("Select a Satellite");
        ObservableList<String> satelliteList = FXCollections.observableArrayList();

        // Load ComboBox with satellite names
        try {
            loadUserSatellites(); // Populates satelliteList
            //satelliteDropdown.setItems(satelliteList);
        } catch (DatabaseError e) {
            System.err.println("Error loading satellites: " + e.getMessage());
        }

        // Text fields (initially disabled and smaller size)
        TextField idTextField = new TextField();
        idTextField.setPromptText("Satellite Name");

        idTextField.setDisable(true);
        idTextField.setPrefWidth(200);

        TextField massTextField = new TextField();
        massTextField.setPromptText("mass in Kg");
        validateText( massTextField,VType.DOUBLE);
        massTextField.setDisable(true);
        massTextField.setPrefWidth(200);

        TextField areaTextField = new TextField();
        areaTextField.setPromptText("area in square Meters");
        validateText(areaTextField,VType.DOUBLE);
        areaTextField.setDisable(true);
        areaTextField.setPrefWidth(200);

        TextField altitudeTextField = new TextField();
        altitudeTextField.setPromptText("altitude in meters");
        validateText(altitudeTextField,VType.DOUBLE);
        altitudeTextField.setDisable(true);
        altitudeTextField.setPrefWidth(200);

        // Submit Button (initially invisible)
        Button submitButton = new Button("Submit");
        submitButton.setVisible(false);
        //submit action
        submitButton.setOnAction(e -> {
            try {

                // Call showAnimation with the provided input fields and stage
                showAnimation(idTextField, massTextField, areaTextField, altitudeTextField, stage);
            } catch (DatabaseError dbError) {
                // Handle DatabaseError exceptions
                System.err.println("Database error: " + dbError.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Database Error");
                alert.setHeaderText("An error occurred");
                alert.setContentText(dbError.getMessage());
                alert.showAndWait();
            } catch (ValidationError validationError) {
                // Handle ValidationError exceptions
                System.err.println("Validation error: " + validationError.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Validation Error");
                alert.setHeaderText("Input Validation Failed");
                alert.setContentText(validationError.getMessage());
                alert.showAndWait();
            } catch (Exception ex) {
                // Handle any unexpected exceptions
                System.err.println("An unexpected error occurred: " + ex.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Unexpected Error");
                alert.setHeaderText("An unexpected error occurred");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });

        // Satellite Dropdown selection logic
        satelliteDropdown.setOnAction(e -> {
            String selectedSatellite = satelliteDropdown.getValue();
            if (selectedSatellite != null) {
                try {
                    Satellite satellite = getSatelliteDataByName(selectedSatellite); // Fetch satellite data

                    idTextField.setText(satellite.getId());
                    massTextField.setText(String.valueOf(satellite.getMass()));
                    areaTextField.setText(String.valueOf(satellite.getArea()));
                    altitudeTextField.setText(String.valueOf(satellite.getAltitude()));

                    // Enable fields and show Submit button
                    idTextField.setDisable(false);
                    massTextField.setDisable(false);
                    areaTextField.setDisable(false);
                    altitudeTextField.setDisable(false);
                    submitButton.setVisible(true);
                } catch (SQLException ex) {
                    System.err.println("Error loading satellite data: " + ex.getMessage());
                }
            }
        });

        // "New Satellite" button logic
        Button newSatelliteButton = new Button("New Satellite");
        newSatelliteButton.setOnAction(e -> {
            // Reset fields to default values
            idTextField.setText("NewSatellite");
            massTextField.setText("10");
            areaTextField.setText("10");
            altitudeTextField.setText("10");

            // Enable fields and show Submit button
            idTextField.setDisable(false);
            massTextField.setDisable(false);
            areaTextField.setDisable(false);
            altitudeTextField.setDisable(false);
            submitButton.setVisible(true);
        });

        // Layouts
        // Layout for ComboBox and New Satellite button
        HBox cboBox = new HBox(10, satelliteDropdown, newSatelliteButton);
        cboBox.setAlignment(Pos.CENTER);

// GridPane for aligning input fields and labels
        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10); // Horizontal gap between columns
        inputGrid.setVgap(10); // Vertical gap between rows
        inputGrid.setStyle("-fx-padding: 20;");
        inputGrid.setAlignment(Pos.CENTER); // Center all components in the GridPane

// Add labels and fields to the GridPane
        inputGrid.add(idLabel, 0, 0);          // Column 0, Row 0
        inputGrid.add(idTextField, 1, 0);      // Column 1, Row 0
        inputGrid.add(massLabel, 0, 1);        // Column 0, Row 1
        inputGrid.add(massTextField, 1, 1);    // Column 1, Row 1
        inputGrid.add(areaLabel, 0, 2);        // Column 0, Row 2
        inputGrid.add(areaTextField, 1, 2);    // Column 1, Row 2
        inputGrid.add(altitudeLabel, 0, 3);    // Column 0, Row 3
        inputGrid.add(altitudeTextField, 1, 3);// Column 1, Row 3
        inputGrid.add(submitButton, 1, 4);     // Column 1, Row 4 (aligned under text fields)

// Set consistent sizes for text fields
        idTextField.setPrefWidth(200);
        massTextField.setPrefWidth(200);
        areaTextField.setPrefWidth(200);
        altitudeTextField.setPrefWidth(200);

// Main Layout
        VBox mainLayout = new VBox(20, cboBox, inputGrid);
        mainLayout.setStyle("-fx-padding: 20; -fx-alignment: center;");

// Scene setup
        Scene scene = new Scene(mainLayout, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.setTitle("Satellite Manager");
        stage.show();
    }

    //getSatelliteDataByName fetches satellite data based on the selected name:
    public Satellite getSatelliteDataByName(String satelliteName) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT * FROM \"Satellite\" WHERE \"SatelliteName\" = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, satelliteName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int satellite_ID = rs.getInt("Satellite_ID");
                int user_ID = rs.getInt("User_ID");
                String id = satelliteName; // Use the satellite name as the ID
                double mass = rs.getDouble("Mass");
                double area = rs.getDouble("Area");
                double altitude = rs.getDouble("Altitude");

                // Create and return a Satellite object
                Satellite satellite = new Satellite(user_ID, id, mass, area, altitude);
                satellite.setSatellite_ID(satellite_ID); // Set the satellite_ID directly
                return satellite;
            }
        }
        throw new SQLException("No satellite found with name: " + satelliteName);
    }

    /*
    private void handleCalculation(TextField idField, TextField massField, TextField areaField, TextField altitudeField, Stage stage) {
        try {
            String id = idField.getText();
            double mass = Double.parseDouble(massField.getText());
            double area = Double.parseDouble(areaField.getText());
            double altitude = Double.parseDouble(altitudeField.getText());
            //double speed = Double.parseDouble(speedField.getText());
            Satellite satellite = new Satellite(id, mass, area, altitude);
            this.satellite = satellite;
            //Simulation simulation = new Simulation(satellite);
            //simulation.start(stage);
            double period = OrbitalPeriod.calculatePeriod(satellite);
            double ballistic = Ballistic.calculateBallisticCoefficient(satellite);
            //double reentryTime = Retrograde.calculateTimeToReentry(satellite);

            // NEW: Reentry time using Retrograde instance
            Retrograde retro = new Retrograde(satellite);
            double reentrySeconds = retro.calculateTimeToReentry();
            String reentryTimeFormatted = retro.getReentryTimeframe();

            //double totalOrbits = NumOrbitsLifecycle.calculateNumberOfOrbits(satellite);
            NumOrbitsLifecycle orbitsCalc = new NumOrbitsLifecycle(satellite);
            double totalOrbits = orbitsCalc.calculateNumberOfOrbits();


            //SubScene simScene = simulation.getSubScene();
            // simScene.setFill(Color.BLACK);
            //VBox simLayout = new VBox(simScene);


        } catch (NumberFormatException _) {

        }
    }
*/

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

        Satellite satellite = new Satellite(SessionData.getUserID(),id, mass, area, altitude);
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
    private void loadUserSatellites() throws DatabaseError {
        try {
            // Get the array of satellite names and IDs from SatelliteDataBaseManager
            SatelliteDataBaseManager dbManager = new SatelliteDataBaseManager();
            String[] satelliteNames = dbManager.getSatelliteNamesAndIds();
            ObservableList<String> satellitesList = FXCollections.observableArrayList(satelliteNames);
            System.out.println("ObservableList: " + satellitesList);
            Platform.runLater(() -> {

                satelliteDropdown.setItems(satellitesList);
                //String[] testNames = {"Hubble", "Voyager", "ISS"};
               // satelliteDropdown.setItems(FXCollections.observableArrayList(testNames));


            });

            }
         catch (DatabaseError e) {
            throw new DatabaseError("Failed to load satellites for the user", e.getMessage());
        }
    }

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
         //combo box
        satelliteDropdown.setPromptText("Select a Satellite");
        ObservableList<String> satelliteList = FXCollections.observableArrayList();

        // Load ComboBox with satellite names
        try {
            loadUserSatellites(); // Populates satelliteList
            //satelliteDropdown.setItems(satelliteList);
        } catch (DatabaseError e) {
            System.err.println("Error loading satellites: " + e.getMessage());
        }
        // Text fields
        TextField idTextField = new TextField();
        idTextField.setPromptText("Satellite Name");

        idTextField.setDisable(true);
        idTextField.setPrefWidth(200);

        TextField massTextField = new TextField();
        massTextField.setPromptText("mass in Kg");
        validateText( massTextField,VType.DOUBLE);
        massTextField.setDisable(true);
        massTextField.setPrefWidth(200);

        TextField areaTextField = new TextField();
        areaTextField.setPromptText("area in square Meters");
        validateText(areaTextField,VType.DOUBLE);
        areaTextField.setDisable(true);
        areaTextField.setPrefWidth(200);

        TextField altitudeTextField = new TextField();
        altitudeTextField.setPromptText("altitude in meters");
        validateText(altitudeTextField,VType.DOUBLE);
        altitudeTextField.setDisable(true);
        altitudeTextField.setPrefWidth(200);

        //
        // Submit Button (initially invisible)
        Button submitButton = new Button("Submit");
        submitButton.setVisible(false);
        //submit action
        submitButton.setOnAction(e -> {
            try {

                // Call showAnimation with the provided input fields and stage
                showAnimation(idTextField, massTextField, areaTextField, altitudeTextField, (Stage) submitButton.getScene().getWindow());
            } catch (DatabaseError dbError) {
                // Handle DatabaseError exceptions
                System.err.println("Database error: " + dbError.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Database Error");
                alert.setHeaderText("An error occurred");
                alert.setContentText(dbError.getMessage());
                alert.showAndWait();
            } catch (ValidationError validationError) {
                // Handle ValidationError exceptions
                System.err.println("Validation error: " + validationError.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Validation Error");
                alert.setHeaderText("Input Validation Failed");
                alert.setContentText(validationError.getMessage());
                alert.showAndWait();
            } catch (Exception ex) {
                // Handle any unexpected exceptions
                System.err.println("An unexpected error occurred: " + ex.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Unexpected Error");
                alert.setHeaderText("An unexpected error occurred");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });

        // Satellite Dropdown selection logic
        satelliteDropdown.setOnAction(e -> {
            String selectedSatellite = satelliteDropdown.getValue();
            if (selectedSatellite != null) {
                try {
                    Satellite satellite = getSatelliteDataByName(selectedSatellite); // Fetch satellite data

                    idTextField.setText(satellite.getId());
                    massTextField.setText(String.valueOf(satellite.getMass()));
                    areaTextField.setText(String.valueOf(satellite.getArea()));
                    altitudeTextField.setText(String.valueOf(satellite.getAltitude()));

                    // Enable fields and show Submit button
                    idTextField.setDisable(false);
                    massTextField.setDisable(false);
                    areaTextField.setDisable(false);
                    altitudeTextField.setDisable(false);
                    submitButton.setVisible(true);
                } catch (SQLException ex) {
                    System.err.println("Error loading satellite data: " + ex.getMessage());
                }
            }
        });

        // "New Satellite" button logic
        Button newSatelliteButton = new Button("New Satellite");
        newSatelliteButton.setOnAction(e -> {
            // Reset fields to default values
            idTextField.setText("NewSatellite");
            massTextField.setText("10");
            areaTextField.setText("10");
            altitudeTextField.setText("10");

            // Enable fields and show Submit button
            idTextField.setDisable(false);
            massTextField.setDisable(false);
            areaTextField.setDisable(false);
            altitudeTextField.setDisable(false);
            submitButton.setVisible(true);
        });

        // Layout for ComboBox and New Satellite button
        HBox cboBox = new HBox(10, satelliteDropdown, newSatelliteButton);
        cboBox.setAlignment(Pos.CENTER);

// GridPane for aligning input fields and labels
        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10); // Horizontal gap between columns
        inputGrid.setVgap(10); // Vertical gap between rows
        inputGrid.setStyle("-fx-padding: 20;");
        inputGrid.setAlignment(Pos.CENTER); // Center all components in the GridPane

// Add labels and fields to the GridPane
        inputGrid.add(idLabel, 0, 0);          // Column 0, Row 0
        inputGrid.add(idTextField, 1, 0);      // Column 1, Row 0
        inputGrid.add(massLabel, 0, 1);        // Column 0, Row 1
        inputGrid.add(massTextField, 1, 1);    // Column 1, Row 1
        inputGrid.add(areaLabel, 0, 2);        // Column 0, Row 2
        inputGrid.add(areaTextField, 1, 2);    // Column 1, Row 2
        inputGrid.add(altitudeLabel, 0, 3);    // Column 0, Row 3
        inputGrid.add(altitudeTextField, 1, 3);// Column 1, Row 3
        inputGrid.add(submitButton, 1, 4);     // Column 1, Row 4 (aligned under text fields)

// Set consistent sizes for text fields
        idTextField.setPrefWidth(200);
        massTextField.setPrefWidth(200);
        areaTextField.setPrefWidth(200);
        altitudeTextField.setPrefWidth(200);

// Main Layout
        VBox inputBox =new VBox(20, cboBox, inputGrid);
        inputBox.setStyle("-fx-padding: 20; -fx-alignment: center;");









        return inputBox;
    }
    private static void validateText(TextField txtValidate, VType vType){

        switch (vType){
            case INT:
                txtValidate.textProperty().addListener((observable,oldValue, newValue) ->{
                    // validate data  must be int or double
                    if(!newValue.matches("\\d*")){
                        txtValidate.setText(newValue.replaceAll("[^\\d]",""));
                    }

                });
                break;
            case DOUBLE:
                txtValidate.textProperty().addListener((observable,oldValue, newValue) ->{
                    // validate data  must be int or double
                    if(!newValue.matches("\\d*(\\.\\d*)?")){
                        txtValidate.setText(newValue.replaceAll("[^\\d.]",""));
                    }

                });
                break;


        }

    }

/*
    private String formatString(String id,int mass,int area, int altitude){
        return String.format("ID: %s, Mass: %d kg, Area: %d m², Altitude: %d m", id, mass, area, altitude);
    }
*/
}