package app.views;

import app.controller.PostViewController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;

public class MainView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("InstaLike");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("LogoInstaLike.png")));
        Scene scene = new Scene(root, 1150, 800);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1150);
        primaryStage.setMinHeight(800);
        primaryStage.show();

        setupUI(loader);

    }

    private void setupUI(FXMLLoader loader) throws Exception {
        AnchorPane rightPane = (AnchorPane) loader.getNamespace().get("rightPane");
        AnchorPane leftPane = (AnchorPane) loader.getNamespace().get("leftPane");

        SplitPane splitPane = (SplitPane) loader.getNamespace().get("splitPane");
        splitPane.setDividerPositions(0.25);
        leftPane.maxWidthProperty().bind(splitPane.widthProperty().multiply(0.25));
        leftPane.minWidthProperty().bind(splitPane.widthProperty().multiply(0.21));

        ScrollPane pane = (ScrollPane) loader.getNamespace().get("RightScroll");
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        pane.setFitToHeight(true);
        pane.setFitToWidth(true);
        pane.setPannable(true);
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);

        VBox postsBox = (VBox) loader.getNamespace().get("postsBox");

        for (int i = 0; i < 10 ; i++) {
            // posts.get(i)

            FXMLLoader postViewLoader = new FXMLLoader(getClass().getResource("PostView.fxml"));

            PostViewController controller = postViewLoader.getController();
       //     controller.initialize();
         //   controller.setAuthor(posts.get(i).getAuthor());

            Parent postViewRoot = postViewLoader.load();
            postViewRoot.setId(Integer.toString(i));
            postsBox.getChildren().add(postViewRoot);
        }

    }

    public static void main(String[] args) {
//        try {
//            URL iconURL = LoginView.class.getResource("LogoInstaLike.png");
//            java.awt.Image image = new ImageIcon(iconURL).getImage();
//            com.apple.eawt.Application.getApplication().setDockIconImage(image);
//        } catch (Exception e) {
//            // Ne marche pas sur Windows et Linux
//        }
        launch(args);
    }
}