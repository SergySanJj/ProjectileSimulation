package com.sergeiyarema.simulation;

import com.jme3.math.Vector2f;

public class Trajectory {
    private Trajectory() {
    }

    public static Vector2f getCoords(DotParams params, float currentTime, float g) {
        float scale = 10.f;
        float y0 = params.getStartPos().y;
        float x0 = params.getStartPos().x;
        float alpha = (float) Math.toRadians(params.get("StartAngle"));
        float vx = (float) (params.get("StartSpeed") * Math.cos(alpha));
        float vy = (float) (params.get("StartSpeed") * Math.sin(alpha));
        float x = currentTime * vx;
        float y = currentTime * vy - g * currentTime * currentTime / 2.f;
        x *= scale;
        y *= scale;
        return new Vector2f(x0 + x, y0 + y);

    }

    public static Vector2f getCoords(DotParams params, float currentTime) {
        float g = 9.80665f;
        return getCoords(params, currentTime, g);
    }
}
