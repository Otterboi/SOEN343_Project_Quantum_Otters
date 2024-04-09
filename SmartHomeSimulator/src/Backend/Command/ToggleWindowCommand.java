package Backend.Command;

import Backend.HouseLayout.House;
import Backend.HouseLayout.IndoorRoom;
import Backend.HouseLayout.Room;
import Backend.Model.DateTime;
import Backend.Model.Log;
import javafx.scene.control.Alert;


public class ToggleWindowCommand implements Command {

    private Room room;
    private boolean isUser = true;

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
            Log.getInstance().getLogEntriesConsole().add("[" + DateTime.getInstance().getTimeAsString() + "] " + output);
            Log.getInstance().getLogEntries().add(
                    "\n\n\nTimestamp: " + DateTime.getInstance().getTimeAndDateAsString()+
                            "\nEvent: Window State Change" +
                            "\nLocation: " + room.getRoomName() +
                            "\nTriggered By: " +  (isUser? House.getLoggedInUser().getName() : "SHH") +
                            "\nEvent Details: Window is now " + (newWindowState ? "OPEN" : "CLOSED")
            );
        }
    }
    }

