package app.controller;

import app.model.Post;
import app.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;

import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class PostViewController implements Initializable {


    @FXML
    public ImageView postImageView;

    @FXML Label usernameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setPostImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setLabel();
    }

    public void initialize(Post post) {
        setAuthor(post.getAuthor());
    }

    public void setAuthor(User author) {
        usernameLabel.setText(author.getUsername());
    }

    @FXML
    public void handleUsernameClick(MouseEvent mouseEvent) {
        System.out.println("Show user profile");
    }

    public void setPostImage() throws Exception {
        FileInputStream input = new FileInputStream("src/app/views/default_pp.png");
        Image postImage = new Image(input);
        postImageView.setImage(postImage);
        PixelReader reader = postImage.getPixelReader();
        WritableImage newImage = new WritableImage(reader, 0, 0, 350, 350);
        //postImageView.setImage(newImage);
    }

    public void setLabel() {
        usernameLabel.setText("nouveau texte");
    }
}

