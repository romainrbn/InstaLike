package app.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;

public class LoginView extends Application {

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
            URL iconURL = LoginView.class.getResource("LogoInstaLike.png");
            java.awt.Image image = new ImageIcon(iconURL).getImage();
            com.apple.eawt.Application.getApplication().setDockIconImage(image);
        } catch (Exception e) {
            // Ne marche pas sur Windows et Linux
        }
        launch(args);
    }
}
