package org.example;

import java.util.Random;

public class QuickSort {
    private static final Random RNG = new Random(42);

    public static void sort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }
    private static void quickSort(int[] arr, int lo, int hi) {
        while (lo < hi) {
            int pivotIndex = lo + RNG.nextInt(hi - lo + 1);
            int pivot = arr[pivotIndex];
            swap(arr, pivotIndex, hi);

            int p = partition(arr, lo, hi, pivot);

            if (p - lo < hi - p) {
                quickSort(arr, lo, p - 1);
                lo = p + 1;
            } else {
                quickSort(arr, p + 1, hi);
                hi = p - 1;
            }
        }
    }

    private static int partition(int[] arr, int lo, int hi, int pivot) {
        int i = lo - 1;
        for (int j = lo; j < hi; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, hi);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int t = arr[i]; arr[i] = arr[j]; arr[j] = t;
    }
}