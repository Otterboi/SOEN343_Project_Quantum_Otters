package Backend.HouseLayout;

import Backend.Model.DateTime;
import Backend.Model.Log;
import Backend.Observer.Observable;
import Backend.Observer.Observer;
import Backend.SimulatorMenu.SimulatorHome;

import java.util.ArrayList;
import java.util.List;

public abstract class Room implements Observable {
    protected String roomName;
    protected boolean hasLight;
    protected boolean hasDoor;
    protected boolean isLightOn;
    protected boolean isDoorOpen;
    protected boolean isPersonInRoom;
    protected List<String> peopleInRoom = new ArrayList<>();
    protected boolean autoModeEnabled = false;
    private ArrayList<Observer> observers = new ArrayList<>();
    protected float temp, overwritingTemp;
    protected Zone zone;
    protected boolean isCooling, isHeating, isOff, isOverwritingTemp = false;
    protected boolean isTempDecaying = false;

    public boolean isTempDecaying(){
        return this.isTempDecaying;
    }

    public boolean isOverwritingTemp(){
        return this.isOverwritingTemp;
    }

    public void setOverwritingTemp(boolean isOverwritingTemp){
        this.isOverwritingTemp = isOverwritingTemp;
        notifyObservers(this);
    }

    public float getOverwritigTemp(){
        return this.overwritingTemp;
    }

    public void setOverwritingTemp(float overwritingTemp){
        this.overwritingTemp = overwritingTemp;
        notifyObservers(this);
    }

    public void setTempDecaying(boolean isTempDecaying){
        this.isTempDecaying = isTempDecaying;
    }

    public void setCooling(boolean isCooling) {
        this.isHeating = false;
        this.isOff = false;
        this.isCooling = isCooling;
        notifyObservers(this);
    }

    public boolean isCooling() {
        return this.isCooling;
    }

    public boolean isHeating() {
        return this.isHeating;
    }

    public boolean isOff() {
        return this.isOff;
    }

    public void setHeating(boolean isHeating) {
        this.isCooling = false;
        this.isOff = false;
        this.isHeating = isHeating;
        notifyObservers(this);
    }

    public void setOff(boolean isOff) {
        this.isHeating = false;
        this.isCooling = false;
        this.isOff = isOff;
        notifyObservers(this);
    }

    public Zone getZone() {
        return this.zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
        this.zone.setCurrentTemp(SimulatorHome.getInstance().getTemp());
        this.temp = zone.getCurrentTemp();
        notifyObservers(this);
    }

    public void deleteZone(Zone zone){
        this.zone = null;
        this.temp = 0f;
        notifyObservers(this);
    }

    public float getTemp() {
        return this.temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
        notifyObservers(this);
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
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

    public boolean isLightOn() {
        return isLightOn;
    }

    public void setLightOn(boolean lightOn) {
        if (this.isLightOn != lightOn) {
            this.isLightOn = lightOn;
            notifyObservers(this);
        }
    }

    public boolean isDoorOpen() {
        return isDoorOpen;
    }

    public void setDoorOpen(boolean doorOpen) {
        isDoorOpen = doorOpen;
        notifyObservers(this);
    }

    public boolean isPersonInRoom() {
        return isPersonInRoom;
    }

    public void setPersonInRoom(boolean personInRoom, String person) {
        isPersonInRoom = personInRoom;
        this.peopleInRoom.add(person);
        if (this.autoModeEnabled && !this.peopleInRoom.isEmpty()) {
            setLightOn(true);
        } else if (this.autoModeEnabled && this.peopleInRoom.isEmpty()) {
            setLightOn(false);
        }
        notifyObservers(this);
    }

    public boolean isRoomEmpty(){
        return this.peopleInRoom.isEmpty();
    }

    public void removePersonInRoom(String person) {
        this.peopleInRoom.remove(person);

        if (this.peopleInRoom.isEmpty()) {
            this.isPersonInRoom = false;
            if (this.autoModeEnabled) {
                setLightOn(false);
            }
            this.notifyObservers(this);
        }
        this.notifyObservers(this);
    }

    public List<String> getPeopleInRoom() {
        return this.peopleInRoom;
    }

    @Override
    public void attachObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void detachObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(Observable o) {
        for (Observer observer : observers) {
            observer.update(o);
        }
    }

    public ArrayList<Observer> getObservers() {
        return observers;
    }

    public boolean isAutoModeEnabled() {
        return autoModeEnabled;
    }

    public void setAutoModeEnabled(boolean autoModeEnabled) {
        this.autoModeEnabled = autoModeEnabled;
        notifyObservers(this);
        String output = "Automode is " + (autoModeEnabled? "enabled." : "disabled.") + "in " + roomName;
        Log.getInstance().getLogEntriesConsole().add("[" + DateTime.getInstance().getTimeAsString() + "] " + output);
        Log.getInstance().getLogEntries().add(
                "\n\n\nTimestamp: " + DateTime.getInstance().getTimeAndDateAsString()+
                        "\nEvent: Door State Change" +
                        "\nLocation: " + roomName +
                        "\nTriggered By: " +House.getLoggedInUser().getName()+
                        "\nEvent Details: " + output
        );
    }
    @Override
    public String toString() {
        return this.roomName;
    }

}
