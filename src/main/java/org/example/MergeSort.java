package org.example;

public class MergeSort {
    private static final int INSERTION_CUTOFF = 32;

    public static void sort(int[] arr) {
        sort(arr, new Metrics());
    }

    public static void sort(int[] arr, Metrics m) {
        m.startTimer();
        int[] buf = new int[arr.length];
        m.enter();
        sort(arr, 0, arr.length, buf, m);
        m.exit();
        m.stopTimer();
    }

    private static void sort(int[] a, int lo, int hi, int[] buf, Metrics m) {
        int n = hi - lo;
        if (n <= 1) return;

        if (n <= INSERTION_CUTOFF) {
            insertion(a, lo, hi, m);
            return;
        }

        int mid = lo + n / 2;
        m.enter(); sort(a, lo,  mid, buf, m); m.exit();
        m.enter(); sort(a, mid, hi,  buf, m); m.exit();
        merge(a, lo, mid, hi, buf, m);
    }

    private static void insertion(int[] a, int lo, int hi, Metrics m) {
        for (int i = lo + 1; i < hi; i++) {
            int key = a[i]; m.addMove(); // read-to-temp proxy
            int j = i - 1;
            while (j >= lo && m.lt(key, a[j])) { // count compare
                m.write(a, j + 1, a[j]);         // shift
                j--;
            }
            m.write(a, j + 1, key);
        }
    }

    private static void merge(int[] a, int lo, int mid, int hi, int[] buf, Metrics m) {
        int i = lo, j = mid, k = 0;
        while (i < mid && j < hi) {
            if (m.leq(a[i], a[j])) { buf[k++] = a[i++]; m.addMove(); }
            else                    { buf[k++] = a[j++]; m.addMove(); }
        }
        while (i < mid) { buf[k++] = a[i++]; m.addMove(); }
        while (j < hi)  { buf[k++] = a[j++]; m.addMove(); }
        for (int t = 0; t < k; t++) { m.write(a, lo + t, buf[t]); }
    }
}