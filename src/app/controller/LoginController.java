package app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {

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

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("main.fxml"));

        Node source = (Node) event.getSource();
        Stage sourceState = (Stage) source.getScene().getWindow();
        sourceState.close();


        try {
            Parent root = (Parent) loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Accueil");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Boolean handleCheckPasswordOnSignUp(String pw) {
        if(pw.isEmpty() || pw.length() < 6 || !Helpers.checkStringForSignUp(pw)){
            return false;
        }
        return true;
    }
}
