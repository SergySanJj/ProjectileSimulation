package com.sergeiyarema.misc;

import java.util.logging.Level;

public class Logger {
    public enum MODE {INFO, DEBUG, WARNING, ERROR, ALWAYS}

    private static MODE currentMode = MODE.INFO;

    private Logger() {
    }

    public static boolean log(String message, MODE mode) {
        if (mode.compareTo(currentMode) >= 0)
            return true;
        java.util.logging.Logger.getLogger("").log(Level.ALL, "    {0}", message);
        return false;
    }

    public static boolean log(String message) {
        return log(message, MODE.DEBUG);
    }

    public static void setMinLevel(MODE mode) {
        currentMode = mode;
    }

    public static MODE getCurrentMode() {
        return currentMode;
    }
}
