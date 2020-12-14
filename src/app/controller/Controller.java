package app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Controller {

    @FXML private TextField userNameTextField;
    @FXML private PasswordField passwordTextField;

    @FXML
    public void handleLogin(ActionEvent event) {
        System.out.println("Handle login with " + userNameTextField.getText() + " and " + passwordTextField.getText());
        // Send to backend and get response
    }

    @FXML
    public void handleSignUp(ActionEvent event) {
        System.out.println("Handle sign up with " + userNameTextField.getText() + " and " + passwordTextField.getText());
        // Send to backend and get response
    }

    @FXML
    public void handleForgotPassword(ActionEvent event) {
        System.out.println("Handle password forgotten");
        // Send to backend and get response
    }
}
