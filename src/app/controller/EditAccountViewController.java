package app.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class EditAccountViewController implements Initializable {

    @FXML
    private ImageView profileImageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Helpers.maskRoundImage(profileImageView);
    }
}
