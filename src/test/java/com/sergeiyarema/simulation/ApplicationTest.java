package com.sergeiyarema.simulation;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import org.junit.Assert;
import org.junit.Test;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
        //can use in method only.
@interface BeforeAppStart {
    public boolean enabled() default true;
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
        //can use in method only.
@interface AfterAppStart {
    public boolean enabled() default true;
}

public class ApplicationTest {
    private Application app = new Application();
    private Vector3f defaultCoords = new Vector3f(10f, 100f, 1000f);
    private DotParams dotParams = new DotParams(defaultCoords,
            45f, 20f, 100f, 9.80665f);

    @Test
    public void testChain() {
        java.util.logging.Logger.getLogger("").setLevel(Level.WARNING);

        processAnnotated(BeforeAppStart.class);
        Thread appThread = new Thread(() -> app.start());
        appThread.start();
        while (!app.isReady()) {
        }
        processAnnotated(AfterAppStart.class);

        app.requestClose(true);
        appThread.interrupt();
    }

    private void processAnnotated(Class<? extends Annotation> classRef) {
        int testsRun = 0;
        for (Method method : ApplicationTest.class.getDeclaredMethods()) {
            if (method.isAnnotationPresent(classRef)) {
                try {
                    method.invoke(this);
                } catch (Exception e) {
                    Logger.getLogger("Test").log(Level.SEVERE, "Assert in " + method.getClass().getName() + "." + method.getName() + "()");
                    e.printStackTrace();
                    Assert.assertNotNull(null);
                }
                testsRun++;
            }
        }
        Logger.getLogger("Test").log(Level.WARNING, "Tests count " + testsRun);
    }

    // BEFORE
    @BeforeAppStart
    private void globalAssetsTestPre() {
        Assert.assertNull(GlobalAssets.manager());
    }

    @BeforeAppStart
    private void trailsStartNumberZero() {
        Assert.assertEquals(0, ParabolicControl.trailsCount());
    }

    // AFTER
    @AfterAppStart
    private void globalAssetsTestAfter() {
        Assert.assertNotNull(GlobalAssets.manager());
    }

    @AfterAppStart
    private void floorCreationTest() {
        Node node = new Node();
        Vector3f floorCoords = Floor.calculateCoordinatesFromTop(100f);
        Floor floor1 =
                new Floor(new DotParams(floorCoords,
                        45f, 20f, 100f, 9.80665f), node);
        Assert.assertEquals(100f, floor1.getTopCoordinate(), 0.001f);
    }

    @AfterAppStart
    private void projectileTestAfter() {
        Node node = new Node();
        Projectile projectile1 = new Projectile(dotParams, node);
        Assert.assertEquals(dotParams, projectile1.getParams());
        Assert.assertEquals(defaultCoords, projectile1.getLocalTranslation());
    }

    @AfterAppStart
    private void cannonTestAfter() {
        Node node = new Node();
        Cannon cannon1 = new Cannon(dotParams, node);
        Assert.assertEquals(dotParams, cannon1.getParams());
        Assert.assertEquals(defaultCoords, cannon1.getLocalTranslation());
    }

    @AfterAppStart
    private void startupObjectsCreated() {
        // Check existence
        Assert.assertNotNull(app.getControls().getObject("Projectile"));
        Assert.assertNotNull(app.getControls().getObject("Floor"));
        Assert.assertNotNull(app.getControls().getObject("Cannon"));

        // Check correct coords
        Assert.assertEquals(app.getControls().getCurrentParams().getStartPos(),
                app.getControls().getObject("Projectile").getLocalTranslation());

        Assert.assertEquals(app.getControls().getFloorParams().getStartPos(),
                app.getControls().getObject("Floor").getLocalTranslation());

        Assert.assertEquals(app.getControls().getCurrentParams().getStartPos(),
                app.getControls().getObject("Cannon").getLocalTranslation());
    }
}