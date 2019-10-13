import com.sun.javafx.geom.Vec2f;
import org.junit.Test;

import static org.junit.Assert.*;

public class TrajectoryTest {

    @Test
    public void getCoords() {
        DotParams dotParams = new DotParams(new Vec2f(0.f, 0.f), 45.f, 10.f);
        assertEquals(new Vec2f(0.f, 0.f), Trajectory.getCoords(dotParams, 0.f));
    }
}