

import org.example.QuickSort;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {
    @Test
    void simpleCase() {
        int[] arr = {4, 1, 3, 2};
        QuickSort.sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4}, arr);
    }

    @Test
    void largeRandom() {
        Random rnd = new Random(2);
        int[] arr = rnd.ints(5000, -10000, 10000).toArray();
        int[] expected = arr.clone();
        Arrays.sort(expected);
        QuickSort.sort(arr);
        assertArrayEquals(expected, arr);
    }
}