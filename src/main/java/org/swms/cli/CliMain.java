package org.swms.cli;

import org.addition.epanet.network.Network;
import org.addition.epanet.network.io.input.InpParser;
import org.addition.epanet.util.ENException;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CliMain {
    public static void main(String[] args) {
        String filePath = args[0];
        Network network = new Network();
        Logger logger = Logger.getAnonymousLogger();
        logger.setLevel(Level.OFF);
        try {
            new InpParser(logger).parse(network, new File(filePath));
        } catch (ENException e) {
            throw new RuntimeException(e);
        }

        Application app = new Application();
        app.run(network);
    }
}
