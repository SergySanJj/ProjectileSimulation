package com.sergeiyarema.simulation;

import com.jme3.math.Vector3f;
import org.junit.Assert;
import org.junit.Test;

public class TrajectoryTest {
    private static boolean isIdentical(Vector3f a, Vector3f b) {
        float delta = Math.abs(a.x - b.x) + Math.abs(a.y - b.y) + Math.abs(a.z - b.z);
        return (delta < 0.1f);
    }

    @Test
    public void getCoords() {
        float g = 9.80665f;
        Vector3f translation = new Vector3f(0.f, 0.f, 0.f);
        boolean t = translationCase(translation, g);
        Assert.assertTrue(t);

        translation = new Vector3f(10.f, 20.f, 0.f);
        t = translationCase(translation, g);
        Assert.assertTrue(t);

        translation = new Vector3f(-10.f, -20.f, 0.f);
        t = translationCase(translation, g);
        Assert.assertTrue(t);
    }

    private boolean translationCase(Vector3f translation, float g) {
        DotParams dotParams = new DotParams(translation, 45.f, 10f, -3f, g);
        return
                isIdentical(new Vector3f(0.f, 0.f, 0.f).add(translation), Trajectory.getCoords(dotParams, 0.f)) &&
                        isIdentical(new Vector3f(10.196f, -0.003f, 0.f).add(translation), Trajectory.getCoords(dotParams, 1.442f));
    }

    @Test
    public void getCoordsFromXValue() {
        float g = 9.80665f;
        Vector3f translation = new Vector3f(0.f, 0.f, 0.f);
        Assert.assertTrue(trajectoryFromX(translation, 0.f, g));
        Assert.assertTrue(trajectoryFromX(translation, 2.f, g));
        Assert.assertTrue(trajectoryFromX(translation, 10.f, g));
        Assert.assertTrue(trajectoryFromX(translation, 100.f, g));
    }

    private boolean trajectoryFromX(Vector3f translation, float time, float g) {
        DotParams dotParams = new DotParams(translation, 45.f, 10f,-3f, g);
        Vector3f actualNewCoords = Trajectory.getCoords(dotParams, time);
        Vector3f dotCoords = Trajectory.getCoordsFromXValue(dotParams, actualNewCoords.x);
        return isIdentical(dotCoords, actualNewCoords);
    }
}