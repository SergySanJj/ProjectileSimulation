package com.sergeiyarema.simulation;

import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.system.AppSettings;

public class Application extends SimpleApplication {
    public static final int WINDOW_HEIGHT = 800;
    public static final int WINDOW_WIDTH = 1000;


    public Application() {
        super();

        setSettings(getBuildSettings());
    }

    @Override
    public void simpleInitApp() {
        GlobalAssets.innitAssets(this.assetManager);
        flyCam.setEnabled(false);
        ApplicationControls applicationControls = new ApplicationControls(inputManager, cam, rootNode);
        viewPort.setBackgroundColor(ColorRGBA.White);

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

    public AppSettings getSettings() {
        return getSettings();
    }
}