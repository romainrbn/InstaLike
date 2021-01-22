package app.controller;

import app.model.Post;
import app.model.User;
import app.views.CommentsView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class PostViewController implements Initializable {

    private Post post;

    @FXML
    public ImageView postImageView;

    @FXML Label usernameLabel;
    @FXML Label locationLabel;
    @FXML ImageView profileImageView;
    @FXML ToggleButton likeButton;
    @FXML Button commentButton;
    @FXML Label descriptionLabel;
    @FXML Label likesCountLabel;

    private static final String HOVER_BUTTON_STYLE = "";
    private static final String IDLE_BUTTON_STYLE = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    //    post = Post.generateExamplePost(); // Chargement réseau

        likeButton.setOnMouseEntered(e -> likeButton.setStyle(HOVER_BUTTON_STYLE));
        likeButton.setOnMouseExited(e -> likeButton.setStyle(IDLE_BUTTON_STYLE));

        try {
            setPostImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Helpers.maskRoundImage(profileImageView);
    }


    public void initializePost(Post post) {
        this.post = post;
        setAuthorLabel();
        setLocationLabel();
        setLikesCountLabel();
        setDescriptionLabel();
        setProfileImageView();
    }

    public void setAuthor(User author) {
        usernameLabel.setText(author.getUsername());
    }

    @FXML
    public void handleUsernameClick(MouseEvent mouseEvent) {
        System.out.println("Show user profile");
    }

    public void setPostImage() throws Exception {
        String url =  "https://www.competencephoto.com/photo/art/grande/31056991-29406133.jpg";
      //  FileInputStream input = new FileInputStream("src/app/resources/icons/paysage.jpg");
        Image postImage = new Image(url);
        postImageView.setImage(postImage);
    }

    public void setProfileImageView() {
     //   String url = post.getUserId().getProfilePicture();
        Image profileImage = new Image("https://hatrabbits.com/wp-content/uploads/2017/01/random.jpg");
        profileImageView.setImage(profileImage);
    }

    public void setAuthorLabel() {
        Connection connection;
        String username = "";
        try {
            connection = Helpers.getConnection();
            String request = "SELECT username FROM users WHERE userID = " + post.getUserId();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(request);
            while(rs.next()) {
                username = rs.getString(1);
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        usernameLabel.setText(username);
    }

    private void setLikesCountLabel() {
        Connection connection;
        int likecompter = 0;
        try {
            connection = Helpers.getConnection();
            String request = "SELECT COUNT(*) FROM likes WHERE postID = " + post.getPostId();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(request);
            while(rs.next()) {
                likecompter = rs.getInt(1);
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        likesCountLabel.setText(likecompter + " J'aime");
    }

    private void setDescriptionLabel() {
        Connection connection;
        String description = "";
        try {
            connection = Helpers.getConnection();
            String request = "SELECT content FROM descriptions WHERE postID = " + post.getPostId();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(request);
            while(rs.next()) {
                description = rs.getString(1);
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        descriptionLabel.setText(description);
    }

    public void setLocationLabel() {
        locationLabel.setText(post.getLocalisation());
    }



    public void handleComments() throws Exception {
        Helpers.runAnotherApp(CommentsView.class);
    }
    public void handleLike() {
        if(likeButton.isSelected()){
            Connection connection;
            PreparedStatement statement;

            try {
                connection = Helpers.getConnection();
                String request = "INSERT INTO likes(postID, userID, publishDate)"+" values(?,?,?);";
                statement = connection.prepareStatement(request);
                statement.setInt(1,post.getPostId());
                statement.setInt(2,LoginController.USER_ID);
                statement.setTimestamp(3,java.sql.Timestamp.from(java.time.Instant.now()));
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}