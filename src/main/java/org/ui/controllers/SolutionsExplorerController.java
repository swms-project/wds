package org.ui.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.ui.MainApp;
import org.ui.models.SolutionModel;

import java.util.List;

public class SolutionsExplorerController {
    private MainApp app;

    @FXML
    private TableView<SolutionModel> solutionsTable;
    @FXML
    private TableColumn<SolutionModel, Double> energyColumn;
    @FXML
    private TableColumn<SolutionModel, Double> pressureColumn;
    @FXML
    private TableColumn<SolutionModel, Double> tanksColumn;
    @FXML
    private TableColumn<SolutionModel, Integer> fragmentsColumn;

    @FXML
    private Label solutionsCount;

    @FXML
    private void initialize() {
        energyColumn.setCellValueFactory(param -> param.getValue().consumedEnergyProperty().asObject());
        pressureColumn.setCellValueFactory(param -> param.getValue().pressureProperty().asObject());
        tanksColumn.setCellValueFactory(param -> param.getValue().tanksVolumeProperty().asObject());
        fragmentsColumn.setCellValueFactory(param -> param.getValue().fragmentsProperty().asObject());
    }

    public void setApp(MainApp app) {
        this.app = app;
    }

    public void setSolutions(List<SolutionModel> solutions) {
        solutionsTable.setItems(FXCollections.observableArrayList(solutions));
        solutionsCount.setText(String.valueOf(solutions.size()));
    }
}
