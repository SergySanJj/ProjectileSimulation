package com.sergeiyarema.simulation;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.shape.Sphere;

import java.util.ArrayList;
import java.util.List;

public class ParabolicControl extends AbstractControl {
    private static final int MAX_TRAILS = 5;
    private static List<List<Spatial>> trailsList = new ArrayList<>();

    private List<Spatial> currentTrail;
    private DotParams params;
    private float totalTime;
    private static final float FLOOR_LEVEL = -2.5f;
    private static final float INTERVAL = 0.5f;

    private Integer lastSpawnedX;

    private ParabolicControl() {
    }

    public ParabolicControl(DotParams params) {
        super();
        this.params = params.copy();
        lastSpawnedX = (int) (params.getStartPos().x / INTERVAL);
        totalTime = 0.f;
        lastSpawnedX = 0;
        currentTrail = createNewTrail();
    }

    @Override
    protected void controlUpdate(float tpf) {
        totalTime += tpf * 2.f;
        Vector3f newCoords = Trajectory.getCoords(params, totalTime);
        if (newCoords.y > FLOOR_LEVEL) {
            spatial.setLocalTranslation(newCoords);

            if ((int) (newCoords.x / INTERVAL) != lastSpawnedX) {
                lastSpawnedX = (int) (newCoords.x / INTERVAL) - 1;
                spawnTrajectoryPoint();
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
                        new Sphere(4, 4, 0.1f, true, false));
        newPoint.setMaterial(matBlack);
        currentTrail.add(newPoint);
        spatial.getParent().attachChild(newPoint);
        Vector3f dotCoords = Trajectory.getCoordsFromXValue(params, (lastSpawnedX + 1) * INTERVAL);
        newPoint.move(dotCoords);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    public static void clearTrails() {
        for (List<Spatial> spatialList : trailsList) {
            for (Spatial spatial : spatialList) {
                spatial.removeFromParent();
            }
        }
        trailsList.clear();
    }

    private static List<Spatial> createNewTrail() {
        List<Spatial> currentTrail = new ArrayList<>();
        if (trailsList.size() > MAX_TRAILS) {
            for (Spatial el : trailsList.get(0)) {
                el.removeFromParent();
            }
            trailsList.remove(0);
        }
        trailsList.add(currentTrail);
        return currentTrail;
    }
}
