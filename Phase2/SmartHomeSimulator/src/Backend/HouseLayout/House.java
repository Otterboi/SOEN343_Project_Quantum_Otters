package Backend.HouseLayout;


import Backend.Users.User;

import java.util.ArrayList;

public class House {
    private static ArrayList<IndoorRoom> indoorRooms;
    private static ArrayList<OutdoorRoom> outdoorRooms;

    private static ArrayList<User> users;
    private static House INSTANCE;

    private House(){
        indoorRooms = new ArrayList<>();
        outdoorRooms = new ArrayList<>();
        users = new ArrayList<>();
    }

    public static House getInstance(){
        if(INSTANCE == null){
            INSTANCE = new House();
        }

        return INSTANCE;
    }

    public static ArrayList<IndoorRoom> getIndoorRooms() {
        return indoorRooms;
    }

    public static ArrayList<OutdoorRoom> getOutdoorRooms() {
        return outdoorRooms;
    }

    public static void addOutdoorRoom(OutdoorRoom outdoorRoom) {
        outdoorRooms.add(outdoorRoom);
    }

    public static void addIndoorRoom(IndoorRoom indoorRoom) {
        indoorRooms.add(indoorRoom);
    }

    public static void addUser(User user){ users.add(user); }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<User> users) {
        House.users = users;
    }
}
