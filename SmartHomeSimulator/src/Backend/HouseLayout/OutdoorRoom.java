package Backend.HouseLayout;

import java.util.ArrayList;

public class OutdoorRoom extends Room{
    private boolean isGarage;

    public OutdoorRoom(String roomName, boolean hasLight, boolean hasDoor){
        this.roomName = roomName;
        this.hasDoor = hasDoor;
        this.hasLight = hasLight;
        isPersonInRoom = false;
        this.peopleInRoom = new ArrayList<>();
        isLightOn = false;
        isDoorOpen = false;
        if(roomName.toLowerCase().equals("garage")){
            isGarage = true;
        }else{
            isGarage = false;
        }

    }


    public boolean isGarage() {
        return isGarage;
    }

    public void setGarage(boolean garage) {
        isGarage = garage;
    }
}
