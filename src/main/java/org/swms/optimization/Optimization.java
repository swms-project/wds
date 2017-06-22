package org.swms.optimization;

import org.addition.epanet.network.Network;
import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;

import java.util.ArrayList;
import java.util.List;

public class Optimization {
    private final Network network;
    private final OptimizationListener listener;
    private List<Solution> solutions = new ArrayList<>();
    private final String algorithm;
    private final int maxEvaluations;

    public Optimization(Network network, OptimizationListener listener, String algorithm, int maxEvaluations) {
        this.network = network;
        this.listener = listener;
        this.algorithm = algorithm;
        this.maxEvaluations = maxEvaluations;
    }

    public void run() {
        NondominatedPopulation result = new Executor()
                .withProblemClass(SchedulingProblem.class
                        , network, listener)
                .withAlgorithm(algorithm)
                .withMaxEvaluations(maxEvaluations)
                .distributeOnAllCores()
                .run();
        result.forEach(solutions::add);
        listener.done(solutions);
    }

    public List<Solution> solutions() {
        return solutions;
    }
}
