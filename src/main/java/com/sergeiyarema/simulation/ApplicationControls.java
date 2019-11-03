package com.sergeiyarema.simulation;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sergeiyarema.simulation.DotParams.*;
import static com.sergeiyarema.simulation.DotParams.START_SPEED;

public class ApplicationControls {
    // Control commands
    private static final String ZOOM_IN = "ZoomIN";
    private static final String ZOOM_OUT = "ZoomOUT";
    private static final String INCREASE_ANGLE = "IncreaseAngle";
    private static final String DECREASE_ANGLE = "DecreaseAngle";
    private static final String FIRE = "Fire";
    private static final String INCREASE_GRAVITY = "IncreaseGravity";
    private static final String DECREASE_GRAVITY = "DecreaseGravity";
    private static final String INCREASE_SPEED = "IncreaseSpeed";
    private static final String DECREASE_SPEED = "DecreaseSpeed";
    private static final String CAM_RIGHT = "CamRight";
    private static final String CAM_LEFT = "CamLeft";
    private static final String CAM_UP = "CamUp";
    private static final String CAM_DOWN = "CamDown";
    private static final String CLEAR = "Clear";

    private static final float GROUND = -5f;

    private InputManager inputManager;
    private Camera cam;
    private Node rootNode;

    private float cameraSize = 14f;

    private Map<String, SimulationObject> objects;

    private DotParams currentParams =
            new DotParams(new Vector3f(-14.f, GROUND + 0.5f, 0.f), 45.f, 20f, GROUND, 9.80665f);

    private ApplicationControls() {
    }

    public ApplicationControls(InputManager inputManager, Camera cam, Node rootNode) {
        this.inputManager = inputManager;
        this.cam = cam;
        this.rootNode = rootNode;

        objects = new HashMap<>();

        initCameraSettings();
        inputManager.setCursorVisible(true);
        initKeys();

        Vector3f floorCoords = Floor.calculateCoordinatesFromTop(GROUND);
        objects.put("Floor",
                new Floor(new DotParams(floorCoords, 45f, 20f, GROUND, 9.80665f), rootNode));

        objects.put("Projectile", new Projectile(currentParams, rootNode));
        objects.put("Cannon", new Cannon(currentParams, rootNode));
    }

    private void initCameraSettings() {
        cam.setParallelProjection(true);
        updateCameraFrustum();
    }

    private void updateCameraFrustum() {
        float aspect = (float) cam.getWidth() / cam.getHeight();
        cam.setFrustum(-1000, 1000,
                -aspect * cameraSize, aspect * cameraSize,
                cameraSize, -cameraSize);
        setCamY(cameraSize + GROUND - 1.5f); // Interpolated linear formula
    }

    private void zoom(float scale) {
        cameraSize = cameraSize * scale;
        updateCameraFrustum();
    }

    private void horizontalCamMove(float delta) {
        Vector3f currLocation = cam.getLocation();
        currLocation.x += delta;
        cam.setLocation(currLocation);
    }

    private void verticalCamMove(float delta) {
        Vector3f currLocation = cam.getLocation();
        currLocation.y += delta;
        cam.setLocation(currLocation);
    }

    private void setCamY(float y) {
        Vector3f currLocation = cam.getLocation();
        currLocation.y = y;
        cam.setLocation(currLocation);
    }

    private void initKeys() {
        inputManager.addMapping(ZOOM_IN, new KeyTrigger(KeyInput.KEY_EQUALS));
        inputManager.addMapping(ZOOM_OUT, new KeyTrigger(KeyInput.KEY_MINUS));

        inputManager.addMapping(INCREASE_ANGLE, new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping(DECREASE_ANGLE, new KeyTrigger(KeyInput.KEY_S));

        inputManager.addMapping(FIRE, new KeyTrigger(KeyInput.KEY_F));

        inputManager.addMapping(INCREASE_GRAVITY, new KeyTrigger(KeyInput.KEY_I));
        inputManager.addMapping(DECREASE_GRAVITY, new KeyTrigger(KeyInput.KEY_U));

        inputManager.addMapping(INCREASE_SPEED, new KeyTrigger(KeyInput.KEY_X));
        inputManager.addMapping(DECREASE_SPEED, new KeyTrigger(KeyInput.KEY_Z));

        inputManager.addMapping(CAM_RIGHT, new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping(CAM_LEFT, new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping(CAM_UP, new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping(CAM_DOWN, new KeyTrigger(KeyInput.KEY_DOWN));

        inputManager.addMapping(CLEAR, new KeyTrigger(KeyInput.KEY_DELETE));

        inputManager.addListener(actionListener,
                FIRE,
                INCREASE_GRAVITY, DECREASE_GRAVITY,
                INCREASE_SPEED, DECREASE_SPEED,
                CLEAR);
        inputManager.addListener(analogListener,
                ZOOM_IN, ZOOM_OUT,
                CAM_LEFT, CAM_RIGHT, CAM_UP, CAM_DOWN,
                INCREASE_ANGLE, DECREASE_ANGLE);
    }

    private final AnalogListener analogListener = (name, value, tpf) -> {
        switch (name) {
            case ZOOM_IN:
                zoom(1.f - 4f * value);
                break;
            case ZOOM_OUT:
                zoom(1.f + 4f * value);
                break;
            case CAM_RIGHT:
                horizontalCamMove(10.f * cameraSize * value);
                break;
            case CAM_LEFT:
                horizontalCamMove(-10.f * cameraSize * value);
                break;
            case CAM_UP:
                verticalCamMove(10.f * cameraSize * value);
                break;
            case CAM_DOWN:
                verticalCamMove(-10.f * cameraSize * value);
                break;
            case INCREASE_ANGLE:
                changeAngle(100.f * value);
                break;
            case DECREASE_ANGLE:
                changeAngle(-100.f * value);
                break;
            default:
                break;
        }
    };

    private final ActionListener actionListener = (name, keyPressed, tpf) -> {
        if (!keyPressed) {
            switch (name) {
                case INCREASE_GRAVITY:
                    changeParamByDelta(GRAVITY, 1.f);
                    break;
                case DECREASE_GRAVITY:
                    changeParamByDelta(GRAVITY, -1.f);
                    break;
                case INCREASE_SPEED:
                    changeParamByDelta(START_SPEED, 1f);
                    break;
                case DECREASE_SPEED:
                    changeParamByDelta(START_SPEED, -1f);
                    break;
                case FIRE:
                    fire();
                    break;
                case CLEAR:
                    clearTrajectoryTraces();
                    break;
                default:
                    break;
            }
        }
    };

    private void changeParamByDelta(String paramName, float delta) {
        this.currentParams.getChangeable(paramName).changeBy(delta);
    }

    private void changeAngle(float delta) {
        ((Cannon) objects.get("Cannon")).setAngle(currentParams.get(START_ANGLE) + delta);
        changeParamByDelta(START_ANGLE, delta);
    }

    private void fire() {
        ((Projectile) objects.get("Projectile")).fire(currentParams);
    }

    private void clearTrajectoryTraces() {
        ParabolicControl.clearTrails();
        List<Spatial> children = rootNode.getChildren();
        for (Spatial el : children) {
            if (el.getName().equals("TrPoint")) {
                el.removeFromParent();
            }
        }
    }

    public DotParams getCurrentParams() {
        return currentParams;
    }
}
