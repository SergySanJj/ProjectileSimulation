package com.sergeiyarema.simulation;

import com.jme3.math.Vector2f;

public class DotParams {
    private DotParams() {
    }

    public DotParams(Vector2f startPos, float startAngle, float startSpeed) {
        this.startPos = startPos;
        this.startAngle = startAngle;
        this.startSpeed = startSpeed;
    }

    private Vector2f startPos;
    private float startAngle;
    private float startSpeed;

    public Vector2f getStartPos() {
        return startPos;
    }

    public void setStartPos(Vector2f startPos) {
        this.startPos = startPos;
    }

    public float getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
    }

    public float getStartSpeed() {
        return startSpeed;
    }

    public void setStartSpeed(float startSpeed) {
        this.startSpeed = startSpeed;
    }
}