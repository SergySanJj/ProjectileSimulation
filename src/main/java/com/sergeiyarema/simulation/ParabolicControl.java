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

import static com.sergeiyarema.simulation.DotParams.*;

public class ParabolicControl extends AbstractControl {
    public static final int MAX_TRAILS = 5;
    private static List<List<Spatial>> trailsList = new ArrayList<>();

    private List<Spatial> currentTrail;
    private DotParams params;
    private float totalTime;
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
        if (isAboveGround(newCoords)) {
            spatial.setLocalTranslation(newCoords);

            if ((int) (newCoords.x / INTERVAL) != lastSpawnedX) {
                lastSpawnedX = (int) (newCoords.x / INTERVAL) - 1;
                spawnTrajectoryPoint();
            }

        } else {
            spatial.removeControl(this);
        }
    }

    private boolean isAboveGround(Vector3f coords) {
        return coords.y - params.get(GROUND_LEVEL) >= params.get(RADIUS);
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
    protected void controlRender(RenderManager rm, ViewPort vp) { // default implementation ignored
    }

    public static void clearFinishedTrails() {
        for (List<Spatial> spatialList : trailsList) {
            if (spatialList.equals(trailsList.get(trailsList.size() - 1)))
                continue;
            for (Spatial spatial : spatialList) {
                spatial.removeFromParent();
            }
        }
        if (!trailsList.isEmpty()) {
            List<Spatial> last = trailsList.get(trailsList.size() - 1);
            trailsList.clear();
            trailsList.add(last);
        }
    }

    private static List<Spatial> createNewTrail() {
        List<Spatial> currentTrail = new ArrayList<>();
        while (trailsList.size() >= MAX_TRAILS) {
            for (Spatial el : trailsList.get(0)) {
                el.removeFromParent();
            }
            trailsList.remove(0);
        }
        trailsList.add(currentTrail);
        return currentTrail;
    }

    public static int trailsCount() {
        return trailsList.size();
    }
}
