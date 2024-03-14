package UI.EditSimulation;

import Backend.HouseLayout.House;
import Backend.HouseLayout.Room;
import Backend.Model.DateTime;
import Backend.SimulatorMenu.SimulatorHome;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditSimulationController implements Initializable {

    private String[] userTypes = new String[House.getUsers().size()];
    private Room[] rooms = new Room[12];

    @FXML
    DatePicker datePicker;
    @FXML
    TextField newTime, newTemp;
    @FXML
    Button saveChangesBTN;
    @FXML
    ChoiceBox<String> changeUserBox, changeRoomBox;

    private SimulatorHome menu;
    private String[] roomTypes = new String[House.getRooms().size()+1];

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.menu = SimulatorHome.getInstance();

        if(userTypes.length != 0){
            for (int i = 0; i < House.getUsers().size(); i++){
                userTypes[i] = House.getUsers().get(i).getName();
            }
        }

        this.datePicker.setValue(LocalDate.parse(menu.getDate()));
        this.newTime.setText(menu.getTime());
        this.newTemp.setText(menu.getTemp());
        this.changeUserBox.getItems().addAll(userTypes);
        this.changeUserBox.setValue(menu.getUser());

        for(int i = 0; i < House.getRooms().size(); i++){
            roomTypes[i] = House.getRooms().get(i).getRoomName();
        }

        roomTypes[roomTypes.length-1] = "Remote";

        this.changeRoomBox.getItems().addAll(roomTypes);
        this.changeRoomBox.setValue(menu.getRoom());
    }

    public void updateSimulation(){
        SimulatorHome menu = SimulatorHome.getInstance();
        DateTime dateTime = DateTime.getInstance();

        menu.setDate(datePicker.getValue().toString());
        menu.setTime(newTime.getText());
        menu.setTemp(newTemp.getText());
        menu.setRoom(changeRoomBox.getValue());
        boolean userLeft = false;

        for(int i = 0; i < House.getRooms().size(); i++){
            Room room = House.getRooms().get(i);
            boolean isRemote = menu.getRoom().equals("Remote");
            if(room.getPeopleInRoom().contains(menu.getUser()) || isRemote){
                room.removePersonInRoom(menu.getUser());
                if(!isRemote){
                    System.out.println("REMOVING logged in user from " + room.getRoomName());

                }else{
                    if(!userLeft) {
                        System.out.println("Logged in user left the house.");
                        userLeft = true;
                    }
                }

            }



            if(room.getRoomName().toLowerCase().equals(menu.getRoom().toLowerCase())){
                room.setPersonInRoom(true, menu.getUser());
                System.out.println("ADDING logged in user to " + room.getRoomName());
            }
        }

        menu.setUser(changeUserBox.getValue());

        dateTime.setTime(newTime.getText());
        dateTime.setDate(datePicker.getValue().toString());

    }
}
