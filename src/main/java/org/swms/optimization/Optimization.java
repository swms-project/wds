package org.swms.optimization;

import org.addition.epanet.network.Network;
import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Optimization {
    public static final List<String> ALGORITHMS = Arrays.asList("NSGAII", "eMOEA", "Random");
    private final Network network;
    private final OptimizationListener listener;
    private List<Solution> solutions = new ArrayList<>();
    private final String algorithm;
    private final int maxEvaluations;
    private final int populationSize;
    private final double bitFlipRate;
    private final double crossoverRate;

    public Optimization(Network network, OptimizationListener listener, String algorithm, int maxEvaluations, int populationSize, double bitFlipRate, double crossoverRate) {
        this.network = network;
        this.listener = listener;
        this.algorithm = algorithm;
        this.maxEvaluations = maxEvaluations;
        this.populationSize = populationSize;
        this.bitFlipRate = bitFlipRate;
        this.crossoverRate = crossoverRate;
    }

    public void run() {
        NondominatedPopulation result = new Executor()
                .withProblemClass(SchedulingProblem.class
                        , network, listener)
                .withAlgorithm(algorithm)
                .withProperty("populationSize", populationSize)
                .withProperty("bf.rate", bitFlipRate)
                .withProperty("1x.rate", crossoverRate)
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
