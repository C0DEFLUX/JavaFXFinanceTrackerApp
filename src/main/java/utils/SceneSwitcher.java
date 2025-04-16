package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SceneSwitcher
{
    public static void switchScene(Node node, String fxmlFile, String title) throws IOException {
        String path = "/com/example/javafxfinancetrackerapp/" + fxmlFile;
        URL fxmlPath = SceneSwitcher.class.getResource(path);

        if (fxmlPath == null) {
            throw new IOException("FXML file not found: " + path);
        }

        Parent root = FXMLLoader.load(fxmlPath);
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.show();
    }
}
