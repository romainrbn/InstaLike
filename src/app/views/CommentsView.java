package app.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CommentsView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("../fxml/CommentsView.fxml"));
        primaryStage.setTitle("Commentaires");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../resources/icons/LogoInstaLike.png")));
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(400);
        primaryStage.show();
    }

    private void addElementsToStage(Stage stage) {
    }

    public static void main(String[] args) {
        launch(args);
    }
}
