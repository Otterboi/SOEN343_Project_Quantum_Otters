package Backend.HouseLayout;

public class OutdoorRoom {

    private String roomName;
    private boolean hasLight;
    private boolean hasDoor;
    private boolean isLightOn;
    private boolean isDoorOpen;

    public OutdoorRoom(String roomName, boolean hasLight, boolean hasDoor){
        this.roomName = roomName;
        this.hasLight = hasLight;
        this.hasDoor = hasDoor;
        this.isDoorOpen = false;
        this.isLightOn = false;
    }

    public boolean isHasDoor() {
        return hasDoor;
    }

    public void setHasDoor(boolean hasDoor) {
        this.hasDoor = hasDoor;
    }

    public boolean isHasLight() {
        return hasLight;
    }

    public void setHasLight(boolean hasLight) {
        this.hasLight = hasLight;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
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
}
