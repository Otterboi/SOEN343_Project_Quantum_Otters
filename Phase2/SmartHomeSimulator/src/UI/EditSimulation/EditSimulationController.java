package UI.EditSimulation;

import Backend.HouseLayout.House;
import Backend.HouseLayout.IndoorRoom;
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

    private String[] userTypes = {"Parent", "Child", "Guest", "Stranger"};
    private IndoorRoom[] rooms = new IndoorRoom[12];

    @FXML
    DatePicker datePicker;
    @FXML
    TextField newTime, newTemp;
    @FXML
    Button saveChangesBTN;
    @FXML
    ChoiceBox<String> changeUserBox, changeRoomBox;

    private SimulatorHome menu;
    private String[] roomTypes = new String[House.getIndoorRooms().size()];
    private String prevRoom;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.menu = SimulatorHome.getInstance();

        this.prevRoom = menu.getRoom();
        this.datePicker.setValue(LocalDate.parse(menu.getDate()));
        this.newTime.setText(menu.getTime());
        this.newTemp.setText(menu.getTemp());
        this.changeUserBox.getItems().addAll(userTypes);
        this.changeUserBox.setValue(menu.getUser());

        for(int i = 0; i < House.getIndoorRooms().size(); i++){
            roomTypes[i] = House.getIndoorRooms().get(i).getRoomName();
        }
        this.changeRoomBox.getItems().addAll(roomTypes);
        this.changeRoomBox.setValue(menu.getRoom());
    }

    public void updateSimulation(){
        SimulatorHome.getInstance().setDate(datePicker.getValue().toString());
        SimulatorHome.getInstance().setTime(newTime.getText());
        SimulatorHome.getInstance().setTemp(newTemp.getText());
        SimulatorHome.getInstance().setRoom(changeRoomBox.getValue());
        SimulatorHome.getInstance().setUser(changeUserBox.getValue());
    }
}
