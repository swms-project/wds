package org.ui.utils;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Utils {
    public static FileChooser inpFileChooser() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("INP files (*.inp)", "*.inp");
        String path = System.getProperty("user.home") + "/Desktop";
        fileChooser.setInitialDirectory(new File(path));
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All files", "*"));
        return fileChooser;
    }

    public static void showError(Stage stage, String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(stage);
        alert.setTitle("Error");
        alert.setHeaderText(error);
        alert.show();
    }

    public static void showInfo(Stage stage, String title, String info) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(stage);
        alert.setTitle(title);
        alert.setHeaderText(info);
        alert.show();
    }
}
