package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        int[] arr1 = {5, 3, 8, 1, 9, 2};
        int[] arr2 = arr1.clone();
        int[] arr3 = arr1.clone();

        System.out.println("Original array: " + Arrays.toString(arr1));

        // 🔹 MergeSort
        MergeSort.sort(arr1);
        System.out.println("After MergeSort: " + Arrays.toString(arr1));

        // 🔹 QuickSort
        QuickSort.sort(arr2);
        System.out.println("After QuickSort: " + Arrays.toString(arr2));

        // 🔹 Deterministic Select (find median)
        int k = arr3.length / 2;
        int kthSmallest = DeterministicSelect.select(arr3.clone(), k);
        System.out.println("k = " + k + "th smallest element (Deterministic Select): " + kthSmallest);

        // 🔹 Closest Pair of Points in 2D
        List<Point2D> pts = List.of(
                new Point2D(0, 0),
                new Point2D(3, 4),
                new Point2D(5, 1),
                new Point2D(1, 1),
                new Point2D(7, 2)
        );
        double d = ClosestPair2D.solve(pts);
        System.out.println("Closest pair distance = " + d);
    }
}