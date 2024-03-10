/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.SmartHomeModules;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

import Backend.HouseLayout.House;
import Backend.HouseLayout.IndoorRoom;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;

/**
 * FXML Controller class
 *
 * @author fan04
 */
public class SHModulesController implements Initializable {

    private IndoorRoom indoorRoom;
    private IndoorRoom indoorRoom2;

    @FXML
    ListView<String> interactables;
    ObservableList<String> items = FXCollections.observableArrayList("a", "b", "c");

    @FXML
    ToggleButton addParentBTN, addChildBTN, addGuestBTN, blockWindowBTN;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        indoorRoom = House.getIndoorRooms().get(1);
        indoorRoom2 = House.getIndoorRooms().get(0);
        interactables.setItems(items);
    }

    public void addParent() {
        if (addParentBTN.isSelected()) {
            addParentBTN.setText("Remove Parent from Room");
            indoorRoom.setPersonInRoom(true, "Parent");
        } else {
            addParentBTN.setText("Add Parent to Room");
            indoorRoom.removePersonInRoom("Parent");
        }
    }

    public void addChild() {
        if (addChildBTN.isSelected()) {
            addChildBTN.setText("Remove Child from Room");
            indoorRoom.setPersonInRoom(true, "Child");
        } else {
            addChildBTN.setText("Add Child to Room");
            indoorRoom.removePersonInRoom("Child");
        }
    }

    public void addGuest() {
        if (addGuestBTN.isSelected()) {
            addGuestBTN.setText("Remove Guest from Room");
            indoorRoom.setPersonInRoom(true, "Guest");
        } else {
            addGuestBTN.setText("Add Guest to Room");
            indoorRoom.removePersonInRoom("Guest");
        }
    }

    public void blockWindow() {
        if (blockWindowBTN.isSelected()) {
            blockWindowBTN.setText("Unblock Window");
            indoorRoom.setWindowBlocked(false);
        } else {
            blockWindowBTN.setText("Block Window");
            indoorRoom.setWindowBlocked(true);
        }
    }
}
