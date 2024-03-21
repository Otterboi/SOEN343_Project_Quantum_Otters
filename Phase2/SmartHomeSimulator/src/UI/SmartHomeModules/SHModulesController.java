/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.SmartHomeModules;

import java.net.URL;
import java.util.ResourceBundle;

import Backend.Command.Command;
import Backend.Command.ToggleDoorCommand;
import Backend.Command.ToggleLightCommand;
import Backend.Command.ToggleWindowCommand;
import Backend.Model.DateTime;
import Backend.SimulatorMenu.SimulatorHome;
import Backend.Users.User;
import Backend.Users.Role;
import Backend.HouseLayout.House;
import Backend.HouseLayout.IndoorRoom;
import Backend.HouseLayout.Room;
import Util.TemperatureUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
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
    private Label ot;
    @FXML
    ToggleButton addParentBTN, addChildBTN, addGuestBTN, blockWindowBTN, autoModeToggle,OpenCloseDoors, OpenCloseWindows, OpenCloseLights;
    public SHModulesController(Room r){
        room = r;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Role CurrentUserRole = House.getLoggedInUser() != null ? House.getLoggedInUser().getRole() : Role.STRANGER;
        setPermissionForSHC(CurrentUserRole);

     //   DateTime.getInstance().dateTimeProperty().addListener((obs,oldTime,newTime)->{
       //     Platform.runLater(this::updateTemperatureDisplay);
        //});

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
            OpenCloseWindows.setDisable(true);
        }

        autoModeToggle.setOnAction(e-> {
            boolean isSelected = autoModeToggle.isSelected();

            room.setAutoModeEnabled(isSelected);
            if(isSelected){
                autoModeToggle.setText("Disable Auto Light Mode");
                OpenCloseLights.setDisable(true);

            }
            else {
                autoModeToggle.setText("Enable Auto Light Mode");
                OpenCloseLights.setDisable(false);
                if(!room.isLightOn()){
                    OpenCloseLights.setSelected(false);
                    OpenCloseLights.setText("Turn ON");
                }
            }

            if(room.isAutoModeEnabled()){
                OpenCloseLights.setDisable(true);
                autoModeToggle.setText("Disable Auto Light Mode");
                autoModeToggle.setSelected(true);
                if(room.isLightOn()){
                    OpenCloseLights.setSelected(true);
                    OpenCloseLights.setText("Light OFF");
                }else{
                    OpenCloseLights.setSelected(false);
                    OpenCloseLights.setText("Turn ON/OFF Lights");
                }

            }else{
                OpenCloseLights.setDisable(false);
            }
        });
        OpenCloseDoors.setOnAction(e-> {
            Command toggleDoor = new ToggleDoorCommand(room);
            toggleDoor.execute();

        });
        OpenCloseWindows.setOnAction(e-> {

           Command toggleWindow = new ToggleWindowCommand(room);
           toggleWindow.execute();

        });
        if(room.isAutoModeEnabled()){
            OpenCloseLights.setDisable(true);
            autoModeToggle.setText("Disable Auto Light Mode");
            autoModeToggle.setSelected(true);
            if(room.isLightOn()){
                OpenCloseLights.setSelected(true);
                OpenCloseLights.setText("Light OFF");
            }else{
                OpenCloseLights.setSelected(false);
                OpenCloseLights.setText("Turn ON/OFF Lights");
            }

        }else{
            if(House.getLoggedInUser().getRoleString().equals("parent")){
                OpenCloseLights.setDisable(false);
            }
        }
        if(room.isLightOn()){
            OpenCloseLights.setSelected(true);
            OpenCloseLights.setText("Light OFF");
        }


        OpenCloseLights.setOnAction(e-> {

            Command toggleLight = new ToggleLightCommand(room);
            toggleLight.execute();
            OpenCloseLights.setText(room.isLightOn() ? "Light OFF":"Light ON");
        });


    }

    private void setPermissionForSHC(Role currentUserRole) {
        boolean doors = false;
        boolean windows = false;
        boolean lights = false;
        boolean autolightmode = false;

        switch (currentUserRole){
            case PARENT:
            case ADMIN:
                doors = windows = lights = autolightmode = true;
                break;
            case CHILD:
                if(SimulatorHome.getInstance().getRoom().equals(room.getRoomName()) == true){
                    lights = true;
                    windows = !(room instanceof IndoorRoom && ((IndoorRoom)room).isWindowBlocked());
                    doors = true;
                }else{
                    OpenCloseLights.setDisable(true);
                }

                break;
            case GUEST:
                if(SimulatorHome.getInstance().getRoom().equals(room.getRoomName()) == true){
                    lights = true;
                    windows = !(room instanceof IndoorRoom && ((IndoorRoom)room).isWindowBlocked());
                }
                break;
            case STRANGER:
                break;

        }
        OpenCloseDoors.setDisable(!doors);
        OpenCloseLights.setDisable(!lights);
        OpenCloseWindows.setDisable(!windows);
        autoModeToggle.setDisable(!autolightmode);
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
