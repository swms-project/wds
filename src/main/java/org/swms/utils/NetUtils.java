package org.swms.utils;

import org.addition.epanet.network.Network;

public class NetUtils {

    public static Network clone(Network network) {
        try {
            return network.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
