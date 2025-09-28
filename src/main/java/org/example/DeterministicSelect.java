package org.example;

import java.util.Arrays;
import java.util.Random;

public class DeterministicSelect {
    public static int select(int[] a, int k) {
        if (a == null || a.length == 0) throw new IllegalArgumentException("empty array");
        if (k < 0 || k >= a.length) throw new IllegalArgumentException("k out of range");
        return selectRange(a, 0, a.length - 1, k);
    }

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

    private static int medianOfMediansIndex(int[] a, int l, int r) {
        int n = r - l + 1;
        if (n <= 5) {
            insertionSort(a, l, r);
            return (l + r) / 2;
        }

        int numGroups = (n + 4) / 5;
        int[] medians = new int[numGroups];

        for (int g = 0; g < numGroups; g++) {
            int start = l + g * 5;
            int end = Math.min(start + 4, r);
            insertionSort(a, start, end);
            medians[g] = a[(start + end) / 2];
        }

        int momValue = selectValue(medians, 0, numGroups - 1, numGroups / 2);

        for (int i = l; i <= r; i++) {
            if (a[i] == momValue) return i;
        }
        return l;
    }

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

    private static int selectIndexValue(int[] a, int l, int r, int kAbs) {
        int k = kAbs;
        while (true) {
            if (l == r) return l;
            int pivotIndex = medianOfMediansIndexValue(a, l, r);
            int p = partitionValue(a, l, r, pivotIndex);
            if (k == p) return p;
            else if (k < p) r = p - 1;
            else l = p + 1;
        }
    }

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