package org.example;

import java.util.Random;

public class QuickSort {
    private static final Random RNG = new Random(42);

    public static void sort(int[] a) { sort(a, new Metrics()); }

    public static void sort(int[] a, Metrics m) {
        m.startTimer();
        m.enter();
        qsort(a, 0, a.length - 1, m);
        m.exit();
        m.stopTimer();
    }

    private static void qsort(int[] a, int lo, int hi, Metrics m) {
        while (lo < hi) {
            int pIdx = lo + RNG.nextInt(hi - lo + 1);
            int pivot = a[pIdx];
            m.addMove(); // read pivot proxy
            m.swap(a, pIdx, hi);

            int p = partition(a, lo, hi, pivot, m);

            // recurse into smaller side
            if (p - 1 - lo < hi - (p + 1)) {
                m.enter(); qsort(a, lo, p - 1, m); m.exit();
                lo = p + 1; // tail recursion elimination
            } else {
                m.enter(); qsort(a, p + 1, hi, m); m.exit();
                hi = p - 1;
            }
        }
    }

    private static int partition(int[] a, int lo, int hi, int pivot, Metrics m) {
        int i = lo - 1;
        for (int j = lo; j < hi; j++) {
            if (m.leq(a[j], pivot)) {
                i++;
                m.swap(a, i, j);
            }
        }
        m.swap(a, i + 1, hi);
        return i + 1;
    }
}