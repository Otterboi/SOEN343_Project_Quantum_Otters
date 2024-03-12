package Backend.HouseLayout;

import java.util.ArrayList;

public class OutdoorRoom extends Room{

    public OutdoorRoom(String roomName, boolean hasLight, boolean hasDoor){
        this.roomName = roomName;
        this.hasDoor = hasDoor;
        this.hasLight = hasLight;
        isPersonInRoom = false;
        this.peopleInRoom = new ArrayList<>();
        isLightOn = false;
        isDoorOpen = false;

    }


}
