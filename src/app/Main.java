package app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("InstaLike");

        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("1024.png")));


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        addControlsToGrid(grid);

        primaryStage.setScene(new Scene(grid, 600, 300));

        primaryStage.show();
    }

    public void addControlsToGrid(GridPane grid) {
        Text sceneTitle = new Text("Bienvenue sur InstaLike");
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
        grid.add(validateButton, 0, 3);
    }


    public static void main(String[] args) {
        try {
            URL iconURL = Main.class.getResource("1024.png");
            java.awt.Image image = new ImageIcon(iconURL).getImage();
            com.apple.eawt.Application.getApplication().setDockIconImage(image);
        } catch (Exception e) {
            // Ne marche pas sur Windows et Linux
        }
        launch(args);
    }
}
