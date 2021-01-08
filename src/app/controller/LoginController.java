package app.controller;

import app.model.User;
import app.views.LoginView;
import app.views.MainView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    @FXML private TextField userNameTextField;
    @FXML private PasswordField passwordTextField;

    @FXML private Button signUpButton;
    @FXML private Button handleLogin;

    private static final String HOVER_BUTTON_STYLE = "";
    private static final String IDLE_BUTTON_STYLE = "";

    public static User currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signUpButton.setOnMouseEntered(e -> signUpButton.setStyle(HOVER_BUTTON_STYLE));
        signUpButton.setOnMouseExited(e -> signUpButton.setStyle(IDLE_BUTTON_STYLE));

        handleLogin.setOnMouseEntered(e -> handleLogin.setStyle(HOVER_BUTTON_STYLE));
        handleLogin.setOnMouseExited(e -> handleLogin.setStyle(IDLE_BUTTON_STYLE));
    }

    @FXML
    public void handleLogin(ActionEvent event) {
        System.out.println("Handle login with " + userNameTextField.getText() + " and " + passwordTextField.getText());

        // Send to backend and get response

        Node source = (Node) event.getSource();
        Stage sourceState = (Stage) source.getScene().getWindow();
        sourceState.close();

        currentUser = User.generateExampleUser();

        try {
            runAnotherApp(MainView.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSignUp(ActionEvent event) {

        if (!handleCheckPasswordOnSignUp(passwordTextField.getText())) {
            System.out.println("Le mot de passe doit contenir au moins 6 caractères, une majuscule et un chiffre.");
            return;
        }

        currentUser = User.generateExampleUser();

        System.out.println("Handle sign up succeed with " + userNameTextField.getText() + " and " + passwordTextField.getText());


        Node source = (Node) event.getSource();
        Stage sourceState = (Stage) source.getScene().getWindow();
        sourceState.close();

        try {
            runAnotherApp(MainView.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Boolean handleCheckPasswordOnSignUp(String pw) {
        return !(pw.isEmpty() || pw.length() < 6 || !Helpers.checkStringForSignUp(pw));
    }

    public void runAnotherApp(Class<? extends Application> anotherAppClass) throws Exception{
        Application application = anotherAppClass.newInstance();
        Stage anotherStage = new Stage();
        application.start(anotherStage);
    }
}
