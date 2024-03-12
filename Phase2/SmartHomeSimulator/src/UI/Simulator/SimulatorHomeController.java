/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.Simulator;
import Backend.Model.DateTime;


import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

import Backend.HouseLayout.House;
import Backend.HouseLayout.IndoorRoom;
import Backend.Users.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import Backend.Observer.*;
import Backend.SimulatorMenu.SimulatorHome;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javafx.fxml.FXML;

import java.text.SimpleDateFormat;

/**
 * FXML Controller class
 *
 * @author fan04
 */
public class SimulatorHomeController implements Initializable {

    /**
     * Initializes the controller class.
     */

    @FXML
    Pane r1, r2, r3, r4, r5, r6 ,r7 ,r8 ,r9, r10, r11, r12;

    @FXML
    ListView<String> userList;
    @FXML
    private AnchorPane simulatorHome;

    ObservableList<String> userLabels = FXCollections.observableArrayList();
    @FXML
    Button editSimulationBTN;

    //Pane bedroom1, bedroom2, bedroom3, bathroom1, bathroom2, livingroom1, kitchen1, diningroom1, basement1, frontporch1, backporch1, garage1;

    Stage stage;
    @FXML
    private Label dateTimeLabel;
    @FXML
    private Slider speedSlider;

    @FXML
    private Slider slider;
    @FXML
    private Label currentMultiplier;
    @FXML
    private ToggleButton startStopToggle;
    @FXML
    private Label chosenDate;
    @FXML
    private Label chosenTime;

    @FXML
    private Label userLabel, tempLabel, roomLabel;

    private DateTime dateTime; // Instance of DateTime model for managing time
    private SimulatorHome menu;
    private Timer timer = new Timer(); // Timer for scheduling time updates

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menu = SimulatorHome.getInstance();
        dateTime = DateTime.getInstance();

        SimulatorHomeObserver menuObserver = new SimulatorHomeObserver(menu, chosenTime, chosenDate, userLabel, tempLabel, roomLabel);
        menu.attachObserver(menuObserver);
        menu.notifyObservers(menu);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        try {
            Date date = dateFormatter.parse(menu.getDate());
            //dateTime.setTime(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        // Assuming dateTime object is correctly initialized
        dateTime.dateTimeProperty().addListener((observable, oldValue, newValue) -> {
            // This method will be called every second
            Platform.runLater(() -> {
                updateDateTimeDisplay(); // Ensure this method updates UI based on dateTime.getDate()
            });
        });
        dateTime.startTime(); // Start the clock
        setupMultiplierSliderListener();

        // Read rooms from House object
        // REMINDER: when hiding shape also hide text.

        System.out.println(userLabel.getText());

        IndoorRoom r = House.getIndoorRooms().get(0);
        RoomObserver ui = new RoomObserver(r1, r);
        r.attachObserver(ui);


        for(User u : House.getUsers()){
            userLabels.add(u.getName());
        }

        userList.setItems(userLabels);
    }

    @FXML
    public void handleBedroomClick() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/UI/SmartHomeModules/SHModules.fxml"));

            stage = new Stage();
            Scene scene = new Scene(root);
            stage.setResizable(false);

            stage.setScene(scene);
            stage.setTitle("Bedroom");
            stage.show();

        } catch (Exception e) {
            System.out.println("oops");
        }
    }

    @FXML
    public void handleEditClick(ActionEvent event) {
        try {
            Parent usr = FXMLLoader.load(getClass().getResource("/UI/Simulator/User.fxml"));
            Scene scene = new Scene(usr);
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("User Profiles");
            stage.show();

        } catch (Exception e) {
            System.out.println("error edit user button");
            System.out.println(e);
        }
    }
    private void updateDateTimeDisplay() {
        // This method updates the displayed date and time based on the current dateTime
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        chosenTime.setText(timeFormat.format(dateTime.getDate().getTime()));
        chosenDate.setText(dateFormat.format(dateTime.getDate().getTime()));
    }

    private void setupMultiplierSliderListener() {
        // Listener for the slider to adjust the time speed
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double multiplier = newValue.doubleValue();
            dateTime.setClockSpeedMultiplier(multiplier);
            currentMultiplier.setText(String.format("x %.2f", multiplier));
        });
    }

    public void toggleSimulation() {
        if (startStopToggle.isSelected()) {
            startStopToggle.setText("Stop Simulation");
            dateTime.startTime();
            // Run simulation
        } else {
            startStopToggle.setText("Start Simulation");
            dateTime.stopTime();
            // Pause simulation
        }
    }

    public void editSimulation() {
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        menu.setTime(timeFormatter.format(dateTime.getDate().getTime()));
        menu.setDate(dateFormatter.format(dateTime.getDate().getTime()));

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/UI/EditSimulation/EditSimulation.fxml"));

            stage = new Stage();
            Scene scene = new Scene(root);
            stage.setResizable(false);

            stage.setScene(scene);
            stage.setTitle("Edit");
            stage.show();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

//    @FXML
//    private void startStopSimulation() {
//        // Toggle the simulation status and update the UI and model accordingly
//        boolean running = dateTime.isRunning();
//        if (running) {
//            dateTime.stopTime();
//            timer.cancel();
//        } else {
//            dateTime.startTime();
//            startAnimatedTime();
//        }
//        startStopToggle.setText(running ? "Start" : "Stop");
//    }//


}
