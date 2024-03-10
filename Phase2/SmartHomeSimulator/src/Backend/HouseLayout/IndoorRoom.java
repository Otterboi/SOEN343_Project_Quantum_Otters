package Backend.HouseLayout;

import Backend.Observer.Observer;
import java.util.ArrayList;
import java.util.List;
import Backend.Observer.Observable;
import Backend.Observer.Observer;
import java.util.ArrayList;
import java.util.List;

public class IndoorRoom implements Observable {
    private String roomName;
    private boolean hasWindow;
    private boolean hasLight;
    private boolean hasDoor;
    private boolean isWindowOpen;
    private boolean isLightOn;
    private boolean isDoorOpen;
    private boolean isWindowBlocked;

    private boolean isPersonInRoom;
    private List<String> peopleInRoom;

    private ArrayList<Observer> observers = new ArrayList<>();


    public IndoorRoom(String roomName, boolean hasWindow, boolean hasDoor, boolean hasLight) {
        this.roomName = roomName;
        this.hasDoor = hasDoor;
        this.hasLight = hasLight;
        this.hasWindow = hasWindow;

        this.peopleInRoom = new ArrayList<>();
        isPersonInRoom = false;

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

    public boolean isPersonInRoom() {
        return isPersonInRoom;
    }

    public void setPersonInRoom(boolean personInRoom, String person) {
        isPersonInRoom = personInRoom;
        this.peopleInRoom.add(person);
        notifyObservers(this);
    }

    public void removePersonInRoom(String person){
        this.peopleInRoom.remove(person);

        if(this.peopleInRoom.isEmpty()){
            this.isPersonInRoom = false;
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
}
