/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.Simulator;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

import Backend.HouseLayout.House;
import Backend.HouseLayout.IndoorRoom;
import Backend.HouseLayout.RoomObserver;
import Backend.Users.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;



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




    //Pane bedroom1, bedroom2, bedroom3, bathroom1, bathroom2, livingroom1, kitchen1, diningroom1, basement1, frontporch1, backporch1, garage1;

    Stage stage;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Read rooms from House object
        // REMINDER: when hiding shape also hide text.
        IndoorRoom r = House.getIndoorRooms().get(0);
        RoomObserver o = new RoomObserver(r1, r);
        r.attachObserver(o);
        r.notifyObservers(r);

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
}
