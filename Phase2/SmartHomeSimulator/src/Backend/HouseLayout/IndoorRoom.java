package Backend.HouseLayout;

import Backend.Observer;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class IndoorRoom implements Observable{
    private String roomName;
    private boolean hasWindow;
    private boolean hasLight;
    private boolean hasDoor;
    private boolean isWindowOpen;
    private boolean isLightOn;
    private boolean isDoorOpen;
    private boolean isWindowBlocked;
    private boolean isPersonInRoom;

    private ArrayList<Observer> observers = new ArrayList<>();






    public IndoorRoom(String roomName, boolean hasWindow, boolean hasDoor, boolean hasLight){
        this.roomName = roomName;
        this.hasDoor = hasDoor;
        this.hasLight = hasLight;
        this.hasWindow = hasWindow;
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
        notifyObservers(this);
    }

    public boolean isLightOn() {
        return isLightOn;
    }

    public void setLightOn(boolean lightOn) {
        isLightOn = lightOn;
        notifyObservers(this);
    }

    public boolean isDoorOpen() {
        return isDoorOpen;
    }

    public void setDoorOpen(boolean doorOpen) {
        isDoorOpen = doorOpen;
        notifyObservers(this);
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

    public void setPersonInRoom(boolean personInRoom) {
        isPersonInRoom = personInRoom;
        notifyObservers(this);
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
    for(Observer observer : observers){
        observer.update(o);
    }
    }


}
