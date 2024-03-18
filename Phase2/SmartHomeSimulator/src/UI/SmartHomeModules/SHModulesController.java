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
    private TextField zoneNameTextField;
    @FXML
    private Button addZoneButton;
    @FXML
    private ListView<Zone> zoneListView;
    @FXML
    private Button deleteZoneButton;
    @FXML
    private ListView<Room> roomsListView;
    @FXML
    private Button addRoomsToZoneButton;
    @FXML
    private TreeView<String> zoneRoomTreeView;

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
        rootItem.getChildren().clear(); // Clear existing items

        for (Zone zone : house.getZones()) {
            TreeItem<String> zoneItem = new TreeItem<>(zone.getName());
            rootItem.getChildren().add(zoneItem);

            for (Room room : zone.getRooms()) {
                TreeItem<String> roomItem = new TreeItem<>(room.getRoomName()); // Make sure Room has a getName() method
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
            // Assume each TreeItem directly under the root represents a Zone
            TreeItem<String> parent = selected.getParent();
            if (parent != null && parent == zoneRoomTreeView.getRoot()) {
                // Remove zone from house
                boolean removed = house.removeZone(selected.getValue());
                if (removed) {
                    // Update UI
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

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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
