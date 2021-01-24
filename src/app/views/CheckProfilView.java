package app.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CheckProfilView extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/CheckProfilView.fxml"));
        Parent root = loader.load();
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../resources/icons/LogoInstaLike.png")));
        primaryStage.setScene(new Scene(root, 350, 350));
        primaryStage.setMinWidth(350);
        primaryStage.setMaxWidth(350);
        primaryStage.setMinHeight(350);
        primaryStage.setMaxHeight(350);
        primaryStage.requestFocus();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
