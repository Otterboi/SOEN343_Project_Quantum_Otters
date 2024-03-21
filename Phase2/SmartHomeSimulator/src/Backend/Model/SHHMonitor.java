package Backend.Model;

import Backend.HouseLayout.House;
import Backend.HouseLayout.Zone;
import Backend.Observer.Observable;
import Backend.Observer.Observer;
import Backend.SimulatorMenu.SimulatorHome;

import java.util.ArrayList;
import java.util.Calendar;

public class SHHMonitor implements Observable {

    private ArrayList<Observer> observers = new ArrayList<>();
    private Zone zone;
    private double outsideTemp;
    private double currentZoneTemp;
    private double roomTemp;
    private double desiredZoneTemp;
    private DateTime dateTime = DateTime.getInstance();
    private SimulatorHome menu = SimulatorHome.getInstance();

    // When the season changes, get the season temp from zones and set it as new desired temp
    // heat/cool with respect to what it has to do based on now temp

    public SHHMonitor(Zone zone){
        this.outsideTemp = 20;
        this.currentZoneTemp = 20;
        this.desiredZoneTemp = 20;
        this.zone = zone;
        this.roomTemp = 20;
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

    public double getOutsideTemp() {
        return outsideTemp;
    }

    public void setOutsideTemp(int outsideTemp) {
        this.outsideTemp = outsideTemp;
        notifyObservers(this);
    }

    public double getRoomTemp() {
        return roomTemp;
    }

    public void setRoomTemp(int roomTemp) {
        this.roomTemp = roomTemp;
        notifyObservers(this);
    }

    public double getDesiredZoneTemp() {
        return desiredZoneTemp;
    }

    public void setDesiredZoneTemp(double desiredZoneTemp) {
        this.desiredZoneTemp = desiredZoneTemp;
        notifyObservers(this);
    }

    public double getCurrentZoneTemp() {
        return currentZoneTemp;
    }

    public void setCurrentZoneTemp(double currentZoneTemp) {
        this.currentZoneTemp = currentZoneTemp;
        notifyObservers(this);
    }

    public Zone getZone() {
        return zone;
    }
}
