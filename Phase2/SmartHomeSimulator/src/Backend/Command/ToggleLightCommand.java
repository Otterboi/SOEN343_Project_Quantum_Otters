package Backend.Command;
import Backend.HouseLayout.Room;
import Backend.Model.DateTime;
import Backend.Model.Log;

public class ToggleLightCommand implements Command {

    private Room room;

    public ToggleLightCommand (Room room){
        this.room = room;
    }

    @Override
    public void execute() {
        boolean newLightState = !room.isLightOn();
        room.setLightOn(newLightState);
        String output = "Light in " + room.getRoomName() + " is now " + (newLightState ? "ON" : "OFF");
        System.out.println(output);
        Log.getInstance().getLogEntries().add("[" + DateTime.getInstance().getTimeAsString() + "] " + output);
    }
}
