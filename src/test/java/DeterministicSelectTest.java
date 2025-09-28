import org.example.DeterministicSelect;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DeterministicSelectTest {
    @Test
    void medianCheck() {
        int[] arr = {9, 1, 5, 3, 7};
        int k = arr.length / 2;
        int result = DeterministicSelect.select(arr.clone(), k);
        Arrays.sort(arr);
        assertEquals(arr[k], result);
    }

    @Test
    void randomTests() {
        Random rnd = new Random(3);
        for (int n = 10; n < 100; n++) {
            int[] arr = rnd.ints(n, -50, 50).toArray();
            int k = rnd.nextInt(n);
            int result = DeterministicSelect.select(arr.clone(), k);
            int[] expected = arr.clone();
            Arrays.sort(expected);
            assertEquals(expected[k], result);
        }
    }
}