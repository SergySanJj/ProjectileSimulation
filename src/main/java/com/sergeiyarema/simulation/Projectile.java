package com.sergeiyarema.simulation;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;
import com.jme3.asset.AssetManager;

public class Projectile extends Geometry {
    private DotParams dotParams;

    private Projectile() {
    }

    public Projectile(DotParams dotParams) {
        super("Sphere", new Sphere(32, 32, 0.4f, true, false));
        this.dotParams = dotParams;
        move(new Vector3f(dotParams.getStartPos().x, dotParams.getStartPos().y, 0.f));
    }


}
