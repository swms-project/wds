package org.swms.optimization;

public class Weights {
    public final double energy;
    public final double head;
    public final double volume;
    public final double fragments;

    public Weights() {
        this(0, 0, 0, 0);
    }

    private Weights(double energy, double head, double volume, double fragments) {
        this.energy = energy;
        this.head = head;
        this.volume = volume;
        this.fragments = fragments;
    }

    public Weights setEnergy(double energy) {
        return new Weights(energy, head, volume, fragments);
    }

    public Weights setHead(double head) {
        return new Weights(energy, head, volume, fragments);
    }

    public Weights setVolume(double volume) {
        return new Weights(energy, head, volume, fragments);
    }

    public Weights setFragments(double fragments) {
        return new Weights(energy, head, volume, fragments);
    }
}
