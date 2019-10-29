package com.sergeiyarema.simulation;

import com.jme3.math.Vector3f;

import java.util.HashMap;
import java.util.Map;

public class DotParams implements Copiable<DotParams> {
    public static final String GRAVITY = "Gravity";
    public static final String START_ANGLE = "StartAngle";
    public static final String START_SPEED = "StartSpeed";

    private Map<String, ChangeableByDelta> mapping = new HashMap<>();
    private Vector3f startPos;

    private DotParams() {
    }

    public DotParams(Vector3f startPos, float startAngle, float startSpeed, float gravity) {
        this.startPos = new Vector3f(startPos);
        set(START_ANGLE, startAngle);
        set(START_SPEED, startSpeed);
        set(GRAVITY, gravity);
    }

    @Override
    public DotParams copy() {
        return new DotParams(startPos, get(START_ANGLE), get(START_SPEED), get(GRAVITY));
    }

    public Vector3f getStartPos() {
        return startPos;
    }

    public void setStartPos(Vector3f startPos) {
        this.startPos = new Vector3f(startPos);
    }

    public float get(String valueName) {
        return getChangeable(valueName).getValue();
    }

    public ChangeableByDelta getChangeable(String valueName) {
        return mapping.get(valueName);
    }

    public void set(String valueName, float newValue) {
        ChangeableByDelta newChangeable = new ChangeableByDelta();
        newChangeable.setValue(newValue);
        mapping.put(valueName, newChangeable);
    }
}