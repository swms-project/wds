package org.swms.optimization;

import org.addition.epanet.network.Network;
import org.addition.epanet.network.structures.Control;
import org.addition.epanet.network.structures.Link;
import org.addition.epanet.network.structures.Pump;
import org.apache.commons.lang3.SerializationUtils;

import java.util.ArrayList;
import java.util.List;

public class ScheduleResolver {
    Network scheduledNetwork(Network network, List<boolean[]> schedule) {
        Network net = copyNetwork(network);
        List<Pump> pumps = new ArrayList<>(net.getPumps());

        for (int i = 0; i < pumps.size(); i++)
            apply(net, pumps.get(i), schedule.get(i));

        return net;
    }

    private Network copyNetwork(Network network) {
        Network net = SerializationUtils.clone(network);
        net.resetControls();
        net.resetRules();
        return net;
    }

    private void apply(Network net, Pump pump, boolean[] schedule) {
        for (int i = 0; i < schedule.length; i++) {
            if (schedule[i])
                net.addControl(openPumpControl(pump, i));
            else
                net.addControl(closedPumpControl(pump, i));
        }
    }

    private Control openPumpControl(Pump pump, int time) {
        Control control = newControl(pump, time);
        control.setStatus(Link.StatType.OPEN);
        control.setSetting(1);
        return control;
    }

    private Control closedPumpControl(Pump pump, int time) {
        Control control = newControl(pump, time);
        control.setStatus(Link.StatType.CLOSED);
        control.setSetting(0);
        return control;
    }

    private Control newControl(Pump pump, int time) {
        Control control = new Control();
        control.setLink(pump);
        control.setNode(null);
        control.setType(Control.ControlType.TIMER);
        control.setTime(time * 3600l);
        return control;
    }
}
