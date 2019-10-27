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
    private float totalTime;

    private Projectile() {
    }

    public Projectile(DotParams dotParams) {
        super("Projectile", new Sphere(32, 32, 0.4f, true, false));
        this.dotParams = dotParams;
        move(new Vector3f(dotParams.getStartPos().x, dotParams.getStartPos().y, 0.f));
        totalTime = 0.f;
    }

    public DotParams getDotParams() {
        return dotParams;
    }

    public void updateMove(float tpf) {
        totalTime += tpf;
        Vector2f newCoords = Trajectory.getCoords(dotParams, totalTime / 1000.f);
        move(new Vector3f(newCoords.x, newCoords.y, 0.f));
        System.out.println(Double.toString(newCoords.x) + " " + Double.toString(newCoords.y));
    }

}
