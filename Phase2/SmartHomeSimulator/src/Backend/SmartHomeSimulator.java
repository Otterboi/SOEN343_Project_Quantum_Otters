package Backend;


import Backend.HouseLayout.IndoorRoom;
import Backend.HouseLayout.OutdoorRoom;
import Backend.Users.User;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import Backend.HouseLayout.House;

import java.io.FileReader;

public class SmartHomeSimulator extends Application {

    public SmartHomeSimulator() {
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/UI/LogIn/LogInPage.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/UI/SmartHomeModules/styles.css").toExternalForm());

        stage.setResizable(false);
        
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                System.out.println("This is closed");
            }
        });
    }

    public static void main(String[] args) throws Exception {
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
            JSONObject items = (JSONObject) outdoorRooms.get(i);
            House.addOutdoorRoom(new OutdoorRoom((String) items.get("name"), (boolean) items.get("hasLight"), (boolean) items.get("hasDoor")));
        }

        fr.close();

        FileReader fr2 = new FileReader("src/Backend/Users/userProfiles.json");

        JSONObject json2 = ((JSONObject) new JSONParser().parse(fr2));
        JSONArray users = (JSONArray) json2.get("users");

        for (int i = 0; i < users.size(); i++) {
            JSONObject items = (JSONObject) users.get(i);
            House.addUser(new User((String) items.get("name"), (String) items.get("password"), (String) items.get("role")));
        }

        fr2.close();
        launch(args);
    }

}
