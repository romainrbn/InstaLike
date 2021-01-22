package app.controller;

import app.model.Post;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class AddPostController implements Initializable {

    private static final String HOVER_BUTTON_STYLE = "fx-background-color: #ff84a7; color: white;";
    private static final String IDLE_BUTTON_STYLE = "fx-background-color: white;";

    @FXML private Button sendPost;
    @FXML private TextField localisationTextField;
    @FXML private TextArea descriptionTextArea;

    private String fileURL;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sendPost.setOnMouseEntered(e -> sendPost.setStyle(HOVER_BUTTON_STYLE));
        sendPost.setOnMouseExited(e -> sendPost.setStyle(IDLE_BUTTON_STYLE));
    }

    @FXML
    public void handleFileChooser(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une photo à publier");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image", "*.png","  *.jpg");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            System.out.println("Picture path : "+file.getPath());
            fileURL = file.getPath();
        }
    }

    public Connection getConnection() {
        Connection connection = null;
        System.out.println("USER_ID : "+LoginController.USER_ID);
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String mariaDbUrl = "jdbc:mariadb://localhost/LDCSS_dev";//?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            connection = DriverManager.getConnection(mariaDbUrl,"root","");
        } catch (Exception e) {
            System.out.println("Error occured while getting the connection: - " + e);
        }
        return connection;
    }

    @FXML
    public void handlePostSend (ActionEvent event) throws Exception {
        String localisation = localisationTextField.getText();
        String description = descriptionTextArea.getText();

        Connection connection = null;
        PreparedStatement statement = null;
        PreparedStatement photoStatement = null;
        FileInputStream inputStream = null;
        int photoIndex = 0;

        try {
            File image = new File(fileURL);
            inputStream = new FileInputStream(image);

            connection = getConnection();

            String sqlRequest = "insert into posts(userID, photoID, localisation, publishDate) " + "values(?,?,?,?);";
            String uploadPhotoRequest = "insert into photos(publishDate, data) " + "values(?,?)";
            String uploadDescriptionrequest = "insert into descriptions(postID, publishDate, content)" + "values(?,?,?);";
            Timestamp currentTime = java.sql.Timestamp.from(java.time.Instant.now());
            Statement st = connection.createStatement();

            // Upload photo
            photoStatement = connection.prepareStatement(uploadPhotoRequest);
            photoStatement.setTimestamp(1, currentTime);
            photoStatement.setBinaryStream(2, inputStream); // Uploads blob
            photoStatement.executeUpdate();
            String getPhotoIndexRequest = "SELECT LAST_INSERT_ID()";
            ResultSet photoIndexResult = st.executeQuery(getPhotoIndexRequest);

            if (photoIndexResult.next()) {
                photoIndex = photoIndexResult.getInt(1);

                // Upload post if photo is uploaded
                statement = connection.prepareStatement(sqlRequest);
                statement.setInt(1, LoginController.USER_ID);
                statement.setInt(2, photoIndex);
                statement.setString(3,localisation);
                statement.setTimestamp(4, currentTime);
                statement.executeUpdate();
                String getPostIndexRequest = "SELECT LAST_INSERT_ID()";
                ResultSet postIndexResult = st.executeQuery(getPostIndexRequest);

                if (postIndexResult.next()){
                    statement = connection.prepareStatement(uploadDescriptionrequest);
                    statement.setInt(1,postIndexResult.getInt(1));
                    statement.setTimestamp(2,currentTime);
                    statement.setString(3,description);
                    statement.executeUpdate();

                    // Ferme la fenêtre
                    Node source = (Node) event.getSource();
                    Stage sourceState = (Stage) source.getScene().getWindow();
                    sourceState.close();
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException: - " + e);
        } catch (SQLException e) {
            System.out.println("SQLException: - " + e);
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                System.out.println("SQLException Finally: - " + e);
            }
        }
    }
}
