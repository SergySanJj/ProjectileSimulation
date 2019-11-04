package com.sergeiyarema.simulation;

import com.jme3.math.Vector2f;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChangeableByDeltaTest {

    @Test
    public void changeBy() {
        ChangeableByDelta changeable = new ChangeableByDelta();
        changeable.setValue(100f);
        Assert.assertEquals(100f, changeable.getValue(), 0.01);
        changeable.setBoundary(new Vector2f(0, 10f));
        changeable.setValue(100f);
        Assert.assertEquals(10f, changeable.getValue(), 0.01);
        changeable.setValue(-100f);
        Assert.assertEquals(0f, changeable.getValue(), 0.01);


    }
}