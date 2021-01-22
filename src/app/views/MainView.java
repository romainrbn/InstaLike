package app.views;

import app.controller.Helpers;
import app.controller.PostViewController;
import app.model.Post;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MainView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/main.fxml"));
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

        System.out.println("PRINT GETPOST" + getPosts().toString());

        // Display each post
        for (int i = 0; i < getPosts().size() ; i++) {

            FXMLLoader postViewLoader = new FXMLLoader(getClass().getResource("../fxml/PostView.fxml"));

            Parent postViewRoot = postViewLoader.load();
            postViewRoot.setId(Integer.toString(i));

            PostViewController controller = (PostViewController) postViewLoader.getController();
            controller.initializePost(getPosts().get(i));




            postsBox.getChildren().add(postViewRoot);
        }
    }

    private List<Post> getPosts() {
        Connection connection;
        List<Post> posts = new ArrayList<>();

        try {
            connection = Helpers.getConnection();

            // Obtenir les 3 derniers éléments de la liste de posts.
            String request = "SELECT * FROM posts ORDER BY postID DESC LIMIT 3";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(request);

            while(resultSet.next()) {
                int postId = resultSet.getInt("postId");
                int userId = resultSet.getInt("userId");
                int photoId = resultSet.getInt("photoId");
                String localisation = resultSet.getString("localisation");
                Timestamp publishDate = resultSet.getTimestamp("publishDate");

                Post post = new Post(
                        postId,
                        userId,
                        photoId,
                        publishDate,
                        new ArrayList<>(),
                        new ArrayList<>(),
                        localisation,
                        Post.PostState.POSTED,
                        false,
                        "description ici"
                );
                posts.add(post);
            }

        } catch (SQLException e) {
            Helpers.showAlert("Erreur", "Une erreur est survenue", e.getMessage());
        }

        return posts;
    }

    public static void main(String[] args) {
        launch(args);
    }
}