package com.sergeiyarema.simulation;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

public class Floor extends Geometry {
    Floor() {
        super("Box", new Box(100.f, 2.f, 10.f));
        move(new Vector3f(0.f, -5.f, 0.f));
    }
}
