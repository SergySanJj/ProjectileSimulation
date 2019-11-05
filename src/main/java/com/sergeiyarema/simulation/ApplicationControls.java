package com.sergeiyarema.simulation;

import com.jme3.font.BitmapFont;
import com.jme3.input.InputManager;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;

import java.util.*;

import static com.sergeiyarema.simulation.DotParams.*;

public class ApplicationControls {
    private static final float GROUND = -5f;
    public static final String FLOOR = "Floor";
    public static final String PROJECTILE = "Projectile";
    public static final String CANNON = "Cannon";

    private InputManager inputManager;
    private KeyMapper keyMapper;
    private SimulationInfoDisplay display;
    private Camera cam;
    private Node rootNode;

    public final Vector2f zoomBoundary = new Vector2f(5f, 20f);
    private ChangeableByDelta cameraSize;

    private Map<String, SimulationObject> objects;

    private DotParams currentParams =
            new DotParams(new Vector3f(-14.f, GROUND + 0.5f, 0.f), 45.f, 20f, GROUND, 9.80665f);

    private ApplicationControls() {
    }

    public ApplicationControls(InputManager inputManager, Camera cam, Node rootNode, Node guiNode, BitmapFont guiFont) {
        this.inputManager = inputManager;
        this.cam = cam;
        this.rootNode = rootNode;

        keyMapper = new KeyMapper(this);
        display = new SimulationInfoDisplay(guiNode, guiFont);

        initCameraSettings();
        inputManager.setCursorVisible(true);

        objects = new HashMap<>();
        createObjectScene();
        getDisplay().updateInfo(getCurrentParams());
    }

    private void createObjectScene() {
        objects.put(FLOOR, new Floor(getFloorParams(), rootNode));
        objects.put(PROJECTILE, new Projectile(currentParams, rootNode));
        objects.put(CANNON, new Cannon(currentParams, rootNode));
    }

    private void initCameraSettings() {
        cameraSize = new ChangeableByDelta();
        cameraSize.setValue(14f);
        cameraSize.setBoundary(zoomBoundary);

        cam.setParallelProjection(true);
        updateCameraFrustum();
    }

    private void updateCameraFrustum() {
        float aspect = (float) cam.getWidth() / cam.getHeight();
        cam.setFrustum(-1000, 1000,
                -aspect * cameraSize.getValue(), aspect * cameraSize.getValue(),
                cameraSize.getValue(), -cameraSize.getValue());
        setCamY(cameraSize.getValue() + GROUND - 1.5f); // Interpolated linear formula
    }

    public void zoom(float scale) {
        cameraSize.setValue(cameraSize.getValue() * scale);
        updateCameraFrustum();
    }

    public void horizontalCamMove(float delta) {
        Vector3f currCamLocation = cam.getLocation().clone();
        currCamLocation.x += delta;
        cam.setLocation(currCamLocation);

        Vector3f currFloor = objects.get(FLOOR).getLocalTranslation();
        objects.get(FLOOR).setLocalTranslation(
                new Vector3f(currCamLocation.x, currFloor.y, currFloor.z));
    }

    public void verticalCamMove(float delta) {
        Vector3f currLocation = cam.getLocation().clone();
        currLocation.y += delta;
        cam.setLocation(currLocation);
    }

    private void setCamY(float y) {
        Vector3f currLocation = cam.getLocation();
        currLocation.y = y;
        cam.setLocation(currLocation);
    }


    public void changeParamByDelta(String paramName, float delta) {
        this.currentParams.getChangeable(paramName).changeBy(delta);
    }

    public void setParam(String paramName, float val) {
        this.currentParams.getChangeable(paramName).setValue(val);
    }

    public void setAngle(float angle) {
        ((Cannon) getObject(CANNON)).setAngle(angle);
        setParam(START_ANGLE, angle);
    }

    public void changeAngle(float delta) {
        setAngle(currentParams.get(START_ANGLE) + delta);
    }

    public void fire() {
        ((Projectile) getObject(PROJECTILE)).fire(currentParams);
    }

    public void clearTrajectoryTraces() {
        ParabolicControl.clearFinishedTrails();
    }

    public DotParams getCurrentParams() {
        return currentParams;
    }

    public SimulationObject getObject(String name) {
        return objects.get(name);
    }

    public DotParams getFloorParams() {
        Vector3f floorCoords = Floor.calculateCoordinatesFromTop(GROUND);
        return new DotParams(floorCoords, currentParams.get(START_ANGLE), currentParams.get(START_SPEED),
                GROUND, currentParams.get(GRAVITY));
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public ChangeableByDelta getCameraSize() {
        return cameraSize;
    }

    public Vector3f getCameraCoordinates() {
        return cam.getLocation().clone();
    }

    public SimulationInfoDisplay getDisplay() {
        return display;
    }

    public KeyMapper getKeyMapper() {
        return keyMapper;
    }
}
