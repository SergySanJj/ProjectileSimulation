package com.sergeiyarema.misc;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class MiscTest {

    @Test
    public void bound() {
        float a = -10f;
        float b = 10f;
        float eps = 0.001f;

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
}