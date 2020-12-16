package app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {
    @FXML private Button homeButton;
    @FXML private Button accountButton;
    @FXML private Button signOutButton;
    private static final String HOVER_BUTTON_STYLE = "fx-background-color: #ff84a7; color: white;";
    private static final String IDLE_BUTTON_STYLE = "fx-background-color: white;";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeButton.setOnMouseEntered(e -> homeButton.setStyle(HOVER_BUTTON_STYLE));
        homeButton.setOnMouseExited(e -> homeButton.setStyle(IDLE_BUTTON_STYLE));

        accountButton.setOnMouseEntered(e -> accountButton.setStyle(HOVER_BUTTON_STYLE));
        accountButton.setOnMouseExited(e -> accountButton.setStyle(IDLE_BUTTON_STYLE));

        signOutButton.setOnMouseEntered(e -> signOutButton.setStyle(HOVER_BUTTON_STYLE));
        signOutButton.setOnMouseExited(e -> signOutButton.setStyle(IDLE_BUTTON_STYLE));
    }

    @FXML
    public void handleSetHome(ActionEvent event) {
        System.out.println("go home");
    }

    @FXML
    public void handleAccount(ActionEvent event) {
        System.out.println("go account");
    }

    @FXML
    public void handleLogout(ActionEvent event) {
        System.out.println("sign out");
    }

}
