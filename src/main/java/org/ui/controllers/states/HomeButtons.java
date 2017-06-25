package org.ui.controllers.states;

import javafx.scene.control.Button;

public class HomeButtons {
    private final Button open;
    private final Button optimize;
    private final Button explore;

    public HomeButtons(Button openBtn, Button optimizeBtn, Button exploreBtn) {
        open = openBtn;
        optimize = optimizeBtn;
        explore = exploreBtn;
        setDefault(open);
    }

    public void open() {
        optimize.setDisable(false);
        explore.setDisable(true);
        setDefault(optimize);
    }

    public void optimize() {
        open.setDisable(true);
        optimize.setDisable(true);
        explore.setDisable(true);
    }

    public void explore() {
        setDefault(open);
    }

    public void done() {
        open.setDisable(false);
        optimize.setDisable(false);
        explore.setDisable(false);
        setDefault(explore);
    }

    private void setDefault(Button btn) {
        open.setDefaultButton(false);
        optimize.setDefaultButton(false);
        explore.setDefaultButton(false);
        btn.setDefaultButton(true);
    }
}
