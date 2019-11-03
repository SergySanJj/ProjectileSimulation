package com.sergeiyarema.misc;

public class Misc {
    private Misc() {
    }

    public static float bound(float x, float a, float b) {
        if (a > b) {
            float c = a;
            a = b;
            b = c;
        }

        if (x >= a && x <= b)
            return x;
        else if (x < a)
            return a;
        else return b;
    }
}
