package Backend.HouseLayout;

import java.util.ArrayList;
import java.util.List;
import Backend.Observer.*;

public class IndoorRoom extends Room{

    private boolean isWindowBlocked;
    protected boolean isWindowOpen;
    protected boolean hasWindow;


    public IndoorRoom(String roomName, boolean hasWindow, boolean hasDoor, boolean hasLight) {
        this.roomName = roomName;
        this.hasDoor = hasDoor;
        this.hasLight = hasLight;
        this.hasWindow = hasWindow;
        isPersonInRoom = false;
        this.peopleInRoom = new ArrayList<>();
        isWindowBlocked = false;
        isLightOn = false;
        isDoorOpen = false;
        isWindowOpen = false;
    }



    public boolean isHasWindow() {
        return hasWindow;
    }

    public void setHasWindow(boolean hasWindow) {
        this.hasWindow = hasWindow;
    }



    public boolean isWindowOpen() {
        return isWindowOpen;
    }

    public void setWindowOpen(boolean windowOpen) {
        isWindowOpen = windowOpen;
        notifyObservers(this);
    }



    public boolean isWindowBlocked() {
        return isWindowBlocked;
    }

    public void setWindowBlocked(boolean windowBlocked) {
        isWindowBlocked = windowBlocked;
    }


}
