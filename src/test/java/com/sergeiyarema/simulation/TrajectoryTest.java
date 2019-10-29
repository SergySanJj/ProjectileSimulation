package com.sergeiyarema.simulation;

import com.jme3.math.Vector2f;
import org.junit.Test;

import static org.junit.Assert.*;

public class TrajectoryTest {
    public static boolean assertEquals(Vector2f a, Vector2f b) {
        float delta = Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
        return (delta < 0.001f);
    }

    @Test
    public void getCoords() {
        float g = 9.80665f;
        Vector2f translation = new Vector2f(0.f, 0.f);
        DotParams dotParams = new DotParams(translation, 45.f, 10f, g);
        assertEquals(new Vector2f(0.f, 0.f).add(translation), Trajectory.getCoords(dotParams, 0.f));
        assertEquals(new Vector2f(10.196f, -0.003f).add(translation), Trajectory.getCoords(dotParams, 1.442f));

        translation = new Vector2f(10.f, 20.f);
        dotParams = new DotParams(translation, 45.f, 10f, g);
        assertEquals(new Vector2f(0.f, 0.f).add(translation), Trajectory.getCoords(dotParams, 0.f));
        assertEquals(new Vector2f(10.196f, -0.003f).add(translation), Trajectory.getCoords(dotParams, 1.442f));

        translation = new Vector2f(-10.f, -20.f);
        dotParams = new DotParams(translation, 45.f, 10f, g);
        assertEquals(new Vector2f(0.f, 0.f).add(translation), Trajectory.getCoords(dotParams, 0.f));
        assertEquals(new Vector2f(10.196f, -0.003f).add(translation), Trajectory.getCoords(dotParams, 1.442f));
    }

    @Test
    public void getCoordsFromXValue() {
        float g = 9.80665f;
        Vector2f translation = new Vector2f(0.f, 0.f);
        DotParams dotParams = new DotParams(translation, 45.f, 10f, g);

    }
}