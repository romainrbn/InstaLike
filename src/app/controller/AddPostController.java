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
import java.net.URL;
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

    @FXML
    public void handlePostSend (ActionEvent event){
        String localisation = localisationTextField.getText();
        String description = descriptionTextArea.getText();

        if(localisation == null || LoginController.currentUser == null || fileURL == null)
            return;

        Post post = new Post(0, LoginController.currentUser, fileURL, new Date(), new ArrayList<>(), new ArrayList<>(), localisation, Post.PostState.POSTED, true, description);
        System.out.println(post.toString());

        Node source = (Node) event.getSource();
        Stage sourceState = (Stage) source.getScene().getWindow();
        sourceState.close();
    }
}
