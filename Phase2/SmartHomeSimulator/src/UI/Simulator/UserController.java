package UI.Simulator;


import java.net.URL;
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
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class UserController implements Initializable {

    @FXML
    ListView<String> userList;
    private Stage stage;
    private Scene scene;
    private Parent root;

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

    }

    @FXML
    public void EditUser(ActionEvent event){

    }

    @FXML
    public void DeleteUser(ActionEvent event){

    }

    @FXML
    public void Save(ActionEvent event){

    }

    @FXML
    public void Cancel(ActionEvent event){

    }

    @FXML
    public void Grant(ActionEvent event){

    }

    @FXML
    public void Deny(ActionEvent event){

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for(User u : House.getUsers()){
            userLabels.add(u.getName());
        }
        userList.setItems(userLabels);

    }

}
