package org.ui.controllers.states;

import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import org.swms.optimization.SimulationNetwork;

import java.util.concurrent.atomic.AtomicInteger;

public class OptimizationCharts {
    private final AtomicInteger solutionsCount = new AtomicInteger(-1);
    private final XYChart.Series<Integer, Double> energySeries = new XYChart.Series<>();
    private final XYChart.Series<Integer, Double> pressureSeries = new XYChart.Series<>();
    private final XYChart.Series<Integer, Double> tanksSeries = new XYChart.Series<>();
    private final XYChart.Series<Integer, Integer> fragmentsSeries = new XYChart.Series<>();

    public OptimizationCharts(AreaChart<Integer, Double> energyChart, AreaChart<Integer, Double> pressureChart, AreaChart<Integer, Double> tanksChart, AreaChart<Integer, Integer> fragmentsChart) {
        addSeries(energyChart, energySeries, 3);
        addSeries(pressureChart, pressureSeries, 1);
        addSeries(tanksChart, tanksSeries, 2);
        fragmentsChart.getData().add(fragmentsSeries);
    }

    private <T> void addSeries(AreaChart<Integer, T> chart, XYChart.Series<Integer, T> series, int index) {
        for (int i = 0; i < index; i++) {
            chart.getData().add(new XYChart.Series<>());
        }
        chart.getData().add(series);
    }

    public void update(SimulationNetwork solution) {
        if (solution.isValid()) {
            int count = solutionsCount.incrementAndGet();
            appendData(energySeries, count, solution.consumedEnergy());
            appendData(pressureSeries, count, solution.totalHead());
            appendData(tanksSeries, count, solution.tanks());
            appendData(fragmentsSeries, count, solution.fragmentsCount());
        }
    }

    private <T> void appendData(XYChart.Series<Integer, T> series, int x, T value) {
        series.getData().add(new XYChart.Data<>(x, value));
    }

    public void reset() {
        solutionsCount.set(0);
        energySeries.getData().clear();
        pressureSeries.getData().clear();
        tanksSeries.getData().clear();
        fragmentsSeries.getData().clear();
    }
}
