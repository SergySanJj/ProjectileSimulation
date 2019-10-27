package com.sergeiyarema.simulation;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.system.AppSettings;


public class Application extends SimpleApplication {
    private static final int WINDOW_HEIGHT = 800;
    private static final int WINDOW_WIDTH = 1000;

    private static float currStartAngle = 45.f;
    private static float currStartSpeed = 1.f;

    private Material matRed, matBlue;
    private Projectile pr;
    private Floor fl;

    private DotParams currentParams =
            new DotParams(new Vector2f(0.f, 0.f), 45.f, 0.05f);

    private float cameraSize = 4f;

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

    public void zoom(float scale) {
        cameraSize = cameraSize * scale;
        updateCameraFrustum();
    }

    public void horizontalCamMove(float delta) {
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

        inputManager.addMapping("CamRight", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping("CamLeft", new KeyTrigger(KeyInput.KEY_LEFT));

        // Add the names to the action listener.
        inputManager.addListener(actionListener,
                "ZoomIN", "ZoomOUT",
                "IncreaseAngle", "DecreaseAngle",
                "Fire",
                "IncreaseGravity", "DecreaseGravity",
                "CamLeft", "CamRight");

    }

    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("ZoomIN") && !keyPressed) {
                zoom(0.9f);
            } else if (name.equals("ZoomOUT") && !keyPressed) {
                zoom(1.1f);
            } else if (name.equals("CamRight") && !keyPressed) {
                horizontalCamMove(1.f);
            } else if (name.equals("CamLeft") && !keyPressed) {
                horizontalCamMove(-1.f);
            } else if (name.equals("Fire") && !keyPressed) {
                fire();
            }
        }
    };

    @Override
    public void simpleUpdate(float tpf) {
        if (pr != null)
            pr.updateMove(tpf);
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
        Projectile projectile = new Projectile(currentParams);
        projectile.setMaterial(matBlue);
        rootNode.attachChild(projectile);

        return projectile;
    }

    private Floor createFloor() {
        Floor floor = new Floor();
        floor.setMaterial(matRed);
        rootNode.attachChild(floor);

        return floor;
    }

    private void initMaterials() {
        matRed = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matRed.setColor("Color", ColorRGBA.Red);
        matBlue = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matBlue.setColor("Color", ColorRGBA.Blue);
    }

    private void fire() {
        pr = createProjectile();
        System.out.println("fire");
    }

}