package app.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CommentsViewListController implements Initializable {

    @FXML
    TextField newCommentTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void addComment() {
        Connection connection;
        PreparedStatement statement;

        if (!newCommentTextField.getText().trim().isEmpty()) {

            try {
                connection = Helpers.getConnection();
                String sqlRequest = "insert into comments(postID,userID,publishDate,content) values(?,?,?,?)";
                Timestamp currentDate = java.sql.Timestamp.from(java.time.Instant.now());
                statement = connection.prepareStatement(sqlRequest);
                statement.setInt(1, PostViewController.passPost.getPostId());
                statement.setInt(2, LoginController.USER_ID);
                statement.setTimestamp(3, currentDate);
                statement.setString(4, newCommentTextField.getText());
                statement.executeUpdate();
                newCommentTextField.setText("");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
