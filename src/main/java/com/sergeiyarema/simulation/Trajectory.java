package com.sergeiyarema.simulation;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

import java.util.Map;

public class Trajectory {
    private Trajectory() {
    }

    public static Vector2f getCoords(DotParams params, float currentTime) {
        float y0 = params.getStartPos().y;
        float x0 = params.getStartPos().x;
        float alpha = (float) Math.toRadians(params.get("StartAngle"));
        float vx = (float) (params.get("StartSpeed") * Math.cos(alpha));
        float vy = (float) (params.get("StartSpeed") * Math.sin(alpha));
        float x = currentTime * vx;
        float y = currentTime * vy - params.get("Gravity") * currentTime * currentTime / 2.f;
        return new Vector2f(x0 + x, y0 + y);
    }

    public static Vector3f getCoordsFromXValue(DotParams params, float x) {
        float y0 = params.getStartPos().y;
        float x0 = params.getStartPos().x;
        x = x - x0;
        float alpha = (float) Math.toRadians(params.get("StartAngle"));
        float v0 = params.get("StartSpeed");
        float g = params.get("Gravity");
        float y = (float) (x * Math.tan(alpha) - (g * x * x) / (2 * v0 * v0 * Math.cos(alpha) * Math.cos(alpha)));
        return new Vector3f(x0 + x, y0 + y, 0.f);
    }
}
