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

public class Cannon {
    private Geometry geometry;
    private DotParams dotParams;

    private Cannon() {
    }

    public Cannon(DotParams params, Node rootNode) {
        this.dotParams = params.copy();
        Material matGray = new Material(GlobalAssets.manager(), "Common/MatDefs/Misc/Unshaded.j3md");
        matGray.setColor("Color", ColorRGBA.Gray);
        geometry =
                new Geometry("Canon",
                        new Cylinder(32, 32,
                                params.get(RADIUS) * 1.1f, params.get(RADIUS) * 3f, true));

        geometry.setMaterial(matGray);
        geometry.move(params.getStartPos());
        rotCylinder();
        //geometry.scale(1f, 0.5f, 1.f);
        setAngle(params.get(START_ANGLE));
        rootNode.attachChild(geometry);
    }

    private void rotCylinder(){
        Quaternion pitch90 = new Quaternion();
        pitch90.fromAngleAxis(FastMath.PI/2, new Vector3f(1, 0, 0));
        geometry.setLocalRotation(pitch90);
        // todo here
    }

    public void setAngle(float angle) {
        dotParams.set(START_ANGLE, angle);
        float radAngle = (float) Math.toRadians(angle);
        System.out.println(angle);
        Quaternion pitch90 = new Quaternion();
        pitch90.fromAngleAxis(radAngle, new Vector3f(0, 0, 1));
        geometry.setLocalRotation(pitch90);
    }
}
