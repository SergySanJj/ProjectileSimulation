package com.sergeiyarema.simulation;

import com.jme3.scene.Node;
import org.junit.Assert;
import org.junit.Test;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.logging.Level;

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

    @Test
    public void testChain() {
        java.util.logging.Logger.getLogger("").setLevel(Level.WARNING);

        processAnnotated(BeforeAppStart.class);
        Thread appThread = new Thread(() -> app.start());
        appThread.start();
        //app.start();
        while (!app.isReady()){
        }
        processAnnotated(AfterAppStart.class);

        appThread.interrupt();
    }

    private void processAnnotated(Class classRef) {
        int testsRun = 0;
        for (Method method : ApplicationTest.class.getDeclaredMethods()) {
            if (method.isAnnotationPresent(classRef)) {
                try {
                    method.invoke(this);
                } catch (Exception e) {
                    System.out.println("Assert in " + method.getClass().getName() + "." + method.getName() + "()");
                    e.printStackTrace();
                    Assert.assertNotNull(null);
                }
                testsRun++;
            }
        }
        System.out.println("Tests count " + testsRun);
    }

    // BEFORE
    @BeforeAppStart
    private void globalAssetsTestPre() {
        Assert.assertNull(GlobalAssets.manager());
    }

    // AFTER
    @AfterAppStart
    private void globalAssetsTestAfter() {
        Assert.assertNotNull(GlobalAssets.manager());
    }

    @AfterAppStart
    private void floorTestAfter() {
        Node node = new Node();
        Floor floor1 = new Floor(node, 0f);
        Assert.assertEquals(0f, floor1.getTopCoordinate(), 0.001f);
    }
}