package app.controller;
import app.model.Post;
import app.views.CheckProfilView;
import app.views.CommentsView;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class PostViewController implements Initializable {


    private Post post;

    @FXML public ImageView postImageView;
    @FXML Label usernameLabel;
    @FXML Label locationLabel;
    @FXML ImageView profileImageView;
    @FXML ToggleButton likeButton;
    @FXML Button commentButton;
    @FXML Label descriptionLabel;
    @FXML Label likesCountLabel;
    @FXML Label commentsCountLabel;
    @FXML Label timestampLabel;

    private static final String HOVER_BUTTON_STYLE = "";
    private static final String IDLE_BUTTON_STYLE = "";

    public static Post passPost;

    private int profilPhotoID = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
        setCommentsCountLabel();
        setTimestampLabel();
    }

    @FXML
    public void handleUsernameClick() throws Exception {
        passPost = post;
        Helpers.runAnotherApp(CheckProfilView.class);
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

            if (resultSet.next()) {
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
            if(rs.next()) {
                username = rs.getString(2);
                profilPhotoID = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        usernameLabel.setText(username);
    }

    public void setTimestampLabel() {
        Connection connection;

        try {
            connection = Helpers.getConnection();
            String request = "SELECT publishDate FROM posts WHERE postID = " + post.getPostId();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(request);
            if(rs.next()) {
                Timestamp ts = rs.getTimestamp(1);
                Date date = new Date(ts.getTime());

                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                String dateForLabel = format.format(date);
                timestampLabel.setText(dateForLabel);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setLikesCountLabel() {
        Connection connection;
        int likeCounter = 0;
        try {
            connection = Helpers.getConnection();
            String request = "SELECT COUNT(*) FROM likes WHERE postID = " + post.getPostId();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(request);
            if(rs.next()) {
                likeCounter = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        likesCountLabel.setText(likeCounter + " J'aime");
    }

    private void setCommentsCountLabel() {
        Connection connection;
        int commentsCounter = 0;
        try {
            connection = Helpers.getConnection();
            String request = "SELECT COUNT(*) FROM comments WHERE postID = " + post.getPostId();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(request);
            if(rs.next()) {
                commentsCounter = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        commentsCountLabel.setText(commentsCounter + " Commentaires");
    }

    private void setDescriptionLabel() {
        Connection connection;
        String description = "";
        try {
            connection = Helpers.getConnection();
            String request = "SELECT content FROM descriptions WHERE postID = " + post.getPostId();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(request);
            if(rs.next()) {
                description = rs.getString(1);
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
        passPost = post;
        Helpers.runAnotherApp(CommentsView.class);
    }

    public void handleLike() throws SQLException {
        Connection connection;
        connection = Helpers.getConnection();

        if(likeButton.isSelected() && !alreadyLike()){
            PreparedStatement statementForLike;
            likesCountLabel.setText(Integer.parseInt(likesCountLabel.getText().split("\\s")[0])+1 +" J'aime");

            try {
                String requestForLike = "INSERT INTO likes(postID, userID, publishDate) values(?,?,?);";
                statementForLike = connection.prepareStatement(requestForLike);
                statementForLike.setInt(1,post.getPostId());
                statementForLike.setInt(2,LoginController.USER_ID);
                statementForLike.setTimestamp(3,java.sql.Timestamp.from(java.time.Instant.now()));
                statementForLike.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if(alreadyLike()){
            likesCountLabel.setText(Integer.parseInt(likesCountLabel.getText().split("\\s")[0])-1 +" J'aime");
            String requestForDislike = "DELETE FROM likes WHERE userID = " + LoginController.USER_ID +
                    " AND postID = " + post.getPostId();
            connection.createStatement().executeQuery(requestForDislike);
        }
    }
    public boolean alreadyLike() throws SQLException {
        Connection connection;
        connection = Helpers.getConnection();
        String alreadyLike = "SELECT * FROM likes WHERE userID = "+ LoginController.USER_ID +
                " AND postID = " + post.getPostId();
        ResultSet rs = connection.createStatement().executeQuery(alreadyLike);
        return rs.next();
    }

    // Initialise l'indicateur de like a l'ouverture de la fenêtre pour chaque post
    private void setLikeIndicator() throws SQLException {
        likeButton.setSelected(alreadyLike());
    }
}