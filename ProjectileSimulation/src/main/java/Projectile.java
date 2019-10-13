import com.sun.javafx.geom.Vec2f;

class DotParams {
    private DotParams() {

    }

    public DotParams(Vec2f startPos, float startAngle, float startSpeed) {
        this.startPos = startPos;
        this.startAngle = startAngle;
        this.startSpeed = startSpeed;
    }

    public Vec2f startPos;
    public float startAngle;
    public float startSpeed;
}

public class Projectile {
    DotParams params;

}
