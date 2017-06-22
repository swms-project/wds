package org.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.ui.controllers.HomeController;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/rootLayout.fxml"));
        Parent root = loader.load();

        HomeController controller = loader.getController();
        controller.setStage(primaryStage);

        primaryStage.setTitle("Water Distribution Table Scheduling");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
