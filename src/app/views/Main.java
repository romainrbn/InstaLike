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
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("LogoInstaLike.png")));
        primaryStage.setScene(new Scene(root, 600, 800));
        primaryStage.show();

    }


    public static void main(String[] args) {
        try {
            URL iconURL = Main.class.getResource("LogoInstaLike.png");
            java.awt.Image image = new ImageIcon(iconURL).getImage();
            com.apple.eawt.Application.getApplication().setDockIconImage(image);
        } catch (Exception e) {
            // Ne marche pas sur Windows et Linux
        }
        launch(args);
    }
}
