import com.sergeiyarema.simulation.Trajectory;
import org.junit.Test;

import static org.junit.Assert.*;

public class TrajectoryTest {
    @Test
    public void getX() {
        assertEquals(0.0, Trajectory.getX(0.0), 1e-11);
        assertEquals(1.0, Trajectory.getX(1.0), 1e-11);
    }
}