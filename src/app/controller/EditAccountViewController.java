package app.controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class EditAccountViewController implements Initializable {


    @FXML
    private ImageView profileImageView;

    @FXML
    private TextField nameTextField;

    @FXML
    private Label currentFriendlyNameLabel;

    @FXML
    private Label likeSentLabel;

    @FXML
    private Label likeReceivedLabel;

    @FXML
    private Button validateButton;

    private static final String HOVER_BUTTON_STYLE = "";
    private static final String IDLE_BUTTON_STYLE = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Helpers.maskRoundImage(profileImageView);

        validateButton.setOnMouseEntered(e -> validateButton.setStyle(HOVER_BUTTON_STYLE));
        validateButton.setOnMouseExited(e -> validateButton.setStyle(IDLE_BUTTON_STYLE));


        try {
            loadImageAndFriendlynameFromServer();
            loadLikeSent();
            loadLikeReceived();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final int currentUser = LoginController.USER_ID; // L'utilisateur à modifier

    private File newImage;

    private boolean hasChangedImage = false;

    @FXML public void handlePictureClicked() {
        String newImagePath = chooseFileFromFinder();
        assert newImagePath != null;

        // Changer `hasChangedImage` à `true` pour déclencher la requête réseau pour la photo de profil.
        hasChangedImage = true;

        // Changer l'image dans l'interface
        File file = new File(newImagePath);
        Image image = new Image(file.toURI().toString());

        profileImageView.setImage(image);

        // Préparer la requête réseau en indiquant l'image à uploader.
        this.newImage = file;
    }

    @FXML
    public void validateForm(ActionEvent actionEvent) throws Exception {
        // Changement du nom d'affichage (frirendlyName)
        String newName = currentFriendlyNameLabel.getText();
        if(!nameTextField.getText().trim().isEmpty()){
            newName = nameTextField.getText();
        }

        executeServerRequest(newName, newImage);

        // Fermer la fenêtre actuelle
        Node source = (Node) actionEvent.getSource();
        Stage sourceState = (Stage) source.getScene().getWindow();
        sourceState.close();
    }

    private void loadImageAndFriendlynameFromServer()  {
        Connection connection;

        try {
            connection = Helpers.getConnection();
            String request = "SELECT photoID, friendlyName FROM users WHERE userID = " + currentUser;
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
            String request = "SELECT COUNT(*) FROM likes WHERE userID = " + currentUser;
            Statement st = connection.createStatement();
            ResultSet getLikeResult = st.executeQuery(request);

            if(getLikeResult.next()) {
                int likeSentCounter = getLikeResult.getInt(1);
                likeSentLabel.setText(likeSentCounter + " J'aime envoyé");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void loadLikeReceived() {
        Connection connection;
        int likeReceivedCounter = 0;

        try{
           connection = Helpers.getConnection();
           String findAllPostsRequest = "SELECT * FROM posts WHERE userID = " + currentUser;
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

    private void executeServerRequest(String friendlyName, File file) throws Exception {
        Connection connection;
        PreparedStatement friendlyNameStatement;
        PreparedStatement photoStatement;
        PreparedStatement photoIndexStatement;
        FileInputStream inputStream; // La photo à mettre sur le serveur
        int photoIndex;

        try {
            connection = Helpers.getConnection();
            Timestamp currentTime = java.sql.Timestamp.from(java.time.Instant.now());


            String sqlRequestDescription = "UPDATE users SET friendlyName = (?) WHERE userID =" + currentUser;
            String uploadPhotoRequest = "INSERT INTO photos(publishDate, data) values(?,?)";

            Statement st = connection.createStatement();

            friendlyNameStatement = connection.prepareStatement(sqlRequestDescription);
            friendlyNameStatement.setString(1, friendlyName);
            friendlyNameStatement.executeUpdate();

            //Si chnage uniquement son friendlyName
            if (!hasChangedImage) {
                return;
            }

            inputStream = new FileInputStream(file);

            photoStatement = connection.prepareStatement(uploadPhotoRequest);
            photoStatement.setTimestamp(1, currentTime);
            photoStatement.setBinaryStream(2, inputStream);
            photoStatement.executeUpdate();

            String getPhotoIndexRequest = "SELECT LAST_INSERT_ID()"; // Obtenir le dernier index des photos
            ResultSet photoIndexResult = st.executeQuery(getPhotoIndexRequest);

            if(photoIndexResult.next()) {
                photoIndex = photoIndexResult.getInt(1);

                // Mettre à jour l'indice de la photo pour l'utilisateur
                String updateIndexRequest = "UPDATE users SET photoID = (?) WHERE userID = " + currentUser;
                photoIndexStatement = connection.prepareStatement(updateIndexRequest);
                photoIndexStatement.setInt(1, photoIndex);
                photoIndexStatement.executeUpdate();
            }

        } catch (SQLException throwables) {
            Helpers.showAlert("Erreur !", "Une erreur est survenue :", throwables.getLocalizedMessage());
        }
    }


    private String chooseFileFromFinder(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une photo à publier");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image", "*.png","  *.jpg");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            return file.getPath();
        }
        return null;
    }
}
