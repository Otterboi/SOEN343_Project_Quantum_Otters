/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.SmartHomeModules;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import Backend.Command.Command;
import Backend.Command.ToggleDoorCommand;
import Backend.Command.ToggleLightCommand;
import Backend.Command.ToggleWindowCommand;
import Backend.HouseLayout.Zone;
import Backend.SimulatorMenu.SimulatorHome;
import Backend.Users.Role;
import Backend.HouseLayout.House;
import Backend.HouseLayout.IndoorRoom;
import Backend.HouseLayout.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


/**
 * FXML Controller class
 *
 * @author fan04
 */
public class SHModulesController implements Initializable {

    @FXML
    private TextField zoneNameTextField, morningTempTextField, afternoonTempTextField, nightTempTextField, summerTempTextField, winterTempTextField, overwriteTempTextField;
    @FXML
    private Button addZoneButton, deleteZoneButton, addRoomsToZoneButton, setMorningTempButton, setAfternoonTempButton, setNightTempButton, setSummerTempButton, setWinterTempButton, overwriteTempButton;

    @FXML
    private ListView<Room> roomsListView;

    @FXML
    private TreeView<String> zoneRoomTreeView;

    @FXML
    private Button addRoomToZoneButton, removeRoomFromZoneButton;
    private House house;

    private Room room;
    private Set<Room> selectedRooms = new HashSet<>();


    @FXML
    ToggleButton addParentBTN, addChildBTN, addGuestBTN, blockWindowBTN, autoModeToggle,OpenCloseDoors, OpenCloseWindows, OpenCloseLights;
    public SHModulesController(Room r){
        room = r;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        house = House.getInstance();
        initializeRoomListView();
        initializeTreeView();
        addZoneButton.setOnAction(e -> addZoneWithSelectedRooms());
        deleteZoneButton.setOnAction(event -> deleteSelectedZone());
        setMorningTempButton.setOnAction(e -> setMorningTemp());
        setAfternoonTempButton.setOnAction(e -> setAfternoonTemp());
        setNightTempButton.setOnAction(e -> setNightTemp());
        setSummerTempButton.setOnAction(e -> setSummerTemp());
        setWinterTempButton.setOnAction(e -> setWinterTemp());
        addRoomToZoneButton.setOnAction(e -> addRoomToSelectedZone());
        removeRoomFromZoneButton.setOnAction(e -> removeRoomFromSelectedZone());
        overwriteTempButton.setOnAction(e -> overwriteTempAction());
        Role CurrentUserRole = House.getLoggedInUser() != null ? House.getLoggedInUser().getRole() : Role.STRANGER;
        setPermissionForSHC(CurrentUserRole);

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
    private void initializeTreeView() {
        TreeItem<String> rootItem = new TreeItem<>("Zones and Rooms");
        zoneRoomTreeView.setRoot(rootItem);
        zoneRoomTreeView.setShowRoot(false);
        refreshTreeView();
    }
    private void initializeRoomListView() {
        roomsListView.setItems(FXCollections.observableArrayList(house.getRooms()));
        roomsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void refreshTreeView() {
        TreeItem<String> rootItem = zoneRoomTreeView.getRoot();
        rootItem.getChildren().clear();

        for (Zone zone : house.getZones()) {
            TreeItem<String> zoneItem = new TreeItem<>(zone.getName());
            rootItem.getChildren().add(zoneItem);

            for (Room room : zone.getRooms()) {
                TreeItem<String> roomItem = new TreeItem<>(room.getRoomName());
                zoneItem.getChildren().add(roomItem);
            }
        }
    }

    private void addZoneWithSelectedRooms() {

        String zoneName = zoneNameTextField.getText().trim();
        if (zoneName.isEmpty()) {
            showAlert("Error", "Zone name cannot be empty.");
            return;
        }

        Set<Room> selectedRooms = new HashSet<>(roomsListView.getSelectionModel().getSelectedItems());
        if (selectedRooms.isEmpty()) {
            showAlert("Error", "At least one room must be selected.");
            return;
        }

        Zone newZone = new Zone(zoneName, selectedRooms);
        house.addZone(newZone);
        refreshTreeView();
        zoneNameTextField.clear();
        roomsListView.getSelectionModel().clearSelection();
        showAlert("Success", "Zone '" + zoneName + "' with selected rooms added successfully.");
    }

    @FXML
    private void deleteSelectedZone() {
        TreeItem<String> selected = zoneRoomTreeView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            TreeItem<String> parent = selected.getParent();
            if (parent != null && parent == zoneRoomTreeView.getRoot()) {
                boolean removed = house.removeZone(selected.getValue());
                if (removed) {
                    zoneRoomTreeView.getRoot().getChildren().remove(selected);
                    showAlert("Success", "Zone removed successfully.");
                } else {
                    showAlert("Error", "Could not remove the selected zone.");
                }
            } else {
                showAlert("Error", "Please select a valid zone to delete.");
            }
        } else {
            showAlert("Error", "No zone selected.");
        }
    }
    private void addRoomToSelectedZone() {
        TreeItem<String> selectedZoneNode = zoneRoomTreeView.getSelectionModel().getSelectedItem();
        if (selectedZoneNode != null && selectedZoneNode.getParent() == zoneRoomTreeView.getRoot()) {
            Zone selectedZone = findZoneByName(selectedZoneNode.getValue());
            Room selectedRoom = roomsListView.getSelectionModel().getSelectedItem();
            if (selectedZone != null && selectedRoom != null && !selectedZone.getRooms().contains(selectedRoom)) {
                selectedZone.addRoom(selectedRoom);
                refreshTreeView();
                showAlert("Success", "Room " + selectedRoom.getRoomName() + " added to zone " + selectedZone.getName() + ".");
            } else {
                showAlert("Error", "Please select both a zone and a room.");
            }
        } else {
            showAlert("Error", "Please select a zone.");
        }
    }

    private void removeRoomFromSelectedZone() {
        TreeItem<String> selectedZoneNode = zoneRoomTreeView.getSelectionModel().getSelectedItem();
        if (selectedZoneNode != null && selectedZoneNode.getParent() == zoneRoomTreeView.getRoot()) {
            Zone selectedZone = findZoneByName(selectedZoneNode.getValue());
            Room selectedRoom = roomsListView.getSelectionModel().getSelectedItem();
            if (selectedZone != null && selectedRoom != null && selectedZone.getRooms().contains(selectedRoom)) {
                selectedZone.removeRoom(selectedRoom);
                refreshTreeView();
                showAlert("Success", "Room " + selectedRoom.getRoomName() + " removed from zone " + selectedZone.getName() + ".");
            } else {
                showAlert("Error", "Please select both a zone and a room.");
            }
        } else {
            showAlert("Error", "Please select a zone.");
        }
    }
    @FXML
    private void overwriteTempAction() {
        TreeItem<String> selectedNode = zoneRoomTreeView.getSelectionModel().getSelectedItem();
        if (selectedNode != null && selectedNode.getParent() == zoneRoomTreeView.getRoot()) {
            String zoneName = selectedNode.getValue();
            Zone selectedZone = findZoneByName(zoneName);
            if (selectedZone != null) {
                try {
                    double temp = Double.parseDouble(overwriteTempTextField.getText());
                    selectedZone.setOverwrittenTemp(temp);
                    selectedZone.setOverwritten(true);
                    showAlert("Success", "Overwritten temperature set to " + temp + "°C for zone " + selectedZone.getName() + ".");
                } catch (NumberFormatException e) {
                    showAlert("Error", "Invalid temperature format.");
                }
            }
        } else {
            showAlert("Error", "Please select a zone.");
        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void setMorningTemp() {
        TreeItem<String> selectedNode = zoneRoomTreeView.getSelectionModel().getSelectedItem();
        if (selectedNode != null && selectedNode.getParent() == zoneRoomTreeView.getRoot()) {
            String zoneName = selectedNode.getValue();
            Zone selectedZone = findZoneByName(zoneName);
            if (selectedZone != null) {
                try {
                    double temp = Double.parseDouble(morningTempTextField.getText());
                    selectedZone.setMorningTemp(temp);
                    showAlert("Success", "Morning temperature set to " + temp + "°C for zone " + selectedZone.getName() + ".");
                } catch (NumberFormatException e) {
                    showAlert("Error", "Invalid temperature format.");
                }
            }
        } else {
            showAlert("Error", "Please select a zone.");
        }
    }

    private void setAfternoonTemp() {
        TreeItem<String> selectedNode = zoneRoomTreeView.getSelectionModel().getSelectedItem();
        if (selectedNode != null && selectedNode.getParent() == zoneRoomTreeView.getRoot()) {
            String zoneName = selectedNode.getValue();
            Zone selectedZone = findZoneByName(zoneName);
            if (selectedZone != null) {
                try {
                    double temp = Double.parseDouble(afternoonTempTextField.getText());
                    selectedZone.setAfternoonTemp(temp);
                    showAlert("Success", "Afternoon temperature set to " + temp + "°C for zone " + selectedZone.getName() + ".");
                } catch (NumberFormatException e) {
                    showAlert("Error", "Invalid temperature format.");
                }
            }
        } else {
            showAlert("Error", "Please select a zone.");
        }
    }

    private void setNightTemp() {
        TreeItem<String> selectedNode = zoneRoomTreeView.getSelectionModel().getSelectedItem();
        if (selectedNode != null && selectedNode.getParent() == zoneRoomTreeView.getRoot()) {
            String zoneName = selectedNode.getValue();
            Zone selectedZone = findZoneByName(zoneName);
            if (selectedZone != null) {
                try {
                    double temp = Double.parseDouble(nightTempTextField.getText());
                    selectedZone.setNightTemp(temp);
                    showAlert("Success", "Night temperature set to " + temp + "°C for zone " + selectedZone.getName() + ".");
                } catch (NumberFormatException e) {
                    showAlert("Error", "Invalid temperature format.");
                }
            }
        } else {
            showAlert("Error", "Please select a zone.");
        }
    }

    private Zone findZoneByName(String zoneName) {
        for (Zone zone : house.getZones()) {
            if (zone.getName().equals(zoneName)) {
                return zone;
            }
        }
        return null;
    }


    private void setSummerTemp() {
        try {
            double temp = Double.parseDouble(summerTempTextField.getText());
            House.setSummerTemperature(temp);
            showAlert("Success", "Summer temperature set to " + temp + "°C for the house.");
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid temperature format.");
        }
    }

    private void setWinterTemp() {
        try {
            double temp = Double.parseDouble(winterTempTextField.getText());
            House.setWinterTemperature(temp);
            showAlert("Success", "Winter temperature set to " + temp + "°C for the house.");
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid temperature format.");
        }
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
