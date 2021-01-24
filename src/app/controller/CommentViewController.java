package app.controller;

import app.model.Comment;
import app.model.Post;
import app.views.CheckProfilView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class CommentViewController implements Initializable {

    private Comment comment;
    private Post post;

    @FXML
    Label userNameLabel;

    @FXML
    Label commentLabel;

    public static Comment passComment;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void initializeComment(Comment comment, Post post) {
        this.comment = comment;
        this.post = post;
        setCommentContent();
    }

    @FXML
    public void handleUsernameClicked() throws Exception {
        passComment=comment;
        Helpers.runAnotherApp(CheckProfilView.class);
    }

    private void setCommentContent()  {
        // Load image from backend
        Connection connection;
        try {
            connection = Helpers.getConnection();
            String request = "SELECT * FROM comments WHERE postID = " + post.getPostId();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(request);
            if (rs.next()) {
                commentLabel.setText(this.comment.getCommentValue());
                String userIDRequest = "SELECT * FROM users where userID = " + this.comment.getUserID();
                ResultSet userIDResultSet = statement.executeQuery(userIDRequest);

                while(userIDResultSet.next()) {
                    String username = userIDResultSet.getString("friendlyName");
                    userNameLabel.setText(username);
                    userNameLabel.setEllipsisString(username);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
