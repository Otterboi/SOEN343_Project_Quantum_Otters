package Backend.SimulatorMenu;

import java.util.ArrayList;
import Backend.Observer.*;

public class SimulatorHome implements Observable {

    private ArrayList<Observer> observers = new ArrayList<>();
    private String date, time, temp, user, room;
    private static SimulatorHome instance;

    private SimulatorHome(String date, String time, String temp, String user, String room){
        this.date = date;
        this.time = time;
        this.temp = temp;
        this.user = user;
        this.room = room;
    }

    public static SimulatorHome getInstance(){
        if(instance == null){
            instance = new SimulatorHome("2024-01-01","12:59","0C","User","Room");
        }

        return instance;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
        notifyObservers(this);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
        notifyObservers(this);
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
        notifyObservers(this);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
        notifyObservers(this);
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
        notifyObservers(this);
    }
}
