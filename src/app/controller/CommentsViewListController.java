package app.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CommentsViewListController implements Initializable {

    @FXML
    TextField newCommentTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void addComment(javafx.event.ActionEvent actionEvent) {
        Connection connection;
        PreparedStatement statement = null;

        try {
            connection = Helpers.getConnection();

            String sqlRequest = "insert into comments(postID,userID,publishDate,content) " +  "values(?,?,?,?)";
            Timestamp currentDate = java.sql.Timestamp.from(java.time.Instant.now());
            Statement st = connection.createStatement();

            statement = connection.prepareStatement(sqlRequest);
            statement.setInt(1, PostViewController.passPost.getPostId());
            statement.setInt(2, LoginController.USER_ID);
            statement.setTimestamp(3, currentDate);
            statement.setString(4, newCommentTextField.getText());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
