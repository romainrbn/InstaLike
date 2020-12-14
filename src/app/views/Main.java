package app.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import javax.swing.*;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("InstaLike");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("app_icon.png")));
        primaryStage.setScene(new Scene(root, 600, 800));
        primaryStage.show();


/*
primaryStage.setTitle("InstaLike");

        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("app_icon.png")));

        primaryStage.show();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        addControlsToGrid(grid);

        primaryStage.setScene(new Scene(grid, 600, 800));


        */
    }

    public void addControlsToGrid(GridPane grid) {
        Text sceneTitle = new Text("Bienvenue sur InstaLike");
        sceneTitle.setStyle("-fx-padding: 20 20 20 20; ");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));

        grid.add(sceneTitle, 0, 0, 2, 1);

        Label userName = new Label("Nom d'utilisateur : ");
        grid.add(userName, 0, 1);

        TextField userNameTextField = new TextField();
        grid.add(userNameTextField, 1, 1);

        Label passwordLabel = new Label("Mot de passe : ");
        grid.add(passwordLabel, 0, 2);

        PasswordField pwField = new PasswordField();
        grid.add(pwField, 1, 2);

        Button validateButton = new Button("Connexion");
        validateButton.setStyle("-fx-font: 14px \"Trebuchet MS, sans-serif\"; -fx-background-color: #48c9f7; -fx-text-inner-color: #ffffff");
        grid.add(validateButton, 0, 4);

        Button signUpButton = new Button("Inscription");
        signUpButton.setStyle("-fx-font: 14px \"Trebuchet MS, sans-serif\"; -fx-background-color: #48c9f7; -fx-text-inner-color: #ffffff");
        grid.add(signUpButton, 1, 4);

        Button forgotPwButton = new Button("Mot de passe oublié ?");
        forgotPwButton.setStyle("-fx-font-size: 10px");
        grid.add(forgotPwButton, 0, 5);

    }


    public static void main(String[] args) {
//        try {
//            URL iconURL = Main.class.getResource("app_icon.png");
//            java.awt.Image image = new ImageIcon(iconURL).getImage();
//            com.apple.eawt.Application.getApplication().setDockIconImage(image);
//        } catch (Exception e) {
//            // Ne marche pas sur Windows et Linux
//        }
        launch(args);
    }
}
