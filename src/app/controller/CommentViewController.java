package app.controller;

import app.model.Comment;
import app.model.Post;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.awt.*;
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
    ImageView profileImageView;

    @FXML
    Label userNameLabel;

    @FXML
    Label commentLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void initializeComment(Comment comment, Post post) throws Exception {
        this.comment = comment;
        this.post = post;
        setCommentContent();
    }

    private void setCommentContent() throws SQLException {
        // Load image from backend
        int userId = 0;
        Connection connection;
        try {
            connection = Helpers.getConnection();
            String request = "SELECT * FROM comments WHERE postID = " + post.getPostId();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(request);
            while (rs.next()) {
                userId = rs.getInt("userID");
                commentLabel.setText(this.comment.getCommentValue());

                String userIDRequest = "SELECT * FROM users where userID = " + this.comment.getUserID();
                ResultSet userIDResultSet = statement.executeQuery(userIDRequest);

                while(userIDResultSet.next()) {
                    String username = userIDResultSet.getString("friendlyName");
                    userNameLabel.setText(username);
                    userNameLabel.setEllipsisString(username);
                }
                break;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}
