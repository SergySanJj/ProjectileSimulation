package com.sergeiyarema.simulation;

import com.jme3.system.AppSettings;
import org.junit.Assert;
import org.junit.Test;

import static com.sergeiyarema.simulation.Application.WINDOW_HEIGHT;
import static com.sergeiyarema.simulation.Application.WINDOW_WIDTH;
import static org.junit.Assert.*;

public class ApplicationTest {
    Application app = new Application();

    @Test
    public void simpleInitApp() {
        app.start();
    }

    @Test
    public void simpleUpdate() {
        app.simpleUpdate(1.f);
    }
}