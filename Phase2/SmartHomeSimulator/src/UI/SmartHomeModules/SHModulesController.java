/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.SmartHomeModules;

import java.net.URL;
import java.util.ResourceBundle;
import Backend.HouseLayout.House;
import Backend.HouseLayout.IndoorRoom;
import Backend.HouseLayout.Room;
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

    private Room room;


    @FXML
    ToggleButton addParentBTN, addChildBTN, addGuestBTN, blockWindowBTN, autoModeToggle,OpenCloseDoors, OpenCloseWindows, OpenCloseLights;

    public SHModulesController(Room r){
        room = r;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {


        for(String person : room.getPeopleInRoom()){
            if(person.equals("Parent")){
                addParentBTN.setText("Remove Parent from Room");
                addParentBTN.setSelected(true);
            }
            else if (person.equals("Child")){
                addChildBTN.setText("Remove Child from Room");
                addChildBTN.setSelected(true);
            } else if (person.equals("Guest")) {
                addGuestBTN.setText("Remove Guest from Room");
                addGuestBTN.setSelected(true);
            }
        }
        if(room instanceof IndoorRoom){
            if(((IndoorRoom) room).isWindowBlocked()){
                blockWindowBTN.setText("Unblock Window");
                blockWindowBTN.setSelected(true);
            }
        }else{
            blockWindowBTN.setDisable(true);
        }

        autoModeToggle.setOnAction(e-> {
            boolean isSelected = autoModeToggle.isSelected();
            room.setAutoModeEnabled(isSelected);
            if(isSelected){
                autoModeToggle.setText("Disable Auto Light Mode");
            }
            else {
                autoModeToggle.setText("Enable Auto Mode");
            }
        });
        OpenCloseDoors.setOnAction(e-> {
            boolean isSelected = OpenCloseDoors.isSelected();
            room.setAutoModeEnabled(isSelected);
            if(isSelected){
                OpenCloseDoors.setText("Door Opened");
            }
            else {
                OpenCloseDoors.setText("Door Closed");
            }
        });
        OpenCloseWindows.setOnAction(e-> {
            boolean isSelected = OpenCloseWindows.isSelected();
            room.setAutoModeEnabled(isSelected);
            if(isSelected){
                OpenCloseWindows.setText("Window Opened");
            }
            else {
                OpenCloseWindows.setText("Window Closed");
            }
        });
        OpenCloseLights.setOnAction(e-> {
            boolean isSelected = OpenCloseLights.isSelected();
            room.setAutoModeEnabled(isSelected);
            if(isSelected){
                OpenCloseLights.setText("Light ON");
            }
            else {
                OpenCloseLights.setText("Light OFF");
            }
        });


    }

    public void addParent() {
        if (addParentBTN.isSelected()) {
            addParentBTN.setText("Remove Parent from Room");
            room.setPersonInRoom(true, "Parent");
        } else {
            addParentBTN.setText("Add Parent to Room");
            room.removePersonInRoom("Parent");
        }
    }

    public void addChild() {
        if (addChildBTN.isSelected()) {
            addChildBTN.setText("Remove Child from Room");
            room.setPersonInRoom(true, "Child");
        } else {
            addChildBTN.setText("Add Child to Room");
            room.removePersonInRoom("Child");
        }
    }

    public void addGuest() {
        if (addGuestBTN.isSelected()) {
            addGuestBTN.setText("Remove Guest from Room");
            room.setPersonInRoom(true, "Guest");
        } else {
            addGuestBTN.setText("Add Guest to Room");
            room.removePersonInRoom("Guest");
        }
    }

    public void blockWindow() {
        if(room instanceof IndoorRoom){
            if (blockWindowBTN.isSelected()) {
                blockWindowBTN.setText("Unblock Window");
                ((IndoorRoom)room).setWindowBlocked(true);
            } else {
                blockWindowBTN.setText("Block Window");
                ((IndoorRoom)room).setWindowBlocked(false);
            }
        }

    }
}
