package org.operations;

import org.addition.epanet.network.Network;
import org.addition.epanet.network.io.input.InpParser;
import org.addition.epanet.util.ENException;
import org.operations.exceptions.ParseException;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OpenNetworkFile {
    private final File file;
    private final Network network;

    public OpenNetworkFile(File file) throws ParseException {
        this.file = file;
        this.network = parseFile(file);
    }

    private Network parseFile(File file) throws ParseException {
        Network net = new Network();
        Logger log = Logger.getAnonymousLogger();
        log.setLevel(Level.OFF);
        try {
            new InpParser(log).parse(net, file);
        } catch (ENException e) {
            throw new ParseException(e);
        }
        return net;
    }

    public Network get() {
        return network;
    }

    public String networkName() {
        return file.getName().replace(".inp", "");
    }

    public int nodesCount() {
        return network.getNodes().size();
    }

    public int pipesCount() {
        return network.getLinks().size();
    }

    public int pumpsCount() {
        return network.getPumps().size();
    }

    public int tanksCount() {
        return network.getTanks().size();
    }
}

