package org.ui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.addition.epanet.network.Network;
import org.moeaframework.core.Solution;
import org.operations.OpenNetworkFile;
import org.operations.exceptions.ParseException;
import org.swms.optimization.Optimization;
import org.swms.optimization.OptimizationBuilder;
import org.swms.optimization.OptimizationListener;
import org.swms.optimization.SimulationNetwork;
import org.ui.controllers.states.EvaluationProgress;
import org.ui.controllers.states.NetworkInfo;
import org.ui.controllers.states.OptimizationCharts;
import org.ui.utils.Utils;

import java.io.File;
import java.util.List;
import java.util.concurrent.Executors;

public class HomeController implements OptimizationListener {
    private Stage stage;
    private Network network;
    private NetworkInfo networkInfo;
    private EvaluationProgress evaluationProgress;
    private OptimizationCharts optimizationCharts;

    @FXML
    private Label networkName;
    @FXML
    private Label nodesCount;
    @FXML
    private Label pipesCount;
    @FXML
    private Label pumpsCount;
    @FXML
    private Label tanksCount;

    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label solutionsCount;
    @FXML
    private Label invalidSolutionsCount;

    @FXML
    private AreaChart<Integer, Double> energyChart;
    @FXML
    private AreaChart<Integer, Double> pressureChart;
    @FXML
    private AreaChart<Integer, Double> tanksChart;
    @FXML
    private AreaChart<Integer, Integer> fragmentsChart;

    @FXML
    private TextField runsField;
    @FXML
    private ChoiceBox<String> algorithmMenu;

    @FXML
    private Button optimizeBtn;
    @FXML
    private Button openBtn;

    @FXML
    private void initialize() {
        networkInfo = new NetworkInfo(networkName, nodesCount, pipesCount, pumpsCount, tanksCount);
        evaluationProgress = new EvaluationProgress(progressBar, solutionsCount, invalidSolutionsCount);
        optimizationCharts = new OptimizationCharts(energyChart, pressureChart, tanksChart, fragmentsChart);
        algorithmMenu.getItems().addAll(Optimization.ALGORITHMS);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleOpenFile() {
        File file = Utils.inpFileChooser().showOpenDialog(stage);
        try {
            OpenNetworkFile openNetwork = new OpenNetworkFile(file);
            network = openNetwork.get();
            networkInfo.update(openNetwork);
            optimizeBtn.setDisable(false);
        } catch (ParseException e) {
            Utils.showError(stage, "Invalid file");
        }
    }

    @FXML
    private void handleOptimizeNetwork() {
        int runs = Integer.parseInt(runsField.getText());
        String algorithm = algorithmMenu.getValue();
        evaluationProgress.reset(runs);
        optimizationCharts.reset();
        Executors.newSingleThreadExecutor().execute(() -> new OptimizationBuilder(network, this)
                .setAlgorithm(algorithm)
                .setMaxEvaluations(runs)
                .create()
                .run());
        openBtn.setDisable(true);
        optimizeBtn.setDisable(true);
    }

    @Override
    public void done(List<Solution> solutions) {
        Platform.runLater(() -> {
            Utils.showInfo(this.stage, "Done", "Optimization finished successfully");
            openBtn.setDisable(false);
            optimizeBtn.setDisable(false);
        });
    }

    @Override
    public void newEvaluation(SimulationNetwork network) {
        Platform.runLater(() -> {
            evaluationProgress.update(network);
            optimizationCharts.update(network);
        });
    }
}

