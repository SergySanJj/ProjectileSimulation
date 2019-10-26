package com.sergeiyarema.simulation;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;

public class Application extends SimpleApplication {
    private static final int HEIGHT = 800;
    private static final int WIDTH = 1000;

    public Application(){
        super();
        setSettings(getBuildSettings());
    }

    private void initCameraSettings() {
        cam.setParallelProjection(true);
        float aspect = (float) cam.getWidth() / cam.getHeight();
        float size = 4f;
        cam.setFrustum(-1000, 1000, -aspect * size, aspect * size, size, -size);
    }

    @Override
    public void simpleInitApp() {
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        rootNode.attachChild(geom);

        initCameraSettings();
    }

    @Override
    public void simpleUpdate(float tpf) {

    }

    private AppSettings getBuildSettings() {
        setShowSettings(false);

        AppSettings settings = new AppSettings(true);
        settings.setTitle("Projectile simulation");
        settings.setHeight(HEIGHT);
        settings.setWidth(WIDTH);
        return settings;
    }
}