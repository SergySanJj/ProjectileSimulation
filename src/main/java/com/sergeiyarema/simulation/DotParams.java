package com.sergeiyarema.simulation;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.sergeiyarema.misc.Copiable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DotParams implements Copiable<DotParams>, Comparable<DotParams> {
    public static final String GRAVITY = "Gravity";
    public static final String START_ANGLE = "StartAngle";
    public static final String START_SPEED = "StartSpeed";
    public static final String GROUND_LEVEL = "GroundLevel";
    public static final String RADIUS = "Radius";

    public static final Vector2f ANGLE_LIMIT = new Vector2f(10f, 170f);
    public static final Vector2f SPEED_LIMIT = new Vector2f(0f, 200f);
    public static final Vector2f GRAVITY_LIMIT = new Vector2f(0.1f, 100f);

    private Map<String, ChangeableByDelta> mapping = new HashMap<>();
    private Vector3f startPos;

    private DotParams() {
    }

    public DotParams(Vector3f startPos, float startAngle, float startSpeed, float groundLevel, float gravity) {
        this.startPos = startPos.clone();
        set(START_ANGLE, startAngle).setBoundary(ANGLE_LIMIT);
        set(START_SPEED, startSpeed).setBoundary(SPEED_LIMIT);
        set(GRAVITY, gravity).setBoundary(GRAVITY_LIMIT);
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


    public ChangeableByDelta set(String valueName, float newValue) {
        ChangeableByDelta newChangeable = mapping.get(valueName);
        if (newChangeable == null) {
            newChangeable = new ChangeableByDelta();
            mapping.put(valueName, newChangeable);
        }
        newChangeable.setValue(newValue);
        return newChangeable;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DotParams dt = (DotParams) o;
        return (this.compareTo(dt) == 0);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startPos, mapping);
    }
}