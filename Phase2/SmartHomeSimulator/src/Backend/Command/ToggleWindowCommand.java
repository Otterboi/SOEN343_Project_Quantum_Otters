package Backend.Command;

import Backend.HouseLayout.IndoorRoom;
import Backend.HouseLayout.Room;
import Backend.Model.DateTime;
import Backend.Model.Log;
import javafx.scene.control.Alert;


public class ToggleWindowCommand implements Command {

    private Room room;

    public ToggleWindowCommand (Room room){
        this.room = room;
    }

    @Override
    public void execute() {
        if (room instanceof IndoorRoom indoorRoom) {
            if (indoorRoom.isWindowBlocked()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Window Operation Error");
                alert.setContentText("Window is blocked and cannot be opened/closed.");
                alert.showAndWait();
                return;
            }
            boolean newWindowState = !indoorRoom.isWindowOpen();
            indoorRoom.setWindowOpen(newWindowState);
            String output = "Window in " + room.getRoomName() + " is now " + (newWindowState ? "OPEN" : "CLOSED");
            System.out.println(output);
            Log.getInstance().getLogEntries().add("[" + DateTime.getInstance().getTimeAsString() + "] " + output);
        }
    }
    }

