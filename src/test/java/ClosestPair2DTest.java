
import org.example.ClosestPair2D;
import org.example.Point2D;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ClosestPair2DTest {
    @Test
    void smallSet() {
        List<Point2D> pts = List.of(
                new Point2D(0, 0),
                new Point2D(3, 4),
                new Point2D(5, 6),
                new Point2D(1, 1)
        );
        double result = ClosestPair2D.solve(pts);
        assertEquals(Math.sqrt(2), result, 1e-9);
    }

    @Test
    void randomPoints() {
        Random rnd = new Random(4);
        List<Point2D> pts = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            pts.add(new Point2D(rnd.nextDouble(), rnd.nextDouble()));
        }
        double result = ClosestPair2D.solve(pts);
        assertTrue(result >= 0);
    }
}