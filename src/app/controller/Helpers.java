package app.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Helpers {

    public static boolean checkStringForSignUp(String str) {
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;

        if (str.isEmpty() || str.length() < 6)
            return false;

        for(int i=0;i < str.length();i++) {
            ch = str.charAt(i);
            if(Character.isDigit(ch)) {
                numberFlag = true;
            }
            else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }
            if(numberFlag && capitalFlag && lowerCaseFlag)
                return true;
        }
        return false;
    }

    public static void openPopUp() throws IOException {
        final Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(Helpers.class.getResource("../fxml/PostView.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("InstaLike");
        Scene scene = new Scene(root, 850, 650);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
