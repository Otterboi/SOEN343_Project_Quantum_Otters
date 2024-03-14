package Backend.Command;
import Backend.HouseLayout.Room;
public class ToggleLightCommand implements Command {

    private Room room;

    public ToggleLightCommand (Room room){
        this.room = room;
    }

    @Override
    public void execute() {
        boolean newLightState = !room.isLightOn();
        room.setLightOn(newLightState);
        System.out.println("Light in " + room.getRoomName() + " is now " + (newLightState ? "ON" : "OFF")); //TODO: Set room name
    }
}
