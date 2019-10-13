import com.sun.javafx.geom.Vec2f;

class DotParams {
    public DotParams(Vec2f startPos, float startAngle, float startSpeed) {
        this.startPos = startPos;
        this.startAngle = startAngle;
        this.startSpeed = startSpeed;
    }

    public final Vec2f startPos;
    public final float startAngle;
    public final float startSpeed;
}

class Trajectory {
    public static Vec2f getCoords(DotParams params, float currentTime) {
        return new Vec2f(1.0f, 1.0f);
    }
}
