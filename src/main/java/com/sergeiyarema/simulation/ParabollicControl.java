package com.sergeiyarema.simulation;

import com.jme3.math.Vector2f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

public class ParabollicControl extends AbstractControl {
    private DotParams params;
    private float totalTime;
    private float floorLevel = -3.f;

    ParabollicControl() {
        super();
        totalTime = 0.f;
    }

    @Override
    protected void controlUpdate(float tpf) {
        totalTime += tpf / 100.f;
        Vector2f newCoords = Trajectory.getCoords(params, totalTime, params.get("Gravity"));
        if (spatial.getLocalTranslation().y > floorLevel)
            spatial.move(newCoords.x, newCoords.y, 0.f);

    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    public void setParams(DotParams params) {
        this.params = params;
    }
}
