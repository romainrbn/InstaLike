package app.controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CheckProfilViewController implements Initializable {

    @FXML
    private ImageView profileImageView;

    @FXML
    private Label currentFriendlyNameLabel;

    @FXML
    private Label likeSentLabel;

    @FXML
    private Label likeReceivedLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Helpers.maskRoundImage(profileImageView);
        try {
            loadImageAndFriendlynameFromServer();
            loadLikeSent();
            loadLikeReceived();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadImageAndFriendlynameFromServer()  {
        Connection connection;

        try {
            connection = Helpers.getConnection();
            String request = "SELECT photoID, friendlyName FROM users WHERE userID = " +
                    PostViewController.passPost.getUserId();
            Statement st = connection.createStatement();
            ResultSet getPhotoIdRs = st.executeQuery(request);

            if(getPhotoIdRs.next()) {
                int photoIndex = getPhotoIdRs.getInt(1);
                currentFriendlyNameLabel.setText(getPhotoIdRs.getString(2));
                String getPhoto = "SELECT * FROM photos WHERE photoID = " + photoIndex;
                ResultSet rs = st.executeQuery(getPhoto);
                if(rs.next()){
                    Blob blob = rs.getBlob("data");
                    InputStream is = blob.getBinaryStream();
                    BufferedImage bufferedImage = ImageIO.read(is);
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    profileImageView.setImage(image);
                }
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }

    private void loadLikeSent(){
        Connection connection;
        try {
            connection = Helpers.getConnection();
            String request = "SELECT COUNT(*) FROM likes WHERE userID = " + PostViewController.passPost.getUserId();
            Statement st = connection.createStatement();
            ResultSet getLikeResult = st.executeQuery(request);

            if (getLikeResult.next()) {
                int likeSentCounter = getLikeResult.getInt(1);
                likeSentLabel.setText(likeSentCounter + " J'aime envoyé");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void loadLikeReceived(){
        Connection connection;
        int likeReceivedCounter = 0;

        try {
            connection = Helpers.getConnection();
            String findAllPostsRequest = "SELECT * FROM posts WHERE userID = " + PostViewController.passPost.getUserId();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(findAllPostsRequest);
            while (resultSet.next()){
                String countLikeReceivedRequest = "SELECT COUNT(*) FROM likes WHERE postID = " +
                        resultSet.getInt("postID");
                Statement statementForCounter = connection.createStatement();
                ResultSet resultSetCounter = statementForCounter.executeQuery(countLikeReceivedRequest);
                while (resultSetCounter.next()){
                    likeReceivedCounter+=resultSetCounter.getInt(1);
                }
            }
            likeReceivedLabel.setText(likeReceivedCounter+" J'aime reçu");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
