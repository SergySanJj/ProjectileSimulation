package com.sergeiyarema.simulation;

import com.jme3.math.Vector3f;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.sergeiyarema.simulation.DotParams.*;


public class DotParamsTest {
    private static final DotParams dotParamsOriginal =
            new DotParams(new Vector3f(1f, 1f, 1f), 45.f, 10f, 9f);

    @Test
    public void copy() {
        DotParams dotParamsCopy = dotParamsOriginal.copy();

        Assert.assertNotEquals(dotParamsCopy, dotParamsOriginal); // Really creates new object
        Assert.assertEquals(0, dotParamsCopy.compareTo(dotParamsOriginal)); // Same data
    }

    @Test
    public void getStartPos() {
        Assert.assertEquals(dotParamsOriginal.getStartPos(), new Vector3f(1f, 1f, 1f));
    }

    @Test
    public void setStartPos() {
        DotParams dotParamsCopy = dotParamsOriginal.copy();
        Vector3f newPos = new Vector3f(10f, 10f, 10f);
        dotParamsCopy.setStartPos(newPos.clone());
        Assert.assertEquals(dotParamsCopy.getStartPos(), newPos);
    }

    @Test
    public void get() {
        Assert.assertEquals(dotParamsOriginal.get(START_ANGLE), 45.f, 0.001);
        Assert.assertEquals(dotParamsOriginal.get(START_SPEED), 10.f, 0.001);
        Assert.assertEquals(dotParamsOriginal.get(GRAVITY), 9f, 0.001);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void getNotExistingParameter() {
        exception.expect(NullPointerException.class);
        dotParamsOriginal.get("NOT_EXISTING");
    }

    @Test
    public void getChangeable() {
        Assert.assertEquals(dotParamsOriginal.getChangeable(START_ANGLE).getValue(), 45.f, 0.001);
        Assert.assertEquals(dotParamsOriginal.getChangeable(START_SPEED).getValue(), 10.f, 0.001);
        Assert.assertEquals(dotParamsOriginal.getChangeable(GRAVITY).getValue(), 9f, 0.001);
    }

    @Test
    public void getNotExistingChangeable() {
        exception.expect(NullPointerException.class);
        float not_existing = dotParamsOriginal.getChangeable("NOT_EXISTING").getValue();
    }

    @Test
    public void set() {
        DotParams dotParamsCopy = dotParamsOriginal.copy();
        dotParamsCopy.set(START_SPEED, -1f);
        dotParamsCopy.set(START_ANGLE, -1f);
        dotParamsCopy.set(GRAVITY, -1f);

        Assert.assertEquals(dotParamsCopy.getChangeable(START_ANGLE).getValue(), -1f, 0.001);
        Assert.assertEquals(dotParamsCopy.getChangeable(START_SPEED).getValue(), -1f, 0.001);
        Assert.assertEquals(dotParamsCopy.getChangeable(GRAVITY).getValue(), -1f, 0.001);
    }

    @Test
    public void compareTo() {
        DotParams dotParamsA = dotParamsOriginal.copy();
        DotParams dotParamsB = dotParamsOriginal.copy();

        DotParams different =
                new DotParams(new Vector3f(0f, 0f, 0f), 45.f, 10f, 9f);

        Assert.assertNotEquals(0, different.compareTo(dotParamsA));
        Assert.assertEquals(0, different.compareTo(different));

        Assert.assertEquals(0, dotParamsA.compareTo(dotParamsA));
        Assert.assertEquals(0, dotParamsA.compareTo(dotParamsB));
        Assert.assertEquals(0, dotParamsB.compareTo(dotParamsA));
    }
}