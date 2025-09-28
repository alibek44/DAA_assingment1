package org.example;
import java.util.Arrays;
public class MergeSort {

    private static final int CUTOFF = 16;

    public static void sort(int[] a) {
        if (a == null || a.length < 2) return;
        int[] aux = new int[a.length];           // reusable buffer
        sort(a, aux, 0, a.length - 1);
    }

    private static void sort(int[] a, int[] aux, int lo, int hi) {
        if (hi - lo + 1 <= CUTOFF) {
            insertionSort(a, lo, hi);
            return;
        }

        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);

        if (a[mid] <= a[mid + 1]) return;

        mergeLinear(a, aux, lo, mid, hi);
    }

    private static void mergeLinear(int[] a, int[] aux, int lo, int mid, int hi) {
        int i = lo, j = mid + 1, k = lo;

        while (i <= mid && j <= hi) {
            if (a[i] <= a[j]) aux[k++] = a[i++];
            else              aux[k++] = a[j++];
        }
        while (i <= mid) aux[k++] = a[i++];
        while (j <= hi)  aux[k++] = a[j++];

        System.arraycopy(aux, lo, a, lo, hi - lo + 1);
    }

    private static void insertionSort(int[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= lo && a[j] > key) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
    }

}