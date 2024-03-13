package Backend.HouseLayout;

import Backend.Observer.Observable;
import Backend.Observer.Observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Room implements Observable {
    protected String roomName;
    protected boolean hasLight;
    protected boolean hasDoor;
    protected boolean isLightOn;
    protected boolean isDoorOpen;

    protected boolean isPersonInRoom;
    protected List<String> peopleInRoom;
    protected boolean autoModeEnabled = false;
    private ArrayList<Observer> observers = new ArrayList<>();

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
        if(this.isLightOn!=lightOn){
            this.isLightOn = lightOn;
            notifyObservers(this);
        }
        /*isLightOn = lightOn;
        notifyObservers(this);*/
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
        if (this.autoModeEnabled && !this.peopleInRoom.isEmpty()){
            setLightOn(true);
        }
        else if (this.autoModeEnabled && this.peopleInRoom.isEmpty()){
            setLightOn(false);
        }
        notifyObservers(this);
    }

    public void removePersonInRoom(String person){
        this.peopleInRoom.remove(person);

        if(this.peopleInRoom.isEmpty()){
            this.isPersonInRoom = false;
            if(this.autoModeEnabled){
                setLightOn(false);
            }
            this.notifyObservers(this);
        }
    }

    public List<String> getPeopleInRoom(){
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

    public ArrayList<Observer> getObservers(){
        return observers;
    }

    public boolean isAutoModeEnabled(){
        return autoModeEnabled;
    }

    public void setAutoModeEnabled(boolean autoModeEnabled){
        this.autoModeEnabled = autoModeEnabled;
        notifyObservers(this);
    }
}
