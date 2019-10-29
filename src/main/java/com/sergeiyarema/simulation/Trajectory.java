package com.sergeiyarema.simulation;

import com.jme3.math.Vector3f;
import static com.sergeiyarema.simulation.DotParams.*;

public class Trajectory {
    private Trajectory() {
    }

    public static Vector3f getCoords(DotParams params, float currentTime) {
        float x0 = params.getStartPos().x;
        float y0 = params.getStartPos().y;
        float z0 = params.getStartPos().z;
        float alpha = (float) Math.toRadians(params.get(START_ANGLE));
        float vx = (float) (params.get(START_SPEED) * Math.cos(alpha));
        float vy = (float) (params.get(START_SPEED) * Math.sin(alpha));
        float x = currentTime * vx;
        float y = currentTime * vy - params.get(GRAVITY) * currentTime * currentTime / 2.f;
        return new Vector3f(x0 + x, y0 + y, z0);
    }

    public static Vector3f getCoordsFromXValue(DotParams params, float x) {
        float x0 = params.getStartPos().x;
        float y0 = params.getStartPos().y;
        float z0 = params.getStartPos().z;
        x = x - x0;
        float alpha = (float) Math.toRadians(params.get(START_ANGLE));
        float v0 = params.get(START_SPEED);
        float g = params.get(GRAVITY);
        float y = (float) (x * Math.tan(alpha) - (g * x * x) / (2 * v0 * v0 * Math.cos(alpha) * Math.cos(alpha)));
        return new Vector3f(x0 + x, y0 + y, z0);
    }
}
