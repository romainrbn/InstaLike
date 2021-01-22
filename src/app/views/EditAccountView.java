package app.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class EditAccountView extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/EditAccountView.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Commentaires");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../resources/icons/LogoInstaLike.png")));
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(400);
        primaryStage.show();

        ImageView profileImageView = (ImageView) loader.getNamespace().get("profileImageView");
        Circle clip = new Circle(profileImageView.getFitWidth());
        profileImageView.setClip(clip);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
