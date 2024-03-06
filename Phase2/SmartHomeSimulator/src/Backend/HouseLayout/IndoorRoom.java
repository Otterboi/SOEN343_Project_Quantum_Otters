package Backend.HouseLayout;

public class IndoorRoom {
    private String roomName;
    private boolean hasWindow;
    private boolean hasLight;
    private boolean hasDoor;
    private boolean isWindowOpen;
    private boolean isLightOn;
    private boolean isDoorOpen;
    private boolean isWindowBlocked;

    public IndoorRoom(String roomName, boolean hasWindow, boolean hasDoor, boolean hasLight){
        this.roomName = roomName;
        this.hasDoor = hasDoor;
        this.hasLight = hasLight;
        this.hasWindow = hasWindow;
        isWindowBlocked = false;
        isLightOn = false;
        isDoorOpen = false;
        isWindowOpen = false;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public boolean isHasWindow() {
        return hasWindow;
    }

    public void setHasWindow(boolean hasWindow) {
        this.hasWindow = hasWindow;
    }

    public boolean isHasLight() {
        return hasLight;
    }

    public void setHasLight(boolean hasLight) {
        this.hasLight = hasLight;
    }

    public boolean isHasDoor() {
        return hasDoor;
    }

    public void setHasDoor(boolean hasDoor) {
        this.hasDoor = hasDoor;
    }

    public boolean isWindowOpen() {
        return isWindowOpen;
    }

    public void setWindowOpen(boolean windowOpen) {
        isWindowOpen = windowOpen;
    }

    public boolean isLightOn() {
        return isLightOn;
    }

    public void setLightOn(boolean lightOn) {
        isLightOn = lightOn;
    }

    public boolean isDoorOpen() {
        return isDoorOpen;
    }

    public void setDoorOpen(boolean doorOpen) {
        isDoorOpen = doorOpen;
    }

    public boolean isWindowBlocked() {
        return isWindowBlocked;
    }

    public void setWindowBlocked(boolean windowBlocked) {
        isWindowBlocked = windowBlocked;
    }
}
