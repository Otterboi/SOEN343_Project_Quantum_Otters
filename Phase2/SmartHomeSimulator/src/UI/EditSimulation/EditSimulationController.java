package UI.EditSimulation;

import Backend.HouseLayout.House;
import Backend.HouseLayout.Room;
import Backend.Model.DateTime;
import Backend.Model.Log;
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
        this.newTemp.setText(Double.toString(menu.getTemp()));
        this.changeUserBox.getItems().addAll(userTypes);
        this.changeUserBox.setValue(menu.getUser());
        String output = "New user " + menu.getUser() + " has logged in.";
        System.out.println(output);
        Log.getInstance().getLogEntriesConsole().add("[" + DateTime.getInstance().getTimeAsString() + "] " + output);
        Log.getInstance().getLogEntries().add(
                "\n\n\nTimestamp: " + DateTime.getInstance().getTimeAndDateAsString()+
                        "\nEvent: Logged In User Change" +
                        "\nLocation: Simulation" +
                        "\nTriggered By: User" +
                        "\nEvent Details: " + output
        );
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

        if(newTemp.getText().isEmpty()){
            menu.setTempOverwritten(false);
        }else{
            menu.setTempOverwritten(true);
        }
        menu.setTemp(Float.parseFloat(newTemp.getText()));
        menu.setRoom(changeRoomBox.getValue());
        boolean userLeft = false;

        for(int i = 0; i < House.getRooms().size(); i++){
            Room room = House.getRooms().get(i);
            boolean isRemote = menu.getRoom().equals("Remote");

            if(room.getPeopleInRoom().contains(changeUserBox.getValue()) || isRemote){
                room.removePersonInRoom(changeUserBox.getValue());
                if(!isRemote){
                    String output = "REMOVING logged in user from " + room.getRoomName();
                    System.out.println(output);
                    Log.getInstance().getLogEntriesConsole().add("[" + DateTime.getInstance().getTimeAsString() + "] " + output);
                    Log.getInstance().getLogEntries().add(
                            "\n\n\nTimestamp: " + DateTime.getInstance().getTimeAndDateAsString()+
                                    "\nEvent: User Location Change" +
                                    "\nLocation: " + room.getRoomName() +
                                    "\nTriggered By: User" +
                                    "\nEvent Details: " + output
                    );

                }else{
                    if(!userLeft) {
                        userLeft = true;
                        String output = "Logged in user left the house.";
                        System.out.println(output);
                        Log.getInstance().getLogEntriesConsole().add("[" + DateTime.getInstance().getTimeAsString() + "] " + output);
                        Log.getInstance().getLogEntries().add(
                                "\n\n\nTimestamp: " + DateTime.getInstance().getTimeAndDateAsString()+
                                        "\nEvent: User Leaving Simulator" +
                                        "\nLocation: Household" +
                                        "\nTriggered By: User" +
                                        "\nEvent Details: " + output
                        );
                    }
                }

            }



            if(room.getRoomName().toLowerCase().equals(menu.getRoom().toLowerCase())){
                room.setPersonInRoom(true, changeUserBox.getValue());
                String output = "ADDING logged in user to " + room.getRoomName();
                System.out.println(output);
                Log.getInstance().getLogEntriesConsole().add("[" + DateTime.getInstance().getTimeAsString() + "] " + output);
                Log.getInstance().getLogEntries().add(
                        "\n\n\nTimestamp: " + DateTime.getInstance().getTimeAndDateAsString()+
                                "\nEvent: User Location Change" +
                                "\nLocation: " + room.getRoomName() +
                                "\nTriggered By: User" +
                                "\nEvent Details: " + output
                );

            }
        }

        menu.setUser(changeUserBox.getValue());

        dateTime.setTime(newTime.getText());
        dateTime.setDate(datePicker.getValue().toString());

    }
}
