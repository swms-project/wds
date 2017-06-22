package org.ui.controllers.states;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import org.swms.optimization.SimulationNetwork;

import java.util.concurrent.atomic.AtomicInteger;

public class EvaluationProgress {
    private int totalSolutions = 100;
    private final AtomicInteger solutionsCounter = new AtomicInteger(0);
    private final AtomicInteger invalidCounter = new AtomicInteger(0);

    private final IntegerProperty solutions = new SimpleIntegerProperty(0);
    private final IntegerProperty invalidSolutions = new SimpleIntegerProperty(0);
    private final DoubleProperty progress = new SimpleDoubleProperty(0);

    public EvaluationProgress(ProgressBar progressBar, Label solutionsCountLabel, Label invalidCountLabel) {
        progressBar.progressProperty().bind(progress);
        solutionsCountLabel.textProperty().bind(solutions.asString());
        invalidCountLabel.textProperty().bind(invalidSolutions.asString());
    }

    public void update(SimulationNetwork solution) {
        int count = solutionsCounter.incrementAndGet();
        solutions.set(count);
        progress.set((double) count / totalSolutions);
        if (!solution.isValid())
            invalidSolutions.set(invalidCounter.incrementAndGet());
    }

    public void reset(int totalSolutions) {
        this.totalSolutions = totalSolutions;
        progress.set(0);
        solutionsCounter.set(0);
        invalidCounter.set(0);
    }
}
