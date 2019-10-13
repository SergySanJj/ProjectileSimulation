import com.sun.javafx.geom.Vec2f;

class DotParams {
    private DotParams() {

    }

    public DotParams(Vec2f startPos, float startAngle, float startSpeed) {
        this.startPos = startPos;
        this.startAngle = startAngle;
        this.startSpeed = startSpeed;
    }

    private Vec2f startPos;
    private float startAngle;
    private float startSpeed;

    public Vec2f getStartPos() {
        return startPos;
    }

    public void setStartPos(Vec2f startPos) {
        this.startPos = startPos;
    }

    public float getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
    }

    public float getStartSpeed() {
        return startSpeed;
    }

    public void setStartSpeed(float startSpeed) {
        this.startSpeed = startSpeed;
    }
}
