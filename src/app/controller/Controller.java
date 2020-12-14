package app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;


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

        if (!handleCheckPasswordOnSignUp(passwordTextField.getText())) {
            System.out.println("Le mot de passe doit contenir au moins 6 caractères, une majuscule et un chiffre.");
            return;
        }

        System.out.println("Handle sign up succeed with " + userNameTextField.getText() + " and " + passwordTextField.getText());

        // Ouvrir autre scene

        // Send to backend and get response.

    }

    private Boolean handleCheckPasswordOnSignUp(String pw) {
        if(pw.isEmpty() || pw.length() < 6 || !Pattern.compile("[0-9]").matcher(pw).find() || !Helpers.checkString(pw)){
            return false;
        }
        return true;
    }
}
