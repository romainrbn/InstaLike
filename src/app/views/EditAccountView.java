package app.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class EditAccountView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/EditAccountView.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Modifier compte");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../resources/icons/LogoInstaLike.png")));
        primaryStage.setScene(new Scene(root, 400, 600));
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(500);
        primaryStage.setMaxWidth(400);
        primaryStage.setMaxHeight(600);
        primaryStage.requestFocus();
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
