package Backend.HouseLayout;


import Backend.Users.User;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class House {
    private static ArrayList<IndoorRoom> indoorRooms;
    private static ArrayList<OutdoorRoom> outdoorRooms;
    private static ArrayList<Room> rooms;
    private static ArrayList<Zone> zones;

    private static ArrayList<User> users;
    private static House INSTANCE;
    private static User loggedInUser;
    private static double summerTemperature;
    private static double winterTemperature;
    private static double avgTemp;
    private static boolean isSHHOn;
    private static boolean tooCold = false;

    private static boolean isHouseEmpty;
    private static boolean isAway;

    private House(){
        indoorRooms = new ArrayList<>();
        outdoorRooms = new ArrayList<>();
        zones = new ArrayList<>();
        users = new ArrayList<>();
        loggedInUser = null;
        rooms = new ArrayList<>();
        isSHHOn = true;
        isHouseEmpty = true;
    }

    public static House getInstance(){
        if(INSTANCE == null){
            INSTANCE = new House();
        }

        return INSTANCE;
    }

    public static boolean isHouseEmpty(){
        for(Room room: rooms){
            if(!room.isRoomEmpty()){
                isHouseEmpty = false;
                break;
            }else{
                isHouseEmpty = true;
            }
        }

        return isHouseEmpty;
    }
    public static boolean addZone(Zone newZone) {
        //prevent adding duplicate zones
        for (Zone zone : zones) {
            if (zone.getName().equalsIgnoreCase(newZone.getName())) {
                return false;
            }
        }
        zones.add(newZone);
        return true;
    }
    public static boolean removeZone(String zoneName) {
        return zones.removeIf(zone -> zone.getName().equals(zoneName));
    }


    public static ArrayList<Zone> getZones() {
        return zones;
    }

    public static ArrayList<IndoorRoom> getIndoorRooms() {
        return indoorRooms;
    }

    public static ArrayList<OutdoorRoom> getOutdoorRooms() {
        return outdoorRooms;
    }

    public static void addOutdoorRoom(OutdoorRoom outdoorRoom) {
        outdoorRooms.add(outdoorRoom);
        rooms.add(outdoorRoom);
    }

    public static void addIndoorRoom(IndoorRoom indoorRoom) {
        indoorRooms.add(indoorRoom);
        rooms.add(indoorRoom);
    }

    public static void addUser(User user){ users.add(user); }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<User> users) {
        House.users = users;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User u) {
        loggedInUser = u;
    }

    public static ArrayList<Room> getRooms(){
        return rooms;
    }

    public static void setSummerTemperature(double temp) {
        summerTemperature = temp;
    }

    public static double getSummerTemperature() {
        return summerTemperature;
    }

    public static void setWinterTemperature(double temp) {
        winterTemperature = temp;
    }

    public static double getWinterTemperature() {
        return winterTemperature;
    }

    public static double getAvgTemp(){
        double avgTemp = 0;
        for (Zone zone: zones){
            avgTemp += zone.getCurrentTemp();
        }

        return avgTemp/zones.size();
    }

    public static boolean isSHHOn() {
        return isSHHOn;
    }

    public static void setSHHOn(boolean isSHHOn) {
        House.isSHHOn = isSHHOn;
    }

    public static boolean isTooCold() {
        return tooCold;
    }

    public static void setTooCold(boolean tooCold) {
        House.tooCold = tooCold;
    }

    public static boolean isAway() {
        return isAway;
    }

    public static void setIsAway(boolean isAway) {
        House.isAway = isAway;
    }
}
