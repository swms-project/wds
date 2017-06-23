package org.ui.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.addition.epanet.network.Network;
import org.addition.epanet.network.structures.Pump;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.swms.optimization.ScheduleResolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolutionModel {
    private final Network network;
    private final Map<String, boolean[]> pumpingSchedule = new HashMap<>();
    private final DoubleProperty consumedEnergy;
    private final DoubleProperty pressure;
    private final DoubleProperty tanksVolume;
    private final IntegerProperty fragments;

    public SolutionModel(Network network, Solution solution) {
        consumedEnergy = new SimpleDoubleProperty(solution.getObjective(0));
        pressure = new SimpleDoubleProperty(solution.getObjective(1));
        tanksVolume = new SimpleDoubleProperty(-solution.getObjective(2));
        fragments = new SimpleIntegerProperty((int) solution.getObjective(3));
        this.network = initNetwork(network, solution);
    }

    public Network initNetwork(Network network, Solution solution) {
        ArrayList<Pump> pumps = new ArrayList<>(network.getPumps());
        List<boolean[]> schedule = new ArrayList<>();
        for (int i = 0; i < solution.getNumberOfVariables(); i++) {
            boolean[] s = EncodingUtils.getBinary(solution.getVariable(i));
            schedule.add(s);
            pumpingSchedule.put(pumps.get(i).getId(), s);
        }
        return new ScheduleResolver().scheduledNetwork(network, schedule);
    }

    public Network getNetwork() {
        return network;
    }

    public Map<String, boolean[]> getPumpingSchedule() {
        return pumpingSchedule;
    }

    public double getConsumedEnergy() {
        return consumedEnergy.get();
    }

    public DoubleProperty consumedEnergyProperty() {
        return consumedEnergy;
    }

    public double getPressure() {
        return pressure.get();
    }

    public DoubleProperty pressureProperty() {
        return pressure;
    }

    public double getTanksVolume() {
        return tanksVolume.get();
    }

    public DoubleProperty tanksVolumeProperty() {
        return tanksVolume;
    }

    public int getFragments() {
        return fragments.get();
    }

    public IntegerProperty fragmentsProperty() {
        return fragments;
    }
}
