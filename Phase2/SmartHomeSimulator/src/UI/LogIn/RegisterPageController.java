/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.LogIn;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author fan04
 */
public class RegisterPageController implements Initializable {

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
            sim = FXMLLoader.load(getClass().getResource("/UI/Simulator/SimulatorHome.fxml"));
            scene = new Scene(sim);
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        userTypes.getItems().addAll(types);
    }    
    
}
