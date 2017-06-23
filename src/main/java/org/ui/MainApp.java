package org.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.ui.controllers.HomeController;
import org.ui.controllers.SolutionsExplorerController;
import org.ui.models.SolutionModel;

import java.util.List;

public class MainApp extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/rootLayout.fxml"));
        Parent root = loader.load();

        HomeController controller = loader.getController();
        controller.setApp(this);

        primaryStage.setTitle("Water Distribution Table Scheduling");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void showSolutionsWindow(List<SolutionModel> solutions) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/solutionsExplorerLayout.fxml"));
        Parent root = loader.load();

        SolutionsExplorerController controller = loader.getController();
        controller.setApp(this);
        controller.setSolutions(solutions);

        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Solutions Explorer");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
