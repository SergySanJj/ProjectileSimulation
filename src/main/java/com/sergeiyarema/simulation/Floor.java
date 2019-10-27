package com.sergeiyarema.simulation;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

public class Floor extends Geometry {
    private static final float HEIGHT = 2.f;
    private static final float WIDTH = 100.f;

    Floor() {
        super("Box", new Box(WIDTH, HEIGHT, 1.f));
        move(new Vector3f(0.f, -5.f, 0.f));
    }

    public float getTopY() {
        return getLocalTranslation().y + HEIGHT / 2.f;
    }
}
