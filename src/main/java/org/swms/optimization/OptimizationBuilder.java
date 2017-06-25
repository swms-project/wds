package org.swms.optimization;

import org.addition.epanet.network.Network;

public class OptimizationBuilder {
    private Network network;
    private OptimizationListener listener;
    private String algorithm = "NSGAII";
    private int maxEvaluations = 100;
    private int populationSize = 20;
    private double bitFlipRate = 0.2;
    private double crossoverRate = 0.8;
    private int threads = 1;

    public OptimizationBuilder(Network network, OptimizationListener listener) {
        this.network = network;
        this.listener = listener;
    }

    public OptimizationBuilder setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    public OptimizationBuilder setMaxEvaluations(int maxEvaluations) {
        this.maxEvaluations = maxEvaluations;
        return this;
    }

    public OptimizationBuilder setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
        return this;
    }

    public OptimizationBuilder setBitFlipRate(double bitFlipRate) {
        this.bitFlipRate = bitFlipRate;
        return this;
    }

    public OptimizationBuilder setCrossoverRate(double crossoverRate) {
        this.crossoverRate = crossoverRate;
        return this;
    }

    public OptimizationBuilder setThreads(int threads) {
        this.threads = threads;
        return this;
    }

    public Optimization create() {
        return new Optimization(network, listener, algorithm, maxEvaluations, populationSize, bitFlipRate, crossoverRate, threads);
    }
}
