package UI.Simulator;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Backend.HouseLayout.House;
import Backend.Users.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class UserController implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private ChoiceBox<String> userRole;
    @FXML
    private Label message;
    @FXML
    private Label usernameTaken;
    @FXML
    private Label mode;
    @FXML
    private AnchorPane userEditorPane;
    @FXML
    private Button createButton;
    @FXML
    private Button saveButton;


    @FXML
    ListView<String> userList;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String[] types = {"Parent", "Child", "Guest", "Stranger"};

    ObservableList<String> userLabels = FXCollections.observableArrayList();

    @FXML
    public void handleBack(ActionEvent event){
        try{
            root = FXMLLoader.load(getClass().getResource("/UI/Simulator/SimulatorHome.fxml"));
            scene = new Scene(root);
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("Simulator Home");
            stage.show();
        }catch(Exception error){
            System.out.println("Back Click Error Occurred!");
            System.out.println(error);
        }
    }

    @FXML
    public void AddUser(ActionEvent event){
        // Show Add mode label
        mode.setText("Adding New User");
        // Show right pane with empty text fields
        userEditorPane.setVisible(true);
        message.setVisible(false);
        usernameTaken.setVisible(false);
        username.setEditable(true);
        // Set fields to null
        username.setText("");
        password.setText("");
        userRole.setValue("");
    }

    @FXML
    public void EditUser(ActionEvent event){
        // Show Edit mode label
        mode.setText("Editing User");
        // Show right pane with loaded text fields
        userEditorPane.setVisible(true);
        usernameTaken.setVisible(false);
        message.setVisible(false);
        username.setEditable(false);

        String selectedUser = userList.getSelectionModel().getSelectedItem();
        username.setText(selectedUser);
        try {
            FileReader reader = new FileReader("src/Backend/Users/userProfiles.json");

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            reader.close();

            for (Object userObj : (JSONArray) jsonObject.get("users")) {
                JSONObject user = (JSONObject) userObj;
                String name = (String) user.get("name");
                if (name.equals(selectedUser)) {
                    password.setText((String) user.get("password"));
                    String role = (String) user.get("role");
                    String cap = role.substring(0, 1).toUpperCase() + role.substring(1);
                    userRole.setValue(cap);
                    break;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void DeleteUser(ActionEvent event) {
        // Get the selected user from the list
        String selectedUser = userList.getSelectionModel().getSelectedItem();

        try{
            // Read the JSON file
            FileReader fr = new FileReader("src/Backend/Users/userProfiles.json");
            JSONObject json = (JSONObject) new JSONParser().parse(fr);
            fr.close();

            // Get the JSON array containing the users
            JSONArray users = (JSONArray) json.get("users");

            // Remove the selected user from the JSON array
            for (int i = 0; i < users.size(); i++) {
                JSONObject userObj = (JSONObject) users.get(i);
                String username = (String) userObj.get("name");
                if (username.equals(selectedUser)) {
                    users.remove(i);
                    break;
                }
            }

            // Write the updated JSON back to the file
            FileWriter fw = new FileWriter("src/Backend/Users/userProfiles.json");
            fw.write(json.toJSONString());
            fw.flush();
            fw.close();

            // Remove the user from the list view
            userList.getItems().remove(selectedUser);

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void Save(ActionEvent event){
        try {
            FileReader fr = new FileReader("src/Backend/Users/userProfiles.json");
            JSONParser jsonParser = new JSONParser();
            JSONObject json = ((JSONObject) jsonParser.parse(fr));
            JSONArray users = (JSONArray) json.get("users");

            String userName = username.getText();
            String userPassword = password.getText();
            String userRoleString = userRole.getValue();

            boolean userExists = false;
            for (Object userObj : users) {
                JSONObject userJson = (JSONObject) userObj;
                String name = (String) userJson.get("name");
                if (name.equals(userName)) {
                    // Update existing user
                    userJson.put("password", userPassword);
                    userJson.put("role", userRoleString);
                    userExists = true;
                    break;
                }
            }

            if (!userExists) {
                // Add new user
                JSONObject newUserJson = new JSONObject();
                newUserJson.put("name", userName);
                newUserJson.put("password", userPassword);
                newUserJson.put("role", userRoleString);
                users.add(newUserJson);
            }

            fr.close();
            FileWriter fw = new FileWriter("src/Backend/Users/userProfiles.json");
            fw.write(json.toJSONString());
            fw.flush();
            fw.close();

            // Update UI
            if (!userExists) {
                // If it's a new user, update UI accordingly
                User user = new User(userName, userPassword, userRoleString);
                House.setLoggedInUser(user);
                House.addUser(user);
                userLabels.add(userName);
                userList.refresh();
            }
            message.setText("Saving successful!");
            usernameTaken.setVisible(false);
        } catch (Exception e) {
            message.setText("Saving failed!");
        }
        message.setVisible(true);
    }

    @FXML
    public void Cancel(ActionEvent event){
        userEditorPane.setVisible(false);
        usernameTaken.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       try {
           FileReader reader = new FileReader("src/Backend/Users/userProfiles.json");

           JSONParser jsonParser = new JSONParser();
           JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
           reader.close();

           for (Object userObj : (JSONArray) jsonObject.get("users")) {
               JSONObject user = (JSONObject) userObj;
               String name = (String) user.get("name");
               userLabels.add(name);
               userList.setItems(userLabels);
           }
       } catch(Exception e) {
           e.printStackTrace();
       }
           userEditorPane.setVisible(false);
           userRole.getItems().addAll(types);
           message.setVisible(false);

    }

}
