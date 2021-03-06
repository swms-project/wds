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
    private final Weights weights;
    private final OptimizationListener listener;
    private List<Solution> solutions = new ArrayList<>();
    private final String algorithm;
    private final int maxEvaluations;
    private final int populationSize;
    private final double bitFlipRate;
    private final double crossoverRate;
    private final int threads;

    public Optimization(Network network, Weights weights, OptimizationListener listener, String algorithm, int maxEvaluations, int populationSize, double bitFlipRate, double crossoverRate, int threads) {
        this.network = network;
        this.weights = weights;
        this.listener = listener;
        this.algorithm = algorithm;
        this.maxEvaluations = maxEvaluations;
        this.populationSize = populationSize;
        this.bitFlipRate = bitFlipRate;
        this.crossoverRate = crossoverRate;
        this.threads = threads;
    }

    public void run() {
        NondominatedPopulation result = new Executor()
                .withProblemClass(SchedulingProblem.class
                        , network, weights, listener)
                .withAlgorithm(algorithm)
                .withProperty("populationSize", populationSize)
                .withProperty("bf.rate", bitFlipRate)
                .withProperty("1x.rate", crossoverRate)
                .withMaxEvaluations(maxEvaluations)
                .distributeOn(threads)
                .run();
        result.forEach(solutions::add);
        listener.done(solutions);
    }

    public List<Solution> solutions() {
        return solutions;
    }
}
