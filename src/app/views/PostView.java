package app.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class PostView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/PostView.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("InstaLike");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("LogoInstaLike.png")));
        Scene scene = new Scene(root, 1150, 800);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1150);
        primaryStage.setMinHeight(800);

        ImageView profileImageView = (ImageView) loader.getNamespace().get("profileImageView");
        Circle clip = new Circle(profileImageView.getFitWidth());
        profileImageView.setClip(clip);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}