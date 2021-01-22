package app.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LoginView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("../fxml/LoginView.fxml"));
        primaryStage.setTitle("Connexion et Inscription");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../resources/icons/LogoInstaLike.png")));
        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(800);
        primaryStage.show();
    }

    private void addElementsToStage(Stage stage) {

    }

    public static void main(String[] args) {
        launch(args);
    }
}
