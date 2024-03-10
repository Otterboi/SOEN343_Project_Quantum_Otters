/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.Simulator;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

import Backend.HouseLayout.House;
import Backend.HouseLayout.IndoorRoom;
import Backend.Observer.RoomObserver;
import Backend.Observer.SimulatorHomeObserver;
import Backend.SimulatorMenu.SimulatorHome;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.fxml.FXML;

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
    Rectangle bedroom1, bedroom2, bedroom3, bathroom1, bathroom2, livingroom1, kitchen1, diningroom1, basement1, frontporch1, backporch1, garage1;
    @FXML
    Label bed1Text, bed2Text, bed3Text, bath1Text, bath2Text, livingRoomText, kitchenText, diningText, basementText, frontPorchText, backPorchText, garageText;

    @FXML
    Pane r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12;
    @FXML
    ToggleButton toggleSimulationBTN;
    @FXML
    Button editSimulationBTN;
    @FXML
    Label roomLabel, timeLabel, dateLabel, tempLabel, userLabel;

    //Pane bedroom1, bedroom2, bedroom3, bathroom1, bathroom2, livingroom1, kitchen1, diningroom1, basement1, frontporch1, backporch1, garage1;

    Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SimulatorHome menu = SimulatorHome.getInstance();
        SimulatorHomeObserver menuObserver = new SimulatorHomeObserver(menu, timeLabel, dateLabel, userLabel, tempLabel, roomLabel);
        menu.attachObserver(menuObserver);

        // Read rooms from House object
        // REMINDER: when hiding shape also hide text.


        IndoorRoom r3 = House.getIndoorRooms().get(0);

        IndoorRoom r = House.getIndoorRooms().get(1);
        RoomObserver o1 = new RoomObserver(r1, r3);
        RoomObserver o = new RoomObserver(r2, r);
        r.attachObserver(o);
        r3.attachObserver(o1);
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

    public void toggleSimulation() {
        if (toggleSimulationBTN.isSelected()) {
            toggleSimulationBTN.setText("ON");
            // Run simulation
        } else {
            toggleSimulationBTN.setText("OFF");
            // Pause simulation
        }
    }

    public void editSimulation() {
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
}
