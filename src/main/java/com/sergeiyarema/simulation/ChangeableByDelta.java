package com.sergeiyarema.simulation;

public class ChangeableByDelta {
    private float value;

    public void changeBy(float delta) {
        value += delta;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float newValue) {
        value = newValue;
    }
}
