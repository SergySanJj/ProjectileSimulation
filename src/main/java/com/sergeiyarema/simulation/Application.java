package com.sergeiyarema.simulation;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;

import java.util.List;

import static com.sergeiyarema.simulation.DotParams.*;

public class Application extends SimpleApplication {
    private static final int WINDOW_HEIGHT = 800;
    private static final int WINDOW_WIDTH = 1000;

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
    private static final String CLEAR = "Clear";

    private Projectile pr;

    private DotParams currentParams =
            new DotParams(new Vector3f(-14.f, -2.5f, 0.f), 45.f, 20f, 9.80665f);

    private float cameraSize = 14f;

    public Application() {
        super(null);
        setSettings(getBuildSettings());
    }

    @Override
    public void simpleInitApp() {
        initCameraSettings();
        initKeys();
        viewPort.setBackgroundColor(ColorRGBA.White);
        inputManager.setCursorVisible(true);
        horizontalCamMove(0.f);
        GlobalAssets.innitManager(this.assetManager);
        Floor fl = createFloor();
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

    private void initKeys() {
        // You can map one or several inputs to one named action
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

        inputManager.addMapping(CLEAR, new KeyTrigger(KeyInput.KEY_DELETE));

        // Add the names to the action listener.
        inputManager.addListener(actionListener,
                INCREASE_ANGLE, DECREASE_ANGLE,
                FIRE,
                INCREASE_GRAVITY, DECREASE_GRAVITY,
                INCREASE_SPEED, DECREASE_SPEED,

                CLEAR);
        inputManager.addListener(analogListener,
                ZOOM_IN, ZOOM_OUT,
                CAM_LEFT, CAM_RIGHT);
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
                case INCREASE_ANGLE:
                    changeParamByDelta(START_ANGLE, 5.f);
                    break;
                case DECREASE_ANGLE:
                    changeParamByDelta(START_ANGLE, -5.f);
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

    @Override
    public void simpleUpdate(float tpf) { // default implementation ignored
    }

    private AppSettings getBuildSettings() {
        setShowSettings(false);

        AppSettings settings = new AppSettings(true);
        settings.setTitle("Projectile simulation");
        settings.setHeight(WINDOW_HEIGHT);
        settings.setWidth(WINDOW_WIDTH);
        return settings;
    }

    private Projectile createProjectile() {
        return new Projectile(currentParams.copy(), rootNode);
    }

    private Floor createFloor() {
        return new Floor(rootNode);
    }

    private void fire() {
        if (pr != null) {
            pr.destroy();
            pr = null;
        }
        pr = createProjectile();
    }

    private void clearTrajectoryTraces() {
        List<Spatial> children = rootNode.getChildren();
        for (Spatial el : children) {
            if (el.getName().equals("TrPoint")) {
                el.removeFromParent();
            }
        }
    }
}