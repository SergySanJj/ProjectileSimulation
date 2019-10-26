package com.sergeiyarema.simulation;

import com.jme3.math.Vector2f;

public class Trajectory {
    private Trajectory() {
    }

    public static Vector2f getCoords(DotParams params, float currentTime, float g) {
        float y0 = params.getStartPos().y;
        float x0 = params.getStartPos().x;
        float alpha = (float) Math.toRadians(params.getStartAngle());
        float vx = (float) (params.getStartSpeed() * Math.cos(alpha));
        float vy = (float) (params.getStartSpeed() * Math.sin(alpha));
        float x = x0 + currentTime * vx;
        float y = y0 + currentTime * vy - g * currentTime * currentTime / 2.f;
        return new Vector2f(x, y);
    }

    public static Vector2f getCoords(DotParams params, float currentTime) {
        float g = 9.80665f;
        return getCoords(params, currentTime, g);
    }
}
