package com.sergeiyarema.simulation;

import com.jme3.app.state.RootNodeAppState;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

public class Floor {
    private static final float HEIGHT = 2.f;
    private static final float WIDTH = 100.f;

    private Geometry geometry;

    Floor(Node rootNode) {
        Material matRed = new Material(GlobalAssets.manager(), "Common/MatDefs/Misc/Unshaded.j3md");
        matRed.setColor("Color", ColorRGBA.Red);

        geometry = new Geometry("Box", new Box(WIDTH, HEIGHT, 1.f));
        geometry.move(new Vector3f(0.f, -5.f, 0.f));
        rootNode.attachChild(geometry);

        geometry.setMaterial(matRed);
    }

}
