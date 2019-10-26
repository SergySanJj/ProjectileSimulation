import com.sergeiyarema.simulation.DotParams;
import com.sergeiyarema.simulation.Trajectory;
import com.jme3.math.Vector2f;
import org.junit.Test;

import static org.junit.Assert.*;

public class TrajectoryTest {
    public static boolean assertEquals(Vector2f a, Vector2f b) {
        float delta = Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
        return (delta < 0.001f);
    }

    @Test
    public void getCoords() {
        DotParams dotParams = new DotParams(new Vector2f(0.f, 0.f), 45.f, 10f);
        assertEquals(new Vector2f(0.f, 0.f), Trajectory.getCoords(dotParams, 0.f));
        assertEquals(new Vector2f(10.196f, -0.003f), Trajectory.getCoords(dotParams, 1.442f));

        float g = 9.80665f;

        assertEquals(new Vector2f(0.f, 0.f), Trajectory.getCoords(dotParams, 0.f, g));
        assertEquals(new Vector2f(10.196f, -0.003f), Trajectory.getCoords(dotParams, 1.442f, g));
    }
}