import com.sun.javafx.geom.Vec2f;

class Trajectory {
    public static Vec2f getCoords(DotParams params, float currentTime, float g) {

        return new Vec2f(0.0f, 0.0f);
    }

    public static Vec2f getCoords(DotParams params, float currentTime) {
        float g = 9.81f;
        return getCoords(params, currentTime, g);
    }
}
