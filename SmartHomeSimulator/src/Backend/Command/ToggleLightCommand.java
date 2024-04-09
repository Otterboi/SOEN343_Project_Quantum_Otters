package Backend.Command;
import Backend.HouseLayout.House;
import Backend.HouseLayout.Room;
import Backend.Model.DateTime;
import Backend.Model.Log;

public class ToggleLightCommand implements Command {

    private Room room;
    private boolean isUser = true;

    public ToggleLightCommand (Room room){
        this.room = room;
    }

    @Override
    public void execute() {
        boolean newLightState = !room.isLightOn();
        room.setLightOn(newLightState);
        String output = "Light in " + room.getRoomName() + " is now " + (newLightState ? "ON" : "OFF");
        System.out.println(output);
        Log.getInstance().getLogEntriesConsole().add("[" + DateTime.getInstance().getTimeAsString() + "] " + output);
        Log.getInstance().getLogEntries().add(
                        "\n\n\nTimestamp: " + DateTime.getInstance().getTimeAndDateAsString()+
                        "\nEvent: Light Change" +
                        "\nLocation: " + room.getRoomName() +
                        "\nTriggered By: "  + (isUser? House.getLoggedInUser().getName() : "SHC") +
                        "\nEvent Details: Light is now " + (newLightState ? "ON" : "OFF")
        );
    }
}
