package com.sergeiyarema.misc;

import com.jme3.math.Vector2f;

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

    public static float bound(float x, Vector2f bounder) {
        if (bounder==null)
            return x;
        return bound(x, bounder.x, bounder.y);
    }
}
