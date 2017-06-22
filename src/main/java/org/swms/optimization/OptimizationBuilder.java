package org.swms.optimization;

import org.addition.epanet.network.Network;

public class OptimizationBuilder {
    private Network network;
    private OptimizationListener listener;
    private String algorithm = "NSGAII";
    private int maxEvaluations = 100;

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

    public Optimization create() {
        return new Optimization(network, listener, algorithm, maxEvaluations);
    }
}
