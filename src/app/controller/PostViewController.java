package app.controller;

import app.model.Post;
import app.model.User;
import app.views.CommentsView;
import javafx.embed.swing.SwingFXUtils;
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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
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

    public static Post passPost;

    private int profilPhotoID = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    //    post = Post.generateExamplePost(); // Chargement réseau

        likeButton.setOnMouseEntered(e -> likeButton.setStyle(HOVER_BUTTON_STYLE));
        likeButton.setOnMouseExited(e -> likeButton.setStyle(IDLE_BUTTON_STYLE));

        Helpers.maskRoundImage(profileImageView);
    }


    public void initializePost(Post post) throws Exception {
        this.post = post;
        setAuthorLabel();
        setLocationLabel();
        setLikesCountLabel();
        setDescriptionLabel();
        setLikeIndicator();
        setPostImage();
        setProfileImageView();
    }

    public void setAuthor(User author) {
        usernameLabel.setText(author.getUsername());
    }

    @FXML
    public void handleUsernameClick(MouseEvent mouseEvent) {
        System.out.println("Show user profile");
    }

    public void setPostImage() {
        Connection connection;
        assert post != null;
        try{
            connection = Helpers.getConnection();
            String requestPicture = "SELECT * FROM photos WHERE photoID = " + post.getPhotoId();
            ResultSet resultSet = connection.createStatement().executeQuery(requestPicture);
            if(resultSet.next()){
                Blob blob = resultSet.getBlob("data");
                InputStream is = blob.getBinaryStream();
                BufferedImage bufferedImage = ImageIO.read(is);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                postImageView.setImage(image);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setProfileImageView() {
        Connection connection;
        assert post != null;
        try{
            connection = Helpers.getConnection();

            String requestPicture = "SELECT * FROM photos WHERE photoID = " + profilPhotoID;
            ResultSet resultSet = connection.createStatement().executeQuery(requestPicture);
            if(resultSet.next()){
                Blob blob = resultSet.getBlob("data");
                InputStream is = blob.getBinaryStream();
                BufferedImage bufferedImage = ImageIO.read(is);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                profileImageView.setImage(image);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAuthorLabel() {
        Connection connection;
        String username = "";
        try {
            connection = Helpers.getConnection();
            String request = "SELECT photoID, friendlyName FROM users WHERE userID = " + post.getUserId();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(request);
            while(rs.next()) {
                username = rs.getString(2);
                profilPhotoID = rs.getInt(1);
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
        this.passPost = post;
        Helpers.runAnotherApp(CommentsView.class);
    }
    public void handleLike() throws SQLException {
        Connection connection;
        connection = Helpers.getConnection();
        if(likeButton.isSelected() && !alreadyLike()){
            PreparedStatement statementForLike;
            likesCountLabel.setText(Integer.parseInt(likesCountLabel.getText().split("\\s")[0])+1 +" J'aime");

            try {
                String requestForLike = "INSERT INTO likes(postID, userID, publishDate)"+" values(?,?,?);";
                statementForLike = connection.prepareStatement(requestForLike);
                statementForLike.setInt(1,post.getPostId());
                statementForLike.setInt(2,LoginController.USER_ID);
                statementForLike.setTimestamp(3,java.sql.Timestamp.from(java.time.Instant.now()));
                statementForLike.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if(alreadyLike()){
            likesCountLabel.setText(Integer.parseInt(likesCountLabel.getText().split("\\s")[0])-1 +" J'aime");
            String requestForDislike = "DELETE FROM likes WHERE userID = "+LoginController.USER_ID + " AND postID = " + post.getPostId();
            connection.createStatement().executeQuery(requestForDislike);
        }
    }
    public boolean alreadyLike() throws SQLException {
        Connection connection;
        connection = Helpers.getConnection();
        String alreadyLike = "SELECT * FROM likes WHERE userID = "+LoginController.USER_ID + " AND postID = " + post.getPostId();
        ResultSet rs = connection.createStatement().executeQuery(alreadyLike);
        while(rs.next()){
            return true;
        }
        return false;
    }
    // Initialise l'indicateur de like a l'ouverture de la fenêtre pour chaque post
    private void setLikeIndicator() throws SQLException {
        likeButton.setSelected(alreadyLike());
    }
}