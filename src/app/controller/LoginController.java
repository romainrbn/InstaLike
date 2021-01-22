package app.controller;

import app.model.User;
import app.views.MainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static app.controller.Helpers.showAlert;


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
    public void handleLogin(ActionEvent event) {
        System.out.println("Handle login with " + userNameTextField.getText() + " and " + passwordTextField.getText());

        Connection connection = null;
        String inputUserName = userNameTextField.getText();
        String inputPassword = passwordTextField.getText();

        try {
            connection = Helpers.getConnection();

            String sqlRequest = "SELECT * FROM users WHERE username = '"+inputUserName+"' AND password = '"+inputPassword+"'";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sqlRequest);

            while(rs.next()){
                System.out.println("Utilisateur connecter");

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
                assert connection != null;
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
        PreparedStatement statement;
        String inputUserName = userNameTextField.getText();
        String inputPassword = passwordTextField.getText();

        try {
            connection = Helpers.getConnection();

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
                assert connection != null;
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

    private Boolean handleCheckPasswordOnSignUp(String pw) {
        return !(pw.isEmpty() || pw.length() < 6 || !Helpers.checkStringForSignUp(pw));
    }

    private Boolean handleCheckUsernameOnSignUp(String un) {
        return !(un.isEmpty() || un.length() >20 || un.length() < 3);
    }
}
