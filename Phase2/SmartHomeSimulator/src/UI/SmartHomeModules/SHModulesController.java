/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.SmartHomeModules;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author fan04
 */
public class SHModulesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    ListView<String> interactables;
    ObservableList<String> items = FXCollections.observableArrayList("a", "b", "c");


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        interactables.setItems(items);


    }    
    
}
