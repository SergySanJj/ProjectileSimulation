package com.sergeiyarema.simulation;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.shape.Sphere;

public class ParabolicControl extends AbstractControl {
    private DotParams params;
    private float totalTime;
    private static final float FLOOR_LEVEL = -2.5f;
    private static final float INTERVAL = 0.5f;

    private final Object mutex = new Object();
    private Integer lastSpawnedX;

    ParabolicControl() {
        super();
        totalTime = 0.f;
        lastSpawnedX = 0;
    }

    @Override
    protected void controlUpdate(float tpf) {
        totalTime += tpf * 2.f;
        Vector3f newCoords = Trajectory.getCoords(params, totalTime);
        if (spatial.getLocalTranslation().y > FLOOR_LEVEL) {
            spatial.setLocalTranslation(newCoords);
            synchronized (mutex) {
                if ((int) (spatial.getLocalTranslation().x / INTERVAL) != lastSpawnedX) {
                    spawnTrajectoryPoint();
                    lastSpawnedX = (int) (spatial.getLocalTranslation().x / INTERVAL);
                }
            }
        } else {
            spatial.removeControl(this);
        }
    }

    private void spawnTrajectoryPoint() {
        Material matBlack = new Material(GlobalAssets.manager(), "Common/MatDefs/Misc/Unshaded.j3md");
        matBlack.setColor("Color", ColorRGBA.Black);
        Geometry newPoint =
                new Geometry("TrPoint",
                        new Sphere(8, 8, 0.1f, true, false));
        newPoint.setMaterial(matBlack);
        spatial.getParent().attachChild(newPoint);
        Vector3f dotCoords = Trajectory.getCoordsFromXValue(params, (lastSpawnedX + 1) * INTERVAL);
        newPoint.move(dotCoords);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    public void setParams(DotParams params) {
        this.params = params;
        lastSpawnedX = (int) (params.getStartPos().x / INTERVAL);
    }
}
