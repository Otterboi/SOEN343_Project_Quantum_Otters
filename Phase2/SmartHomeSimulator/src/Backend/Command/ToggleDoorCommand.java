package Backend.Command;
import Backend.HouseLayout.Room;

public class ToggleDoorCommand implements Command{
    private Room room;
    public ToggleDoorCommand(Room room){
        this.room = room;
    }
    @Override
    public void execute() {
        boolean newDoorState = !room.isDoorOpen();
        room.setDoorOpen(newDoorState);
        System.out.println("Door in " + room.getRoomName() + " is now " + (newDoorState? "OPEN" : "CLOSED"));
    }
}
