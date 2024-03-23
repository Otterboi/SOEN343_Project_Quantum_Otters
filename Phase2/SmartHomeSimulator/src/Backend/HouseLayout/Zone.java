package Backend.HouseLayout;

import Backend.Model.DateTime;
import Backend.Observer.Observable;
import Backend.Observer.Observer;
import Backend.SimulatorMenu.SimulatorHome;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

public class Zone {

    private final String name;
    private ArrayList<Room> rooms = new ArrayList<>();
    private float currentTemp;
    private float desiredTemp;
    private float nightTemp;
    private float morningTemp;
    private float afternoonTemp;
    private boolean overwritten;
    private float overwrittenTemp;
    private boolean isSummer;

    public Zone(String name, Set<Room> selectedRooms) {
        this.name = name;
        this.rooms = new ArrayList<>(selectedRooms);
        // Default temperatures
        nightTemp = 20;
        morningTemp = 20;
        afternoonTemp = 20;
        overwritten = false;
        overwrittenTemp = 0;
        currentTemp = SimulatorHome.getInstance().getTemp();
        isSummer = false;
        updateDesiredTemp();
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public boolean isSummer(){
        int month = DateTime.getInstance().getDate().get(Calendar.MONTH);

        if(month <= 8 && month >= 5){
            isSummer = true;
        }

        return isSummer;
    }

    public void updateDesiredTemp(){
        int hour = Integer.parseInt(DateTime.getInstance().getTimeAsString().split(":")[0]);
        if(hour >= 5 && hour < 12){
            setDesiredTemp(morningTemp);
        }else if (hour >= 12 && hour < 22){
            setDesiredTemp(afternoonTemp);
        }else{
            setDesiredTemp(nightTemp);
        }

    }

    public void addRoom(Room room) {
        this.rooms.add(room);
    }

    public void removeRoom(Room room) {
        this.rooms.remove(room);
    }

    public float getNightTemp() {
        return nightTemp;
    }

    public void setNightTemp(float nightTemp) {
        this.nightTemp = nightTemp;
        updateDesiredTemp();
    }

    public float getMorningTemp() {
        return morningTemp;
    }

    public void setMorningTemp(float morningTemp) {
        this.morningTemp = morningTemp;
        updateDesiredTemp();
    }

    public float getAfternoonTemp() {
        return afternoonTemp;
    }

    public void setAfternoonTemp(float afternoonTemp) {
        this.afternoonTemp = afternoonTemp;
        updateDesiredTemp();
    }

    public float getOverwrittenTemp() {
        return overwrittenTemp;
    }

    public void setOverwrittenTemp(float overwrittenTemp) {
        this.overwrittenTemp = overwrittenTemp;
        overwritten = true;
    }

    public boolean getOverwritten() {
        return overwritten;
    }

    public void setOverwritten(boolean overwritten) {
        this.overwritten = overwritten;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }

    public float getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(float currentTemp) {
        this.currentTemp = currentTemp;
    }

    public float getDesiredTemp() {
        return desiredTemp;
    }

    public void setDesiredTemp(float desiredTemp) {
        this.desiredTemp = desiredTemp;
    }
}