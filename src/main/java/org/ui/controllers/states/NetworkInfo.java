package org.ui.controllers.states;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import org.operations.OpenNetworkFile;

public class NetworkInfo {
    private final StringProperty name = new SimpleStringProperty("No network selected");
    private final StringProperty nodesCount = new SimpleStringProperty("0 node");
    private final StringProperty pipesCount = new SimpleStringProperty("0 pipe");
    private final StringProperty pumpsCount = new SimpleStringProperty("0 pump");
    private final StringProperty tanksCount = new SimpleStringProperty("0 tank");

    public NetworkInfo(Label nameLabel, Label nodesCountLabel, Label pipesCountLabel, Label pumpsCountLabel, Label tanksCountLabel) {
        nameLabel.textProperty().bind(name);
        nodesCountLabel.textProperty().bind(nodesCount);
        pipesCountLabel.textProperty().bind(pipesCount);
        pumpsCountLabel.textProperty().bind(pumpsCount);
        tanksCountLabel.textProperty().bind(tanksCount);
    }

    public void update(OpenNetworkFile openNetwork) {
        name.set("Network " + openNetwork.networkName());
        nodesCount.set(String.valueOf(openNetwork.nodesCount()) + " nodes");
        pipesCount.set(String.valueOf(openNetwork.pipesCount()) + " pipes");
        pumpsCount.set(String.valueOf(openNetwork.pumpsCount()) + " pumps");
        tanksCount.set(String.valueOf(openNetwork.tanksCount()) + " tanks");
    }
}
