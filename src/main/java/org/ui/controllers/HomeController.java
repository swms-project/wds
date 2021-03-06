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
import org.swms.optimization.OptimizationBuilder;
import org.swms.optimization.OptimizationListener;
import org.swms.optimization.SimulationNetwork;
import org.ui.MainApp;
import org.ui.controllers.states.*;
import org.ui.models.SolutionModel;
import org.ui.utils.Utils;

import java.io.File;
import java.util.List;
import java.util.concurrent.Executors;

import static java.util.stream.Collectors.toList;

public class HomeController implements OptimizationListener {
    private MainApp app;
    private Network network;
    private List<SolutionModel> solutions;
    private NetworkInfo networkInfo;
    private EvaluationProgress evaluationProgress;
    private OptimizationCharts optimizationCharts;
    private HomeInput input;
    private HomeButtons buttons;

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
    private ChoiceBox<String> algorithmMenu;
    @FXML
    private TextField runsField;
    @FXML
    private CheckBox energyCheck;
    @FXML
    private CheckBox pressureCheck;
    @FXML
    private CheckBox volumeCheck;
    @FXML
    private CheckBox fragmentsCheck;
    @FXML
    public Slider threadsSlider;
    @FXML
    private TextField populationSizeField;
    @FXML
    private TextField bitFlipRateField;
    @FXML
    private TextField crossoverRateField;

    @FXML
    private Button openBtn;
    @FXML
    private Button optimizeBtn;
    @FXML
    private Button exploreBtn;

    @FXML
    private void initialize() {
        networkInfo = new NetworkInfo(networkName, nodesCount, pipesCount, pumpsCount, tanksCount);
        evaluationProgress = new EvaluationProgress(progressBar, solutionsCount, invalidSolutionsCount);
        optimizationCharts = new OptimizationCharts(energyChart, pressureChart, tanksChart, fragmentsChart);
        input = new HomeInput(algorithmMenu, runsField, threadsSlider, energyCheck, pressureCheck, volumeCheck,
                fragmentsCheck, populationSizeField, bitFlipRateField, crossoverRateField);
        buttons = new HomeButtons(openBtn, optimizeBtn, exploreBtn);
    }

    public void setApp(MainApp app) {
        this.app = app;
    }

    public Stage getStage() {
        return app.getPrimaryStage();
    }

    @FXML
    private void handleOpenFile() {
        File file = Utils.inpFileChooser().showOpenDialog(getStage());
        try {
            OpenNetworkFile openNetwork = new OpenNetworkFile(file);
            network = openNetwork.get();
            networkInfo.update(openNetwork);
            buttons.open();
        } catch (ParseException e) {
            Utils.showError(getStage(), "Invalid file");
        }
    }

    @FXML
    private void handleOptimizeNetwork() {
        evaluationProgress.reset(input.runs());
        optimizationCharts.reset(input.runs());
        Executors.newSingleThreadExecutor().execute(() -> new OptimizationBuilder(network, this)
                .setAlgorithm(input.algorithm())
                .setMaxEvaluations(input.runs())
                .setEnergy(input.energy())
                .setHead(input.pressure())
                .setVolume(input.volume())
                .setFragments(input.fragments())
                .setPopulationSize(input.population())
                .setBitFlipRate(input.bitFlip())
                .setCrossoverRate(input.crossover())
                .setThreads(input.threads())
                .create()
                .run());
        buttons.optimize();
    }

    @FXML
    private void handleExploreSolutions() {
        try {
            app.showSolutionsWindow(solutions);
            buttons.explore();
        } catch (Exception e) {
            e.printStackTrace();
            Utils.showError(getStage(), "Something went wrong! Can't open solutions explorer.");
        }
    }

    @Override
    public void done(List<Solution> solutions) {
        Platform.runLater(() -> {
            Utils.showInfo(getStage(), "Done", "Optimization finished successfully");
            this.solutions = solutions.stream()
                    .map(sol -> new SolutionModel(network, sol))
                    .collect(toList());
            buttons.done();
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

