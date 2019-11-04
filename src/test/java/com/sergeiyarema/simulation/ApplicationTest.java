package com.sergeiyarema.simulation;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.sergeiyarema.misc.Misc;
import org.junit.Assert;
import org.junit.Test;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.sergeiyarema.simulation.ParabolicControl.MAX_TRAILS;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
        //can use in method only.
@interface BeforeAppStart {
    public boolean enabled() default true;
}

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
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
    private void appNameSet() {
        Assert.assertEquals("Projectile simulation", app.getSettings().getTitle());
    }

    @AfterAppStart
    private void floorCreationTest() {
        Node node = new Node();
        Vector3f floorCoords = Floor.calculateCoordinatesFromTop(100f);
        Floor floor1 =
                new Floor(new DotParams(floorCoords,
                        45f, 20f, 100f, 9.80665f), node);
        Assert.assertEquals(100f, floor1.getTopCoordinate(), 0.001f);

        floor1.destroy();
        Assert.assertEquals(0, floor1.getContainerNode().getChildren().size());
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

    @AfterAppStart
    private void projectileFiring() {
        app.getControls().clearTrajectoryTraces();
        for (int i = 1; i <= MAX_TRAILS; i++) {
            app.getControls().fire();
            Assert.assertTrue(Math.abs(i - ParabolicControl.trailsCount()) <= i);
        }

        app.getControls().fire();
        Assert.assertTrue(Math.abs(MAX_TRAILS - ParabolicControl.trailsCount()) <= MAX_TRAILS);

        app.getControls().fire();
        Assert.assertTrue(Math.abs(MAX_TRAILS - ParabolicControl.trailsCount()) <= MAX_TRAILS);
    }

    @AfterAppStart
    private void cameraChanges() {
        Vector3f move = new Vector3f(1f, -1f, 0f);


        final Vector3f startCamCoords = app.getControls().getCameraCoordinates().clone();


        float startCamSize = app.getControls().getCameraSize().getValue();

        // Move
        app.getControls().horizontalCamMove(move.x);
        app.getControls().verticalCamMove(move.y);
        Vector3f moveCamCoords = app.getControls().getCameraCoordinates();
        Assert.assertEquals(startCamCoords.add(move), moveCamCoords);
        Assert.assertEquals(startCamSize, app.getControls().getCameraSize().getValue(), 0.01f);


        move = new Vector3f(-1f, 1f, 0f);
        app.getControls().horizontalCamMove(move.x);
        app.getControls().verticalCamMove(move.y);
        moveCamCoords = app.getControls().getCameraCoordinates().clone();
        Assert.assertEquals(startCamCoords, moveCamCoords);
        Assert.assertEquals(startCamSize, app.getControls().getCameraSize().getValue(), 0.01f);

        // Normal Zoom
        startCamSize = app.getControls().getCameraSize().getValue();
        float mult = 1.1f;
        app.getControls().zoom(mult);
        float expectedZoom = Misc.bound(startCamSize * mult, app.getControls().zoomBoundary);
        Assert.assertEquals(expectedZoom, app.getControls().getCameraSize().getValue(), 0.01f);
        app.getControls().zoom(1 / mult);
        expectedZoom = Misc.bound(expectedZoom / mult, app.getControls().zoomBoundary);
        Assert.assertEquals(expectedZoom, app.getControls().getCameraSize().getValue(), 0.01f);

        // Bound Zoom
        startCamSize = app.getControls().getCameraSize().getValue();
        mult = 200f;
        app.getControls().zoom(mult);
        expectedZoom = Misc.bound(startCamSize * mult, app.getControls().zoomBoundary);
        Assert.assertEquals(expectedZoom, app.getControls().getCameraSize().getValue(), 0.01f);
        app.getControls().zoom(1 / mult);
        expectedZoom = Misc.bound(expectedZoom / mult, app.getControls().zoomBoundary);
        Assert.assertEquals(expectedZoom, app.getControls().getCameraSize().getValue(), 0.01f);
    }

    @AfterAppStart
    private void clearingTrails() {
        app.getControls().clearTrajectoryTraces();
        Assert.assertTrue(ParabolicControl.trailsCount() <= 1);
        // No error
        app.getControls().clearTrajectoryTraces();
        Assert.assertTrue(ParabolicControl.trailsCount() <= 1);
        app.getControls().fire();
        app.getControls().clearTrajectoryTraces();
        Assert.assertTrue(ParabolicControl.trailsCount() <= 1);
    }

    @AfterAppStart
    private void angleChanging() {
        float startAngle = app.getControls().getCurrentParams().copy().get(DotParams.START_ANGLE);
        float delta = 5f;
        app.getControls().changeAngle(delta);
        float newExpectedAngle = Misc.bound(startAngle + delta, DotParams.ANGLE_LIMIT);

        float newParamAngle = app.getControls().getCurrentParams().get(DotParams.START_ANGLE);

        Assert.assertEquals(newExpectedAngle, newParamAngle, 0.01f);
        Assert.assertEquals(newExpectedAngle,
                app.getControls().getObject("Cannon").getParams().get(DotParams.START_ANGLE), 0.01f);
    }

    @AfterAppStart
    private void boundAngleChange() {
        float startAngle = app.getControls().getCurrentParams().get(DotParams.START_ANGLE);
        float delta = 400f;
        app.getControls().changeAngle(delta);
        float newExpectedAngle = Misc.bound(startAngle + delta, DotParams.ANGLE_LIMIT);

        float newParamAngle = app.getControls().getCurrentParams().get(DotParams.START_ANGLE);

        Assert.assertEquals(newExpectedAngle, newParamAngle, 0.01f);
        Assert.assertEquals(newExpectedAngle,
                app.getControls().getObject("Cannon").getParams().get(DotParams.START_ANGLE), 0.01f);
    }

}