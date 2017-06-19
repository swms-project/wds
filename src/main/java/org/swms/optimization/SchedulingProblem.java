package org.swms.optimization;

import org.addition.epanet.network.Network;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.Variable;
import org.moeaframework.core.variable.EncodingUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SchedulingProblem implements Problem {
    private final SimulationNetwork network;
    private final OptimizationListener listener;

    public SchedulingProblem(Network network, OptimizationListener listener) {
        super();
        this.network = new SimulationNetwork(network);
        this.listener = listener;
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
        return 4;
    }

    @Override
    public int getNumberOfConstraints() {
        return 1;
    }

    @Override
    public void evaluate(Solution solution) {
        SimulationNetwork net = this.network.withPumpingSchedule(schedule(solution));
        net.simulate();
        List<Objective> objectives = objectives(net, solution);
        for (int i = 0; i < objectives.size(); i++)
            solution.setObjective(i, objectives.get(i).value);
        solution.setConstraint(0, net.isValid() ? 0 : 1);
        listener.newEvaluation(net, objectives);
    }

    private List<boolean[]> schedule(Solution solution) {
        List<boolean[]> schedule = new ArrayList<>();
        for (int i = 0; i < getNumberOfVariables(); i++) {
            Variable variable = solution.getVariable(i);
            schedule.add(EncodingUtils.getBinary(variable));
        }
        return schedule;
    }

    private List<Objective> objectives(SimulationNetwork net, Solution solution) {
        return Arrays.asList(
                new Objective("Energy", net.consumedEnergy()),
                new Objective("Pressure", net.totalHead()),
                new Objective("Tanks", -net.tanks()),
                new Objective("Fragments", fragments(schedule(solution))));
    }

    private int fragments(List<boolean[]> schedule) {
        int res = 0;
        for (boolean[] s : schedule) {
            boolean prev = false;
            for (boolean b : s) {
                if (b && !prev) ++res;
                prev = b;
            }
        }
        return res;
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
