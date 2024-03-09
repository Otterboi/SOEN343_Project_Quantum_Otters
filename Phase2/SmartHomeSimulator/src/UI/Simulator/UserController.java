package UI.Simulator;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class UserController implements Initializable {

    @FXML
        ListView<String> interactables;
    ObservableList<String> items = FXCollections.observableArrayList("a", "b","c","a", "b","c","a", "b","c","a", "b","c","a", "b","c","a", "b","c");


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interactables.setItems(items);
        // TODO
    }
}
