package com.sergeiyarema.misc;

import org.junit.Assert;
import org.junit.Test;

public class LoggerTest {

    @Test
    public void log() {
        String testMessage = "test message";
        Logger.setMinLevel(Logger.MODE.INFO);
        Assert.assertTrue(Logger.log(testMessage, Logger.MODE.INFO));
        Assert.assertTrue(Logger.log(testMessage, Logger.MODE.DEBUG));
        Assert.assertTrue(Logger.log(testMessage, Logger.MODE.WARNING));
        Assert.assertTrue(Logger.log(testMessage, Logger.MODE.ERROR));

        Logger.setMinLevel(Logger.MODE.DEBUG);

        Assert.assertFalse(Logger.log(testMessage, Logger.MODE.INFO));

        Assert.assertTrue(Logger.log(testMessage, Logger.MODE.DEBUG));
        Assert.assertTrue(Logger.log(testMessage, Logger.MODE.WARNING));
        Assert.assertTrue(Logger.log(testMessage, Logger.MODE.ERROR));

        Logger.setMinLevel(Logger.MODE.ALWAYS);
        Assert.assertFalse(Logger.log(testMessage, Logger.MODE.INFO));
        Assert.assertFalse(Logger.log(testMessage, Logger.MODE.DEBUG));
        Assert.assertFalse(Logger.log(testMessage, Logger.MODE.WARNING));
        Assert.assertFalse(Logger.log(testMessage, Logger.MODE.ERROR));
    }

    @Test
    public void testLog() {
        String testMessage = "test message";

        Logger.setMinLevel(Logger.MODE.INFO);
        Assert.assertTrue(Logger.log(testMessage));

        Logger.setMinLevel(Logger.MODE.DEBUG);
        Assert.assertTrue(Logger.log(testMessage));

        Logger.setMinLevel(Logger.MODE.WARNING);
        Assert.assertFalse(Logger.log(testMessage));

        Logger.setMinLevel(Logger.MODE.ERROR);
        Assert.assertFalse(Logger.log(testMessage));

        Logger.setMinLevel(Logger.MODE.ALWAYS);
        Assert.assertFalse(Logger.log(testMessage));
    }

    @Test
    public void setMaxLevel() {
        Logger.setMinLevel(Logger.MODE.INFO);
        Assert.assertEquals(Logger.MODE.INFO, Logger.getCurrentMode());

        Logger.setMinLevel(Logger.MODE.DEBUG);
        Assert.assertEquals(Logger.MODE.DEBUG, Logger.getCurrentMode());

        Logger.setMinLevel(Logger.MODE.WARNING);
        Assert.assertEquals(Logger.MODE.WARNING, Logger.getCurrentMode());

        Logger.setMinLevel(Logger.MODE.ERROR);
        Assert.assertEquals(Logger.MODE.ERROR, Logger.getCurrentMode());

        Logger.setMinLevel(Logger.MODE.ALWAYS);
        Assert.assertEquals(Logger.MODE.ALWAYS, Logger.getCurrentMode());
    }
}