package com.sergeiyarema.simulation;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

public class Projectile {
    private DotParams dotParams;
    private Geometry geometry;

    private Projectile() {
    }

    public Projectile(DotParams dotParams, Node rootNode) {
        Material matBlue = new Material(GlobalAssets.manager(), "Common/MatDefs/Misc/Unshaded.j3md");
        matBlue.setColor("Color", ColorRGBA.Blue);
        geometry =
                new Geometry("Projectile",
                        new Sphere(32, 32, 0.5f, true, false));
        geometry.setMaterial(matBlue);
        this.dotParams = dotParams;
        rootNode.attachChild(geometry);
        ParabolicControl control = new ParabolicControl();
        control.setParams(this.dotParams);
        geometry.addControl(control);
    }

    public DotParams getDotParams() {
        return dotParams;
    }

    public void destroy(){
        geometry.removeFromParent();
    }


}
