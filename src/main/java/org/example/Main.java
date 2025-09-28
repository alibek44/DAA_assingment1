package org.example;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int n = 1_000;
        int[] base = new Random(7).ints(n, -1_000_000, 1_000_000).toArray();

        CsvWriter csv = new CsvWriter("out/metrics.csv");

        // ---- MergeSort
        int[] a1 = base.clone();
        Metrics m1 = new Metrics();
        MergeSort.sort(a1, m1);
        writeRow(csv, "MergeSort", n, m1, Arrays.equals(a1, sorted(base)));

        // ---- QuickSort
        int[] a2 = base.clone();
        Metrics m2 = new Metrics();
        QuickSort.sort(a2, m2);
        writeRow(csv, "QuickSort", n, m2, Arrays.equals(a2, sorted(base)));

        System.out.println("Done. CSV at out/metrics.csv");
    }

    private static void writeRow(CsvWriter csv, String algo, int n, Metrics m, boolean ok) {
        var row = new LinkedHashMap<String, String>();
        row.put("algo", algo);
        row.put("n", Integer.toString(n));
        row.put("elapsed_ns", Long.toString(m.elapsedNanos()));
        row.put("comparisons", Long.toString(m.getComparisons()));
        row.put("moves", Long.toString(m.getMoves()));
        row.put("max_depth", Integer.toString(m.maxDepth()));
        row.put("ok", Boolean.toString(ok));
        csv.appendRow(row);
    }

    private static int[] sorted(int[] x) {
        int[] c = x.clone();
        Arrays.sort(c);
        return c;
    }
}