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
import Backend.Model.DateTime;
import Backend.Model.Log;
import Backend.SimulatorMenu.SimulatorHome;
import Backend.Users.Role;
import Backend.HouseLayout.House;
import Backend.HouseLayout.IndoorRoom;
import Backend.HouseLayout.Room;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;

import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;

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
    private ToggleButton test1BTN, test2BTN;

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
    private ListView<Room> roomsListViews;

    private Timer policeCallTimer;
    @FXML
    private TextField motionDetectorLocation;
    @FXML
    private Spinner<Integer> timerToCallPolice;
    @FXML
    private Button setLocationButton;
    @FXML
    private Button setTimerButton;
    @FXML
    private Label countdownLabel;

    private Button startPoliceCallTimer;


    @FXML
    ToggleButton addParentBTN, addChildBTN, addGuestBTN, blockWindowBTN, autoModeToggle, OpenCloseDoors, OpenCloseWindows, OpenCloseLights;

    public SHModulesController(Room r) {
        room = r;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        house = House.getInstance();
        initializeRoomListView();
        initializeTreeView();
        initializeSHPComponents();
        addZoneButton.setOnAction(e -> addZoneWithSelectedRooms());
        deleteZoneButton.setOnAction(event -> deleteSelectedZone());
        setMorningTempButton.setOnAction(e -> setMorningTemp());
        setAfternoonTempButton.setOnAction(e -> setAfternoonTemp());
        setNightTempButton.setOnAction(e -> setNightTemp());
        addRoomToZoneButton.setOnAction(e -> addRoomToSelectedZone());
        removeRoomFromZoneButton.setOnAction(e -> removeRoomFromSelectedZone());
        overwriteTempButton.setOnAction(e -> overwriteTempAction());
        Role CurrentUserRole = House.getLoggedInUser() != null ? House.getLoggedInUser().getRole() : Role.STRANGER;
        setPermissionForSHC(CurrentUserRole);

        for (String person : room.getPeopleInRoom()) {
            if (person.equals("Parent")) {
                addParentBTN.setText("Remove Parent from Room");
                addParentBTN.setSelected(true);
            } else if (person.equals("Child")) {
                addChildBTN.setText("Remove Child from Room");
                addChildBTN.setSelected(true);
            } else if (person.equals("Guest")) {
                addGuestBTN.setText("Remove Guest from Room");
                addGuestBTN.setSelected(true);
            }
        }
        if (room instanceof IndoorRoom) {
            if (((IndoorRoom) room).isWindowBlocked()) {
                blockWindowBTN.setText("Unblock Window");
                blockWindowBTN.setSelected(true);
            }
        } else {
            blockWindowBTN.setDisable(true);
            OpenCloseWindows.setDisable(true);
        }

        autoModeToggle.setOnAction(e -> {
            boolean isSelected = autoModeToggle.isSelected();

            room.setAutoModeEnabled(isSelected);
            if (isSelected) {
                autoModeToggle.setText("Disable Auto Light Mode");
                OpenCloseLights.setDisable(true);

            } else {
                autoModeToggle.setText("Enable Auto Light Mode");
                OpenCloseLights.setDisable(false);
                if (!room.isLightOn()) {
                    OpenCloseLights.setSelected(false);
                    OpenCloseLights.setText("Turn ON");
                }
            }

            if (room.isAutoModeEnabled()) {
                OpenCloseLights.setDisable(true);
                autoModeToggle.setText("Disable Auto Light Mode");
                autoModeToggle.setSelected(true);
                if (room.isLightOn()) {
                    OpenCloseLights.setSelected(true);
                    OpenCloseLights.setText("Light OFF");
                } else {
                    OpenCloseLights.setSelected(false);
                    OpenCloseLights.setText("Turn ON/OFF Lights");
                }

            } else {
                OpenCloseLights.setDisable(false);
            }
        });
        OpenCloseDoors.setOnAction(e -> {
            Command toggleDoor = new ToggleDoorCommand(room);
            toggleDoor.execute();

        });
        OpenCloseWindows.setOnAction(e -> {

            Command toggleWindow = new ToggleWindowCommand(room);
            toggleWindow.execute();

        });
        if (room.isAutoModeEnabled()) {
            OpenCloseLights.setDisable(true);
            autoModeToggle.setText("Disable Auto Light Mode");
            autoModeToggle.setSelected(true);
            if (room.isLightOn()) {
                OpenCloseLights.setSelected(true);
                OpenCloseLights.setText("Light OFF");
            } else {
                OpenCloseLights.setSelected(false);
                OpenCloseLights.setText("Turn ON/OFF Lights");
            }

        } else {
            if (House.getLoggedInUser().getRoleString().equals("parent")) {
                OpenCloseLights.setDisable(false);
            }
        }
        if (room.isLightOn()) {
            OpenCloseLights.setSelected(true);
            OpenCloseLights.setText("Light OFF");
        }


        OpenCloseLights.setOnAction(e -> {

            Command toggleLight = new ToggleLightCommand(room);
            toggleLight.execute();
            OpenCloseLights.setText(room.isLightOn() ? "Light OFF" : "Light ON");
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
        roomsListViews.setItems(FXCollections.observableArrayList(house.getRooms()));
        roomsListViews.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
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

        for (Room room : selectedRooms) {
            room.setZone(newZone);
        }

        refreshTreeView();
        zoneNameTextField.clear();
        roomsListView.getSelectionModel().clearSelection();
        String logMessage = "[" + DateTime.getInstance().getTimeAsString() + "]" + " Zone '" + zoneName + "' with selected rooms added successfully.";
        Log.getInstance().getLogEntriesConsole().add(logMessage);
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
                    String logMessage = "[" + DateTime.getInstance().getTimeAsString() + "]" + " Zone '" + selected.getValue() + "' removed successfully.";
                    Log.getInstance().getLogEntriesConsole().add(logMessage);
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
                String logMessage = "[" + DateTime.getInstance().getTimeAsString() + "]" + " Room " + selectedRoom.getRoomName() + " added to zone " + selectedZone.getName();
                Log.getInstance().getLogEntriesConsole().add(logMessage);
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
                String logMessage = "[" + DateTime.getInstance().getTimeAsString() + "]" + " Room " + selectedRoom.getRoomName() + " removed from zone " + selectedZone.getName();
                Log.getInstance().getLogEntriesConsole().add(logMessage);
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
        room.setOverwritingTemp(Float.parseFloat(overwriteTempTextField.getText()));
        room.setOverwritingTemp(true);
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
            selectedZone.setUser(true);
            if (selectedZone != null) {
                try {
                    float temp = Float.parseFloat(morningTempTextField.getText());
                    selectedZone.setMorningTemp(temp);

                    for (Zone zone : House.getZones()) {
                        if (zone.getName().equals(zoneName)) {
                            zone.setMorningTemp(temp);
                        }
                    }

                    String logMessage = "[" + DateTime.getInstance().getTimeAsString() + "]" + " Morning temperature set to " + temp + "°C for zone " + selectedZone.getName();
                    Log.getInstance().getLogEntriesConsole().add(logMessage);

                    String output = "Morning temperature was set to " + temp + "°C.";
                    Log.getInstance().getLogEntries().add(
                            "\n\n\nTimestamp: " + DateTime.getInstance().getTimeAndDateAsString() +
                                    "\nEvent: Zone Temperature & Time State Change" +
                                    "\nZone: " + selectedZone.getName() +
                                    "\nTriggered By: " + House.getLoggedInUser().getName() +
                                    "\nEvent Details: " + output
                    );
                    showAlert("Success", "Morning temperature set to " + temp + "°C for zone " + selectedZone.getName() + ".");

                } catch (NumberFormatException e) {
                    showAlert("Error", "Invalid temperature format.");
                }
            }
            selectedZone.setUser(false);
        } else {
            showAlert("Error", "Please select a zone.");
        }
    }

    private void setAfternoonTemp() {
        TreeItem<String> selectedNode = zoneRoomTreeView.getSelectionModel().getSelectedItem();
        if (selectedNode != null && selectedNode.getParent() == zoneRoomTreeView.getRoot()) {
            String zoneName = selectedNode.getValue();
            Zone selectedZone = findZoneByName(zoneName);
            selectedZone.setUser(true);
            if (selectedZone != null) {
                try {
                    float temp = Float.parseFloat(afternoonTempTextField.getText());
                    selectedZone.setAfternoonTemp(temp);

                    for (Zone zone : House.getZones()) {
                        if (zone.getName().equals(zoneName)) {
                            zone.setAfternoonTemp(temp);
                        }
                    }

                    String logMessage = "[" + DateTime.getInstance().getTimeAsString() + "]" + " Afternoon temperature set to " + temp + "°C for zone " + selectedZone.getName();
                    Log.getInstance().getLogEntriesConsole().add(logMessage);

                    String output = "Afternoon temperature was set to " + temp + "°C.";
                    Log.getInstance().getLogEntries().add(
                            "\n\n\nTimestamp: " + DateTime.getInstance().getTimeAndDateAsString() +
                                    "\nEvent: Zone Temperature & Time State Change" +
                                    "\nZone: " + selectedZone.getName() +
                                    "\nTriggered By: " + House.getLoggedInUser().getName() +
                                    "\nEvent Details: " + output
                    );
                    showAlert("Success", "Afternoon temperature set to " + temp + "°C for zone " + selectedZone.getName() + ".");
                } catch (NumberFormatException e) {
                    showAlert("Error", "Invalid temperature format.");
                }
            }
            selectedZone.setUser(false);
        } else {
            showAlert("Error", "Please select a zone.");
        }

    }

    private void setNightTemp() {
        TreeItem<String> selectedNode = zoneRoomTreeView.getSelectionModel().getSelectedItem();
        if (selectedNode != null && selectedNode.getParent() == zoneRoomTreeView.getRoot()) {
            String zoneName = selectedNode.getValue();
            Zone selectedZone = findZoneByName(zoneName);
            selectedZone.setUser(true);
            if (selectedZone != null) {
                try {
                    float temp = Float.parseFloat(nightTempTextField.getText());
                    selectedZone.setNightTemp(temp);

                    for (Zone zone : House.getZones()) {
                        if (zone.getName().equals(zoneName)) {
                            zone.setNightTemp(temp);
                        }
                    }

                    String logMessage = "[" + DateTime.getInstance().getTimeAsString() + "]" + " Night temperature set to " + temp + "°C for zone " + selectedZone.getName();
                    Log.getInstance().getLogEntriesConsole().add(logMessage);

                    String output = "Night temperature was set to " + temp + "°C.";
                    Log.getInstance().getLogEntries().add(
                            "\n\n\nTimestamp: " + DateTime.getInstance().getTimeAndDateAsString() +
                                    "\nEvent: Zone Temperature & Time State Change" +
                                    "\nZone: " + selectedZone.getName() +
                                    "\nTriggered By: " + House.getLoggedInUser().getName() +
                                    "\nEvent Details: " + output
                    );
                    showAlert("Success", "Night temperature set to " + temp + "°C for zone " + selectedZone.getName() + ".");
                } catch (NumberFormatException e) {
                    showAlert("Error", "Invalid temperature format.");
                }
            }
            selectedZone.setUser(false);
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
            String logMessage = "[" + DateTime.getInstance().getTimeAsString() + "]" + " Summer temperature set to " + temp + "°C for the house.";
            Log.getInstance().getLogEntriesConsole().add(logMessage);
            showAlert("Success", "Summer temperature set to " + temp + "°C for the house.");
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid temperature format.");
        }
    }

    private void setWinterTemp() {
        try {
            double temp = Double.parseDouble(winterTempTextField.getText());
            House.setWinterTemperature(temp);
            String logMessage = "[" + DateTime.getInstance().getTimeAsString() + "]" + " Winter temperature set to " + temp + "°C for the house.";
            Log.getInstance().getLogEntriesConsole().add(logMessage);
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
        boolean shh = false;
        boolean shhOvr = false;
        boolean shp = false;

        switch (currentUserRole) {
            case PARENT:
            case ADMIN:
                doors = windows = lights = autolightmode = shh = shhOvr = shp = true;
                break;
            case CHILD:
                if (SimulatorHome.getInstance().getRoom().equals(room.getRoomName()) == true) {
                    lights = true;
                    windows = !(room instanceof IndoorRoom && ((IndoorRoom) room).isWindowBlocked());
                    doors = true;
                    shhOvr = true;
                } else {
                    OpenCloseLights.setDisable(true);
                }

                break;
            case GUEST:
                if (SimulatorHome.getInstance().getRoom().equals(room.getRoomName()) == true) {
                    lights = true;
                    shhOvr = true;
                    windows = !(room instanceof IndoorRoom && ((IndoorRoom) room).isWindowBlocked());
                }
                break;
            case STRANGER:
                break;

        }
        OpenCloseDoors.setDisable(!doors);
        OpenCloseLights.setDisable(!lights);
        OpenCloseWindows.setDisable(!windows);
        autoModeToggle.setDisable(!autolightmode);
        addZoneButton.setDisable(!shh);
        deleteZoneButton.setDisable(!shh);
        setMorningTempButton.setDisable(!shh);
        setAfternoonTempButton.setDisable(!shh);
        setNightTempButton.setDisable(!shh);
        addRoomToZoneButton.setDisable(!shh);
        removeRoomFromZoneButton.setDisable(!shh);
        overwriteTempButton.setDisable(!shhOvr);
        setLocationButton.setDisable(!shp);
        setTimerButton.setDisable(!shp);
    }

    private void initializeSHPComponents() {

        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 600, 30, 1);
        timerToCallPolice.setValueFactory(valueFactory);
        timerToCallPolice.setEditable(true);

        setLocationButton.setOnAction(event -> setMotionDetectorLocation());
        setTimerButton.setOnAction(event -> {
            int seconds = timerToCallPolice.getValue();
            SimulatorHome.getInstance().setPoliceTimer(seconds);
            String output = "Motion Sensor timer has been set for " + seconds + " seconds!";
            Log.getInstance().getLogEntriesConsole().add("[" + DateTime.getInstance().getTimeAsString() + "] " + output);
            Log.getInstance().getLogEntries().add(
                    "\n\n\nTimestamp: " + DateTime.getInstance().getTimeAndDateAsString() +
                            "\nEvent: Authority Timer" +
                            "\nLocation: " + "Entire Household" +
                            "\nTriggered By: SHP" +
                            "\nDestined to: " + House.getLoggedInUser().getName() +
                            "\nEvent Details: " + output
            );
        });

    }

    private void setMotionDetectorLocation() {
        Room selectedRoom = roomsListViews.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            selectedRoom.setMotionDetector(true);
            String location = selectedRoom.toString();
            String logEntry = "Motion detector location set to: " + location;
            Log.getInstance().getLogEntriesConsole().add("[" + DateTime.getInstance().getTimeAsString() + "] " + logEntry);
            Log.getInstance().getLogEntries().add(logEntry);

            showAlert("Motion Detector Location", "Location set to: " + location);
        } else {
            showAlert("Motion Detector Location", "No room selected.");
        }
    }

    private void activateSecurityFeatures() {
        String logEntry = "Activating security features...";
        Log.getInstance().getLogEntriesConsole().add("[" + DateTime.getInstance().getTimeAsString() + "] " + logEntry);
        Log.getInstance().getLogEntries().add(logEntry);

        showAlert("Security Features", "Activating security features...");
    }

    private void deactivateSecurityFeatures() {
        String logEntry = "Deactivating security features...";
        Log.getInstance().getLogEntriesConsole().add("[" + DateTime.getInstance().getTimeAsString() + "] " + logEntry);
        Log.getInstance().getLogEntries().add(logEntry);

        showAlert("Security Features", "Deactivating security features...");
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
        if (room instanceof IndoorRoom) {
            if (blockWindowBTN.isSelected()) {
                blockWindowBTN.setText("Unblock Window");
                ((IndoorRoom) room).setWindowBlocked(true);
            } else {
                blockWindowBTN.setText("Block Window");
                ((IndoorRoom) room).setWindowBlocked(false);
            }
        }

    }

    public void test1() {
        room.setTemp(135.1f);
        room.getZone().setCurrentTemp(135.1f);
    }

}
