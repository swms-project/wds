package org.swms.optimization;

import org.addition.epanet.network.Network;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.Variable;
import org.moeaframework.core.variable.EncodingUtils;

import java.util.ArrayList;
import java.util.List;

public class SchedulingProblem implements Problem {
    private final SimulationNetwork network;

    public SchedulingProblem(Network network) {
        super();
        this.network = new SimulationNetwork(network);
    }

    @Override
    public String getName() {
        return "Scheduling";
    }

    @Override
    public int getNumberOfVariables() {
        return network.pumpsNumber();
    }

    @Override
    public int getNumberOfObjectives() {
        return 1;
    }

    @Override
    public int getNumberOfConstraints() {
        return 1;
    }

    @Override
    public void evaluate(Solution solution) {
        SimulationNetwork net = this.network.withPumpingSchedule(schedule(solution));
        net.simulate();
        solution.setObjective(0, net.consumedEnergy());
        solution.setConstraint(0, net.isValid() ? 0 : 1);
    }

    private List<boolean[]> schedule(Solution solution) {
        List<boolean[]> schedule = new ArrayList<>();
        for (int i = 0; i < getNumberOfVariables(); i++) {
            Variable variable = solution.getVariable(i);
            schedule.add(EncodingUtils.getBinary(variable));
        }
        return schedule;
    }

    @Override
    public Solution newSolution() {
        Solution solution = new Solution(getNumberOfVariables(), getNumberOfObjectives(), getNumberOfConstraints());
        for (int i = 0; i < network.pumpsNumber(); i++)
            solution.setVariable(i, EncodingUtils.newBinary(24));
        return solution;
    }

    @Override
    public void close() {

    }
}
