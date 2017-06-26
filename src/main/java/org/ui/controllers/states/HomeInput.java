package org.ui.controllers.states;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import org.swms.optimization.Optimization;

public class HomeInput {
    private final ChoiceBox<String> algorithms;
    private final TextField runs;
    private final Slider threads;
    private final CheckBox energy;
    private final CheckBox pressure;
    private final CheckBox volume;
    private final CheckBox fragments;
    private final TextField populationSize;
    private final TextField bitFlip;
    private final TextField crossover;

    public HomeInput(ChoiceBox<String> algorithmMenu, TextField runsField, Slider threadsSlider, CheckBox energyCheck, CheckBox pressureCheck, CheckBox volumeCheck, CheckBox fragmentsCheck, TextField populationSizeField, TextField bitFlipRateField, TextField crossoverRateField) {
        this.algorithms = algorithmMenu;
        this.runs = runsField;
        this.threads = threadsSlider;
        this.energy = energyCheck;
        this.pressure = pressureCheck;
        this.volume = volumeCheck;
        this.fragments = fragmentsCheck;
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

    public boolean energy() {
        return energy.isSelected();
    }

    public boolean pressure() {
        return pressure.isSelected();
    }

    public boolean volume() {
        return volume.isSelected();
    }

    public boolean fragments() {
        return fragments.isSelected();
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
