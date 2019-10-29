package com.sergeiyarema.simulation;

import com.jme3.math.Vector2f;

import java.util.HashMap;
import java.util.Map;

public class DotParams implements Copiable<DotParams> {
    private Map<String, ChangeableByDelta> mapping = new HashMap<>();
    private Vector2f startPos;

    private DotParams() {
    }

    public DotParams(Vector2f startPos, float startAngle, float startSpeed, float gravity) {
        this.startPos = new Vector2f(startPos);
        set("StartAngle", startAngle);
        set("StartSpeed", startSpeed);
        set("Gravity", gravity);
    }

    @Override
    public DotParams copy() {
        DotParams paramsCopy =
                new DotParams(startPos, get("StartAngle"), get("StartSpeed"), get("Gravity"));
        return paramsCopy;
    }

    public Vector2f getStartPos() {
        return startPos;
    }

    public void setStartPos(Vector2f startPos) {
        this.startPos = startPos;
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