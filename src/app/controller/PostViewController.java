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
import java.sql.Connection;
import java.sql.PreparedStatement;
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
        maskRoundImage();
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
     //   usernameLabel.setText(post.getAuthor().getFriendlyName());
        usernameLabel.setText("" + post.getUserId());
    }

    private void setLikesCountLabel() {
        likesCountLabel.setText(post.getLikesList().size() + " J'aime");
        System.out.println("POPOP "+post.toString());
    }

    private void setDescriptionLabel() {
        descriptionLabel.setText(post.getDescription());
    }

    public void setLocationLabel() {
        locationLabel.setText(post.getLocalisation());
    }

    private void maskRoundImage() {
        Circle maskCircle = new Circle();
        maskCircle.setCenterX(profileImageView.getFitWidth() / 2);
        maskCircle.setCenterY((profileImageView.getFitHeight() / 2) - 4);
        maskCircle.setRadius(profileImageView.getFitWidth() / 2);
        profileImageView.setClip(maskCircle);
    }

    public void handleComments() throws Exception {
        Helpers.runAnotherApp(CommentsView.class);
    }
}

