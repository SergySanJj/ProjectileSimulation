package com.sergeiyarema.simulation;

import com.jme3.math.Vector3f;

import java.util.HashMap;
import java.util.Map;

public class DotParams implements Copiable<DotParams>, Comparable<DotParams> {
    public static final String GRAVITY = "Gravity";
    public static final String START_ANGLE = "StartAngle";
    public static final String START_SPEED = "StartSpeed";
    public static final String GROUND_LEVEL = "GroundLevel";
    public static final String RADIUS = "Radius";

    private Map<String, ChangeableByDelta> mapping = new HashMap<>();
    private Vector3f startPos;

    private DotParams() {
    }

    public DotParams(Vector3f startPos, float startAngle, float startSpeed, float groundLevel, float gravity) {
        this.startPos = startPos.clone();
        set(START_ANGLE, startAngle);
        set(START_SPEED, startSpeed);
        set(GRAVITY, gravity);
        set(GROUND_LEVEL, groundLevel);
        set(RADIUS, 0.5f);
    }

    @Override
    public DotParams copy() {
        return new DotParams(startPos, get(START_ANGLE), get(START_SPEED), get(GROUND_LEVEL), get(GRAVITY));
    }

    public Vector3f getStartPos() {
        return startPos;
    }

    public void setStartPos(Vector3f startPos) {
        this.startPos = startPos.clone();
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

    @Override
    public int compareTo(DotParams dotParams) {
        if (this.hashCode() == dotParams.hashCode())
            return 0;

        boolean mappingCheck = true;
        for (Map.Entry<String, ChangeableByDelta> entry : mapping.entrySet()) {
            if (dotParams.get(entry.getKey()) - entry.getValue().getValue() > 0.001) {
                mappingCheck = false;
                break;
            }
        }
        if (startPos.equals(dotParams.getStartPos()) && mappingCheck) return 0;
        else return -1;
    }
}