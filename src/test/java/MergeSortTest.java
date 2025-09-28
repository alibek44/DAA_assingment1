
import org.example.MergeSort;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {
    @Test
    void smallArray() {
        int[] arr = {5, 3, 8, 1};
        MergeSort.sort(arr);
        assertArrayEquals(new int[]{1, 3, 5, 8}, arr);
    }

    @Test
    void randomArray() {
        Random rnd = new Random(1);
        int[] arr = rnd.ints(1000, -1000, 1000).toArray();
        int[] expected = arr.clone();
        Arrays.sort(expected);
        MergeSort.sort(arr);
        assertArrayEquals(expected, arr);
    }
}