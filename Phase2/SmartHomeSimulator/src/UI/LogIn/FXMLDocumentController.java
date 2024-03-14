package UI.LogIn;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Backend.HouseLayout.House;
import Backend.Users.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {
    private Parent sim;
    Stage stage;
    Scene scene;
    @FXML
    private Button logInButton, signUpButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private AnchorPane loginPane;
    @FXML
    private Label errorLabel;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        for (User u : House.getUsers()) {
            if (usernameField.getText().equals(u.getName())) {
                if (passwordField.getText().equals(u.getPassword())) {
                    House.setLoggedInUser(u);
                    sim = FXMLLoader.load(getClass().getResource("/UI/Simulator/SimulatorHome.fxml"));
                    scene = new Scene(sim);
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setResizable(false);
                    stage.setScene(scene);
                    stage.show();
                    stage.setTitle("Simulator Home");
                    break;
                } else {
                    errorLabel.setText("Wrong password!");
                    errorLabel.setVisible(true);
                    break;
                }
            } else {
                errorLabel.setText("User not found!");
                errorLabel.setVisible(true);
            }
        }

    }


    @FXML
    private void handleSignUpButton(ActionEvent event) {
        try {
            sim = FXMLLoader.load(getClass().getResource("/UI/LogIn/RegisterPage.fxml"));
            scene = new Scene(sim);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();


        } catch (Exception e) {
            System.out.println("Sign up button failed lmao");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        errorLabel.setVisible(false);

    }

}
