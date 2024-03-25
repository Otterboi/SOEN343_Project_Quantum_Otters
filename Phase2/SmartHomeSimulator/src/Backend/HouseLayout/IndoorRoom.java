package Backend.HouseLayout;

import Backend.Model.DateTime;
import Backend.Model.Log;

import java.util.ArrayList;

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
        notifyObservers(this);
        String output = "Window in " + roomName + " is now " + ((isWindowBlocked == true) ? "BLOCKED" : "UNBLOCKED");
        System.out.println(output);
        Log.getInstance().getLogEntriesConsole().add("[" + DateTime.getInstance().getTimeAsString() + "] " + output);
        Log.getInstance().getLogEntries().add(
                "\n\n\nTimestamp: " + DateTime.getInstance().getTimeAndDateAsString()+
                        "\nEvent: Window Block State Change" +
                        "\nLocation: " + roomName +
                        "\nTriggered By: " + House.getLoggedInUser().getName() +
                        "\nEvent Details: Window is now " + ((isWindowBlocked == true) ? "BLOCKED" : "UNBLOCKED")
        );
    }


}
