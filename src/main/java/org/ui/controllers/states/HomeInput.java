package org.ui.controllers.states;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import org.swms.optimization.Optimization;

public class HomeInput {
    private final ChoiceBox<String> algorithms;
    private final TextField runs;
    private final Slider threads;
    private final TextField populationSize;
    private final TextField bitFlip;
    private final TextField crossover;

    public HomeInput(ChoiceBox<String> algorithmMenu, TextField runsField, Slider threadsSlider, TextField populationSizeField, TextField bitFlipRateField, TextField crossoverRateField) {
        this.algorithms = algorithmMenu;
        this.runs = runsField;
        this.threads = threadsSlider;
        this.populationSize = populationSizeField;
        this.bitFlip = bitFlipRateField;
        this.crossover = crossoverRateField;
        init();
    }

    private void init() {
        algorithms.getItems().addAll(Optimization.ALGORITHMS);
        threads.setMax(Runtime.getRuntime().availableProcessors());
        threads.valueProperty().addListener((observable, oldValue, newValue) -> threads.setValue(newValue.intValue()));
    }

    public int threads() {
        return (int) threads.getValue();
    }

    public double crossover() {
        return Double.valueOf(crossover.getText());
    }

    public int runs() {
        return Integer.valueOf(runs.getText());
    }

    public String algorithm() {
        return algorithms.getValue();
    }

    public int population() {
        return Integer.valueOf(populationSize.getText());
    }

    public double bitFlip() {
        return Double.valueOf(bitFlip.getText());
    }
}
