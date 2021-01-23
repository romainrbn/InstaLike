package app.controller;

import app.views.EditAccountView;
import app.views.LoginView;
import app.views.MainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;

import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {

    @FXML private Button homeButton;
    @FXML private Button signOutButton;
    @FXML private Button addButton;
    @FXML private Button accountButton;
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

        addButton.setOnMouseEntered(e -> addButton.setStyle(HOVER_BUTTON_STYLE));
        addButton.setOnMouseExited(e -> addButton.setStyle(IDLE_BUTTON_STYLE));
    }

    @FXML
    public void handleMorePosts() throws Exception {
        MainView.afficherPosts();
    }

    @FXML
    public void handleAccount() {

        try {
            Helpers.runAnotherApp(EditAccountView.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAddPicture() throws IOException {
        Helpers.openPopUp("AddPostView");
    }

    @FXML
    public void handleLogout(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage sourceState = (Stage) source.getScene().getWindow();
        sourceState.close();

        try {
            Helpers.runAnotherApp(LoginView.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
