package app.controller;

import app.model.Post;
import com.mysql.jdbc.Driver;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
            System.out.println(file.getPath());
            fileURL = file.getPath();
        }
    }

    public Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String mysqlUrl = "jdbc:mysql://192.168.4.187:5678/";
            connection = DriverManager.getConnection(mysqlUrl, "ldcss1rabou", "romain");
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
        FileInputStream inputStream = null;

        try {
            File image = new File(fileURL);
            inputStream = new FileInputStream(image);

            connection = getConnection();
            statement = connection.prepareStatement("insert into testImages(img_title, img_data) " + "values(?,?)");
            statement.setString(1, "Image de test");
            statement.setBinaryStream(2, (InputStream) inputStream, (int) (image.length()));
            statement.executeUpdate();

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

        /*if(localisation == null || LoginController.currentUser == null || fileURL == null)
            return;

        Post post = new Post(0, LoginController.currentUser, fileURL, new Date(), new ArrayList<>(), new ArrayList<>(), localisation, Post.PostState.POSTED, true, description);
        System.out.println(post.toString());

        Node source = (Node) event.getSource();
        Stage sourceState = (Stage) source.getScene().getWindow();
        sourceState.close();*/
    } // ldcss1rabo pw : romain
}
