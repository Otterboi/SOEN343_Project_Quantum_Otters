package Backend.Command;
import Backend.HouseLayout.House;
import Backend.HouseLayout.Room;
import Backend.Model.DateTime;
import Backend.Model.Log;

public class ToggleDoorCommand implements Command{
    private Room room;
    private boolean isUser = true;
    public ToggleDoorCommand(Room room){
        this.room = room;
    }
    @Override
    public void execute() {
        boolean newDoorState = !room.isDoorOpen();
        room.setDoorOpen(newDoorState);
        String output = "Door in " + room.getRoomName() + " is now " + (newDoorState? "OPEN" : "CLOSED");
        System.out.println(output);
        Log.getInstance().getLogEntriesConsole().add("[" + DateTime.getInstance().getTimeAsString() + "] " + output);
        Log.getInstance().getLogEntries().add(
                        "\n\n\nTimestamp: " + DateTime.getInstance().getTimeAndDateAsString()+
                        "\nEvent: Door State Change" +
                        "\nLocation: " + room.getRoomName() +
                        "\nTriggered By: " + (isUser? House.getLoggedInUser().getName() : "SHC") +
                        "\nEvent Details: Door is now " + (newDoorState? "OPEN" : "CLOSED")
        );
    }
}
