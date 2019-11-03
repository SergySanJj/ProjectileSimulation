package com.sergeiyarema.simulation;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;

import static com.sergeiyarema.simulation.DotParams.RADIUS;
import static com.sergeiyarema.simulation.DotParams.START_ANGLE;

public class Cannon extends SimulationObject {


    public Cannon(DotParams dotParams, Node rootNode) {
        super(dotParams, rootNode);

        this.params = params.copy();
        Material matGray = new Material(GlobalAssets.manager(), "Common/MatDefs/Misc/Unshaded.j3md");
        matGray.setColor("Color", ColorRGBA.Gray);

        float radius = params.get(RADIUS) * 1.1f;
        float height = params.get(RADIUS) * 6f;

        geometry =
                new Geometry("Canon",
                        new Cylinder(32, 32, radius, height, true));

        geometry.setMaterial(matGray);
        geometry.move(params.getStartPos());
        setAngle(params.get(START_ANGLE));

        node.attachChild(geometry);
    }

    public void setAngle(float angle) {
        params.set(START_ANGLE, angle);

        Quaternion cameraAlign = new Quaternion();
        cameraAlign.fromAngleAxis(FastMath.PI / 2.f, new Vector3f(0, 1, 0));

        float radAngle = (float) Math.toRadians(angle);
        Quaternion angleSetter = new Quaternion();
        angleSetter.fromAngleAxis(radAngle, new Vector3f(0, 0, 1));

        geometry.setLocalRotation(angleSetter.mult(cameraAlign));
    }
}
