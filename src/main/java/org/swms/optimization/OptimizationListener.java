package org.swms.optimization;

import org.moeaframework.core.Solution;

import java.util.List;

public interface OptimizationListener {
    void done(List<Solution> solutions);

    void newEvaluation(SimulationNetwork network);
}
