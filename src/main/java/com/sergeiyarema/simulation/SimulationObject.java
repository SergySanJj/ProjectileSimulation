package com.sergeiyarema.simulation;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

public abstract class SimulationObject {
    protected Node node;
    protected DotParams params;
    protected Geometry geometry = null;

    private SimulationObject() {
    }

    public SimulationObject(DotParams dotParams, Node rootNode) {
        node = new Node();
        rootNode.attachChild(node);
        params = dotParams;
    }

    public void destroy(){
        geometry.removeFromParent();
        node.removeFromParent();
    }

    public Vector3f getLocalTranslation() {
        return geometry.getLocalTranslation();
    }

    public DotParams getParams() {
        return params;
    }
}
