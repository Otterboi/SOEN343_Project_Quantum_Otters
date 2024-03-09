/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;


import Backend.HouseLayout.IndoorRoom;
import Backend.HouseLayout.OutdoorRoom;
import Backend.Users.User;
import com.sun.source.tree.WhileLoopTree;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import Backend.HouseLayout.House;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author fan04
 */
public class SmartHomeSimulator extends Application {
    
    

    public SmartHomeSimulator() {
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/UI/LogIn/LogInPage.fxml"));
        
        Scene scene = new Scene(root);
        stage.setResizable(false);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParseException {
        House.getInstance();
        FileReader fr = new FileReader("src/Backend/HouseLayout/houseLayout.json");
        Object obj = new JSONParser().parse(fr);
        JSONObject json = (JSONObject) obj;

        JSONArray indoorRooms = (JSONArray) json.get("indoor");
        JSONArray outdoorRooms = (JSONArray) json.get("outdoor");

        for(int i = 0; i < indoorRooms.size(); i++){
            JSONObject items = (JSONObject) indoorRooms.get(i);
            House.addIndoorRoom(new IndoorRoom((String) items.get("name"), (boolean) items.get("hasWindows"), (boolean) items.get("hasDoor"), (boolean) items.get("hasLight")));
        }

        for(int i = 0; i < outdoorRooms.size(); i++){
            JSONObject items = (JSONObject) indoorRooms.get(i);
            House.addOutdoorRoom(new OutdoorRoom((String) items.get("name"), (boolean) items.get("hasLight"), (boolean) items.get("hasDoor")));
        }

        fr.close();

        FileReader fr2 = new FileReader("src/Backend/Users/userProfiles.json");

        JSONObject json2 = ((JSONObject) new JSONParser().parse(fr2));
        JSONArray users = (JSONArray) json2.get("users");

        for (int i = 0; i < users.size(); i++){
            System.out.println(i);
            JSONObject items = (JSONObject) users.get(i);
            House.addUser(new User((String) items.get("name"), (String) items.get("password"), (String) items.get("role")));
        }
        launch(args);
    }

}
