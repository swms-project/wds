package org.swms.cli;

import org.addition.epanet.network.Network;
import org.moeaframework.core.Solution;
import org.moeaframework.core.Variable;
import org.swms.optimization.Optimization;
import org.swms.optimization.OptimizationListener;
import org.swms.optimization.SimulationNetwork;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Application implements OptimizationListener {

    private final AtomicInteger solutionsCount = new AtomicInteger(0);
    private final AtomicInteger invalidCount = new AtomicInteger(0);

    public void run(Network network) {
        solutionsCount.set(0);
        invalidCount.set(0);
        new Optimization(network, this).run();
    }

    @Override
    public void done(List<Solution> solutions) {
        System.out.println("Done");
        System.out.println(invalidCount.get() + " invalid solutions");
        for (Solution solution : solutions) {
            output(solution);
        }
    }

    private void output(Solution solution) {
        System.out.println("---------------------");
        System.out.println(Arrays.toString(solution.getObjectives()));
        for (int i = 0; i < solution.getNumberOfVariables(); i++) {
            Variable variable = solution.getVariable(i);
            System.out.println(variable);
        }
    }

    @Override
    public void newEvaluation(SimulationNetwork network) {
        int count = solutionsCount.incrementAndGet();
        if (network.isValid())
            report(network, count);
        else
            invalidCount.incrementAndGet();
    }

    private void report(SimulationNetwork network, int count) {
        System.out.printf("#%3d:\t Energy %.2f%n", count, network.consumedEnergy());
    }

}
