package com.sergeiyarema.simulation;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

import static com.sergeiyarema.simulation.DotParams.RADIUS;

public class Projectile extends SimulationObject {
    private ParabolicControl control;

    public Projectile(DotParams dotParams, Node rootNode) {
        super(dotParams, rootNode);

        Material matBlue = new Material(GlobalAssets.manager(), "Common/MatDefs/Misc/Unshaded.j3md");
        matBlue.setColor("Color", ColorRGBA.Blue);
        geometry =
                new Geometry("Projectile",
                        new Sphere(32, 32, params.get(RADIUS), true, false));
        geometry.setMaterial(matBlue);
        geometry.setLocalTranslation(params.getStartPos());
        node.attachChild(geometry);
    }

    public void fire(DotParams dotParams) {
        if (flying())
            geometry.removeControl(control);
        this.params = dotParams.copy();
        control = new ParabolicControl(this.params);
        geometry.addControl(control);
    }

    public boolean flying() {
        return (geometry.getNumControls()!=0);
    }
}
