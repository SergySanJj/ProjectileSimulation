import com.sun.javafx.geom.Vec2f;


class Trajectory {
    public static Vec2f getCoords(DotParams params, float currentTime, float g) {
        float y0 = params.getStartPos().y;
        float x0 = params.getStartPos().x;
        float alpha = (float) Math.toRadians(params.getStartAngle());
        float vx = (float) (params.getStartSpeed() * Math.cos(alpha));
        float vy = (float) (params.getStartSpeed() * Math.sin(alpha));
        float x = x0 + currentTime * vx;
        float y = y0 + currentTime * vy - g * currentTime * currentTime / 2.f;
        return new Vec2f(x, y);
    }

    public static Vec2f getCoords(DotParams params, float currentTime) {
        float g = 9.80665f;
        return getCoords(params, currentTime, g);
    }
}
