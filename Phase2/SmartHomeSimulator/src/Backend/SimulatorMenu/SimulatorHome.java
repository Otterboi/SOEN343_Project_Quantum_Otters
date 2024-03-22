package Backend.SimulatorMenu;

import java.util.ArrayList;
import Backend.HouseLayout.House;
import Backend.Observer.*;
import Backend.Users.User;

public class SimulatorHome implements Observable {

    private ArrayList<Observer> observers = new ArrayList<>();
    private String date, time, user, room;
    private double temp;
    private static SimulatorHome instance;

    private SimulatorHome(String date, String time, double temp, String user, String room){
        this.date = date;
        this.time = time;
        this.temp = temp;
        this.user = user;
        this.room = room;
    }

    public static SimulatorHome getInstance(){
        if(instance == null){
            instance = new SimulatorHome("2024-01-01","00:00:00",20, House.getLoggedInUser().getName(), "Remote");
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

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
        notifyObservers(this);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;

        for(User userList: House.getUsers()){
            if(userList.getName().equals(user)){
                House.setLoggedInUser(userList);
            }
        }

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
