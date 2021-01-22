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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    @FXML private TextField userNameTextField;
    @FXML private PasswordField passwordTextField;

    @FXML private Button signUpButton;
    @FXML private Button handleLogin;

    private static final String HOVER_BUTTON_STYLE = "";
    private static final String IDLE_BUTTON_STYLE = "";

    public static User currentUser;

    public static int USER_ID = 0 ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signUpButton.setOnMouseEntered(e -> signUpButton.setStyle(HOVER_BUTTON_STYLE));
        signUpButton.setOnMouseExited(e -> signUpButton.setStyle(IDLE_BUTTON_STYLE));

        handleLogin.setOnMouseEntered(e -> handleLogin.setStyle(HOVER_BUTTON_STYLE));
        handleLogin.setOnMouseExited(e -> handleLogin.setStyle(IDLE_BUTTON_STYLE));
    }

    @FXML
    public void handleLogin(ActionEvent event) throws Exception {
        System.out.println("Handle login with " + userNameTextField.getText() + " and " + passwordTextField.getText());

        Connection connection = null;
        PreparedStatement statement = null;
        FileInputStream inputStream = null;
        String inputUserName = userNameTextField.getText();
        String inputPassword = passwordTextField.getText();

        try {
            connection = getConnection();

            String sqlRequest = "SELECT * FROM users WHERE username = '"+inputUserName+"' AND password = '"+inputPassword+"'";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sqlRequest);

            while(rs.next()){
                System.out.println("Utilasateur connecter");

                USER_ID = rs.getInt(1);

                Node source = (Node) event.getSource();
                Stage sourceState = (Stage) source.getScene().getWindow();
                sourceState.close();

                try {
                    Helpers.runAnotherApp(MainView.class);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert("Erreur","Une erreur est survenue",e.getMessage());
                    return;
                }
            }
            showAlert("Erreur","Une erreur est survenue","Utilisateur introuvable");

        } catch (SQLException e) {
            System.out.println("SQLException: - " + e);
            showAlert("Erreur","Une erreur est survenue",e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("SQLException Finally: - " + e);
                showAlert("Erreur","Une erreur est survenue",e.getMessage());
            }
        }
    }

    @FXML
    public void handleSignUp(ActionEvent event) {

        if (!handleCheckPasswordOnSignUp(passwordTextField.getText()) || !handleCheckUsernameOnSignUp(userNameTextField.getText())) {
            showAlert("Erreur","Inscription impossible","Le nom d'utilisateur doit contenir entre 3 et 20 caractères. " +
                    "\nLe mot de passe doit contenir au moins 6" +
                    " caractères, une majuscule et un chiffre.");
            return;
        }

        Connection connection = null;
        PreparedStatement statement = null;
        String inputUserName = userNameTextField.getText();
        String inputPassword = passwordTextField.getText();

        try {
            connection = getConnection();

            String findAlreadyRegistered = "SELECT * FROM users WHERE username = '"+inputUserName+"' AND password = '"+inputPassword+"'";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(findAlreadyRegistered);

            while(rs.next()){
                showAlert("Erreur","Inscription impossible","L'utilisateur existe déjà");
                return;
            }

            String sqlRequest = "insert into users(photoID, username, friendlyName, password, isAdmin) " + "values(?,?,?,?,?);";

            statement = connection.prepareStatement(sqlRequest);

            statement.setInt(1, 1);
            statement.setString(2, inputUserName);
            statement.setString(3, inputUserName);
            statement.setString(4, inputPassword);
            statement.setInt(5, 0);
            statement.executeUpdate();

            String findIdNewUser = "SELECT * FROM users WHERE username = '"+inputUserName+"' AND password = '"+inputPassword+"'";
            Statement st2 = connection.createStatement();
            ResultSet rs2 = st2.executeQuery(findIdNewUser);

            if (rs2.next()){
                USER_ID = rs2.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("SQLException: - " + e);
            showAlert("Erreur","Une erreur est survenue",e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("SQLException Finally: - " + e);
                showAlert("Erreur","Une erreur est survenue",e.getMessage());
            }
        }


        currentUser = User.generateExampleUser();

        System.out.println("Handle sign up succeed with " + userNameTextField.getText() + " and " + passwordTextField.getText());


        Node source = (Node) event.getSource();
        Stage sourceState = (Stage) source.getScene().getWindow();
        sourceState.close();

        try {
            Helpers.runAnotherApp(MainView.class);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur","Une erreur est survenue",e.getMessage());
        }

    }

    public Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String mariaDbUrl = "jdbc:mariadb://localhost/LDCSS_dev";//?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            connection = DriverManager.getConnection(mariaDbUrl,"root","");
        } catch (Exception e) {
            System.out.println("Error occured while getting the connection: - " + e);
        }
        return connection;
    }

    private Boolean handleCheckPasswordOnSignUp(String pw) {
        return !(pw.isEmpty() || pw.length() < 6 || !Helpers.checkStringForSignUp(pw));
    }

    private Boolean handleCheckUsernameOnSignUp(String un) {
        return !(un.isEmpty() || un.length() >20 || un.length() < 3);
    }



    private void showAlert(String title, String headerText, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
