package org.ui.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.operations.SaveNetwork;
import org.ui.models.SolutionModel;
import org.ui.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SolutionsExplorerController {
    private Stage stage;

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
    private VBox solutionView;
    @FXML
    private ScrollPane pane;

    @FXML
    private void initialize() {
        solutionsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> show(newValue));
        energyColumn.setCellValueFactory(param -> param.getValue().consumedEnergyProperty().asObject());
        pressureColumn.setCellValueFactory(param -> param.getValue().pressureProperty().asObject());
        tanksColumn.setCellValueFactory(param -> param.getValue().tanksVolumeProperty().asObject());
        fragmentsColumn.setCellValueFactory(param -> param.getValue().fragmentsProperty().asObject());
        solutionView.prefWidthProperty().bind(pane.widthProperty());
    }

    private void show(SolutionModel solution) {
        solutionView.getChildren().clear();
        int i = 0;
        for (Map.Entry<String, boolean[]> e : solution.getPumpingSchedule().entrySet())
            solutionView.getChildren().add(pumpChart(e.getKey(), e.getValue(), i++));
    }

    private AreaChart<Number, Number> pumpChart(String pump, boolean[] schedule, int index) {
        AreaChart<Number, Number> chart = getAreaChart(schedule.length + 1);
        chart.setTitle(pump);
        chart.setCreateSymbols(false);
        for (int i = 0; i < index; i++)
            chart.getData().add(new XYChart.Series<>());

        chart.getData().add(pumpSeries(schedule));
        return chart;
    }

    private AreaChart<Number, Number> getAreaChart(int xLength) {
        NumberAxis xAxis = new NumberAxis(0, xLength, 1);
        NumberAxis yAxis = new NumberAxis(0, 1, 0);
        StringConverter<Number> formatter = new StringConverter<Number>() {
            @Override
            public String toString(Number number) {
                int hour = number.intValue();
                return (hour % 12 == 0 ? 12 : hour % 12) + ((hour / 12) % 2 == 0 ? " AM" : " PM");
            }

            @Override
            public Number fromString(String string) {
                return null;
            }
        };
        xAxis.setTickLabelFormatter(formatter);
        return new AreaChart<>(xAxis, yAxis);
    }

    private XYChart.Series<Number, Number> pumpSeries(boolean[] schedule) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        for (int i = 0; i < schedule.length; i++) {
            series.getData().add(new XYChart.Data<>(i, schedule[i] ? 1 : 0));
            series.getData().add(new XYChart.Data<>(i + 1, schedule[i] ? 1 : 0));
        }
        return series;
    }

    @FXML
    private void handleSaveNetwork() {
        SolutionModel solution = solutionsTable.getSelectionModel().getSelectedItem();
        if (solution != null) {
            File file = Utils.inpFileChooser().showSaveDialog(stage);
            try {
                SaveNetwork.save(solution.getNetwork(), file);
            } catch (IOException e) {
                Utils.showError(stage, "Something went wrong! Can't save the file");
            }
        } else {
            Utils.showError(stage, "Select a solution");
        }
    }

    public void setSolutions(List<SolutionModel> solutions) {
        solutionsTable.setItems(FXCollections.observableArrayList(solutions));
        solutionsCount.setText(String.valueOf(solutions.size()));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
