package org.swms.optimization;

public class Objective {
    private final String name;
    public final double value;

    public Objective(String name, double value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("%s\t%.0f", name, value);
    }
}
