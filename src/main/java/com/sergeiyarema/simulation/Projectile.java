package com.sergeiyarema.simulation;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.jme3.asset.AssetManager;

public class Projectile {
    private DotParams dotParams;
    private float totalTime;
    private Geometry geometry;

    private Projectile() {
    }

    public Projectile(DotParams dotParams, Node rootNode, AssetManager assetManager) {
        Material matBlue = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matBlue.setColor("Color", ColorRGBA.Blue);
        geometry =
                new Geometry("Projectile",
                        new Sphere(32, 32, 0.4f, true, false));
        geometry.setMaterial(matBlue);
        this.dotParams = dotParams;
        totalTime = 0.f;
        rootNode.attachChild(geometry);
        ParabollicControl control = new ParabollicControl();
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
