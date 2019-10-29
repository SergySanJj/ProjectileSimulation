package com.sergeiyarema.simulation;

import com.jme3.asset.AssetManager;

public class GlobalAssets {
    private static AssetManager assetManager;

    public static void innitManager(AssetManager assetManager) {
        GlobalAssets.assetManager = assetManager;
    }

    public static AssetManager manager() {
        return assetManager;
    }
}
