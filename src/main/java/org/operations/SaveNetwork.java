package org.operations;

import org.addition.epanet.network.Network;
import org.addition.epanet.network.io.output.InpComposer;
import org.addition.epanet.util.ENException;

import java.io.File;
import java.io.IOException;

public class SaveNetwork {
    public static void save(Network network, File file) throws IOException {
        try {
            new InpComposer().composer(network, file);
        } catch (ENException e) {
            throw new IOException(e);
        }
    }
}
