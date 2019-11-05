package com.sergeiyarema.simulation;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.system.AppSettings;
import com.jme3.ui.Picture;


public class Application extends SimpleApplication {
    public static final int WINDOW_HEIGHT = 800;
    public static final int WINDOW_WIDTH = 1000;
    private volatile boolean isReady = false;
    private ApplicationControls applicationControls;

    public Application() {
        super();
        super.setDisplayFps(false);
        super.setDisplayStatView(false);
        setSettings(getBuildSettings());
    }

    @Override
    public void simpleInitApp() {
        GlobalAssets.innitAssets(this.assetManager);
        flyCam.setEnabled(false);
        applicationControls = new ApplicationControls(inputManager, cam, rootNode, guiNode, guiFont);
        viewPort.setBackgroundColor(ColorRGBA.White);
        isReady = true;
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
        return super.settings;
    }

    public synchronized boolean isReady() {
        return isReady;
    }

    public ApplicationControls getControls() {
        return applicationControls;
    }
}