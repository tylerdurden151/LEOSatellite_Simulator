package application;

package application;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;

public class UserInterface {

    private static final double WIDTH = 1400;
    private static final double HEIGHT = 1000;
    private Satellite satellite;
    private ObservableList<VBox> satelliteDataList = FXCollections.observableArrayList();
    private enum VType {INT, DOUBLE}
    private Stage primaryStage;
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

        // Main Layout
        VBox mainLayout;
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
        validateText(massTextField, VType.DOUBLE);
        massTextField.setDisable(true);
        massTextField.setPrefWidth(200);

        TextField areaTextField = new TextField();
        areaTextField.setPromptText("area in square Meters");
        validateText(areaTextField, VType.DOUBLE);
        areaTextField.setDisable(true);
        areaTextField.setPrefWidth(200);

        TextField altitudeTextField = new TextField();
        altitudeTextField.setPromptText("altitude in meters");
        validateText(altitudeTextField, VType.DOUBLE);
        altitudeTextField.setDisable(true);
        altitudeTextField.setPrefWidth(200);

        // Submit Button (initially invisible)
        Button submitButton = new Button("Submit");
        submitButton.setVisible(false);
        //Delete Button
        Button deleteButton = new Button("Delete");
        deleteButton.setVisible(false);

        //submit action
        submitButton.setOnAction(e -> {
            try {
                // Call showAnimation with the provided input fields and stage
                showAnimation(idTextField, massTextField, areaTextField, altitudeTextField, stage);
                satelliteDropdown.setValue(satellite.getId());
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
                    if (SatelliteDataBaseManager.checkExistingSatellite(selectedSatellite)) {
                        satellite = SatelliteDataBaseManager.getSatelliteDataByName(selectedSatellite); // Fetch satellite data

                        idTextField.setText(satellite.getId());
                        massTextField.setText(String.valueOf(satellite.getMass()));
                        areaTextField.setText(String.valueOf(satellite.getArea()));
                        altitudeTextField.setText(String.valueOf(satellite.getAltitude()));
                        idTextField.setDisable(false);
                        massTextField.setDisable(false);
                        areaTextField.setDisable(false);
                        altitudeTextField.setDisable(false);
                        submitButton.setVisible(true);
                        deleteButton.setVisible(true);
                    } else {
                        resetNewSatellite(idTextField, massTextField, areaTextField, altitudeTextField, submitButton, deleteButton);
                        satelliteDropdown.setValue("New Satellite");
                    }
                    // Enable fields and show Submit button

                } catch (SQLException | DatabaseError ex) {
                    System.err.println("Error loading satellite data: " + ex.getMessage());
                }
            }
        });

        // "New Satellite" button logic
        Button newSatelliteButton = new Button("New Satellite");
        newSatelliteButton.setOnAction(e -> {
            resetNewSatellite(idTextField, massTextField, areaTextField, altitudeTextField, submitButton, deleteButton);
            satelliteDropdown.setValue("New Satellite");
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
        inputGrid.add(deleteButton, 0, 4);     // Column 0, Row 4

        // Set consistent sizes for text fields
        idTextField.setPrefWidth(200);
        massTextField.setPrefWidth(200);
        areaTextField.setPrefWidth(200);
        altitudeTextField.setPrefWidth(200);

        //set main lay out
        mainLayout = new VBox(20, cboBox, inputGrid);
        mainLayout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: transparent;");

        // Create background ImageView
        ImageView backgroundImageView = prepareBackgroundImageView();

        // Use StackPane to layer background image behind mainLayout
        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundImageView, mainLayout);

        //delete button Action
        deleteButton.setOnAction(event -> {
            // Create a confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to delete this satellite?",
                    ButtonType.YES, ButtonType.CANCEL);

            // Show the dialog and wait for a response
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText(null); // Optional: Remove header for simplicity
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    // Call the delete method
                    try {
                        if (SatelliteDataBaseManager.checkExistingSatellite(satellite.getId())) {
                            SatelliteDataBaseManager.deleteSatellite(satellite);
                        } else {
                            resetNewSatellite(idTextField, massTextField, areaTextField, altitudeTextField, submitButton, deleteButton);
                            satelliteDropdown.setValue("New Satellite");
                        }
                    } catch (DatabaseError e) {
                        throw new RuntimeException(e);
                    }

                    // Refresh the GUI
                    mainLayout.getChildren().clear();
                    primaryStage = stage;
                    build(primaryStage);
                }
            });
        });
        // Scene setup
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.setFill(Color.BLACK); // Fallback background color
        stage.setScene(scene);
        stage.setTitle("Satellite Manager");
        stage.show();
    }

    private ImageView prepareBackgroundImageView() {
        ImageView imageView = new ImageView();
        try {
            // Use file: protocol for absolute paths
            String appBackground = "file:C:/Computer Science Major/ProjectOrionV2/src/Images/appBackgroundCustom.png";
            Image image = new Image(appBackground);
            imageView.setImage(image);
            imageView.setPreserveRatio(true);
            // Fit the image to the scene dimensions
            imageView.setFitWidth(WIDTH);
            imageView.setFitHeight(HEIGHT);
        } catch (Exception e) {
            System.err.println("Error loading background image: " + e.getMessage());
            System.err.println("Please ensure the image is located at 'C:/Computer Science Major/ProjectOrionV2/src/Images/appBackgroundCustom.png'.");
        }
        return imageView;
    }

    private void resetNewSatellite(TextField idField, TextField massField, TextField areaField, TextField altitudeField, Button submitButton, Button deleteButton) {
        satellite = new Satellite(SessionData.getUserID(), "NewSatellite", 10, 10, 10);
        // Reset fields to default values
        idField.setText("NewSatellite");
        massField.setText("10");
        areaField.setText("10");
        altitudeField.setText("10");

        // Enable fields and show Submit button
        idField.setDisable(false);
        massField.setDisable(false);
        areaField.setDisable(false);
        altitudeField.setDisable(false);
        submitButton.setVisible(true);
        deleteButton.setVisible(true);
    }

    private void showAnimation(TextField idField, TextField massField, TextField areaField, TextField altitudeField, Stage stage) throws DatabaseError, ValidationError {
        // Validate satellite name length
        String id = idField.getText().trim();
        if (id.isEmpty() || id.length() < 3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Invalid Satellite Name");
            alert.setContentText("Name must be at least three characters long");
            // Apply red text styling to the content
            alert.getDialogPane().lookup(".content.label").setStyle("-fx-text-fill: red;");
            alert.showAndWait();
            return;
        }

        // Validate altitude
        double altitude;
        try {
            altitude = Double.parseDouble(altitudeField.getText());
        } catch (NumberFormatException e) {
            throw new ValidationError("Invalid altitude", "Altitude must be a valid number");
        }
        if (altitude > 2_000_000) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Invalid Altitude");
            alert.setContentText("low earth orbit is less than 2000km according to European Space Agency definitions, please consult programming team");
            // Apply red text styling to the content
            alert.getDialogPane().lookup(".content.label").setStyle("-fx-text-fill: red;");
            alert.showAndWait();
            return;
        }

        // Create Satellite from user inputs
        double mass = Double.parseDouble(massField.getText());
        double area = Double.parseDouble(areaField.getText());
        // instantiate a database manager
        SatelliteDataBaseManager dbManager = new SatelliteDataBaseManager();

        this.satellite = new Satellite(SessionData.getUserID(), id, mass, area, altitude);
        dbManager.addSatellite(satellite);

        // Step 3: Perform calculations
        double period = OrbitalPeriod.calculatePeriod(satellite);
        double ballistic = Ballistic.calculateBallisticCoefficient(satellite);
        double totalOrbits = new NumOrbitsLifecycle(satellite).calculateNumberOfOrbits();
        String reentryFormattedTime = new Retrograde(satellite).getReentryTimeframe();

        // Create TabPane for simulation and UI tabs
        TabPane layout = new TabPane();
        layout.setStyle("-fx-background-color: transparent;"); // Ensure TabPane is transparent
        Tab simTab = new Tab("Simulation");

        // Simulation Tab
        Simulation simulation = new Simulation(satellite);
        SubScene simScene = simulation.getSubScene();
        simScene.setFill(Color.BLACK);

        VBox simLayout = new VBox(simScene);
        simTab.setContent(simLayout);

        // UI Tab
        ListView<VBox> listView = new ListView<>(satelliteDataList);
        listView.setPrefSize(300, 400);

        StackPane addSatellite = createInput();
        ScrollPane scrollPane = new ScrollPane(listView);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        BorderPane uiPane = new BorderPane();
        uiPane.setStyle("-fx-background-color: transparent;"); // Ensure BorderPane is transparent
        uiPane.setLeft(scrollPane);
        uiPane.setCenter(addSatellite);

        Tab uiTab = new Tab("UI Tab", uiPane);

        // Add tabs to TabPane
        layout.getTabs().addAll(simTab, uiTab);

        // Create and set new scene
        Scene resultScene = new Scene(layout, WIDTH, HEIGHT);
        resultScene.setFill(Color.BLACK); // Fallback background color

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
            });
        } catch (DatabaseError e) {
            throw new DatabaseError("Failed to load satellites for the user", e.getMessage());
        }
    }

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
        satelliteBox.setStyle("-fx-padding: 10; -fx-border-color: gray; -fx-border-width: 1; -fx-background-color: transparent;");

        // Add to the UI container
        satelliteDataList.add(satelliteBox);
    }

    public StackPane createInput() {
        Label idLabel = new Label("Satellite Id");
        Label massLabel = new Label("Mass");
        Label areaLabel = new Label("Area");
        Label altitudeLabel = new Label("Altitude");
        //combo box
        satelliteDropdown.setPromptText("Select a Satellite");
        satelliteDropdown.setValue(satellite.getId());
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
        idTextField.setPrefWidth(200);

        TextField massTextField = new TextField();
        massTextField.setPromptText("mass in Kg");
        validateText(massTextField, VType.DOUBLE);
        massTextField.setPrefWidth(200);

        TextField areaTextField = new TextField();
        areaTextField.setPromptText("area in square Meters");
        validateText(areaTextField, VType.DOUBLE);
        areaTextField.setPrefWidth(200);

        TextField altitudeTextField = new TextField();
        altitudeTextField.setPromptText("altitude in meters");
        validateText(altitudeTextField, VType.DOUBLE);
        altitudeTextField.setPrefWidth(200);

        // Submit Button (initially invisible)
        Button submitButton = new Button("Submit");
        submitButton.setVisible(false);
        Button deleteButton = new Button("Delete");
        deleteButton.setVisible(false);

        if (satelliteDropdown.getValue() != null) {
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
            deleteButton.setVisible(true);
        } else {
            idTextField.setDisable(true);
            massTextField.setDisable(true);
            areaTextField.setDisable(true);
            altitudeTextField.setDisable(true);
            submitButton.setVisible(false);
            deleteButton.setVisible(false);
        }

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
                    if (SatelliteDataBaseManager.checkExistingSatellite(selectedSatellite)) {
                        satellite = SatelliteDataBaseManager.getSatelliteDataByName(selectedSatellite); // Fetch satellite data

                        idTextField.setText(satellite.getId());
                        massTextField.setText(String.valueOf(satellite.getMass()));
                        areaTextField.setText(String.valueOf(satellite.getArea()));
                        altitudeTextField.setText(String.valueOf(satellite.getAltitude()));
                        idTextField.setDisable(false);
                        massTextField.setDisable(false);
                        areaTextField.setDisable(false);
                        altitudeTextField.setDisable(false);
                        submitButton.setVisible(true);
                        deleteButton.setVisible(true);
                    } else {
                        resetNewSatellite(idTextField, massTextField, areaTextField, altitudeTextField, submitButton, deleteButton);
                        satelliteDropdown.setValue("New Satellite");
                    }
                    // Enable fields and show Submit button

                } catch (SQLException | DatabaseError ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // "New Satellite" button logic
        Button newSatelliteButton = new Button("New Satellite");
        newSatelliteButton.setOnAction(e -> {
            // Reset fields to default values
            resetNewSatellite(idTextField, massTextField, areaTextField, altitudeTextField, submitButton, deleteButton);
            satelliteDropdown.setValue("New Satellite");
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
        inputGrid.add(deleteButton, 0, 4);     // Column 0, Row 4
        // Set consistent sizes for text fields
        idTextField.setPrefWidth(200);
        massTextField.setPrefWidth(200);
        areaTextField.setPrefWidth(200);
        altitudeTextField.setPrefWidth(200);

        // Main Layout for UI components
        VBox inputBox = new VBox(20, cboBox, inputGrid);
        inputBox.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: transparent;");

        // Create background ImageView
        ImageView backgroundImageView = prepareBackgroundImageView();

        // Use StackPane to layer background image behind inputBox
        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundImageView, inputBox);

        //delete button Action
        deleteButton.setOnAction(event -> {
            // Create a confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to delete this satellite?",
                    ButtonType.YES, ButtonType.CANCEL);

            // Show the dialog and wait for a response
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText(null); // Optional: Remove header for simplicity
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    // Call the delete method
                    try {
                        if (SatelliteDataBaseManager.checkExistingSatellite(satellite.getId())) {
                            SatelliteDataBaseManager.deleteSatellite(satellite);
                        }
                        // else {
                        //     resetNewSatellite(idTextField, massTextField,areaTextField,altitudeTextField,submitButton,deleteButton);
                        // }
                    } catch (DatabaseError e) {
                        throw new RuntimeException(e);
                    }

                    resetNewSatellite(idTextField, massTextField, areaTextField, altitudeTextField, submitButton, deleteButton);
                    satelliteDropdown.setPromptText("Select a Satellite");
                    satelliteDropdown.setValue("New Satellite");
                    // Load ComboBox with satellite names
                    try {
                        loadUserSatellites(); // Populates satelliteList
                        //satelliteDropdown.setItems(satelliteList);
                    } catch (DatabaseError e) {
                        System.err.println("Error loading satellites: " + e.getMessage());
                    }
                }
            });
        });

        return root;
    }

    private static void validateText(TextField txtValidate, VType vType) {
        switch (vType) {
            case INT:
                txtValidate.textProperty().addListener((observable, oldValue, newValue) -> {
                    // validate data  must be int or double
                    if (!newValue.matches("\\d*")) {
                        txtValidate.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });
                break;
            case DOUBLE:
                txtValidate.textProperty().addListener((observable, oldValue, newValue) -> {
                    // validate data  must be int or double
                    if (!newValue.matches("\\d*(\\.\\d*)?")) {
                        txtValidate.setText(newValue.replaceAll("[^\\d.]", ""));
                    }
                });
                break;
        }
    }
}
