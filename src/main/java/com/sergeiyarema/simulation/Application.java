package com.sergeiyarema.simulation;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;

import java.lang.ref.WeakReference;
import java.util.List;


public class Application extends SimpleApplication {
    private static final int WINDOW_HEIGHT = 800;
    private static final int WINDOW_WIDTH = 1000;

    private Material matRed, matBlue;
    private Projectile pr;
    private Floor fl;

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
        initMaterials();
        horizontalCamMove(0.f);
        GlobalAssets.innitManager(this.assetManager);
        fl = createFloor();
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
        inputManager.addMapping("ZoomIN", new KeyTrigger(KeyInput.KEY_EQUALS));
        inputManager.addMapping("ZoomOUT", new KeyTrigger(KeyInput.KEY_MINUS));

        inputManager.addMapping("IncreaseAngle", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("DecreaseAngle", new KeyTrigger(KeyInput.KEY_S));

        inputManager.addMapping("Fire", new KeyTrigger(KeyInput.KEY_F));

        inputManager.addMapping("IncreaseGravity", new KeyTrigger(KeyInput.KEY_I));
        inputManager.addMapping("DecreaseGravity", new KeyTrigger(KeyInput.KEY_U));

        inputManager.addMapping("IncreaseSpeed", new KeyTrigger(KeyInput.KEY_X));
        inputManager.addMapping("DecreaseSpeed", new KeyTrigger(KeyInput.KEY_Z));

        inputManager.addMapping("CamRight", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping("CamLeft", new KeyTrigger(KeyInput.KEY_LEFT));

        inputManager.addMapping("Clear", new KeyTrigger(KeyInput.KEY_DELETE));

        // Add the names to the action listener.
        inputManager.addListener(actionListener,
                "IncreaseAngle", "DecreaseAngle",
                "Fire",
                "IncreaseGravity", "DecreaseGravity",
                "IncreaseSpeed", "DecreaseSpeed",

                "Clear");
        inputManager.addListener(analogListener,
                "ZoomIN", "ZoomOUT",
                "CamLeft", "CamRight");
    }

    private final AnalogListener analogListener = (name, value, tpf) -> {
        if (name.equals("ZoomIN")) {
            zoom(1.f - 4f * value);
        } else if (name.equals("ZoomOUT")) {
            zoom(1.f + 4f * value);
        } else if (name.equals("CamRight")) {
            horizontalCamMove(10.f * cameraSize * value);
        } else if (name.equals("CamLeft")) {
            horizontalCamMove(-10.f * cameraSize * value);
        }
    };

    private final ActionListener actionListener = (name, keyPressed, tpf) -> {
        if (name.equals("IncreaseGravity") && !keyPressed) {
            changeParamByDelta("Gravity", 1.f);
        } else if (name.equals("DecreaseGravity") && !keyPressed) {
            changeParamByDelta("Gravity", -1.f);
        } else if (name.equals("IncreaseAngle") && !keyPressed) {
            changeParamByDelta("StartAngle", 5.f);
        } else if (name.equals("DecreaseAngle") && !keyPressed) {
            changeParamByDelta("StartAngle", -5.f);
        } else if (name.equals("IncreaseSpeed") && !keyPressed) {
            changeParamByDelta("StartSpeed", 1f);
        } else if (name.equals("DecreaseSpeed") && !keyPressed) {
            changeParamByDelta("StartSpeed", -1f);
        } else if (name.equals("Fire") && !keyPressed) {
            fire();
        } else if (name.equals("Clear") && !keyPressed) {
            clearTrajectoryTraces();
        }
    };

    private void changeParamByDelta(String paramName, float delta) {
        this.currentParams.getChangeable(paramName).changeBy(delta);
    }

    @Override
    public void simpleUpdate(float tpf) {
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

    private void initMaterials() {
        matRed = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matRed.setColor("Color", ColorRGBA.Red);
        matBlue = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matBlue.setColor("Color", ColorRGBA.Blue);
    }

    private void fire() {
        if (pr != null) {
            WeakReference<Projectile> wr = new WeakReference<>(pr);
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