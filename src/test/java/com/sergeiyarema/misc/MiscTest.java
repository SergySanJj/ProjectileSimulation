package com.sergeiyarema.misc;

import com.jme3.math.Vector2f;
import org.junit.Assert;
import org.junit.Test;

public class MiscTest {
    private float eps = 0.001f;

    @Test
    public void bound() {
        float a = -10f;
        float b = 10f;

        Assert.assertEquals(5f, Misc.bound(5f, a, b), eps);
        Assert.assertEquals(-5f, Misc.bound(-5f, a, b), eps);

        Assert.assertEquals(a, Misc.bound(-200f, a, b), eps);
        Assert.assertEquals(b, Misc.bound(200f, a, b), eps);

        a = 10f;
        b = -10f;

        Assert.assertEquals(5f, Misc.bound(5f, a, b), eps);
        Assert.assertEquals(-5f, Misc.bound(-5f, a, b), eps);

        Assert.assertEquals(b, Misc.bound(-200f, a, b), eps);
        Assert.assertEquals(a, Misc.bound(200f, a, b), eps);
    }

    @Test
    public void testBound() {
        Vector2f bounder = new Vector2f(-10f, 10f);
        Assert.assertEquals(5f, Misc.bound(5f, bounder), eps);

        bounder = new Vector2f(10f, -10f);
        Assert.assertEquals(5f, Misc.bound(5f, bounder), eps);
    }
}