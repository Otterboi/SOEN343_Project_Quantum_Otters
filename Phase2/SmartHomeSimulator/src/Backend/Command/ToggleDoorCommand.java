package Backend.Command;
import Backend.HouseLayout.Room;
import Backend.Model.DateTime;
import Backend.Model.Log;

public class ToggleDoorCommand implements Command{
    private Room room;
    public ToggleDoorCommand(Room room){
        this.room = room;
    }
    @Override
    public void execute() {
        boolean newDoorState = !room.isDoorOpen();
        room.setDoorOpen(newDoorState);
        String output = "Door in " + room.getRoomName() + " is now " + (newDoorState? "OPEN" : "CLOSED");
        System.out.println(output);
        Log.getInstance().getLogEntries().add("[" + DateTime.getInstance().getTimeAsString() + "] " + output);
    }
}
