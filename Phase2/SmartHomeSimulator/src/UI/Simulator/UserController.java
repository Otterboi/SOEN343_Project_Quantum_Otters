package UI.Simulator;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class UserController implements Initializable {

    @FXML
        ListView<String> interactables;
    private Stage stage;
    ObservableList<String> items = FXCollections.observableArrayList("a", "b","c","a", "b","c","a", "b","c","a", "b","c","a", "b","c","a", "b","c");


    @FXML
    public void handleBack(ActionEvent e){
        try{
            Parent usr = FXMLLoader.load(getClass().getResource("/UI/Simulator/SimulatorHome.fxml"));
            Scene scene = new Scene(usr);
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("Simulator Home");
            stage.show();
        }catch(Exception error){
            System.out.println("Back Click Error Occurred!");
            System.out.println(error);
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interactables.setItems(items);
        // TODO
    }
}
