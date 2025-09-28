package org.example;

import java.util.Arrays;
import java.util.Random;

public class DeterministicSelect {
    /**
     * Returns the k-th smallest (0-based) element from the array.
     * Does NOT fully sort the array.
     */
    public static int select(int[] a, int k) {
        if (a == null || a.length == 0) throw new IllegalArgumentException("empty array");
        if (k < 0 || k >= a.length) throw new IllegalArgumentException("k out of range");
        return selectRange(a, 0, a.length - 1, k);
    }

    // Iterative selection within a[l..r]
    private static int selectRange(int[] a, int l, int r, int k) {
        while (true) {
            if (l == r) return a[l];

            int pivotIndex = medianOfMediansIndex(a, l, r);
            int p = partition(a, l, r, pivotIndex);

            if (k == p) return a[p];
            else if (k < p) r = p - 1;
            else l = p + 1;
        }
    }

    /** Partition a[l..r] around a[pivotIndex]. Returns final index of the pivot. */
    private static int partition(int[] a, int l, int r, int pivotIndex) {
        int pivot = a[pivotIndex];
        swap(a, pivotIndex, r);
        int store = l;
        for (int i = l; i < r; i++) {
            if (a[i] < pivot) {
                swap(a, store, i);
                store++;
            }
        }
        swap(a, store, r);
        return store;
    }

    /**
     * Deterministically pick a good pivot index using median-of-medians (groups of 5).
     * Returns an index within [l..r].
     */
    private static int medianOfMediansIndex(int[] a, int l, int r) {
        int n = r - l + 1;
        if (n <= 5) {
            // sort the tiny block in-place and return median index
            insertionSort(a, l, r);
            return (l + r) / 2;
        }

        // Collect medians of each 5-group
        int numGroups = (n + 4) / 5;
        int[] medians = new int[numGroups];

        for (int g = 0; g < numGroups; g++) {
            int start = l + g * 5;
            int end = Math.min(start + 4, r);
            insertionSort(a, start, end);
            medians[g] = a[(start + end) / 2];
        }

        // Recursively find the median value of the medians array (OK to use deterministic select again)
        int momValue = selectValue(medians, 0, numGroups - 1, numGroups / 2);

        // Return ANY index in [l..r] that has value == momValue
        for (int i = l; i <= r; i++) {
            if (a[i] == momValue) return i;
        }
        // Fallback (shouldn't happen unless extreme duplicates with all not equal?):
        return l;
    }

    // Deterministic select on a VALUE array (helper for medians)
    private static int selectValue(int[] a, int l, int r, int k) {
        while (true) {
            if (l == r) return a[l];
            int pivotIndex = medianOfMediansIndexValue(a, l, r);
            int p = partitionValue(a, l, r, pivotIndex);
            if (k == p) return a[p];
            else if (k < p) r = p - 1;
            else l = p + 1;
        }
    }

    // Median-of-medians for the VALUE helper
    private static int medianOfMediansIndexValue(int[] a, int l, int r) {
        int n = r - l + 1;
        if (n <= 5) {
            insertionSortValue(a, l, r);
            return (l + r) / 2;
        }
        int numGroups = (n + 4) / 5;
        for (int g = 0; g < numGroups; g++) {
            int s = l + g * 5;
            int e = Math.min(s + 4, r);
            insertionSortValue(a, s, e);
            swap(a, l + g, (s + e) / 2);
        }
        int midIdx = l + numGroups / 2;
        return selectIndexValue(a, l, l + numGroups - 1, midIdx);
    }

    // Returns INDEX (within [l..r]) of the k-th smallest (where k is an absolute index)
    private static int selectIndexValue(int[] a, int l, int r, int kAbs) {
        int k = kAbs; // already absolute in this helper
        while (true) {
            if (l == r) return l;
            int pivotIndex = medianOfMediansIndexValue(a, l, r);
            int p = partitionValue(a, l, r, pivotIndex);
            if (k == p) return p;
            else if (k < p) r = p - 1;
            else l = p + 1;
        }
    }

    // Partition for value helper
    private static int partitionValue(int[] a, int l, int r, int pivotIndex) {
        int pivot = a[pivotIndex];
        swap(a, pivotIndex, r);
        int store = l;
        for (int i = l; i < r; i++) {
            if (a[i] < pivot) { swap(a, store, i); store++; }
        }
        swap(a, store, r);
        return store;
    }

    private static void insertionSort(int[] a, int l, int r) {
        for (int i = l + 1; i <= r; i++) {
            int key = a[i], j = i - 1;
            while (j >= l && a[j] > key) { a[j + 1] = a[j]; j--; }
            a[j + 1] = key;
        }
    }

    private static void insertionSortValue(int[] a, int l, int r) {
        for (int i = l + 1; i <= r; i++) {
            int key = a[i], j = i - 1;
            while (j >= l && a[j] > key) { a[j + 1] = a[j]; j--; }
            a[j + 1] = key;
        }
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }
}