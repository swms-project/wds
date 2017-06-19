package org.swms.optimization;

import org.addition.epanet.hydraulic.HydraulicSim;
import org.addition.epanet.hydraulic.structures.SimulationNode;
import org.addition.epanet.hydraulic.structures.SimulationTank;
import org.addition.epanet.network.Network;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimulationNetwork {
    private static final Logger logger = Logger.getAnonymousLogger();

    static {
        logger.setLevel(Level.OFF);
    }

    private final Network network;
    private final List<boolean[]> schedule;
    private final ScheduleResolver scheduleResolver = new ScheduleResolver();
    private double energy = 0;
    private double head = 0;
    private double tanks = 0;
    private boolean valid = true;

    public SimulationNetwork(Network network) {
        this.network = network;
        this.schedule = new ArrayList<>();
    }

    public SimulationNetwork(Network network, List<boolean[]> schedule) {
        this.network = network;
        this.schedule = schedule;
    }

    public SimulationNetwork withPumpingSchedule(List<boolean[]> schedule) {
        return new SimulationNetwork(scheduleResolver.scheduledNetwork(network, schedule), schedule);
    }

    public void simulate() {
        try {
            HydraulicSim sim = new HydraulicSim(network, logger);
            while (sim.getHtime() < network.getPropertiesMap().getDuration()) {
                sim.simulateSingleStep();
                valid = valid && isNegativePressure(sim);
                if (!valid) return;
                if (sim.getHtime() % network.getPropertiesMap().getHstep() == 0)
                    head += networkHead(sim);
            }

            tanks = sim.getnTanks().stream().mapToDouble(SimulationTank::getSimVolume).sum();
            energy = networkEnergy(sim);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private double networkEnergy(HydraulicSim sim) {
        return sim.getnPumps().stream().mapToDouble(p -> p.getEnergy(3)).sum();
    }

    private double networkHead(HydraulicSim sim) {
        return sim.getnNodes().stream().mapToDouble(SimulationNode::getSimHead).sum();
    }

    private boolean isNegativePressure(HydraulicSim sim) {
        return sim.getnNodes().stream().allMatch(node -> node.getSimHead() >= 0);
    }

    public boolean isValid() {
        return valid;
    }

    public double consumedEnergy() {
        return energy;
    }

    public double totalHead() {
        return head;
    }

    public double tanks() {
        return tanks;
    }

    public Network getNetwork() {
        return network;
    }

    public int pumpsNumber() {
        return network.getPumps().size();
    }

    int fragmentsCount() {
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
}
