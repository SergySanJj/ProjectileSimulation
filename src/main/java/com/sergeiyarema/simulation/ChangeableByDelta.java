package com.sergeiyarema.simulation;

import com.jme3.math.Vector2f;
import com.sergeiyarema.misc.Misc;

public class ChangeableByDelta {
    private float value;
    private Vector2f boundary;

    public void changeBy(float delta) {
        if (boundary == null)
            value += delta;
        else {
            value = Misc.bound(value + delta, boundary);
        }
    }

    public void setBoundary(Vector2f bounder) {
        boundary = bounder;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float newValue) {
        value = newValue;
    }
}
