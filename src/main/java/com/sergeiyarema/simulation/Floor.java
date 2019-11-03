package com.sergeiyarema.simulation;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

public class Floor extends SimulationObject {
    public static final float HEIGHT = 2.f;
    public static final float WIDTH = 100.f;

    Floor(DotParams dotParams, Node rootNode) {
        super(dotParams, rootNode);

        Material matRed = new Material(GlobalAssets.manager(), "Common/MatDefs/Misc/Unshaded.j3md");
        matRed.setColor("Color", ColorRGBA.Red);

        geometry = new Geometry("Box", new Box(WIDTH, HEIGHT, 1.f));
        geometry.setMaterial(matRed);
        geometry.move(dotParams.getStartPos());

        node.attachChild(geometry);
    }

    public float getTopCoordinate() {
        return geometry.getLocalTranslation().y + HEIGHT;
    }

    public static Vector3f calculateCoordinatesFromTop(float topCoordinate) {
        return new Vector3f(0.f, topCoordinate - HEIGHT, 0.f);
    }

}
