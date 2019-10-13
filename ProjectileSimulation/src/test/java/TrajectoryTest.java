import com.sun.javafx.geom.Vec2f;
import org.junit.Test;

import static org.junit.Assert.*;

public class TrajectoryTest {

    public static boolean assertEquals(Vec2f a, Vec2f b) {
        float delta = Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
        return (delta < 0.001f);
    }

    @Test
    public void getCoords() {
        DotParams dotParams = new DotParams(new Vec2f(0.f, 0.f), 45.f, 10f);
        assertEquals(new Vec2f(0.f, 0.f), Trajectory.getCoords(dotParams, 0.f));
        assertEquals(new Vec2f(10.196f, -0.003f), Trajectory.getCoords(dotParams, 1.442f));

        float g = 9.80665f;

        assertEquals(new Vec2f(0.f, 0.f), Trajectory.getCoords(dotParams, 0.f, g));
        assertEquals(new Vec2f(10.196f, -0.003f), Trajectory.getCoords(dotParams, 1.442f, g));
    }
}