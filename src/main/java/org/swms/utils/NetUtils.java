package org.swms.utils;

import org.addition.epanet.network.Network;
import org.apache.commons.lang3.SerializationUtils;

public class NetUtils {
    public static Network copy(Network network) {
        return SerializationUtils.clone(network);
    }
}
