package com.sergeiyarema.simulation;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

import static com.sergeiyarema.simulation.DotParams.RADIUS;

public class Projectile {
    private DotParams dotParams;
    private Geometry geometry;
    private ParabolicControl control;


    private Projectile() {
    }

    public Projectile(DotParams dotParams, Node rootNode) {

        Material matBlue = new Material(GlobalAssets.manager(), "Common/MatDefs/Misc/Unshaded.j3md");
        matBlue.setColor("Color", ColorRGBA.Blue);
        geometry =
                new Geometry("Projectile",
                        new Sphere(32, 32, dotParams.get(RADIUS), true, false));
        geometry.setMaterial(matBlue);
        this.dotParams = dotParams.copy();
        rootNode.attachChild(geometry);
    }

    public void fire(DotParams dotParams) {
        if (control != null)
            geometry.removeControl(control);
        this.dotParams = dotParams.copy();
        control = new ParabolicControl(this.dotParams);
        geometry.addControl(control);
    }

    public DotParams getDotParams() {
        return dotParams;
    }

    public void destroy() {
        geometry.removeFromParent();
    }
}
