package Backend.HouseLayout;

import Backend.Model.DateTime;
import Backend.Model.Log;
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
    private boolean isUser = false;
    private boolean isMorning = false, isAfternoon = false, isNight = false;

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
            isAfternoon = false;
            isNight = false;
            setDesiredTemp(morningTemp);
            if(!isUser && isMorning == false){
                String output = "Temperature was set to morning conditions.";
                Log.getInstance().getLogEntriesConsole().add("[" + DateTime.getInstance().getTimeAsString() + "] " + output);
                Log.getInstance().getLogEntries().add(
                        "\n\n\nTimestamp: " + DateTime.getInstance().getTimeAndDateAsString()+
                                "\nEvent: Zone Temperature & Time State Change" +
                                "\nZone: " + name +
                                "\nTriggered By: SHH" +
                                "\nEvent Details: " + output
                );
            }isMorning = true;
        }else if (hour >= 12 && hour < 22){
            isMorning = false;
            isNight = false;
            setDesiredTemp(afternoonTemp);
            if(!isUser && isAfternoon == false){
                String output = "Temperature was set to afternoon conditions.";
                Log.getInstance().getLogEntriesConsole().add("[" + DateTime.getInstance().getTimeAsString() + "] " + output);
                Log.getInstance().getLogEntries().add(
                        "\n\n\nTimestamp: " + DateTime.getInstance().getTimeAndDateAsString()+
                                "\nEvent: Zone Temperature & Time State Change" +
                                "\nZone: " + name +
                                "\nTriggered By: SHH" +
                                "\nEvent Details: " + output
                );
            }

            isAfternoon = true;
        }else{
            isMorning = false;
            isAfternoon = false;
            setDesiredTemp(nightTemp);
            if(isUser == false && isNight == false){

                String output = "Temperature was set to night conditions.";
                Log.getInstance().getLogEntriesConsole().add("[" + DateTime.getInstance().getTimeAsString() + "] " + output);
                Log.getInstance().getLogEntries().add(
                        "\n\n\nTimestamp: " + DateTime.getInstance().getTimeAndDateAsString()+
                                "\nEvent: Zone Temperature & Time State Change" +
                                "\nZone: " + name +
                                "\nTriggered By: SHH" +
                                "\nEvent Details: " + output
                );
            }

        isNight = true;
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
        //Not logging since I'm assuming this is a SIMULATION setting, rather than something that any of the modules can actually do.
        this.currentTemp = currentTemp;
    }

    public float getDesiredTemp() {
        return desiredTemp;
    }

    public void setDesiredTemp(float desiredTemp) {
        this.desiredTemp = desiredTemp;

    }

    public void setUser(boolean b){
        isUser = b;
    }
}