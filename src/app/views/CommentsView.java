package app.views;

import app.controller.CommentViewController;
import app.controller.Helpers;
import app.controller.PostViewController;
import app.model.Comment;
import app.model.Post;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentsView extends Application {

    VBox commentsList;
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/CommentsViewList.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Commentaires");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../resources/icons/LogoInstaLike.png")));
        primaryStage.setScene(new Scene(root, 500, 600));
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(600);
        primaryStage.show();

        ScrollPane pane = (ScrollPane) loader.getNamespace().get("scrollComments");
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        pane.setFitToHeight(true);
        pane.setFitToWidth(true);
        pane.setPannable(true);
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);

        commentsList = (VBox) loader.getNamespace().get("commentsList");

        for (int i = 0; i < getComments().size() ; i++) {
            FXMLLoader commentViewLoader = new FXMLLoader(getClass().getResource("../fxml/CommentView.fxml"));
            Parent commentViewRoot = commentViewLoader.load();
            commentViewRoot.setId(Integer.toString(i));
            Post getPost = PostViewController.passPost;
            CommentViewController controller = commentViewLoader.getController();
            controller.initializeComment(getComments().get(i), getPost);

            commentsList.getChildren().add(commentViewRoot);
        }
        primaryStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST,this::closeWindowEvent);
    }


    private List<Comment> getComments() {
        Connection connection;
        List<Comment> comments = new ArrayList<>();

        try {
            connection = Helpers.getConnection();

            String request = "SELECT * FROM comments WHERE postID = " + PostViewController.passPost.getPostId();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(request);

            while (resultSet.next()) {
                int commentID = resultSet.getInt("commentID");
                int postID = resultSet.getInt("postID");
                int userID = resultSet.getInt("userID");
                Timestamp publishDate = resultSet.getTimestamp("publishDate");
                String content = resultSet.getString("content");
                Comment comment = new Comment(commentID,content,publishDate,userID,postID);
                comments.add(comment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return comments;
    }
    private void closeWindowEvent(WindowEvent windowEvent){
        commentsList.getChildren().clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
