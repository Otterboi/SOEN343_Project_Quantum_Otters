/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.LogIn;

import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.util.ResourceBundle;

import Backend.HouseLayout.House;
import Backend.Users.User;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * FXML Controller class
 *
 * @author fan04
 */
public class RegisterPageController implements Initializable {
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Button registerButton;
    @FXML
    public Label errorLabel;
    /**
     * Initializes the controller class.
     */
    
    private Parent sim;
    Stage stage;
    Scene scene;
    @FXML
    
    private ChoiceBox<String> userTypes;
    
    private String[] types = {"Parent", "Child", "Guest", "Stranger"};
    
    
    @FXML
    private void signUpLogIn(ActionEvent event){
        try {
            for(User u : House.getUsers()){
                if(usernameField.getText().equals(u.getName())){
                    throw new Exception();
                }
            }
            FileReader fr2 = new FileReader("src/Backend/Users/userProfiles.json");

            JSONObject json2 = ((JSONObject) new JSONParser().parse(fr2));
            JSONArray users = (JSONArray) json2.get("users");
            User user = new User(usernameField.getText(), passwordField.getText(), userTypes.getValue());
            JSONObject jsonUser = new JSONObject();
            jsonUser.put("name", user.getName());
            jsonUser.put("password", user.getPassword());
            jsonUser.put("role", user.getRoleString());
            users.add(jsonUser);

            fr2.close();
            FileWriter fw = new FileWriter("src/Backend/Users/userProfiles.json");
            fw.write(json2.toJSONString());
            fw.flush();
            fw.close();

            House.setLoggedInUser(user);
            House.addUser(user);

            sim = FXMLLoader.load(getClass().getResource("/UI/Simulator/SimulatorHome.fxml"));
            scene = new Scene(sim);
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            errorLabel.setText("User already exists!");
            errorLabel.setVisible(true);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        userTypes.getItems().addAll(types);
        errorLabel.setVisible(false);
    }    
    
}
